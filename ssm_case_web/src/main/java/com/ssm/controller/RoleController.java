package com.ssm.controller;

import com.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author Harlan
 * @Date 2020/9/26
 */
@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private IRoleService service;

    @RequestMapping("findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView("role-list");
        mv.addObject("roleList", service.findAll());
        return mv;
    }

}
