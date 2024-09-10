package com.mrgao.thread.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/9/9 22:06
 */
@Service
public class UserEnhanceService {

    /**
     * =========可用于测试不存在循环依赖的Bean Begin=====
     */
    //@Autowired
    //private UserService userService;
    //
    //@Transactional
    //public void enhanceUser() {
    //    System.out.println("增强用户...!");
    //}
    /**
     * =========可用于测试不存在循环依赖的Bean End=====
     */

    /**
     * =========可用于测试存在循环依赖的Bean Begin=====
     */
    @Autowired
    private AccountEnhanceService accountEnhanceService;

    @Transactional
    public void enhanceUser() {
        System.out.println("增强用户...!");
        accountEnhanceService.enhanceAccount();
    }
    /**
     * =========可用于测试存在循环依赖的Bean End=====
     */
}
