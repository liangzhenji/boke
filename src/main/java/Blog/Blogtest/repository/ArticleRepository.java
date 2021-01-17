package Blog.Blogtest.repository;
import Blog.Blogtest.bean.Article;

import java.util.List;
import Blog.Blogtest.bean.Class;
import Blog.Blogtest.bean.FKUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
public interface ArticleRepository extends JpaRepository<Article,Integer>{
	//查找阅读量最多的文章12条
	@Query(nativeQuery=true, value = "select * from article order by read_count desc limit 0,12")
	List<Article> findreadmax_article(); 
	//查找评论最多的文章12条
	@Query(nativeQuery=true, value = "select * from article order by comment_count desc limit 0,12")
	List<Article> findcommentmax_article();
	//根据标签分页显示文章
	@Query(nativeQuery=true, value = "select * from article a RIGHT JOIN article_class b on a.articleid=b.articleid where b.Classid=?1 order by comment_count desc")
	Page<Article> findByClassid(Long Classid,Pageable pageable);
	//根据文章id列表查找文章
	@Query(nativeQuery=true, value = "select * from article where articleid in (:Articleid)")
	Page<Article> findByArticleid(@Param(value="Articleid") List<Long> Articleid,Pageable pageable);
	//根据userid查找文章分页
	@Query(nativeQuery=true, value = "select * from article where Userid in (:Userid) ")
	Page<Article> findByUserid(@Param(value="Userid") List<Long> Userid,Pageable pageable);
	
	List<Long> findArticleidByUserid(Long userid);
	
	@Query(nativeQuery=true, value = "select * from article where Userid = ?1 order by read_count desc limit 0,6")
	List<Article> findbyuseridreadmax(Long userid);
	
	@Query(nativeQuery=true, value = "select * from article where Userid = ?1 order by comment_count desc limit 0,6")
	List<Article> findbyuseridcommentmax(Long userid);
	
	@Query(nativeQuery=true, value = "select * from article where Userid = ?1 ")
	Page<Article> findByUseridpage(Long Userid,Pageable pageable);
	
	Article findByArticleid(Long articleid);
	
	@Modifying
	@Query("update Article m set m.AgreedCount=m.AgreedCount+1 where m.articleid=?1")
	int updateagreecount(Long articleid);
	
	@Query("select m from Article m where m.userid = ?1")
	List<Article> findByUserids(Long userid);
	
	@Query(nativeQuery=true, value = "select * from article a left join article_class b on a.articleid = b.articleid where Classid = ?2 And Userid = ?1")
	Page<Article> test(Long userid,Long classid,Pageable pageable);
	
	@Query(nativeQuery=true, value = "select count(*) from article where Userid =?1")
	Long findCountByUserid(Long userid);
	
	@Query(nativeQuery=true, value = "select Max(articleid) from article")
	Long findArticleMaxid();
	
	@Query(nativeQuery=true, value = "select COUNT(*) from article")
	Long findAtricleCOUNT();
	
	@Query(nativeQuery=true, value = "select COUNT(*) from article where (Releasetime like :releasetime or :releasetime='%%') and (Username like :username  or :username = '%%') and (Title like :title  or :title = '%%')")
	Long findAtricleCOUNT2(String username,String releasetime,String title);
	
	@Query(nativeQuery=true, value = "select COUNT(*) from article where (Releasetime like :releasetime or :releasetime='%%') and (Username like :username  or :username = '%%') and (Title like :title  or :title = '%%') and articleid in (:articleid)")
	Long findAtricleCOUNT3(String username,String releasetime,String title,List<Long> articleid);
	
	@Query(nativeQuery=true, value = "select * from article where (Releasetime like :releasetime or :releasetime='%%') and (Username like :username  or :username = '%%') and (Title like :title  or :title = '%%') Limit :page,:size")
	List<Article> findAllArticleByPage(Integer page,Integer size,String username,String releasetime,String title);
	
	@Query(nativeQuery=true, value = "select * from article where (Releasetime like :releasetime or :releasetime='%%') and (Username like :username  or :username = '%%') and (Title like :title  or :title = '%%') and articleid in (:articleid) Limit :page,:size")
	List<Article> findAllArticleByPage2(Integer page,Integer size,String username,String releasetime,String title,List<Long> articleid);
	
	@Modifying
	@Transactional
	@Query(nativeQuery=true, value = "update article set Title = :title , Releasetime = :date where articleid = :articleid")
	int updateArticle(Long articleid,String title,String date);
	
	@Modifying
	@Transactional
	@Query(nativeQuery=true, value = "delete from article where articleid = :articleid")
	int deleteArticle(Long articleid);
	
	@Modifying
	@Transactional
	@Query(nativeQuery=true, value = "delete from article where articleid in (:articleid)")
	int deleteArticles(List<Long> articleid);
	
}
