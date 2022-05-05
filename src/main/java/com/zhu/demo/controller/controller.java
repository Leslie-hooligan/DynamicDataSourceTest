package com.zhu.demo.controller;

import com.zhu.demo.dao.User;
import com.zhu.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 河南张国荣
 * @create 2022-03-14 21:12
 */
@RestController
public class controller {

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/test")
    public List<User> getUserList() {
        List<User> users = userMapper.selectList(null);
        return users;
    }

    @GetMapping("/test1")
    public List<User> getUserList1() {
        List<User> users = userMapper.selectList(null);
        return users;
    }
}
