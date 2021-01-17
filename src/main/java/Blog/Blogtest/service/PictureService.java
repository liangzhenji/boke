package Blog.Blogtest.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Blog.Blogtest.bean.Picture;
import Blog.Blogtest.repository.PictureRepository;

@Service
public class PictureService {
	@Resource
	private PictureRepository picturepository;
	public Page<Picture> findBypictureid(Long Userid,Pageable pageable){
		return picturepository.findBypictureid(Userid, pageable);
	}
	public Picture savepicture(Picture pi) {
		return picturepository.save(pi);
	}
}
