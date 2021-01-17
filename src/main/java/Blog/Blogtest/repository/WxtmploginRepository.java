package Blog.Blogtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Blog.Blogtest.bean.Wxtmplogin;

public interface WxtmploginRepository extends JpaRepository<Wxtmplogin,Integer>{
	//根据id查找临时登录人员
	Wxtmplogin findById(Long id);
}
