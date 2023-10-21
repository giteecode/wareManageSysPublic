package com.education.api.controller.admin.system;

import com.education.auth.annotation.Logical;
import com.education.auth.annotation.RequiresPermissions;
import com.education.business.service.system.SystemMenuService;
import com.education.common.annotation.SystemLog;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.dto.MenuTree;
import com.education.model.entity.SystemMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


/**
 * 菜单管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/22 21:44
 */
@RestController
@RequestMapping("/menu")
public class SystemMenuController extends BaseController {

    @Autowired
    private SystemMenuService systemMenuService;

    /**
     * 菜单列表
     * @return
     */
    @GetMapping("tree")
    @RequiresPermissions("system:menu:list")
    @SystemLog(describe = "获取菜单列表")
    public Result<List<MenuTree>> menuTreeList() {
        return Result.success(systemMenuService.selectMenuTreeList());
    }

    /**
     * 菜单详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<MenuTree> selectById(@PathVariable Integer id) {
        return Result.success(systemMenuService.selectById(id));
    }

    /**
     * 保存或修改菜单
     * @param systemMenu
     * @return
     */
    @PostMapping
    @RequiresPermissions(value = {"system:menu:save", "system:menu:update"}, logical = Logical.OR)
    public Result saveOrUpdate(@RequestBody SystemMenu systemMenu) {
        systemMenuService.saveOrUpdate(systemMenu);
        return Result.success();
    }

    /**
     * 根据id 删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @RequiresPermissions("system:menu:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        systemMenuService.deleteById(id);
        return Result.success(ResultCode.SUCCESS, "删除成功");
    }
}
