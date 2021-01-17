package Blog.Blogtest.service;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Blog.Blogtest.bean.Diary;
import Blog.Blogtest.repository.DiaryRepository;

@Service
public class DiaryService {
	@Resource
	private DiaryRepository diaryrepository;
	public Page<Diary> findByUserid(Long Userid,Pageable pageable){
		return diaryrepository.findByUserid(Userid, pageable);
	}
	public Diary savediary(Diary di) {
		return diaryrepository.save(di);
	}
}
