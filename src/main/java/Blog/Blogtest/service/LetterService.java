package Blog.Blogtest.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Blog.Blogtest.bean.Letter;
import Blog.Blogtest.repository.LetterRepository;

@Service
public class LetterService {
	@Resource
	private LetterRepository letterrepository;
	public Page<Letter> findByUserid(Long Userid,Pageable pageable){
		return letterrepository.findByUseridpage(Userid, pageable);
	}
}
