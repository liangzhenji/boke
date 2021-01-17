package Blog.Blogtest.bean;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
//文章类
@Entity
@Table(name="article")
public class Article implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="articleid")
	private Long articleid;
	private String Releasetime;
	private String Context;
	private String Title;
	public List<Class> getArticleclass() {
		return articleclass;
	}
	public void setArticleclass(List<Class> articleclass) {
		this.articleclass = articleclass;
	}
	@Column(name="Userid")
	private Long userid;
	private String Username;
	//用中间表关联多对多，级联刷新，急加载
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    //添加连接表
    @JoinTable(name="article_class",
	joinColumns={@JoinColumn(name="articleid")},
	//标注关联表的字段
	inverseJoinColumns={@JoinColumn(name="Classid")})
	private List<Class> articleclass;
	public Long getReadCount() {
		return ReadCount;
	}
	public void setReadCount(Long readCount) {
		ReadCount = readCount;
	}
	public Long getCommentCount() {
		return CommentCount;
	}
	public void setCommentCount(Long commentCount) {
		CommentCount = commentCount;
	}
	private String Imageurl;
	private String Articleurl;
	private Long ReadCount;
	private Long CommentCount;
	private Long AgreedCount;
	public Long getAgreedCount() {
		return AgreedCount;
	}
	public void setAgreedCount(Long agreedCount) {
		AgreedCount = agreedCount;
	}
	public Long getArticleid() {
		return articleid;
	}
	public void setArticleid(Long articleid) {
		this.articleid = articleid;
	}
	public String getReleasetime() {
		return Releasetime;
	}
	public void setReleasetime(String releasetime) {
		Releasetime = releasetime;
	}
	public String getContext() {
		return Context;
	}
	public void setContext(String context) {
		Context = context;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getImageurl() {
		return Imageurl;
	}
	public void setImageurl(String imageurl) {
		Imageurl = imageurl;
	}
	public String getArticleurl() {
		return Articleurl;
	}
	public void setArticleurl(String articleurl) {
		Articleurl = articleurl;
	}
	public String getHosturl() {
		return Hosturl;
	}
	public void setHosturl(String hosturl) {
		Hosturl = hosturl;
	}
	private String Hosturl;
	public Article() {
		super();
		// TODO Auto-generated constructor stub
	}
}
