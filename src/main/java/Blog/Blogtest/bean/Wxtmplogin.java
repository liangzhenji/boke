package Blog.Blogtest.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//微信临时登录类，记录当前需要登录的用户（没有条件办理微信服务号开通功能）	
@Entity
@Table(name="wxtmplogin")
public class Wxtmplogin {
private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	private Long id;
	private String loginName;
	private String userName;
	private String Password;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
}
