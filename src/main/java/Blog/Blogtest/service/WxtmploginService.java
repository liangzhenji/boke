package Blog.Blogtest.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import Blog.Blogtest.bean.Wxtmplogin;
import Blog.Blogtest.repository.WxtmploginRepository;

@Service
public class WxtmploginService {
	@Resource
	private WxtmploginRepository wxtmp;
	//保存微信登录临时信息
	public Wxtmplogin savewxtmplogin(Wxtmplogin wxtmplogin) {
		return wxtmp.save(wxtmplogin);
	}
	//查找微信登录临时信息
	public Wxtmplogin findwxtmplogin() {
		return wxtmp.findById(1L);
	}
}