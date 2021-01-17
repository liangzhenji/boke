package Blog.Blogtest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import Blog.Blogtest.bean.Agreed;
import Blog.Blogtest.repository.AgreedRepository;

@Service
public class AgreedService {
	@Resource
	private AgreedRepository agreedrepository;
	public List<Long> findArticleidByuserid(Long userid){
		return agreedrepository.findArticleidByuserid(userid);
	}
	public Agreed findByArticleidAndUserid(Long articleid,Long userid) {
		return agreedrepository.findByArticleidAndUserid(articleid, userid);
	}
	public Agreed saveAgreed(Agreed ar) {
		return agreedrepository.save(ar);
	}
}
