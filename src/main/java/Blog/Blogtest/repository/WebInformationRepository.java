package Blog.Blogtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Blog.Blogtest.bean.WebInformation;

public interface WebInformationRepository extends JpaRepository<WebInformation, Long>{
	//根据标签点击量降序显示14个标签
		@Query(nativeQuery=true, value = "select SUM(browes) from web_information")
		Long findSumBrowes();
}
