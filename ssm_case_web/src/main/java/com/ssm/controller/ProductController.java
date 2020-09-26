package com.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.ssm.domain.Product;
import com.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;


/**
 * @Author Harlan
 * @Date 2020/9/20
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService service;

    /**
     * 查询所有产品
     * @return mv
     * @throws Exception 异常
     */
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page", defaultValue = "1")int page, @RequestParam(name = "size", defaultValue = "12") int pageSize) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageInfo<Product> pageInfo = new PageInfo<>(service.findAll(page, pageSize));
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("product-list");
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(Product product){
        service.save(product);
        return "redirect:findAll.do";
    }

}
