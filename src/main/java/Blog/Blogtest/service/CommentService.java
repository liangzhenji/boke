package Blog.Blogtest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Blog.Blogtest.bean.Comment;
import Blog.Blogtest.repository.CommentRepository;

@Service
public class CommentService {
	@Resource
	private CommentRepository commentrepository;
	//根据评论人id查找评论id
	public List<Long> findCommentArticleid(Long commentuserid) {
		return commentrepository.findArticleidByCommentuserid(commentuserid);
	}
	public Page<Comment> findByCommenthostid(Long commenthostid,Pageable pageable){
		return commentrepository.findByCommenthostid(commenthostid,pageable);
	}
	public Comment savecomment(Comment co) {
		return commentrepository.save(co);
	}
	@Transactional
	public int savecommentchild(Long userid,String username,Long followuserid,String followusername,Long articleid,String content,Long parent_commentid,String time) {
		return commentrepository.savecomment(userid,username,followuserid,followusername,articleid,content,parent_commentid,time);
	}
}
