package Blog.Blogtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import Blog.Blogtest.bean.WebSetting;



public interface WebSettingRepository extends JpaRepository<WebSetting, Long>{
	
			@Query(nativeQuery=true, value = "select * from web_setting where setting_id = 1")
			WebSetting findWebSetting();
			
			@Modifying
			@Transactional
			@Query(nativeQuery=true, value = "update web_setting set article_num = ?1,file_num = ?2,collection_num = ?3,file_size = ?4 where setting_id = 1")
			int updateWebSetting(Long article_num,Long file_num,Long collection_num,Long file_size);
}
