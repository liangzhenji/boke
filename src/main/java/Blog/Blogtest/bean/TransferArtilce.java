package Blog.Blogtest.bean;

public class TransferArtilce {
	private Long articleid;
	private String releasetime;
	private String context;
	private String title;
	private String articleclass;
	private Long userid;
	private String username;
	private String imageurl;
	private String articleurl;
	private String hosturl;
	private Long readcount;
	private Long commentcount;
	private Long agreedcount;
	private String classname;
	
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public Long getArticleid() {
		return articleid;
	}
	
	public String getHosturl() {
		return hosturl;
	}

	public void setHosturl(String hosturl) {
		this.hosturl = hosturl;
	}

	public void setArticleid(Long articleid) {
		this.articleid = articleid;
	}
	public String getReleasetime() {
		return releasetime;
	}
	public void setReleasetime(String releasetime) {
		this.releasetime = releasetime;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArticleclass() {
		return articleclass;
	}
	public void setArticleclass(String articleclass) {
		this.articleclass = articleclass;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getArticleurl() {
		return articleurl;
	}
	public void setArticleurl(String articleurl) {
		this.articleurl = articleurl;
	}
	public Long getReadcount() {
		return readcount;
	}
	public void setReadcount(Long readcount) {
		this.readcount = readcount;
	}

	public Long getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(Long commentcount) {
		this.commentcount = commentcount;
	}

	public Long getAgreedcount() {
		return agreedcount;
	}
	public void setAgreedcount(Long agreedcount) {
		this.agreedcount = agreedcount;
	}
	
}
