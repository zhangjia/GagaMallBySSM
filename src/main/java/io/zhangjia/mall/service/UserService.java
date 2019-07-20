package io.zhangjia.mall.service;

import java.util.List;
import java.util.Map;

/**
 * 用户的业务层
 *
 */
public interface UserService {

	Map<String, Object> login(String userName, String userPassword);

	Map<String, Object> register(Map<String,Object> user);

	Map<String,Object> getUserInformation(String userName);

	int editUserInfo(Map<String,Object> user);

	Map<String,Object> judgePayPassword(Integer userId, String RpayPassword);

	List<Map<String,Object>> queryUserAll();

	Map<String,Object> getUser(Map<String,Object> params);


	Map<String,Object> isTrueUsername(String username,String action);

	Map<String,Object> editAvatar(String url,Integer userId);
}
