package Blog.Blogtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import Blog.Blogtest.bean.User_role;

public interface User_roleRepository extends JpaRepository<User_role, Long>{
	@Modifying
	@Transactional
	@Query(nativeQuery=true, value = "update user_role set Roleid = ?1 where Userid = ?2")
	int updateuserrole(Long roleid,Long userid);
	
	@Query(nativeQuery=true, value = "select Userid from user_role where Roleid = ?1")
	List<Integer> selectUseridByRoleid(Long roleid);
}
