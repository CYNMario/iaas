package com.han.iaas.controller;

import com.han.iaas.dao.ResultDao;
import com.han.iaas.service.UserService;

import org.apache.logging.log4j.spi.CopyOnWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;


@RestController
public class UserController {
    @Autowired
    private UserService userService;
//    注册
    @CrossOrigin(origins = "http://127.0.0.1:8080")
    @PostMapping("/register")
    public ResultDao register (@RequestParam("name") String name,
                               @RequestParam("password") String password,
                               @RequestParam("passwordConfirm") String passwordConfirm,
                               @RequestParam("vali") String vali,
                               @RequestParam("phoneVali") String phoneVali,
                               HttpServletRequest request) {
//        获取session中的验证码缓存
        HttpSession session = request.getSession();
        String sessionValiStr = session.getAttribute("valiStr").toString();
        return userService.register(name, password, passwordConfirm, vali, phoneVali, sessionValiStr);
    }
//    登录
    @CrossOrigin(origins = "http://127.0.0.1:8080")
    @PostMapping("/userlogin")
    public ResultDao userLogin(@RequestParam("name") String name,
                               @RequestParam("password") String password,
                               @RequestParam("valicode") String vali,
                               HttpServletRequest request) {
//        获取session中的验证码缓存
        HttpSession session = request.getSession();
        String sessionValiStr = session.getAttribute("valiStr").toString();
        return userService.login(name, password, vali, sessionValiStr, request);
    }
//    用户注销
    @GetMapping("/userlogout")
    public ResultDao userLogout(HttpServletRequest request) {
        return userService.logout(request);
    }
//    登录状态
    @GetMapping("/status")
    public ResultDao checkStatus(@PathParam("userId") Integer userId,
                                 HttpServletRequest request) {
        return userService.checkStatus(userId, request);
    }
//    身份验证
    @GetMapping("/authentication")
    public ResultDao authentication(@PathParam("userName") String userName,
                                    @PathParam("vali") String vali,
                                    HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionValiStr = session.getAttribute("valiStr").toString();
        return userService.authentication(userName,vali,sessionValiStr);
    }
//    密码重置
    @PostMapping("/authentication")
    public ResultDao resetPassword(@RequestParam("userId") Integer userId,
                                   @RequestParam("password") String password) {
        return userService.resetPassword(userId, password);
    }
}
