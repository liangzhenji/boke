package Blog.Blogtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import Blog.Blogtest.bean.Article_class;

public interface Article_classRepository extends JpaRepository<Article_class,Integer>{
	@Query(nativeQuery=true, value = "select Classid from article_class where articleid in (:articleid) ")
	List<Long> findClassidByArticleid(@Param(value="articleid") List<Long> articleid);

	@Query(nativeQuery=true, value = "select Classid from article_class where articleid = ?1 ")
	List<Long> findClassidByArticleid2(Long articleid);
	
	@Modifying
	@Query(nativeQuery=true, value = "INSERT INTO article_class (articleid,Classid) VALUES (?1, ?2) ")
	int saveArticleclass(Long articleid,Long Classid);
	
	@Query(nativeQuery=true, value = "select * from article_class where articleid = ?1 ")
	List<Article_class> findACByArticleid2(Long articleid);
	
	@Query(nativeQuery=true, value = "select articleid from article_class where Classid in (:classid) ")
	List<Long> findArticleidByClassid(@Param(value="classid") List<Long> classid);
	
	@Query(nativeQuery=true, value = "select articleid from article_class where Classid in (:classid) ")
	List<Integer> findArticleidByClassid2(@Param(value="classid") List<Long> classid);
	
	@Modifying
	@Transactional
	@Query(nativeQuery=true, value = "delete from article_class where articleid = ?1")
	int deleteArticleclass(Long articleid);
	
}
