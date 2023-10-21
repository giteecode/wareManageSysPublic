package com.education.business.service.education;

import com.education.business.session.StudentSession;
import com.education.business.session.UserSessionContext;
import com.education.common.constants.CacheKey;
import com.education.common.utils.DateUtils;
import com.education.common.utils.Ip2regionUtil;
import com.education.common.utils.IpUtils;
import com.education.model.dto.ExamMonitor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 试卷监控中心
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/29 15:20
 */
@Service
public class ExamMonitorService {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private HttpServletRequest request;
    @Resource
    private TestPaperInfoService testPaperInfoService;

    /**
     * 添加学员监控
     * @param examMonitor
     */
    public void addStudentToExamMonitor(ExamMonitor examMonitor) {
        StudentSession studentSession = UserSessionContext.getStudentUserSession();
        Integer studentId = studentSession.getId();
        examMonitor.setStartExamTime(DateUtils.getSecondDate(new Date()));
        examMonitor.setStudentId(studentId);
        examMonitor.setHeadImg(studentSession.getHeadImg());
        examMonitor.setName(studentSession.getName());
        String ip = IpUtils.getAddressIp(request);
        String ipAddressName = Ip2regionUtil.getIpProvinceAndCity(ip);
        examMonitor.setIp(ip);
        examMonitor.setIpAddress(ipAddressName);
        // 获取试卷试题数量
        Integer questionNumber = testPaperInfoService.getPaperQuestionNumById(examMonitor.getTestPaperInfoId());
        examMonitor.setQuestionCount(questionNumber);
        redisTemplate.boundHashOps(CacheKey.EXAM_MONITOR_CACHE_KEY + examMonitor.getTestPaperInfoId())
                .put(studentId, examMonitor);
    }

    /**
     * 更新学员答题进度
     * @param examMonitor
     */
    public void updateProgress(ExamMonitor examMonitor) {
        Integer studentId = UserSessionContext.getStudentId();
        ExamMonitor examMonitorCache = getExamMonitorStudent(examMonitor.getTestPaperInfoId(), studentId);
        examMonitorCache.setAnswerQuestionCount(examMonitorCache.getAnswerQuestionCount());
        redisTemplate.boundHashOps(CacheKey.EXAM_MONITOR_CACHE_KEY + examMonitor.getTestPaperInfoId())
                .put(studentId, examMonitorCache);
    }

    /**
     * 获取学员监控信息
     * @param testPaperInfoId
     * @param studentId
     * @return
     */
    public ExamMonitor getExamMonitorStudent(Integer testPaperInfoId, Integer studentId) {
       return (ExamMonitor) redisTemplate.boundHashOps(CacheKey.EXAM_MONITOR_CACHE_KEY + testPaperInfoId).get(studentId);
    }

    /**
     * 获取试卷学员监控列表
     * @param testPaperInfoId
     * @return
     */
    public List<ExamMonitor> getExamMonitorByTestPaperId(Integer testPaperInfoId) {
        return redisTemplate.boundHashOps(CacheKey.EXAM_MONITOR_CACHE_KEY + testPaperInfoId)
                .values();
    }

    /**
     * 移除考试监控
     * @param studentId
     * @param testPaperInfoId
     */
    public void removeStudent(Integer studentId, Integer testPaperInfoId) {
        redisTemplate.boundHashOps(CacheKey.EXAM_MONITOR_CACHE_KEY + testPaperInfoId).delete(studentId);
    }

    /**
     * 强制交卷
     * @param examMonitor
     */
    public void forceCommitPaper(ExamMonitor examMonitor) {
    }
}
