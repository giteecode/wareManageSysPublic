package com.education.core.controller;

import com.baidu.ueditor.ActionEnter;
import com.education.common.utils.PathKit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 百度编辑器后台接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/25 22:58
 */
@RestController
public class UeditorController {

    @RequestMapping(value = "/ueditor/exec", method = { RequestMethod.GET, RequestMethod.POST })
    public String exec(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-Type" , "text/html");
        return new ActionEnter(request, PathKit.getWebRootPath()).exec();
    }
}
