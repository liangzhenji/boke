package Blog.Blogtest.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_role")
public class User_role {
private static final long serialVersionUID = 1L;
//用户权限对照类
	@Id
	@Column(name="Userid")
    private Long Userid;
    private Long Roleid;
	public Long getUserid() {
		return Userid;
	}
	public void setUserid(Long userid) {
		Userid = userid;
	}
	@Override
	public String toString() {
		return "User_role [Userid=" + Userid + ", Roleid=" + Roleid + "]";
	}
	public Long getRoleid() {
		return Roleid;
	}
	public void setRoleid(Long roleid) {
		Roleid = roleid;
	}
}
