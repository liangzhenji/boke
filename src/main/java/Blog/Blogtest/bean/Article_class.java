package Blog.Blogtest.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//记录文章对应的标签id
@Entity
@Table(name="article_class")
public class Article_class {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="articleid")
    private Long Articleid;
    public Long getArticleid() {
		return Articleid;
	}
	public void setArticleid(Long articleid) {
		Articleid = articleid;
	}
	public Long getClassid() {
		return Classid;
	}
	public void setClassid(Long classid) {
		Classid = classid;
	}
	@Column(name="Classid")
	private Long Classid;
}
