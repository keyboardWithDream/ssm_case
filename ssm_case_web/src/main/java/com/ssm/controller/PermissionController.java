package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.domain.Permission;
import com.ssm.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author Harlan
 * @Date 2020/9/27
 */
@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private IPermissionService service;

    @RequestMapping("findAll")
    public ModelAndView findAll(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "12") int size) throws Exception {
        ModelAndView mv = new ModelAndView("permission-list");
        PageInfo<Permission> pageInfo = new PageInfo<>(service.findAll(page, size));
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }

    @RequestMapping("save.do")
    public String save(Permission permission) throws Exception {
        service.save(permission);
        return "redirect:findAll.do";
    }
}
