package cn.miaosha.controller;

import cn.miaosha.controller.viewobject.UserVO;
import cn.miaosha.dataobject.UserDO;
import cn.miaosha.dataobject.UserPasswordDO;
import cn.miaosha.error.BusinessException;
import cn.miaosha.error.EmBusinessError;
import cn.miaosha.response.CommonReturnType;
import cn.miaosha.service.UserService;
import cn.miaosha.service.model.UserModel;
import com.alibaba.druid.util.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


/**
 * Description:
 * User: mask-fish
 * Date: 2019/4/18 0018
 * Time: 17:38
 */
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")    // 解决跨域问题（在response headers里面加上：Access-Control-Allow-Origin: *）
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest; //bean方式注入，所以单例模式（如何支持并发访问呢？）

    //用户登录接口
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(String telphone, String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if (StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //用户登录服务，用来校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telphone, this.encodeByMD5(password));

        //将登录凭证加入到用户登陆成功的session内（一般不使用session）
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        return CommonReturnType.create(null);

    }

    //用户注册接口
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(UserDO userDO,String password,String otpCode) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和杜英的otpcode相符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(userDO.getTelphone());
        if (!StringUtils.equals(otpCode, inSessionOtpCode)) { // StringUtils的equals方法内部有判空处理
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }
        //用户的注册流程
        UserModel userModel = new UserModel();
        userModel.setName(userDO.getName());
        userModel.setGender(userDO.getGender());
        userModel.setAge(userDO.getAge());
        userModel.setTelphone(userDO.getTelphone());
        userModel.setRegisterMode("byphone");
        //MD5Encoder.encode(password.getBytes())  自带的MD5只支持16位加密
        userModel.setEncrptPassword(this.encodeByMD5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    public String encodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }

    //用户获取otp短信接口
    @RequestMapping(value = "/getotp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone) {
        // 1.需要按照一定的规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(9999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);

        // 2.将OTP验证码同对应的用户手机号关联,使用httpsession的方式绑定他的手机号和otpCode（分布式中用redis）
        httpServletRequest.getSession().setAttribute(telphone, otpCode);

        // 3.将OTP验证码通过短信通道发送给用户，省略
        System.out.println("telphone = " + telphone + " & otpCode = " + otpCode);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        UserModel userModel = userService.getUserById(id);

        //若获取的对应用户信息不存在
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        //将核心领域模型用户对象转化为可供UI使用的viewobject；
        UserVO userVO = converFromModel(userModel);

        //返回通用对象
        return CommonReturnType.create(userVO);
    }

    private UserVO converFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }


}
