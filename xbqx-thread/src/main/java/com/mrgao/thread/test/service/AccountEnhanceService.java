package com.mrgao.thread.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description TODO
 * @Author Mr.Gao
 * @Date 2024/9/9 22:07
 */
@Service
public class AccountEnhanceService {

    @Autowired
    private UserEnhanceService userEnhanceService;


    @Transactional
    public void enhanceAccount() {
        System.out.println("增强账户...");
        userEnhanceService.enhanceUser();
    }
}
