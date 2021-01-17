package Blog.Blogtest.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Blog.Blogtest.bean.Article;
import Blog.Blogtest.repository.ArticleRepository;
import Blog.Blogtest.bean.Class;
@Service
public class ArticleService {
	@Resource
	private ArticleRepository articleRepository;
	//查找全部文章分页
	public Page<Article> findAll(Pageable page) {
		return articleRepository.findAll(page);
	}
	//查找最多阅读文章
	public List<Article> findreadmax_article(){
		return articleRepository.findreadmax_article();
	}
	//查找评论最多文章
	public List<Article> findcommetntmax_article(){
		return articleRepository.findcommentmax_article();
	}
	//根据分类查找文章分页
	public Page<Article> findarticlebycate(Long cate,Pageable pageable){
		return articleRepository.findByClassid(cate, pageable);
	}
	//根据文章id查找文章并分页
	public Page<Article> findByArticleid(List<Long> Articleid,Pageable pageable) {
		return articleRepository.findByArticleid(Articleid, pageable);
	}
	//根据userid查找文章并分页
	public Page<Article> findByUserid(List<Long> Userid,Pageable pageable) {
		return articleRepository.findByUserid(Userid, pageable);
	}
	public List<Long> findArticleidByUserid(Long userid){
		return articleRepository.findArticleidByUserid(userid);
	}
	public List<Article> findbyuseridreadmax(Long userid){
		return articleRepository.findbyuseridreadmax(userid);
	}
	public List<Article> findbyuseridcommentmax(Long userid){
		return articleRepository.findbyuseridcommentmax(userid);
	}
	public Page<Article> findByUseridpage(Long userid,Pageable pageable){
		return articleRepository.findByUseridpage(userid,pageable);
	}
	public Article findByArticleid(Long articleid) {
		return articleRepository.findByArticleid(articleid);
	}
	@Transactional
	public int updateagreecount(Long articleid) {
		return articleRepository.updateagreecount(articleid);
	}
	public List<Article> findByUserid(Long userid){
		return articleRepository.findByUserids(userid);
	}
	
	public Page<Article> test(Long userid,Long classid,Pageable pageable){
		return articleRepository.test(userid, classid,pageable);
	}
	
	public Long findCountByUserid(Long userid) {
		return articleRepository.findCountByUserid(userid);
	}
	
	public Article saveArticle(Article ar) {
		return articleRepository.save(ar);
	}
	
	public Long findArticleMaxid() {
		return articleRepository.findArticleMaxid();
	}
}
