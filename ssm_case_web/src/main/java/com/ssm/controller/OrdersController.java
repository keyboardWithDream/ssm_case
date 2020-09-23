package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.domain.Orders;
import com.ssm.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    //查询所有订单,未分页
//    @RequestMapping("/findAll.do")
//    public ModelAndView findAll() throws Exception {
//        ModelAndView mv = new ModelAndView();
//        List<Orders> ordersList = service.findAll();
//        mv.setViewName("orders-list");
//        mv.addObject("ordersList", ordersList);
//        return mv;
//    }

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "12") int size)  throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Orders> ordersList = service.findAll(page, size);
        //PageInfo 就是一个分页Bean
        PageInfo<Orders> pageInfo = new PageInfo<Orders>(ordersList);
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("orders-list");
        return mv;
    }
}
