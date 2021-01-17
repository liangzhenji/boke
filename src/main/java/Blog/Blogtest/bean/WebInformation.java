package Blog.Blogtest.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="web_information")
public class WebInformation {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="informationId")
    private Long informationId;
	private Long browes;
	private String date;
	private Long articleAdd;
	private Long userAdd;
	public Long getInformationId() {
		return informationId;
	}
	public void setInformationId(Long informationId) {
		this.informationId = informationId;
	}
	public Long getBrowes() {
		return browes;
	}
	public void setBrowes(Long browes) {
		this.browes = browes;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getArticleAdd() {
		return articleAdd;
	}
	public void setArticleAdd(Long articleAdd) {
		this.articleAdd = articleAdd;
	}
	public Long getUserAdd() {
		return userAdd;
	}
	public void setUserAdd(Long userAdd) {
		this.userAdd = userAdd;
	}
	
}
