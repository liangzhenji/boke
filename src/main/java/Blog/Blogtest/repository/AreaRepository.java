package Blog.Blogtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Blog.Blogtest.bean.AreaSetting;

public interface AreaRepository extends JpaRepository<AreaSetting,Long>{
	@Query(nativeQuery=true, value = "select * from erea_setting where setting_id = 1")
	AreaSetting findAreaSetting();
	
	@Query(nativeQuery=true, value = "select website_class from erea_setting where setting_id = 1")
	String findwebsite_class();
	
	@Query(nativeQuery=true, value = "select website_publish from erea_setting where setting_id = 1")
	String findwebsite_publish();
	
	@Query(nativeQuery=true, value = "select website_function from erea_setting where setting_id = 1")
	String findwebsite_function();
	
	@Query(nativeQuery=true, value = "select website_relevant from erea_setting where setting_id = 1")
	String findwebsite_relevant();
	
	@Query(nativeQuery=true, value = "select website_business from erea_setting where setting_id = 1")
	String findwebsite_business();
}
