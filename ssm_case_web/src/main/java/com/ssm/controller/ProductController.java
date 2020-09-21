package com.ssm.controller;

import com.ssm.domain.Product;
import com.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Product> products = service.findAll();
        mv.addObject("productList",products);
        mv.setViewName("product-list");
        return mv;
    }

    @RequestMapping("/save.do")
    public String save(Product product, HttpServletRequest request){
        product.setId(UUID.randomUUID().toString());
        service.save(product);
        return "redirect:findAll.do";
    }
}
