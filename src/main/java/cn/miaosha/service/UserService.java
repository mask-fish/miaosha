package cn.miaosha.service;

import cn.miaosha.error.BusinessException;
import cn.miaosha.service.model.UserModel;

/**
 * Description:
 * User: mask-fish
 * Date: 2019/4/18 0018
 * Time: 17:42
 */
public interface UserService {
    //通过id获取用户对象的方法
    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusinessException;

    /**
     * 登录校验
     * @param telphone 用户注册手机
     * @param encrptPassword 用户加密过的密码
     * @throws BusinessException
     */
    UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException;
}
