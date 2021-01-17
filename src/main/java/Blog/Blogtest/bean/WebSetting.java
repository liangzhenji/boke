package Blog.Blogtest.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="web_setting")
public class WebSetting {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="settingId")
	private Long settingId;
    private Long fileNum;
	private Long articleNum;
	private Long collectionNum;
	private Long fileSize;
	
	public Long getSettingId() {
		return settingId;
	}
	public void setSettingId(Long settingId) {
		this.settingId = settingId;
	}
	public Long getFileNum() {
		return fileNum;
	}
	public void setFileNum(Long fileNum) {
		this.fileNum = fileNum;
	}
	public Long getArticleNum() {
		return articleNum;
	}
	public void setArticleNum(Long articleNum) {
		this.articleNum = articleNum;
	}
	public Long getCollectionNum() {
		return collectionNum;
	}
	public void setCollectionNum(Long collectionNum) {
		this.collectionNum = collectionNum;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
