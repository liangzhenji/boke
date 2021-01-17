package Blog.Blogtest.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="letter")
public class Letter {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Letterid")
	private Long Letterid;
	private String Title;
	private Long Reuser;
	private String Reusername;
	private Long Userid;
	private String Username;
	private String Context;
	private String Hosturl;
	private String Releasetime;
	public String getReleasetime() {
		return Releasetime;
	}
	public void setReleasetime(String releasetime) {
		Releasetime = releasetime;
	}
	public String getHosturl() {
		return Hosturl;
	}
	public void setHosturl(String hosturl) {
		Hosturl = hosturl;
	}
	public Long getLetterid() {
		return Letterid;
	}
	public void setLetterid(Long letterid) {
		Letterid = letterid;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public Long getReuser() {
		return Reuser;
	}
	public void setReuser(Long reuser) {
		Reuser = reuser;
	}
	public String getReusername() {
		return Reusername;
	}
	public void setReusername(String reusername) {
		Reusername = reusername;
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
	public String getContext() {
		return Context;
	}
	public void setContext(String context) {
		Context = context;
	}
}
