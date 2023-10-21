package com.education.api.controller.admin.system;

import com.education.auth.annotation.Logical;
import com.education.auth.annotation.RequiresPermissions;
import com.education.business.service.system.SystemMenuService;
import com.education.business.service.system.SystemRoleService;
import com.education.common.annotation.SystemLog;
import com.education.common.base.BaseController;
import com.education.common.model.PageInfo;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.dto.RoleMenuDto;
import com.education.model.entity.SystemRole;
import com.education.model.request.PageParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;


/**
 * 角色管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/22 21:39
 */
@RestController
@RequestMapping("/role")
public class SystemRoleController extends BaseController {

    @Resource
    private SystemRoleService systemRoleService;
    @Resource
    private SystemMenuService systemMenuService;

    /**
     * 管理员列表
     * @param pageParam
     * @param systemRole
     * @return
     */
    @GetMapping
    @SystemLog(describe = "获取管理员列表")
    @RequiresPermissions("system:role:list")
    public Result<PageInfo<SystemRole>> list(PageParam pageParam, SystemRole systemRole) {
        return Result.success(systemRoleService.listPage(pageParam, systemRole));
    }

    /**
     * 修改角色权限
     * @param roleMenuDto
     * @return
     */
    @PostMapping("permission")
    @RequiresPermissions("system:role:savePermission")
    public Result saveRolePermission(@RequestBody @Validated RoleMenuDto roleMenuDto) {
        systemRoleService.saveRolePermission(roleMenuDto);
        return Result.success(ResultCode.SUCCESS, "权限设置成功");
    }

    /**
     * 保存或修改角色
     * @param systemRole
     * @return
     */
    @PostMapping
    @RequiresPermissions(value = {"system:role:save", "system:role:update"}, logical = Logical.OR)
    public Result saveOrUpdate(@RequestBody @Validated SystemRole systemRole) {
        systemRoleService.saveOrUpdate(systemRole);
        return Result.success();
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @RequiresPermissions("system:role:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        return Result.success(systemRoleService.deleteById(id));
    }

    /**
     * 获取角色 选中的 tree 菜单
     * @param id
     * @return
     */
    @GetMapping("/{id}/menuTree")
    public Result<List<Integer>> selectMenuListById(@PathVariable Integer id) {
        return Result.success(systemMenuService.selectTreeCheckedMenuId(id));
    }
}
