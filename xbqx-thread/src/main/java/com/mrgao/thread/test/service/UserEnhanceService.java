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

    @Autowired
    private AccountEnhanceService accountEnhanceService;

    @Transactional
    public void enhanceUser() {
        System.out.println("增强用户...!");
        accountEnhanceService.enhanceAccount();
    }
}
