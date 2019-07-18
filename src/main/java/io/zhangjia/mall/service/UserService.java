package io.zhangjia.mall.service;

import java.util.List;
import java.util.Map;

/**
 * 用户的业务层
 *
 */
public interface UserService {
	/**
	 * 用户登录
	 * @param userName 用户名
	 * @param userPassword 密码
	 * @return 登录结果
	 */
	Map<String, Object> login(String userName, String userPassword);

	/**
	 * 用户注册

	 */
	Map<String, Object> register(Map<String,Object> user);

	Map<String,Object> getUserInformation(String userName);
//
	int editUserInfo(Map<String,Object> user);
//
	Map<String,Object> judgePayPassword(Integer userId, String RpayPassword);
//
	List<Map<String,Object>> queryUserAll();

	Map<String,Object> getUser(Map<String,Object> params);
}
