package Blog.Blogtest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Blog.Blogtest.bean.Article_class;
import Blog.Blogtest.repository.Article_classRepository;

@Service
public class Article_classService {
	@Resource
	private Article_classRepository article_classRepository;
	
	public List<Long> findClassidByArticleid(List<Long> articleid){
		return article_classRepository.findClassidByArticleid(articleid);
	}
	@Transactional
	public int savearticleclass(Long articleid,Long Classid) {
		return article_classRepository.saveArticleclass(articleid, Classid);
	}
}
