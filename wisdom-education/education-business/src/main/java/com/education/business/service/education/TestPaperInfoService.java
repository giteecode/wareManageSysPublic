package com.education.business.service.education;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.TestPaperInfoMapper;
import com.education.business.service.BaseService;
import com.education.business.session.StudentSession;
import com.education.business.session.UserSessionContext;
import com.education.common.constants.CacheKey;
import com.education.common.constants.SystemConstants;
import com.education.common.enums.QuestionTypeEnum;
import com.education.common.exception.BusinessException;
import com.education.common.model.PageInfo;
import com.education.common.template.BaseTemplate;
import com.education.common.template.EnjoyTemplate;
import com.education.common.utils.*;
import com.education.model.dto.QuestionInfoDto;
import com.education.model.dto.TestPaperInfoDto;
import com.education.model.dto.TestPaperQuestionDto;
import com.education.model.entity.QuestionInfo;
import com.education.model.entity.TestPaperInfo;
import com.education.model.entity.TestPaperQuestionInfo;
import com.education.model.request.AutoCreatePaperItem;
import com.education.model.request.AutoCreatePaperRequest;
import com.education.model.request.PageParam;
import com.education.model.request.TestPaperQuestionRequest;
import com.jfinal.kit.Kv;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/20 21:22
 */
@Service
public class TestPaperInfoService extends BaseService<TestPaperInfoMapper, TestPaperInfo> {

    @Resource
    private TestPaperQuestionInfoService testPaperQuestionInfoService;
    @Resource
    private QuestionInfoService questionInfoService;

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private TestPaperInfoMapper testPa;

    /**
     * 试卷分页列表
     * @param pageParam
     * @param testPaperInfo
     * @return
     */
    public PageInfo<TestPaperInfoDto> selectPageList(PageParam pageParam, TestPaperInfo testPaperInfo) {
        Page<TestPaperInfoDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        StudentSession studentInfo = UserSessionContext.getStudentUserSession();
        if (studentInfo != null) {
            testPaperInfo.setGradeInfoId(studentInfo.getGradeInfoId());
        }
        return selectPage(baseMapper.selectPageList(page, testPaperInfo));
    }

    /**
     * 获取试卷试题
     * 先读缓存、缓存为null 再查数据库
     * @param testPaperQuestionRequest
     * @return
     */
    private List<TestPaperQuestionDto> selectPaperQuestionListByCache(TestPaperQuestionRequest testPaperQuestionRequest) {
        Integer testPaperInfoId = testPaperQuestionRequest.getTestPaperInfoId();
        List<TestPaperQuestionDto> list = cacheBean.get(CacheKey.TEST_PAPER_INFO_CACHE, testPaperInfoId);
        if (list == null) {
            RLock lock = redissonClient.getLock(CacheKey.TEST_PAPER_INFO_CACHE + testPaperInfoId);
            try {
                lock.lock();
                list = cacheBean.get(CacheKey.TEST_PAPER_INFO_CACHE, testPaperInfoId);
                if (list == null) {
                    list = this.selectPaperQuestionList(testPaperQuestionRequest);
                    cacheBean.putValue(CacheKey.TEST_PAPER_INFO_CACHE, testPaperInfoId, list);
                }
            } finally {
                lock.unlock();
            }
        }
        return list;
    }


    /**
     * 从缓存中获取试卷试题
     * @param testPaperInfoId
     * @return
     */
    public List<TestPaperQuestionDto> selectPaperQuestionListByCache(Integer testPaperInfoId) {
        TestPaperQuestionRequest paperQuestionRequest = new TestPaperQuestionRequest();
        paperQuestionRequest.setTestPaperInfoId(testPaperInfoId);
        return selectPaperQuestionListByCache(paperQuestionRequest);
    }

