package io.zhangjia.mall.service.impl;



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
	
//	private UserDao userDao = new UserDaoImpl();
//	private WalletDao walletDao = new WalletDaoImpl();
//	private IouDao iouDao = new IouDaoImpl();
	@Autowired
	private WalletMapper walletMapper;
	@Autowired
	private IouMapper iouMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public Map<String, Object> login(String userName, String userPassword) {
		Map<String,Object> params = new HashMap<>();
		params.put("userName",userName);
		List<Map<String, Object>> users = userMapper.query(params);


		Map<String,Object> map = new HashMap<>();
		if(!users.isEmpty()) { //注意这里不能用user != null
			Map<String,Object> user = users.get(0);
			if(user.get("user_name").equals(userName) && user.get("user_password").equals(userPassword)) {
				map.put("user",user);
			} else {
				map.put("error","密码错误");
			}
		} else {
			map.put("error","用户名不存在");
		}
		return map;
	}

	@Override
	public Map<String, Object>register(Map<String,Object> user) {
		System.out.println("user123 = [" + user + "]");
		Map<String,Object> userName = new HashMap<>();
//		保证只根据用户名查
		userName.put("userName",user.get("userName"));
		List<Map<String, Object>> users = userMapper.query(userName);

		Map<String,Object> map = new HashMap<>();

		if(users.isEmpty()) {
			System.out.println("用户名不存在");
			user.put("userId",null);
			int i = userMapper.doInsert(user);
			if(i != 0) {
				map.put("user",user);
				String stringUserId = user.get("userId").toString(); //直接强转String不可以
				walletMapper.doInsert(Integer.parseInt(stringUserId));
				iouMapper.doInsert(Integer.parseInt(stringUserId));

			} else {
				map.put("error","注册失败");
			}
		} else {
			System.out.println("用户名已经存在");
			map.put("error","用户名已存在");
		}

		return map;

	}
//
//	@Override
//	public User getUserInformation(String userName) {
//		return userDao.queryByUsername(userName);
//	}
//
//	@Override
//	public int editUserInformation(User user) {
//		return userDao.updateUserInformation(user);
//	}
//
//	@Override
//	public Map<String, Object> judgePayPassword(String userId, String payPassword) {
//		Map<String, Object> result = new HashMap<>();
//		if(userId != null && !"".equals(userId) && payPassword != null && !"".equals(payPassword)){
//			result = userDao.queryByPayPassword(Integer.parseInt(userId),payPassword);
//		} else {
//			result.put("error","错误");
//		}
//		return  result;
//	}
//
//	@Override
//	public List<User> queryUserAll() {
//
//		return userDao.queryUserAll();
//	}


}
