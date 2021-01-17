package Blog.Blogtest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Blog.Blogtest.bean.Diary;

public interface DiaryRepository extends JpaRepository<Diary,Integer>{
	@Query(nativeQuery=true, value = "select * from diary where Userid = ?1 ")
	Page<Diary> findByUserid(Long Userid,Pageable pageable);
	
}
