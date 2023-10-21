package com.education.api.controller.admin.system;

import com.education.auth.annotation.Logical;
import com.education.auth.annotation.RequiresPermissions;
import com.education.business.service.system.SystemAdminService;
import com.education.common.annotation.SystemLog;
import com.education.common.base.BaseController;
import com.education.common.model.PageInfo;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.dto.AdminRoleDto;
import com.education.model.entity.SystemAdmin;
import com.education.model.request.PageParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;


/**
 * 管理员管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/21 19:48
 */
@RestController
@RequestMapping("/admin")
public class SystemAdminController extends BaseController {

    @Resource
    private SystemAdminService systemAdminService;

    /**
     * 管理员列表
     * @param pageParam
     * @param systemAdmin
     * @return
     */
    @GetMapping
    @SystemLog(describe = "获取管理员列表")
    @RequiresPermissions("system:admin:list")
    public Result<PageInfo<SystemAdmin>> list(PageParam pageParam, SystemAdmin systemAdmin) {
        return Result.success(systemAdminService.listPage(pageParam, systemAdmin));
    }

    /**
     * 管理员详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @SystemLog(describe = "查看管理员详情")
    public Result selectById(@PathVariable Integer id) {
        return Result.success(systemAdminService.selectById(id));
    }

    /**
     * 添加或修改管理员
     * @param adminRoleDto
     * @return
     */
    @PostMapping
    @SystemLog(describe = "添加或修改管理员")
    @RequiresPermissions(value = {"system:admin:save", "system:admin:update"}, logical = Logical.OR)
    public Result saveOrUpdate(@RequestBody AdminRoleDto adminRoleDto) {
        systemAdminService.saveOrUpdate(adminRoleDto);
        return Result.success();
    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @SystemLog(describe = "删除管理员")
    @RequiresPermissions("system:admin:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        systemAdminService.deleteById(id);
        return Result.success();
    }


    /**
     * 修改密码
     * @param adminRoleDto
     * @return
     */
    @PostMapping("password")
    @SystemLog(describe = "修改管理员密码")
    @RequiresPermissions("system:admin:updatePassword")
    public Result updatePassword(@RequestBody AdminRoleDto adminRoleDto) {
        systemAdminService.updatePassword(adminRoleDto);
        return Result.success(ResultCode.SUCCESS, "修改密码成功");
    }

    /**
     * 修改密码
     * @param adminRoleDto
     * @return
     */
    @PostMapping("/resetting/password")
    @SystemLog(describe = "管理员重置密码")
    public Result resettingPassword(@RequestBody AdminRoleDto adminRoleDto) {
        systemAdminService.resettingPassword(adminRoleDto);
        return Result.success(ResultCode.SUCCESS, "修改重置成功，退出后请使用新密码进行登录");
    }
}
