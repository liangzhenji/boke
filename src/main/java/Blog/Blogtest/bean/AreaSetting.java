package Blog.Blogtest.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="erea_setting")
public class AreaSetting {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="settingId")
	private Long settingId;
    private String website_class;
	private String website_publish;
	private String website_function;
	private String website_relevant;
	private String website_business;
	public Long getSettingId() {
		return settingId;
	}
	public void setSettingId(Long settingId) {
		this.settingId = settingId;
	}
	public String getWebsite_class() {
		return website_class;
	}
	public void setWebsite_class(String website_class) {
		this.website_class = website_class;
	}
	public String getWebsite_publish() {
		return website_publish;
	}
	public void setWebsite_publish(String website_publish) {
		this.website_publish = website_publish;
	}
	public String getWebsite_function() {
		return website_function;
	}
	public void setWebsite_function(String website_function) {
		this.website_function = website_function;
	}
	public String getWebsite_relevant() {
		return website_relevant;
	}
	public void setWebsite_relevant(String website_relevant) {
		this.website_relevant = website_relevant;
	}
	public String getWebsite_business() {
		return website_business;
	}
	public void setWebsite_business(String website_business) {
		this.website_business = website_business;
	}
	
}
