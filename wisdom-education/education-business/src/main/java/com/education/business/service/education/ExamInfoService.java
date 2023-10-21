package com.education.business.service.education;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.correct.QuestionCorrect;
import com.education.business.correct.SystemQuestionCorrect;
import com.education.business.correct.TeacherQuestionCorrect;
import com.education.business.mapper.education.ExamInfoMapper;
import com.education.business.message.ExamMessage;
import com.education.business.message.QueueManager;
import com.education.business.message.config.MqExamQueueConfig;
import com.education.business.service.BaseService;
import com.education.business.session.UserSessionContext;
import com.education.business.task.param.WebSocketMessageParam;
import com.education.common.constants.CacheKey;
import com.education.common.constants.LocalQueueConstants;
import com.education.common.constants.SystemConstants;
import com.education.common.enums.BooleanEnum;
import com.education.common.enums.CommitAfterTypeEnum;
import com.education.common.enums.SocketMessageTypeEnum;
import com.education.common.exception.BusinessException;
import com.education.common.model.PageInfo;
import com.education.common.utils.DateUtils;
import com.education.common.utils.NumberUtils;
import com.education.common.utils.ObjectUtils;
import com.education.model.dto.ExamCount;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.dto.StudentExamInfoDto;
import com.education.model.dto.TestPaperQuestionDto;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.ExamQuestionAnswer;
import com.education.model.entity.ExamWrongBook;
import com.education.model.entity.StudentInfo;
import com.education.model.entity.SubjectInfo;
import com.education.model.entity.TestPaperInfo;
import com.education.model.entity.TestPaperInfoSetting;
import com.education.model.request.PageParam;
import com.education.model.request.StudentQuestionRequest;
import com.education.model.response.ExamAnalyse;
import com.education.model.response.ExamQuestionAnalyse;
import com.education.model.response.ExamQuestionGroupResponse;
import com.education.model.response.ExamRanking;
import com.education.model.response.QuestionCorrectResponse;
import com.education.model.response.QuestionGroupItemResponse;
import com.education.model.response.QuestionGroupResponse;
import com.education.model.response.TestPaperInfoReport;
import com.jfinal.kit.Kv;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 16:12
 */
@Service
public class ExamInfoService extends BaseService<ExamInfoMapper, ExamInfo> {

    @Resource
    private ExamQuestionAnswerService studentQuestionAnswerService;
    @Resource
    private QuestionInfoService questionInfoService;
    @Resource
    private TestPaperInfoService testPaperInfoService;
    @Resource
    private ExamMonitorService examMonitorService;
    @Resource
    private QueueManager queueManager;
    @Resource
    private TestPaperInfoSettingService testPaperInfoSettingService;
    @Resource
    private MqExamQueueConfig mqExamQueueConfig;


    /**
     * 后台考试列表
     * @param pageParam
     * @param studentExamInfoDto
     * @return
     */
    public PageInfo<StudentExamInfoDto> selectExamInfoList(PageParam pageParam, StudentExamInfoDto studentExamInfoDto) {
        Page<StudentExamInfoDto> page = new Page(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectExamList(page, studentExamInfoDto));
    }


    /**
     * 获取学员考试记录列表
     * @param pageParam
     * @param studentExamInfoDto
     * @return
     */
    public PageInfo<StudentExamInfoDto> selectStudentExamInfoList(PageParam pageParam, StudentExamInfoDto studentExamInfoDto) {
        studentExamInfoDto.setStudentId(UserSessionContext.getStudentId());
        Page<StudentExamInfoDto> page = new Page(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectStudentExamList(page, studentExamInfoDto));
    }

