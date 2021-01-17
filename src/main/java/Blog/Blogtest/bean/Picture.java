package Blog.Blogtest.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="picture")
public class Picture {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pictureid")
	private Long pictureid;
	private String Releasetime;
	private Long Userid;
	private String Username;
	private String Title;
	private String Describes;
	private String Imageurl;
	private Long Visible;
	public Long getVisible() {
		return Visible;
	}
	public void setVisible(Long visible) {
		Visible = visible;
	}
	public Long getPictureid() {
		return pictureid;
	}
	public void setPictureid(Long pictureid) {
		this.pictureid = pictureid;
	}
	public String getReleasetime() {
		return Releasetime;
	}
	public void setReleasetime(String releasetime) {
		Releasetime = releasetime;
	}
	public Long getUserid() {
		return Userid;
	}
	public void setUserid(Long userid) {
		Userid = userid;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescribe() {
		return Describes;
	}
	public void setDescribe(String describe) {
		Describes = describe;
	}
	public String getImageurl() {
		return Imageurl;
	}
	public void setImageurl(String imageurl) {
		Imageurl = imageurl;
	}
	
}