    /**
     * 分页获取试卷试题列表
     * @param pageParam
     * @param testPaperQuestionRequest
     * @return
     */
    public PageInfo<TestPaperQuestionDto> selectPaperQuestionList(PageParam pageParam, TestPaperQuestionRequest testPaperQuestionRequest) {
        Page<TestPaperQuestionDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectPaperQuestionList(page, testPaperQuestionRequest));
    }

    /**
     * 获取试卷试题列表
     * @param testPaperQuestionRequest
     * @return
     */
    public List<TestPaperQuestionDto> selectPaperQuestionList(TestPaperQuestionRequest testPaperQuestionRequest) {
        return baseMapper.selectPaperQuestionList(testPaperQuestionRequest);
    }


    /**
     * 修改试卷试题分数或者排序
     * @param testPaperQuestionDto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePaperQuestionMarkOrSort(TestPaperQuestionDto testPaperQuestionDto) {
        Integer mark = testPaperQuestionDto.getMark();
        Integer testPaperInfoId = testPaperQuestionDto.getTestPaperInfoId();
        if (mark != null) {
            TestPaperQuestionInfo testPaperQuestionInfo = testPaperQuestionInfoService.getById(testPaperQuestionDto.getId());
            // 原试卷试题分数
            Integer oldPaperQuestionMark = testPaperQuestionInfo.getMark();
            if (!mark.equals(oldPaperQuestionMark)) {
                // 更新试卷分数
                baseMapper.updateMark(testPaperInfoId, mark, oldPaperQuestionMark);
            }
        }
        // 更新试卷试题分数
        LambdaUpdateWrapper<TestPaperQuestionInfo> updateWrapper = Wrappers.lambdaUpdate(TestPaperQuestionInfo.class)
                .eq(TestPaperQuestionInfo::getQuestionInfoId, testPaperQuestionDto.getQuestionInfoId())
                .eq(TestPaperQuestionInfo::getTestPaperInfoId, testPaperInfoId)
                .set(ObjectUtils.isNotEmpty(mark), TestPaperQuestionInfo::getMark, mark)
                .set(TestPaperQuestionInfo::getUpdateDate, new Date())
                .set(ObjectUtils.isNotEmpty(testPaperQuestionDto.getSort()), TestPaperQuestionInfo::getSort, testPaperQuestionDto.getSort());
        testPaperQuestionInfoService.update(updateWrapper);
        // 删除试卷试题缓存 (此处不用考虑缓存数据同步问题, 因此修改试题缓存问题不存在并发)
        this.deleteCacheByPaperId(testPaperQuestionDto.getTestPaperInfoId());
    }

    @Override
    public boolean saveOrUpdate(TestPaperInfo testPaperInfo) {
        if (testPaperInfo.getId() != null) {
            TestPaperInfo dataBaseTestPaperInfo = super.getById(testPaperInfo.getId());
            if (dataBaseTestPaperInfo.getExamNumber() > 0) {
                throw new BusinessException("试卷已被使用, 无法修改");
            }

            if (dataBaseTestPaperInfo.getExamNumber() > 0) {
                throw new BusinessException("试卷已被使用, 无法修改");
            }
        }
        return super.saveOrUpdate(testPaperInfo);
    }

    @Transactional
    public ResultCode deleteById(Integer id) {
        TestPaperInfo testPaperInfo = super.getById(id);
        if (testPaperInfo.getExamNumber() == 0) {
            this.deleteCacheByPaperId(id);
            super.removeById(id);
            // 删除试卷试题关联信息
            testPaperQuestionInfoService.deleteByTestPaperInfoId(id);
            return new ResultCode(ResultCode.SUCCESS, "删除成功");
        }
        return new ResultCode(ResultCode.FAIL, "试卷已被使用, 无法删除");
    }

    /**
     * 试卷发布
     * @param testPaperInfoId
     * @return
     */
    public ResultCode publishTestPaperInfo(Integer testPaperInfoId) {
        TestPaperInfo testPaperInfo = super.getById(testPaperInfoId);
        if (testPaperInfo.getPublishFlag()) {
            return new ResultCode(ResultCode.FAIL, "试卷已发布,请勿重复操作");
        }

        boolean flag = testPaperQuestionInfoService.hasTestPaperInfoQuestion(testPaperInfoId);
        if (!flag) {
            return new ResultCode(ResultCode.FAIL, "改试卷暂未关联试题,请关联试题之后在发布");
        }

        testPaperInfo.setPublishFlag(true);
        testPaperInfo.setPublishTime(new Date());
        super.updateById(testPaperInfo);
        return new ResultCode(ResultCode.SUCCESS, "发布成功");
    }

    /**
     * 撤销试卷
     * @param testPaperInfoId
     * @return
     */
    public ResultCode cancelTestPaperInfo(Integer testPaperInfoId) {
        TestPaperInfo testPaperInfo = super.getById(testPaperInfoId);
        if (testPaperInfo.getExamNumber() > 0) {
            return new ResultCode(ResultCode.FAIL, "试卷已有学员作答, 无法撤回");
        }
        testPaperInfo.setPublishFlag(false);
        super.updateById(testPaperInfo);
        return new ResultCode(ResultCode.SUCCESS, "撤销成功");
    }

    /**
     * 保存时间试题信息
     * @param testPaperQuestionInfoList
     */
    @Transactional
    public void saveTestPaperInfoQuestion(List<TestPaperQuestionInfo> testPaperQuestionInfoList) {
        Date now = new Date();
        Integer testPaperInfoId = testPaperQuestionInfoList.get(0).getTestPaperInfoId();
        testPaperQuestionInfoList.forEach(item -> item.setCreateDate(now));
        // 更新试卷试题数量
        baseMapper.updateQuestionNumber(testPaperInfoId, testPaperQuestionInfoList.size());
        // 保存试卷试题
        testPaperQuestionInfoService.saveBatch(testPaperQuestionInfoList);
        this.deleteCacheByPaperId(testPaperInfoId);
    }


    private void deleteCacheByPaperId(Integer testPaperInfoId) {
        // 删除试卷试题缓存 (此处不用考虑缓存数据同步问题)
        cacheBean.remove(CacheKey.TEST_PAPER_INFO_CACHE, testPaperInfoId);
    }

    public void deleteTestCacheByPaperId(Integer testPaperInfoId) {
        redisTemplate.delete(CacheKey.PAPER_TIME+testPaperInfoId);
    }

    /**
     * 删除试卷试题
     * @param testPaperQuestionInfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultCode removePaperQuestion(TestPaperQuestionInfo testPaperQuestionInfo) {
        TestPaperInfo testPaperInfo = super.getById(testPaperQuestionInfo.getTestPaperInfoId());
        if (testPaperInfo.getExamNumber() > 0) {
            return new ResultCode(ResultCode.FAIL, "试卷已被使用,无法移除试题");
        }
        Integer testPaperInfoId = testPaperQuestionInfo.getTestPaperInfoId();
        testPaperQuestionInfoService.removeById(testPaperQuestionInfo.getId());
        // 更新试卷试题数量及分数
        baseMapper.updateMarkAndQuestionNumber(testPaperInfoId, testPaperQuestionInfo.getMark(), 1);
        // 删除试卷试题缓存 (此处不用考虑缓存数据同步问题)
        deleteCacheByPaperId(testPaperInfo.getId());
        return new ResultCode(ResultCode.SUCCESS, "删除成功");
    }

    /**
     * 更新缓存考试人数
     * @param testPaperInfoId
     * @return
     */
    public boolean updateCacheExamNumber(Integer testPaperInfoId) {
        // 考试人数加1
        redisTemplate.boundHashOps(CacheKey.PAPER_EXAM_NUMBER).increment(testPaperInfoId, 1);
        return true;
    }

    /**
     * 更新数据库考试人数
     * 注意 该操作不适合高并发场景
     * @param testPaperInfoId
     * @return
     */
    public boolean updateExamNumber(Integer testPaperInfoId) {
        // 考试人数加1
        return baseMapper.updateTestPaperExamNumber(testPaperInfoId, 1);
    }

    public String getNameById(Integer id) {
        LambdaQueryWrapper<TestPaperInfo> queryWrapper = Wrappers.<TestPaperInfo>lambdaQuery()
                .select(TestPaperInfo::getName)
                .eq(TestPaperInfo::getId, id);
        TestPaperInfo testPaperInfo = super.getOne(queryWrapper);
        Assert.notNull(testPaperInfo, () -> new BusinessException("数据不存在或已被删除"));
        return testPaperInfo.getName();
    }

    /**
     * 打印试卷
     * @param testPaperInfoId
     */
    public String printPaperInfo(Integer testPaperInfoId) {
        TestPaperInfo testPaperInfo = super.getById(testPaperInfoId);
        if (StrUtil.isNotBlank(testPaperInfo.getHtmlDownloadUrl())) {
            return testPaperInfo.getHtmlDownloadUrl();
        }
        TestPaperQuestionRequest testPaperQuestionRequest = new TestPaperQuestionRequest();
        testPaperQuestionRequest.setTestPaperInfoId(testPaperInfoId);
        testPaperQuestionRequest.setAddExamMonitor(false);
        PageInfo<TestPaperQuestionDto> pageInfo = this.selectPaperQuestionList(new PageParam(),
                testPaperQuestionRequest);

        List<TestPaperQuestionDto> testPaperQuestionDtoList = pageInfo.getDataList();


        Kv data = Kv.create().set("testPaperQuestionList", this.groupQuestion(testPaperQuestionDtoList))
                .set("title", testPaperInfo.getName());
        String htmlName = SpellUtils.getSpell(testPaperInfo.getName()) + SystemConstants.HTML;
        String outDirPath = "/paperPrint" + SystemConstants.FILE_SEPARATOR
                + testPaperInfoId + SystemConstants.FILE_SEPARATOR;
        String paperTemplateSavePath = FileUtils.getUploadPath() + outDirPath;
        BaseTemplate template = new EnjoyTemplate(SystemConstants.PAPER_INFO_TEMPLATE, paperTemplateSavePath);
        template.generateTemplateToOss(data, outDirPath, htmlName, fileUpload);
        String downloadUrl = outDirPath + htmlName;
        testPaperInfo.setHtmlDownloadUrl(downloadUrl);
        super.update(Wrappers.lambdaUpdate(TestPaperInfo.class).set(TestPaperInfo::getHtmlDownloadUrl, downloadUrl)
                .eq(TestPaperInfo::getId, testPaperInfoId));
        return downloadUrl;
    }

    /**
     * 对试卷试题进行分组
     * @param testPaperQuestionDtoList
     * @return
     */
    public List<Map<String, Object>> groupQuestion(List<TestPaperQuestionDto> testPaperQuestionDtoList) {
        List<Map<String, Object>> testPaperQuestionGroupList = new ArrayList<>();
        int number = 1;
        for (QuestionTypeEnum questionType : QuestionTypeEnum.values()) {
            Map<String, Object> item = new HashMap<>();
            Integer questionTypeValue = questionType.getValue();
            List<TestPaperQuestionDto> groupQuestionList = testPaperQuestionDtoList.stream()
                    .filter(question -> questionTypeValue.equals(question.getQuestionType()))
                    .collect(Collectors.toList());
            if (ObjectUtils.isNotEmpty(groupQuestionList)) {
                item.put("groupQuestionTypeTitle", questionType.getName());
                // 答题类型序号
                item.put("groupQuestionTypeTitleNumber", NumberUtils.numberToChinese(number));
                item.put("groupQuestionList", groupQuestionList);
                testPaperQuestionGroupList.add(item);
                number++;
            }
        }
        return testPaperQuestionGroupList;
    }

    /**
     * 智能组卷
     * @param autoCreatePaperRequest
     */
    @Transactional(rollbackFor = Exception.class)
    public void autoCreate(AutoCreatePaperRequest autoCreatePaperRequest) {
        List<AutoCreatePaperItem> autoCreatePaperItemList = autoCreatePaperRequest.getAutoCreatePaperItemList();
        List<Integer> questionNumberList = autoCreatePaperItemList.stream()
                .map(AutoCreatePaperItem::getQuestionNumber)
                .collect(Collectors.toList())
                .stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<Integer> questionMarkList = autoCreatePaperItemList.stream()
                .map(AutoCreatePaperItem::getQuestionMark)
                .collect(Collectors.toList())
                .stream().filter(Objects::nonNull).collect(Collectors.toList());;

        if (CollUtil.isEmpty(questionMarkList) || CollUtil.isEmpty(questionNumberList)) {
            throw new BusinessException("请至少为一种试题类型设置试题数量及试题分数");
        }

        List<Integer> questionTypeList = new ArrayList<>();
        Map<Integer, Integer> questionTypeMarkMapping = new HashMap<>();
        int paperMark = 0; // 统计试卷总分
        int paperQuestionNumber = 0;
        autoCreatePaperItemList = autoCreatePaperItemList.stream()
                .filter(item -> item.getQuestionMark() != null && item.getQuestionNumber() != null)
                .collect(Collectors.toList());
        for (AutoCreatePaperItem item : autoCreatePaperItemList) {
            Integer questionType = item.getQuestionType();
            questionTypeList.add(questionType);
            Integer questionMark = item.getQuestionMark();
            Integer questionNumber = item.getQuestionNumber();
            questionTypeMarkMapping.put(questionType, questionMark);
            int mark = questionMark * questionNumber;
            paperQuestionNumber += questionNumber;
            paperMark += mark;
        }

        TestPaperInfo paperInfo = autoCreatePaperRequest.getTestPaperInfo();
        Integer gradeInfoId = paperInfo.getGradeInfoId();
        Integer subjectId = paperInfo.getSubjectId();
        // 创建试卷
        TestPaperInfo testPaperInfo = new TestPaperInfo();
        testPaperInfo.setName(paperInfo.getName());
        testPaperInfo.setGradeInfoId(gradeInfoId);
        testPaperInfo.setSubjectId(subjectId);
        testPaperInfo.setMark(paperMark);
        testPaperInfo.setQuestionNumber(paperQuestionNumber);
        testPaperInfo.setExamTime(paperInfo.getExamTime());
        testPaperInfo.setSchoolType(paperInfo.getSchoolType());
        Date now = new Date();
        testPaperInfo.setCreateDate(now);
        super.save(testPaperInfo);

        List<QuestionInfo> questionInfoList = questionInfoService.selectByTypes(questionTypeList, gradeInfoId, subjectId);
        // 将试题按试题类型分组
        Map<Integer, List<QuestionInfo>> questionGroupMap = questionInfoList.stream()
                .collect(Collectors.groupingBy(QuestionInfo::getQuestionType));

        List<TestPaperQuestionInfo> testPaperQuestionInfoList = new ArrayList<>();
        int sort = 1;
        Integer paperId = testPaperInfo.getId();
        for (AutoCreatePaperItem item : autoCreatePaperItemList) {
            Integer questionType = item.getQuestionType();
            Integer questionNumber = item.getQuestionNumber();
            List<QuestionInfo> list = questionGroupMap.get(questionType);
            String questionTypeName = QuestionTypeEnum.getName(questionType);
            if (CollUtil.isEmpty(list)) {
                throw new BusinessException(questionTypeName + "试题数量为0，请选择其它试题类型或添加新的试题");
            }
            if (questionNumber > list.size()) {
                throw new BusinessException(questionTypeName + "试题数量不足" + questionNumber + "道");
            }
            // 从题库中随机挑选n道id 不重复的试题
            List<QuestionInfo> paperQuestionList = NumberUtils.getRandomListFromData(list, questionNumber);
            Integer mark = questionTypeMarkMapping.get(questionType);
            for (QuestionInfo questionInfo : paperQuestionList) {
                TestPaperQuestionInfo testPaperQuestionInfo = new TestPaperQuestionInfo();
                testPaperQuestionInfo.setSort(sort);
                testPaperQuestionInfo.setCreateDate(now);
                testPaperQuestionInfo.setMark(mark);
                testPaperQuestionInfo.setTestPaperInfoId(paperId);
                testPaperQuestionInfo.setQuestionInfoId(questionInfo.getId());
                testPaperQuestionInfoList.add(testPaperQuestionInfo);
            }
        }
        testPaperQuestionInfoService.saveBatch(testPaperQuestionInfoList);
    }


    /**
     * 获取试卷试题数量
     * @param id
     * @return
     */
    public Integer getPaperQuestionNumById(Integer id) {
        LambdaQueryWrapper<TestPaperQuestionInfo> queryWrapper = Wrappers.lambdaQuery(TestPaperQuestionInfo.class)
                .eq(TestPaperQuestionInfo::getTestPaperInfoId, id);
        return testPaperQuestionInfoService.count(queryWrapper);
    }

    /**
     * 试卷试题作答分析
     * @param id
     * @param pageParam
     * @return
     */
    public PageInfo<QuestionInfoDto> questionAnalysis(Integer id, PageParam pageParam) {
        Page page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        Page<QuestionInfoDto> result = baseMapper.getQuestionAnalysis(page, id);
        if (result.getTotal() > 0) {
            result.getRecords().forEach(item -> {
                item.setRightRate(NumberUtils.getPercentRate(item.getRightAnswerNum(), item.getTotalAnswerNum()));
                item.setErrorRate(NumberUtils.getPercentRate(item.getErrorAnswerNum(), item.getTotalAnswerNum()));
            });
        }
        return selectPage(result);
    }

    /**
     * 获取试卷详情
     * @param id
     */
    public TestPaperInfoDto getDetail(Integer id) {
        TestPaperInfo testPaperInfo = super.getById(id);
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(CacheKey.PAPER_TIME + id);
        Integer studentId = UserSessionContext.getStudentId();
        String s = String.valueOf(boundHashOperations.get(studentId));
        Integer countDown;
        Integer examTime = testPaperInfo.getExamTime()* 60 * 1000;
        if (StringUtils.isEmpty(s) || s.equals("null")) {
            long l = System.currentTimeMillis();
            // 缓存开始考试的时间
            boundHashOperations.put(UserSessionContext.getStudentId(), l);
            countDown = examTime;
        } else {
            Long time = Long.valueOf(s);
            Long value = (System.currentTimeMillis() - time);
            countDown = examTime - value.intValue();
        }
        TestPaperInfoDto testPaperInfoDto = new TestPaperInfoDto();
        BeanUtils.copyProperties(testPaperInfo, testPaperInfoDto);
        testPaperInfoDto.setCountDown(countDown);
        return testPaperInfoDto;
    }
}
