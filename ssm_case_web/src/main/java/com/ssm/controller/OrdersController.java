package com.ssm.controller;

import com.ssm.domain.Orders;
import com.ssm.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/22
 */
@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrdersService service;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Orders> ordersList = service.findAll();
        mv.setViewName("orders-list");
        mv.addObject("ordersList", ordersList);
        return mv;
    }
}
