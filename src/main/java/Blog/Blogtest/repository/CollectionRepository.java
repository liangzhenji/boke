package Blog.Blogtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Blog.Blogtest.bean.Collection;

public interface CollectionRepository extends JpaRepository<Collection,Integer>{
	Collection findByArticleidAndUserid(Long articleid,Long userid);
	List<Collection> findByUserid(Long userid);
}
