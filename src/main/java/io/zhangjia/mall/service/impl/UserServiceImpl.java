package io.zhangjia.mall.service.impl;


import io.zhangjia.mall.mapper.ImgMapper;
import io.zhangjia.mall.mapper.IouMapper;
import io.zhangjia.mall.mapper.UserMapper;
import io.zhangjia.mall.mapper.WalletMapper;
import io.zhangjia.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private WalletMapper walletMapper;
    @Autowired
    private IouMapper iouMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ImgMapper imgMapper;

    @Override
    public Map<String, Object> login(String userName, String userPassword) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_name", userName);
        List<Map<String, Object>> users = userMapper.query(params); //只根据用户名查询


        Map<String, Object> map = new HashMap<>();
        if (!users.isEmpty()) { // 判断用户名是否存在注意这里不能用user != null
            Map<String, Object> user = users.get(0);
            if (user.get("user_name").equals(userName) && user.get("user_password").equals(userPassword)) {
                map.put("user", user);
            } else {
                map.put("error", "密码错误");
            }
        } else {
            map.put("error", "用户名不存在");
        }
        return map;
    }

    @Override
    public Map<String, Object> register(Map<String, Object> user) {
        System.out.println("user123 = [" + user + "]");
        Map<String, Object> userName = new HashMap<>();
//		保证只根据用户名查
        userName.put("user_name", user.get("user_name"));
        List<Map<String, Object>> users = userMapper.query(userName);

        Map<String, Object> map = new HashMap<>();

        if (users.isEmpty()) {
            System.out.println("用户名不存在");
            user.put("user_id", null);
            int i = userMapper.doInsert(user);
            if (i != 0) {
                map.put("user", user);
                String stringUserId = user.get("user_id").toString(); //直接强转String不可以
                walletMapper.doInsert(Integer.parseInt(stringUserId));
                iouMapper.doInsert(Integer.parseInt(stringUserId));

            } else {
                map.put("error", "注册失败");
            }
        } else {
            System.out.println("用户名已经存在");
            map.put("error", "用户名已存在");
        }

        return map;

    }

    @Override
    public Map<String, Object> getUserInformation(String userName) {
        Map<String, Object> queryUserInfoByUserName = new HashMap<>();
        queryUserInfoByUserName.put("user_name", userName);
        return userMapper.query(queryUserInfoByUserName).get(0);
    }

    @Override
    public int editUserInfo(Map<String, Object> user) {
        return userMapper.updateUserInfo(user);
    }

    @Override
    public Map<String, Object> judgePayPassword(Integer userId, String payPassword) {
        System.out.println("嘎嘎嘎userId = [" + userId + "], payPassword = [" + payPassword + "]");
        Map<String, Object> result = new HashMap<>();
        if (userId != null && !"".equals(userId) && payPassword != null && !"".equals(payPassword)) {
            System.out.println("大狗熊");
            if (userMapper.isPayPasswordEmpty(userId).isEmpty()) {
                System.out.println("用户未设置密码");

                result.put("error", "未设置支付密码");

            } else {

                Map<String, Object> queryByPayPassword = new HashMap<>();
                queryByPayPassword.put("user_id", userId);
                queryByPayPassword.put("user_pay_password", payPassword);

                List<Map<String, Object>> isTruePayPassword = userMapper.query(queryByPayPassword);
                if (isTruePayPassword.isEmpty()) {
                    System.out.println("支付密码不正确");
                    result.put("error", "支付密码不正确");
                }
            }
        } else {
            result.put("error", "错误");
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> queryUserAll() {
        Map<String, Object> queryUserAll = new HashMap<>();
        return userMapper.query(queryUserAll);
    }

    @Override
    public Map<String, Object> getUser(Map<String, Object> params) {
        return userMapper.query(params).get(0);
    }

    @Override
    public Map<String, Object> isTrueUsername(String username,String action) {

        Map<String,Object> param = new HashMap<>();
        Map<String,Object> result = new HashMap<>();
        if(action.equals("login")) {
            param.put("user_name",username);
        }
        param.put(action,username);
        List<Map<String, Object>> isTrueUserName = userMapper.query(param);


        if(isTrueUserName.isEmpty()) {
            if(action.equals("login")) {
                result.put("error","用户名不存在哦！");
            }

        } else {
            if(action.equals("user_name")) {
                result.put("error","用户名已存在哦！");
            }
            if(action.equals("user_mail")) {
                result.put("error","邮箱已存在哦！");
            }
            if(action.equals("user_tel")) {
                result.put("error","手机号已存在哦！");
            }

        }
        return result;
    }

    @Override
    public Map<String, Object> editAvatar(String url, Integer userId) {
        System.out.println("url = [" + url + "], userId = [" + userId + "]");
        Map<String,Object> map = new HashMap<>();
        imgMapper.doUpdateImgStatus(userId,"用户头像");
        Integer result = imgMapper.doInsert(url, userId, 1, "用户头像");
         map.put("success",result == 1 ? true : false);
        return  map;

    }
//    TODO：注意，如果用户更改邮箱或者手机号，那么应该要求用户先修改
    /*
     * 思路整理，登录的时候，action为login，所以不影响
     * 注册的时候，username可能为用户名、手机号、邮箱
     * action可以为user_name,user_mail,user_phone        *
     * */

}
