package Blog.Blogtest.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import Blog.Blogtest.bean.FKUser;

public interface UserRepository extends JpaSpecificationExecutor<FKUser>,JpaRepository<FKUser, Long>{

	// 根据登录名查询出用户
	FKUser findByLoginName(String loginName);
	//根据用户名查询用户
	FKUser findByUserName(String userName);
	//更新微信用户信息
	@Modifying
	@Query("update FKUser m set m.Lastlogintime=?2,m.userName=?3,m.Imageurl=?4 where m.Userid=?1")
	int updatewxuser(Long userid,Date lastlogintime,String username,String imgurl);
	
	@Query(nativeQuery=true, value = "select * from user where Userid in (:Userid) ")
	Page<FKUser> findByUserid(@Param(value="Userid") List<Long> Userid,Pageable pageable);
	
	@Modifying
	@Query(nativeQuery=true, value = "update user set Imageurl = ?1 where Userid = ?2")
	int updateuserimg(String imgurl,Long userid);
	
	@Modifying
	@Query(nativeQuery=true, value = "update user set Password = ?1 where Userid = ?2")
	int updateuserpassword(String password,Long userid);
	
	@Modifying
	@Query(nativeQuery=true, value = "update user set Autograph = ?1 where Userid = ?2")
	int updateuserautograph(String autograph,Long userid);
	
	@Modifying
	@Query(nativeQuery=true, value = "update user set user_name = ?1 where Userid = ?2")
	int updateuserusername(String username,Long userid);
	
	@Query(nativeQuery=true, value = "select COUNT(*) from user")
	int findUserCount();
	
	@Query(nativeQuery=true, value = "select COUNT(*) from user where (user_name like :username or :username='%%') and (login_name like :loginname  or :loginname = '%%')")
	int findUserCount2(String username,String loginname);
	
	@Query(nativeQuery=true, value = "select COUNT(*) from user where (user_name like :username or :username='%%') and (login_name like :loginname  or :loginname = '%%') and Userid in (:Userid)")
	int findUserCount3(String username,String loginname,@Param(value="Userid") List<Integer> Userid);
	
	@Query(nativeQuery=true, value = "select * from user where (user_name like :username or :username='%%') and (login_name like :loginname  or :loginname = '%%') Limit :page,:size")
	List<FKUser> findAllUserByPage(Integer page,Integer size,String username,String loginname);
	
	@Query(nativeQuery=true, value = "select * from user where (user_name like :username or :username='%%') and (login_name like :loginname  or :loginname = '%%') and Userid in (:Userid) Limit :page,:size")
	List<FKUser> findAllUserByPage2(Integer page,Integer size,String username,String loginname,@Param(value="Userid") List<Integer> Userid);
	
	@Modifying
	@Transactional
	@Query(nativeQuery=true, value = "update user set login_name = ?1 where Userid = ?2")
	int updateuserloginname(String loginname,Long userid);
	
	@Modifying
	@Transactional
	@Query(nativeQuery=true, value = "DELETE FROM user where Userid = ?1")
	int deleteuserbyid(Long userid);
	
	@Modifying
	@Transactional
	@Query(nativeQuery=true, value = "DELETE FROM user where Userid in (:userids)")
	int deleteuserbyids(@Param(value="userids") List<Long> userids);
}
