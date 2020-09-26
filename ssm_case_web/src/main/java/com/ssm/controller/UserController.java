package com.ssm.controller;

import com.ssm.domain.UserInfo;
import com.ssm.service.IUserService;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService service;

    @RequestMapping("findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView("user-list");
        mv.addObject("userList", service.findAll());
        return mv;
    }

    @RequestMapping("findById.do")
    public ModelAndView findById(@RequestParam("id") String id) throws Exception {
        ModelAndView mv = new ModelAndView("user-show");
        mv.addObject("user", service.findById(id));
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(UserInfo userInfo) throws Exception {
        service.save(userInfo);
        return "redirect:findAll.do";
    }
}
