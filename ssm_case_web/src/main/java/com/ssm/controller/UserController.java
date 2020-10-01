package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.domain.Role;
import com.ssm.domain.UserInfo;
import com.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/26
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size", defaultValue = "12") int size) throws Exception {
        ModelAndView mv = new ModelAndView("user-list");
        PageInfo<UserInfo> pageInfo = new PageInfo<>(service.findAll(page, size));
        mv.addObject("pageInfo", pageInfo);
        return mv;
    }

    @RequestMapping("findById.do")
    public ModelAndView findById(@RequestParam("id") String id) throws Exception {
        ModelAndView mv = new ModelAndView("user-show");
        mv.addObject("user", service.findById(id));
        return mv;
    }

    @PreAuthorize("authentication.principal.username == 'admin'")
    @RequestMapping("save.do")
    public String save(UserInfo userInfo) throws Exception {
        service.save(userInfo);
        return "redirect:findAll.do";
    }


    @RequestMapping("findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id") String id) throws Exception {
        ModelAndView mv = new ModelAndView("user-role-add");
        UserInfo userInfo = service.findById(id);
        List<Role> roles = service.findOtherRole(id);
        mv.addObject("user", userInfo);
        mv.addObject("roleList", roles);
        return mv;
    }

    @RequestMapping("addRoleToUser.do")
    public String addRoleToUser(@RequestParam("userId") String userId, @RequestParam("ids") String[] roleIds) throws Exception {
        service.addRoleToUser(userId, roleIds);
        return "redirect:findAll.do";
    }
}
