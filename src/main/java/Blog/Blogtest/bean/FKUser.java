package Blog.Blogtest.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
//标记持久化类，springboot启动后会自动根据持久化类建表
//用户类
@Entity
@Table(name="user")
public class FKUser implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//指定主键，指定主键的生成策略，默认自增长
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Userid")
    private Long Userid;
	private String loginName;
	private String userName;
    private String Password;
    private Calendar Firstlogintime;
    private Calendar Lastlogintime;
    private String Autograph;
    private String Imageurl;
    private String Hosturl;
    private Long fanscount;
    private Long followcount;
    public Long getFanscount() {
		return fanscount;
	}
	public void setFanscount(Long fanscount) {
		this.fanscount = fanscount;
	}
	public Long getFollowcount() {
		return followcount;
	}
	public void setFollowcount(Long followcount) {
		this.followcount = followcount;
	}
	//用中间表关联多对多，级联刷新，急加载
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    //添加连接表
    @JoinTable(name="user_role",
	joinColumns={@JoinColumn(name="Userid")},
	//标注关联表的字段
	inverseJoinColumns={@JoinColumn(name="Roleid")})
    private List<FKRole> roles;
	public Long getUserid() {
		return Userid;
	}
	public void setUserid(Long userid) {
		Userid = userid;
	}
	
	public String getUsername() {
		return userName;
	}
	public void setUsername(String username) {
		userName = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public Calendar getFirstlogintime() {
		return Firstlogintime;
	}
	public void setFirstlogintime(Calendar firstlogintime) {
		Firstlogintime = firstlogintime;
	}
	public Calendar getLastlogintime() {
		return Lastlogintime;
	}
	public void setLastlogintime(Calendar lastlogintime) {
		Lastlogintime = lastlogintime;
	}
	public String getAutograph() {
		return Autograph;
	}
	public void setAutograph(String autograph) {
		Autograph = autograph;
	}
	public String getImageurl() {
		return Imageurl;
	}
	public void setImageurl(String imageurl) {
		Imageurl = imageurl;
	}
	public String getHosturl() {
		return Hosturl;
	}
	public void setHosturl(String hosturl) {
		Hosturl = hosturl;
	}
	public List<FKRole> getRoles() {
		return roles;
	}
	public void setRoles(List<FKRole> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "FKUser [Userid=" + Userid + ", Loginname=" + loginName + ", Username=" + userName + ", Password="
				+ Password + ", Firstlogintime=" + Firstlogintime + ", Lastlogintime=" + Lastlogintime + ", Autograph="
				+ Autograph + ", Imageurl=" + Imageurl + ", Hosturl=" + Hosturl + ", roles=" + roles + "]";
	}
	public String getLoginname() {
		return loginName;
	}
	public void setLoginname(String loginname) {
		this.loginName = loginname;
	}
}
