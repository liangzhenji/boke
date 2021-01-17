package Blog.Blogtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Blog.Blogtest.bean.Follow;

public interface FollowRepository extends JpaRepository<Follow, Integer>{
	List<Long> findFollowuseridByuserid(Long userid);
	Follow findByUseridAndFollowuserid(Long userid,Long followuserid);
}
