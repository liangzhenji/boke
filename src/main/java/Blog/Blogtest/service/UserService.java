package Blog.Blogtest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Blog.Blogtest.bean.FKRole;
import Blog.Blogtest.bean.FKUser;
import Blog.Blogtest.repository.UserRepository;


/**
 * 需要实现UserDetailsService接口
 * 因为在Spring Security中配置的相关参数需要是UserDetailsService类型的数据
 * */
@Service
public class UserService implements UserDetailsService{

	// 注入持久层接口UserRepository
	@Autowired
    UserRepository userRepository;
	
	/*
	 *  重写UserDetailsService接口中的loadUserByUsername方法，通过该方法查询到对应的用户(non-Javadoc)
	 *  返回对象UserDetails是Spring Security中一个核心的接口。
	 *  其中定义了一些可以获取用户名、密码、权限等与认证相关的信息的方法。
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 调用持久层接口findByLoginName方法查找用户，此处的传进来的参数实际是loginName
		FKUser fkUser = userRepository.findByLoginName(username);
        if (fkUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 创建List集合，用来保存用户权限，GrantedAuthority对象代表赋予给当前用户的权限
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // 获得当前用户权限集合
        List<FKRole> roles = fkUser.getRoles();
        for (FKRole role : roles) {
        	// 将关联对象Role的authority属性保存为用户的认证权限
        	authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        // 此处返回的是org.springframework.security.core.userdetails.User类，该类是Spring Security内部的实现
        return new User(fkUser.getUsername(), fkUser.getPassword(), authorities);
	}
	//根据登录名获取用户
	public FKUser getUserByLoginname(String loginname) {
		return userRepository.findByLoginName(loginname);
	}
	//用户注册保存
	public FKUser userregister(FKUser user) {
		return userRepository.save(user);
	}
	//根据用户名获取用户
	public FKUser getUserByUsername(String username) {
		return userRepository.findByUserName(username);
	}
	//更新微信用户信息
	@Transactional
	public int updatewxuser(Long userid,Date lastlogintime,String username,String imgurl) {
		return userRepository.updatewxuser(userid, lastlogintime,username,imgurl);
	}
	
	public Page<FKUser> findByuserid(List<Long> Userid,Pageable pageable){
		return userRepository.findByUserid(Userid, pageable);
	}
	@Transactional
	public int updateuserimgurl(String imgurl,Long userid) {
		return userRepository.updateuserimg(imgurl, userid);
	}
	
	@Transactional
	public int updateuserpassword(String password,Long userid) {
		return userRepository.updateuserpassword(password, userid);
	}
	
	@Transactional
	public int updateusername(String username,Long userid) {
		return userRepository.updateuserusername(username, userid);
	}
	@Transactional
	public int updateuserautograph(String autograph,Long userid) {
		return userRepository.updateuserautograph(autograph, userid);
	}
}
