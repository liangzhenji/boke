package Blog.Blogtest.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//文章类型类，类型id、类型名、点击量等
@Entity
@Table(name="class")
public class Class {
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Classid")
    private Long classid;
    private String Classname;
    public Long getClickcount() {
		return Clickcount;
	}
	public void setClickcount(Long clickcount) {
		Clickcount = clickcount;
	}
	private String Classurl;
    private Long Clickcount;
	public String getClassurl() {
		return Classurl;
	}
	public void setClassurl(String classurl) {
		Classurl = classurl;
	}
	public Long getClassid() {
		return classid;
	}
	public void setClassid(Long classid) {
		this.classid = classid;
	}
	public String getClassname() {
		return Classname;
	}
	public void setClassname(String classname) {
		Classname = classname;
	}
}
