package com.education.api.controller.student;

import com.education.business.service.education.CourseValuateService;
import com.education.common.base.BaseController;
import com.education.common.constants.LockKey;
import com.education.common.utils.Result;
import com.education.model.entity.CourseValuate;
import com.education.model.request.PageParam;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * 课程评价接口
 * @author zengjintao
 * @create_at 2021/10/17 9:27
 * @since version 1.0.3
 */
@RestController
@RequestMapping("/courseValuate")
public class CourseValuateController extends BaseController {

    @Resource
    private CourseValuateService courseValuateService;
    @Resource
    private RedissonClient redissonClient;

    /**
     * 课程评价列表
     * @param pageParam
     * @param courseValuate
     * @return
     */
    @GetMapping
    public Result listPage(PageParam pageParam, CourseValuate courseValuate) {
        return Result.success(courseValuateService.listPage(pageParam, courseValuate));
    }


    /**
     * 保存评价
     * @param courseValuate
     * @return
     */
    @PostMapping
    public Result save(@RequestBody @Validated CourseValuate courseValuate) {
        RLock lock = redissonClient.getLock(LockKey.COURSE_INFO_VALUATE + courseValuate.getCourseId());
        lock.lock();
        try {
            courseValuateService.save(courseValuate);
        } finally {
            lock.unlock();
        }
        return Result.success();
    }
}
