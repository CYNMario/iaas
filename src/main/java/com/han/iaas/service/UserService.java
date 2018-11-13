package com.han.iaas.service;

import com.han.iaas.cache.SessionCache;
import com.han.iaas.dao.ResultDao;
import com.han.iaas.dao.SessionEntity;
import com.han.iaas.dao.UserDao;
import com.han.iaas.enums.ResultEnum;
import com.han.iaas.repository.UserRepository;
import com.han.iaas.utils.ResultUtil;

import org.apache.logging.log4j.spi.CopyOnWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    用户登录
    public ResultDao login(String name, String password, String vali, String sessionValiStr, HttpServletRequest request) {
        HttpSession session = request.getSession();
//        判断用户是否存在
        if (!userRepository.existsUserDaosByUserName(name)) {
            return ResultUtil.unitedResultReturnNull(ResultEnum.LOGIN_ERROR_USER_NOT_EXIST);
        }

//        验证码超时
        if (session.getAttribute("valiStr").equals(null)) {
            return ResultUtil.unitedResultReturnNull(ResultEnum.VALI_TIMEOUT);
        }
//        判断验证码是否正确
        if (vali.equalsIgnoreCase(sessionValiStr)) {
//            通过用户名搜索数据库
            UserDao userDao = userRepository.selectByName(name);
//            获取用户密码
            String userPassword = userDao.getPassword();
//            判断用户名和密码是否匹配
            if (password.equals(userPassword)) {
//            	验证是否唯一登录
                CopyOnWriteArrayList<SessionEntity> sessionCache = SessionCache.getSessionCache();
                Iterator<SessionEntity> it = sessionCache.iterator();
                SessionEntity tempSe = null;
                boolean isUserExist = false;
                while(it.hasNext()) {
                	tempSe = it.next();
                	if(name.equals(tempSe.getUserName())){
                		isUserExist = true;
                		break;
                	}
                }
//                如果用户已登录则禁止登录
                if(isUserExist) {
                	return ResultUtil.unitedResultReturnNull(ResultEnum.LOGIN_ERROR_USER_LOGGED);
                }else {
                	SessionCache.addInSessionCache(name, session);
                }

                while(it.hasNext()) {
                	System.out.println("session中有以下sessionid：" + it.next().getSession().getId());
                }
            	
                session.setAttribute("uid", userDao.getUserId());
//                设置session存活时间,单位秒
//                session.setMaxInactiveInterval(10);
                session.setAttribute("sessionId", session.getId());
                return ResultUtil.unitedResult(ResultEnum.LOGIN_SUCCESS, userDao);
            }else {
                return ResultUtil.unitedResultReturnNull(ResultEnum.LOGIN_ERROR_PASSWORD);
            }
        }else {
            return ResultUtil.unitedResultReturnNull(ResultEnum.VALI_ERROR);
        }
    }
//    注册
    public ResultDao register(String name, String password, String passwordConfirm,
                              String vali, String phoneVali, String sessionValiStr) {
//        验证码超时
        if (sessionValiStr.equals(null)) {
            return ResultUtil.unitedResultReturnNull(ResultEnum.VALI_TIMEOUT);
        }
//        判断验证码是否正确
        if (vali.equalsIgnoreCase(sessionValiStr)) {
//            判断用户是否已经存在
            if (userRepository.existsUserDaosByUserName(name)) {
                return ResultUtil.unitedResultReturnNull(ResultEnum.USER_EXIST);
            }else {
                UserDao userDao = new UserDao();
                userDao.setUserName(name);
                userDao.setPassword(password);
                userRepository.save(userDao);
                return ResultUtil.unitedResult(ResultEnum.SUCCESS, userDao);
            }
        }else {
            return ResultUtil.unitedResultReturnNull(ResultEnum.VALI_ERROR);
        }
    }
//    注销
    public ResultDao logout(HttpServletRequest request) {
//        判断是否登录或是否登录超时
        if (request.getSession().getAttribute("uid") != null) {
            request.getSession().removeAttribute("uid");
            return ResultUtil.unitedResultReturnNull(ResultEnum.SUCCESS);
        }else {
            return ResultUtil.unitedResultReturnNull(ResultEnum.ERROR);
        }
    }
//    登录状态判别
    public ResultDao checkStatus(Integer userId, HttpServletRequest request) {
        HttpSession session = request.getSession();
//        判断登录是否超时,用户id是否匹配
//        if (!session.getId().equals(session.getAttribute("sessionId")) || !session.getAttribute("uid").equals(userId)) {
        if (!session.getId().equals(session.getAttribute("sessionId"))) {
            System.out.println("登录超时" + session.getId());
            return ResultUtil.unitedResultReturnNull(ResultEnum.LOGIN_TIMEOUT);
        }else {
            return ResultUtil.unitedResultReturnNull(ResultEnum.LOGIN_SUCCESS);
        }
    }
//    密码重置——身份验证
    public ResultDao authentication(String name,String vali, String sessionValiStr) {
//        验证码超时
        if (sessionValiStr.equals(null)) {
            return ResultUtil.unitedResultReturnNull(ResultEnum.VALI_TIMEOUT);
        }
//        判断验证码是否正确
        if (vali.equalsIgnoreCase(sessionValiStr)) {
//            判断用户是否存在
            if (userRepository.existsUserDaosByUserName(name)) {
                return ResultUtil.unitedResult(ResultEnum.SUCCESS, userRepository.selectByName(name).getUserId());
            }else {
                return ResultUtil.unitedResultReturnNull(ResultEnum.USER_NOT_EXIST);
            }
        }else {
            return ResultUtil.unitedResultReturnNull(ResultEnum.VALI_ERROR);
        }
    }
//    密码重置——密码重置
    public ResultDao resetPassword(Integer userId, String password) {
        userRepository.updatePasswordByUserName(userId, password);
        return ResultUtil.unitedResultReturnNull(ResultEnum.SUCCESS);
    }
}
