package Blog.Blogtest.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import Blog.Blogtest.bean.Follow;
import Blog.Blogtest.repository.FollowRepository;

@Service
public class FollowService {
	@Resource
	private FollowRepository followrepository;
	
	public List<Long> findfollowuseridByuserid(Long userid){
		return followrepository.findFollowuseridByuserid(userid);
	}
	public Follow findByUseridAndFollowuserid(Long userid,Long followuserid) {
		return followrepository.findByUseridAndFollowuserid(userid, followuserid);
	}
	public Follow savefollow(Follow fol) {
		return followrepository.save(fol);
	}
}
