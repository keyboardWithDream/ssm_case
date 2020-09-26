package com.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.ssm.dao.IProductDao;
import com.ssm.domain.Product;
import com.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @Author Harlan
 * @Date 2020/9/20
 */
@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao dao;

    @Override
    public List<Product> findAll(int page, int pageSize) throws Exception {
        PageHelper.startPage(page, pageSize);
        return dao.findAll();
    }

    @Override
    public void save(Product product) {
        product.setId(UUID.randomUUID().toString());
        dao.save(product);
    }
}
