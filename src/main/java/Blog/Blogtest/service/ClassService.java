package Blog.Blogtest.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import Blog.Blogtest.repository.ClassRepository;

import Blog.Blogtest.bean.Class;

@Service
public class ClassService {
	@Resource
	private ClassRepository classRepository;
	//查找热门分类
	public List<Class> findhotclass() {
		return classRepository.findhostclass();
	}
	//根据id查找分类
	public Class findByClassid(Long classid) {
		return classRepository.findByClassid(classid);
	}
	public List<Class> findclassnameByclassid(List<Long> classid){
		return classRepository.findClassidByArticleid(classid);
	}
	public Long findClassidByClassname(String Classname) {
		return classRepository.findClassidByClassname(Classname);
	}
	
	public List<Class> findAllClass(){
		return classRepository.findAll();
	}
}