    @Transactional(rollbackFor = Exception.class)
    public QuestionCorrectResponse commitTestPaperInfoQuestion(StudentQuestionRequest studentQuestionRequest) {

        Integer testPaperInfoId = studentQuestionRequest.getTestPaperInfoId();
        // 从缓存读取试卷配置，提升并发性能
        TestPaperInfoSetting testPaperInfoSetting = testPaperInfoSettingService.selectByTestPaperInfoId(testPaperInfoId);
        this.checkAnswerPaper(testPaperInfoSetting);

        QuestionCorrectResponse questionCorrectResponse = new QuestionCorrectResponse();
        Integer studentId = UserSessionContext.getStudentId();
        studentQuestionRequest.setStudentId(studentId);
        Integer commitAfterType = CommitAfterTypeEnum.SHOW_MARK_NOW.getValue();
        if (testPaperInfoSetting != null) {
            commitAfterType = testPaperInfoSetting.getCommitAfterType();
        }
        QuestionCorrect questionCorrect = new SystemQuestionCorrect(studentQuestionRequest, getQuestionAnswerInfoByPaperId(testPaperInfoId));
        ExamInfo examInfo = questionCorrect.correctStudentQuestion();
        List<ExamQuestionAnswer> studentQuestionAnswerList = questionCorrect.getStudentQuestionAnswerList();
        List<ExamWrongBook> studentWrongBookList = questionCorrect.getStudentWrongBookList();
        // 获取系统评分之后立即返回客户端, 然后通过rabbitmq 发送考试提交消息 异步保存学员答题记录及错题信息
        if (CommitAfterTypeEnum.SHOW_MARK_AFTER_CORRECT.getValue().equals(commitAfterType)) {
            ExamMessage examMessage = new ExamMessage();
            examMessage.setStudentQuestionAnswerList(studentQuestionAnswerList);
            examMessage.setStudentWrongBookList(studentWrongBookList);
            examMessage.setExamInfo(examInfo);
            examMessage.setRoutingKey(mqExamQueueConfig.getRoutingKey());
            examMessage.setExchange(mqExamQueueConfig.getDirectExchange());
            queueManager.sendQueueMessage(examMessage);
        } else {
            // 更新试卷参考人数
            testPaperInfoService.updateExamNumber(testPaperInfoId);
            // 同步保存考试、错题、答题记录
            this.save(examInfo);
            questionCorrect.saveStudentQuestionAnswer(testPaperInfoId,
                    testPaperInfoService.getNameById(testPaperInfoId),
                    examInfo.getId(),
                    studentQuestionAnswerList, studentWrongBookList);
        }
        examMonitorService.removeStudent(studentId, testPaperInfoId); // 离开考试监控
        questionCorrectResponse.setExamTime(examInfo.getExamTime());
        questionCorrectResponse.setExamInfoId(examInfo.getId());

        // 发送批改完成消息通知
        if (questionCorrect.getQuestionNumber() == questionCorrect.getCorrectQuestionNumber()) {
            this.sendStudentMessage(studentId, testPaperInfoId);
        }
        return questionCorrectResponse;
    }


    private void sendStudentMessage(Integer studentId, Integer testPaperInfoId) {
        WebSocketMessageParam taskParam = new WebSocketMessageParam(LocalQueueConstants.SYSTEM_SOCKET_MESSAGE);
        taskParam.setSocketMessageTypeEnum(SocketMessageTypeEnum.EXAM_CORRECT);
        taskParam.setStudentId(studentId);
        taskParam.setTestPaperId(testPaperInfoId);
        taskManager.pushTask(taskParam);
    }


    /**
     * 系统评分成绩排序
     * @param examInfo
     * @param questionCorrectResponse
     */
    private void examMarkSort(ExamInfo examInfo, QuestionCorrectResponse questionCorrectResponse) {
        // redis 计算分数排行榜
        Set<ZSetOperations.TypedTuple<StudentInfo>> tuples = new HashSet<>();
        Integer systemMark = examInfo.getSystemMark();
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setId(UserSessionContext.getStudentId());
        studentInfo.setName(UserSessionContext.getStudentUserSession().getName());
        DefaultTypedTuple tuple = new DefaultTypedTuple(studentInfo, systemMark.doubleValue());
        tuples.add(tuple);
        String sortKey = CacheKey.EXAM_SORT_KEY + examInfo.getTestPaperInfoId();
        redisTemplate.opsForZSet().add(sortKey, tuples);
        // 取出排行榜1-10的学员
        Set<StudentInfo> studentScore = redisTemplate.opsForZSet().reverseRange(sortKey, 1, 10);
        questionCorrectResponse.setStudentInfoSet(studentScore);
        questionCorrectResponse.setStudentMark(systemMark); // 返回考试分数
    }

    /**
     * 从缓存获取试卷试题答案 (开考的时候已经将试卷试题进行缓存)
     * @param testPaperInfoId
     * @return
     */
    public Map<Integer, String> getQuestionAnswerInfoByPaperId(Integer testPaperInfoId) {
        List<TestPaperQuestionDto> testPaperQuestionDtoList = testPaperInfoService.selectPaperQuestionListByCache(testPaperInfoId);
        Map<Integer, String> questionAnswerInfo = new HashMap<>();
        testPaperQuestionDtoList.forEach(questionItem -> {
            questionAnswerInfo.put(questionItem.getQuestionInfoId(), questionItem.getAnswer());
        });
        return questionAnswerInfo;
    }

