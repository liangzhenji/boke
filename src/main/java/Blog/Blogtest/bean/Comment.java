package Blog.Blogtest.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
//评论类
@Entity
@Table(name="comment")
public class Comment {
		private static final long serialVersionUID = 1L;
		@Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="commentid")
	    private Long commentid;
	    private Long commenthostid;
	    private Long commentuserid;
	    private String replyName;
	    private  String content;
	    private String time;
	    private Long articleid;
	    private String beReplyName;
		public String getBeReplyName() {
			return beReplyName;
		}
		public void setBeReplyName(String beReplyName) {
			this.beReplyName = beReplyName;
		}
		
		@ManyToOne
		private Comment parent;
		/**
		 * 子节点 
		 */
		@OneToMany(mappedBy="parent",fetch=FetchType.EAGER)
		@OrderBy("commentid asc")
		private Collection<Comment> replyBody = new ArrayList<Comment>();
		public Long getCommentid() {
			return commentid;
		}
		public void setCommentid(Long commentid) {
			this.commentid = commentid;
		}
		public Long getCommenthostid() {
			return commenthostid;
		}
		public void setCommenthostid(Long commenthostid) {
			this.commenthostid = commenthostid;
		}
		public Long getCommentuserid() {
			return commentuserid;
		}
		public void setCommentuserid(Long commentuserid) {
			this.commentuserid = commentuserid;
		}
		public String getReplyName() {
			return replyName;
		}
		public void setReplyName(String replyName) {
			this.replyName = replyName;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public Long getArticleid() {
			return articleid;
		}
		public void setArticleid(Long articleid) {
			this.articleid = articleid;
		}		
		public Collection<Comment> getReplyBody() {
			return replyBody;
		}
		public void setReplyBody(Collection<Comment> replyBody) {
			this.replyBody = replyBody;
		}
		
		
}
