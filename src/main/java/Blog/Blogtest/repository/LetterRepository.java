package Blog.Blogtest.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Blog.Blogtest.bean.Letter;
public interface LetterRepository extends JpaRepository<Letter,Integer>{
	@Query(nativeQuery=true, value = "select * from letter where Reuser = ?1 ")
	Page<Letter> findByUseridpage(Long Userid,Pageable pageable);
}
