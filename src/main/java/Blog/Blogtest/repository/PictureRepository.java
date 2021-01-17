package Blog.Blogtest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Blog.Blogtest.bean.Picture;


public interface PictureRepository extends JpaRepository<Picture, Integer>{
	@Query(nativeQuery=true, value = "select * from picture where Userid = ?1 And Visible = 1 ")
	Page<Picture> findBypictureid(Long Userid,Pageable pageable);
	
	
}
