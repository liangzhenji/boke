package Blog.Blogtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Blog.Blogtest.bean.Agreed;

public interface AgreedRepository extends JpaRepository<Agreed, Integer>{
	List<Long> findArticleidByuserid(Long userid);
	
	Agreed findByArticleidAndUserid(Long articleid,Long userid);
}
