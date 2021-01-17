package Blog.Blogtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Blog.Blogtest.bean.Article_class;
import Blog.Blogtest.bean.Class;

public interface ClassRepository extends JpaRepository<Class,Integer>{
	//根据标签点击量降序显示14个标签
	@Query(nativeQuery=true, value = "select * from class order by clickcount desc limit 0,14")
	List<Class> findhostclass();
	//根据标签id查找标签
	Class findByClassid(Long classid);
	
	@Query(nativeQuery=true, value = "select * from class where Classid in (:classid) ")
	List<Class> findClassidByArticleid(@Param(value="classid") List<Long> classid);
	
	@Query(nativeQuery=true, value = "select Classid from class where Classname = ?1 ")
	Long findClassidByClassname(String Classname);
	
}
