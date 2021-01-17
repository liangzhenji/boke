package Blog.Blogtest.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import Blog.Blogtest.bean.User_role;
import Blog.Blogtest.repository.User_roleRepository;

@Service
public class User_roleService {
	@Resource
	private User_roleRepository user_role;
	//保存用户权限
	public User_role saveuserrole(User_role userrole) {	
		return user_role.save(userrole);
	}
}