    /**
     * 获取考试试题答案
     * @param studentId
     * @param examInfoId
     * @return
     */
    public QuestionGroupResponse selectExamQuestionAnswer(Integer studentId, Integer examInfoId) {
        StudentExamInfoDto studentExamInfoDto = this.getExamInfoById(examInfoId);
        List<QuestionInfoAnswer> examQuestionAnswerList = studentQuestionAnswerService
                .getQuestionAnswerByExamInfoId(studentId, examInfoId);
        List<QuestionGroupItemResponse> list = questionInfoService.groupQuestion(examQuestionAnswerList);
        ExamQuestionGroupResponse examQuestionResponse = new ExamQuestionGroupResponse();
        examQuestionResponse.setQuestionGroupItemResponseList(list);
        examQuestionResponse.setTotalQuestion(list.size());
        examQuestionResponse.setStudentExamInfoDto(studentExamInfoDto);
        return examQuestionResponse;
    }

    /**
     * 获取考试详情
     * @param examInfoId
     * @return
     */
    public StudentExamInfoDto getExamInfoById(Integer examInfoId) {
        return baseMapper.selectById(examInfoId);
    }

    /**
     * 批改学员试卷
     * @param studentQuestionRequest
     */
    @Transactional
    public void correctStudentExam(StudentQuestionRequest studentQuestionRequest) {
        ExamInfo examInfo = super.getById(studentQuestionRequest.getExamInfoId());
        Assert.notNull(examInfo, () -> new BusinessException("考试记录不存在或已被删除"));
        if (BooleanEnum.YES.getCode().equals(examInfo.getCorrectFlag())) {
            throw new BusinessException("试卷已被批改");
        }
        Integer studentId = studentQuestionRequest.getStudentId();

        // 先删除原先的主观题答题记录
        studentQuestionAnswerService.deleteByExamInfoId(studentId, examInfo.getId());
        studentQuestionRequest.setTestPaperInfoId(examInfo.getTestPaperInfoId());

        QuestionCorrect questionCorrect = new TeacherQuestionCorrect(studentQuestionRequest, examInfo,
                getQuestionAnswerInfoByPaperId(studentQuestionRequest.getTestPaperInfoId()));
        examInfo = questionCorrect.correctStudentQuestion();
        examInfo.setAdminId(UserSessionContext.getAdminUserId());
        super.updateById(examInfo);

        List<ExamQuestionAnswer> studentQuestionAnswerList = questionCorrect.getObjectiveQuestionAnswerList();
        List<ExamWrongBook> studentWrongBookList = questionCorrect.getStudentWrongBookList();
        Date now = new Date();
        studentQuestionAnswerList.forEach(item -> item.setCreateDate(now));
        // 重新保存主观题答题记录
        // 保存后台教师指定错题
        questionCorrect.saveStudentQuestionAnswer(examInfo.getTestPaperInfoId(),
                testPaperInfoService.getNameById(examInfo.getTestPaperInfoId()),
                examInfo.getId(),
                studentQuestionAnswerList, studentWrongBookList);
        // 发送试卷批改完成通知
        this.sendStudentMessage(studentId, examInfo.getTestPaperInfoId());
    }

