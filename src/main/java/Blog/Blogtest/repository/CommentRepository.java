package Blog.Blogtest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import Blog.Blogtest.bean.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer>{
	//查找文章id根据评论人id
	List<Long> findArticleidByCommentuserid(Long commentuserid);
	
	@Query(nativeQuery=true, value = "select * from comment where parent_commentid IS NULL And  Articleid = ?1")
	Page<Comment> findByCommenthostid(Long Articleid,Pageable pageable);
	
	@Modifying
	@Query(nativeQuery=true, value = "INSERT INTO comment (Commenthostid,be_reply_name,Commentuserid,reply_name,Articleid,content,parent_commentid,time) VALUES (?1,?2,?3,?4,?5,?6,?7,?8)")
	int savecomment(Long userid,String username,Long followuserid,String followusername,Long articleid,String content,Long parent_commentid,String time);
}
