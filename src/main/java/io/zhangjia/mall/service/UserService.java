package io.zhangjia.mall.service;

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

//	User getUserInformation(String userName);
//
//	int editUserInformation(User user);
//
//	Map<String,Object> judgePayPassword(String userId, String payPassword);
//
//	List<User> queryUserAll();
}
