package Blog.Blogtest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import Blog.Blogtest.bean.Collection;
import Blog.Blogtest.repository.CollectionRepository;

@Service
public class CollectionService {
	@Resource
	private CollectionRepository collectionrepository;
	
	public Collection findByArticleidAndUserid(Long articleid,Long userid) {
		return collectionrepository.findByArticleidAndUserid(articleid, userid);
	}
	
	public Collection saveCollection(Collection cl) {
		return collectionrepository.save(cl);
	}
	public List<Collection> findByUserid(Long userid){
		return collectionrepository.findByUserid(userid);
	}
}
