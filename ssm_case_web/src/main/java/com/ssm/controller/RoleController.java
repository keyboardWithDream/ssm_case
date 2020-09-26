package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.domain.Role;
import com.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView findAll(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "12") int size) throws Exception {
        ModelAndView mv = new ModelAndView("role-list");
        PageInfo<Role> pageInfo = new PageInfo<>(service.findAll(page, size));
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }

    @RequestMapping("save.do")
    public String save(Role role) throws Exception{
        service.save(role);
        return "redirect:findAll.do";
    }

}
