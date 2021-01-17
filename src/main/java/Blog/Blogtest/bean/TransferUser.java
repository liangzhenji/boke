package Blog.Blogtest.bean;

public class TransferUser {
	private Long userid;
	private String loginname;
	private String username;
    private String password;
    private String firstlogintime;
    private String lastlogintime;
    private String autograph;
    private String imageurl;
    private String hosturl;
    private Long fanscount;
    private Long followcount;
    private String roles;
    
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAutograph() {
		return autograph;
	}
	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	public String getImageurl() {
		return imageurl;
	}	
	public String getFirstlogintime() {
		return firstlogintime;
	}
	public void setFirstlogintime(String firstlogintime) {
		this.firstlogintime = firstlogintime;
	}
	public String getLastlogintime() {
		return lastlogintime;
	}
	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getHosturl() {
		return hosturl;
	}
	public void setHosturl(String hosturl) {
		this.hosturl = hosturl;
	}
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
    
}