    public PageInfo<TestPaperInfoReport> getExamAnalyseListPage(PageParam pageParam, TestPaperInfo testPaperInfo) {
        Page<TestPaperInfoReport> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectExamReportList(page, testPaperInfo));
    }

    public ExamAnalyse getExamAnalyseByPaperId(Integer testPaperInfoId) {
        TestPaperInfo testPaperInfo = testPaperInfoService.getById(testPaperInfoId);
        Integer mark = testPaperInfo.getMark();
        double passMark = NumberUtils.doubleToBigDecimal(mark * SystemConstants.PASS_MARK_RATE);
        double niceMark = NumberUtils.doubleToBigDecimal(mark * SystemConstants.NICE_MARK_RATE);
        Kv params = Kv.create().set("testPaperInfoId", testPaperInfoId).set("passMark", passMark)
                .set("niceMark", niceMark)
                .set("mark", mark);
        ExamAnalyse examAnalyse = baseMapper.selectExamInfoDetail(params);
        examAnalyse.setPassExamMark(passMark);
        examAnalyse.setNiceExamMark(niceMark);
        examAnalyse.setExamTime(testPaperInfo.getExamTime());
        examAnalyse.setExamNumber(testPaperInfo.getExamNumber());
        if (examAnalyse.getExamNumber() != null && examAnalyse.getPassExamNumber() != null && BooleanEnum.NO.getCode().equals(examAnalyse.getExamNumber())) {
            examAnalyse.setPassRateStr(NumberUtils.getPercentRate(examAnalyse.getPassExamNumber(), examAnalyse.getExamNumber()));
        } else {
            examAnalyse.setPassRateStr("0%");
        }
        return examAnalyse;
    }


    /**
     * 获取考试排名列表
     * @param pageParam
     * @param testPaperInfoId
     * @return
     */
    public PageInfo<ExamRanking> getExamRankingList(PageParam pageParam, Integer testPaperInfoId) {
        Page<ExamRanking> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectExamListByTestPaperInfoId(page, testPaperInfoId));
    }

    /**
     * 查询学员是否已经作答过当前试卷
     * @param testPaperId
     * @return
     */
    public boolean queryHasDoTestPaper(Integer testPaperId) {
        LambdaQueryWrapper<ExamInfo> lambdaQueryWrapper = Wrappers.<ExamInfo>lambdaQuery().eq(ExamInfo::getTestPaperInfoId, testPaperId)
                .eq(ExamInfo::getStudentId, UserSessionContext.getStudentId());
        return super.selectFirst(lambdaQueryWrapper) != null;
    }

    /**
     * 获取近七天考试记录统计
     * @return
     */
    public List<ExamCount> selectExamInfoData() {
        Date now = new Date();
        String startTime = DateUtils.getDayBefore(DateUtils.getSecondDate(now), 7);
        String endTime = DateUtils.getDayBefore(DateUtils.getSecondDate(now), 1);
        // 获取近七天的开始时间和结束时间
        startTime += " 00:00:00";
        endTime += " 23:59:59";

        List<ExamCount> dataList = baseMapper.countByDateTime(startTime, endTime);
        Map<String, Integer> dataTimeMap = new HashMap<>();
        dataList.forEach(data -> {
            String day = data.getDayGroup();
            Integer examNumber = data.getExamNumber();
            dataTimeMap.put(day, examNumber);
        });

        List<String> weekDateList = DateUtils.getSectionByOneDay(8);
        // 近七天日期集合
        weekDateList.remove(weekDateList.size() - 1); // 移除最后一天，也就是当天的日期
        List<ExamCount> resultDataList = new ArrayList<>();
        weekDateList.forEach(day -> {
            ExamCount item = new ExamCount();
            item.setDayGroup(day);
            item.setExamNumber(ObjectUtils.isNotEmpty(dataTimeMap.get(day)) ? dataTimeMap.get(day) : 0);
            resultDataList.add(item);
        });

        return resultDataList;
    }

    /**
     * 获取考试记录科目
     * @return
     */
    public List<SubjectInfo> getSubjectList() {
        return baseMapper.getSubjectList();
    }

    /**
     * 开始考试
     * @param testPaperInfoId
     */
    public List<TestPaperQuestionDto> startExam(Integer testPaperInfoId) {
        TestPaperInfoSetting testPaperInfoSetting = testPaperInfoSettingService.selectByTestPaperInfoId(testPaperInfoId);
        this.checkAnswerPaper(testPaperInfoSetting);
        return testPaperInfoService.selectPaperQuestionListByCache(testPaperInfoId);
    }

    private void checkAnswerPaper(TestPaperInfoSetting testPaperInfoSetting) {
        if (testPaperInfoSetting == null) {
            return;
        }

        // 验证考试是否结束
        Date statDate = testPaperInfoSetting.getStartTime();
        Date endDate = testPaperInfoSetting.getEndTime();
        if (statDate != null && endDate != null) {
            long now = System.currentTimeMillis();
            if (statDate.getTime() > now) {
                throw new BusinessException("当前考试暂未开始");
            }
            if (endDate.getTime() < now) {
                throw new BusinessException("当前考试已结束");
            }
        }

        // 验证参考次数
        LambdaQueryWrapper<ExamInfo> queryWrapper = Wrappers.<ExamInfo>lambdaQuery()
                .eq(ExamInfo::getStudentId, UserSessionContext.getStudentId())
                .eq(ExamInfo::getTestPaperInfoId, testPaperInfoSetting.getTestPaperInfoId());
        int count = super.count(queryWrapper);
        Integer number = testPaperInfoSetting.getReferenceNumber();
        if (count >= number) {
            throw new BusinessException("您的参考次数已达上线，无法继续参加本次考试");
        }
    }

    public PageInfo<ExamQuestionAnalyse> getExamQuestionAnalysis(PageParam pageParam, Integer testPaperInfoId) {
        return null;
    }
}
