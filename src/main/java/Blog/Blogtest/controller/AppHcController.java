package Blog.Blogtest.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Blog.Blogtest.bean.Class;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import Blog.Blogtest.bean.AreaSetting;
import Blog.Blogtest.bean.Article;
import Blog.Blogtest.bean.Article_class;
import Blog.Blogtest.bean.FKRole;
import Blog.Blogtest.bean.FKUser;
import Blog.Blogtest.bean.TransferArtilce;
import Blog.Blogtest.bean.TransferUser;
import Blog.Blogtest.bean.User_role;
import Blog.Blogtest.bean.WebInformation;
import Blog.Blogtest.bean.WebSetting;
import Blog.Blogtest.repository.AreaRepository;
import Blog.Blogtest.repository.ArticleRepository;
import Blog.Blogtest.repository.Article_classRepository;
import Blog.Blogtest.repository.ClassRepository;
import Blog.Blogtest.repository.UserRepository;
import Blog.Blogtest.repository.User_roleRepository;
import Blog.Blogtest.repository.WebInformationRepository;
import Blog.Blogtest.repository.WebSettingRepository;
import Blog.Blogtest.service.Article_classService;
import Blog.Blogtest.service.UserService;
import Blog.Blogtest.service.User_roleService;

@RestController
@RequestMapping("/hc")
public class AppHcController {
	@Resource
	private UserService userservice;
	
	@Resource
	private ArticleRepository articleRepository;
	
	@Resource
	private User_roleService user_role;
	
	@Autowired
    UserRepository userRepository;
	
	
	@Autowired
	ClassRepository classRepository;
	
	@Autowired
	Article_classRepository articleclassRepository;
	
	@Autowired
	WebInformationRepository webInformationRepository;
	
	@Autowired
	User_roleRepository userroleRepository;
	
	@Autowired
	WebSettingRepository websRepository;
	
	@Autowired
	AreaRepository areaRepository;

	@Resource
	private Article_classService ACServer;

	private String getUsername(){
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			System.out.println("username = " + username);
			return username;
		}catch(Exception e) {
			return null;
		}
	}
	
	
	private FKUser getuser() {
    	try {
    	String username=getUsername();
    	FKUser user= userservice.getUserByUsername(username);
    	if(user != null) {
    		return user;
    	}else {
    		FKUser us=new FKUser();
    		return us;
    	}
    	}catch(Exception e) {
    		FKUser us=new FKUser();
    		return us;
    	}
    }
	//设置页面
		 @RequestMapping(value = "/setting")
	    public String setting(HttpServletRequest reuqest,@RequestBody Map<String, String> params) {
			 Long userid = getuser().getUserid();
			int usernametag=userservice.updateusername(params.get("username"), userid);
			int passwordtag=userservice.updateuserpassword(params.get("Password"), userid);
			if(usernametag != 0 && passwordtag != 0) {
				return "success";
			}else {
				return "false";
			}	        
	    }
		//用户设置
		 @RequestMapping(value = "/hcsetting")
	    public String hcsetting(HttpServletRequest reuqest,@RequestBody Map<String, String> params) {			
			Long userid = Long.valueOf(params.get("userid"));
			int usernametag=userservice.updateusername(params.get("username"), userid);
			int passwordtag=userservice.updateuserpassword(params.get("Password"), userid);
			int loginnametag = userRepository.updateuserloginname(params.get("loginname"), userid);
			int roletag = userroleRepository.updateuserrole(Long.valueOf(params.get("role")), userid);
			if(usernametag != 0 && passwordtag != 0 && loginnametag != 0 && roletag != 0) {
				return "success";
			}else {
				return "false";
			}        
	    }
	  //文章编辑
		 @RequestMapping(value = "/articleEdit")
	    public String articleEdit(HttpServletRequest reuqest,@RequestBody Map<String, Object> params) {			
			Long articleid = Long.valueOf((String)params.get("articleid"));
			String date = params.get("date")+" "+"00:00:00";			
			int articletag = articleRepository.updateArticle(articleid, (String)params.get("title"), date);
			List<String> classlist = new ArrayList<String>();
			try {
				classlist = (List<String>) params.get("type");
			}catch(Exception e) {
				e.printStackTrace();
			}
			int classtag = 0;
			if(classlist.size() != 0) {
				articleclassRepository.deleteArticleclass(articleid);			
				for(String id : classlist) {
					ACServer.savearticleclass(articleid,Long.valueOf(id));
					classtag += 1;
				}
			}else {
				articleclassRepository.deleteArticleclass(articleid);
				classtag = 1;
			}
			if(articletag != 0 && classtag != 0) {
				return "success";
			}else {
				return "false";
			}        
	    }
	  //Setting编辑
		 @RequestMapping(value = "/webSetting")
	    public String webSetting(HttpServletRequest reuqest,@RequestBody Map<String, Long> params) {			
			int tag = websRepository.updateWebSetting(params.get("articleNum"), params.get("fileNum"), params.get("collectionNum"), params.get("fileSize"));
			if(tag != 0) {
				return "success";
			}else {
				return "false";
			}        
	    }
		//AreaSetting编辑
		 @RequestMapping(value = "/areaSetting")
	    public String areaSetting(HttpServletRequest reuqest,@RequestBody Map<String, String> params) {
			AreaSetting as =new AreaSetting();
			as.setSettingId(1L);
			as.setWebsite_business(params.get("website_business"));
			as.setWebsite_class(params.get("website_class"));
			as.setWebsite_function(params.get("website_function"));
			as.setWebsite_publish(params.get("website_publish"));
			as.setWebsite_relevant(params.get("website_relevant"));
			AreaSetting tag = areaRepository.save(as);
			if(tag != null) {
				return "success";
			}else {
				return "false";
			}        
	    }
		 //添加用户
		 @RequestMapping(value = "/hcadd")
		    public String hcadd(HttpServletRequest reuqest,@RequestBody Map<String, String> params) {
			 FKUser user=new FKUser();
			 user.setLoginname(params.get("loginname"));
			 user.setUsername(params.get("username"));
			 user.setPassword(params.get("Password"));
			 user.setHosturl("/user/page?loginame="+params.get("loginname"));
			 user.setLastlogintime(Calendar.getInstance());
			 FKUser users=userservice.userregister(user);
			 if(users!=null)
			 {
				 Long Userid=users.getUserid();
				 User_role userrole=new User_role();
				 userrole.setUserid(users.getUserid());
				 userrole.setRoleid(Long.valueOf(params.get("role")));
				 user_role.saveuserrole(userrole);
				 return "success";
			 }else {
				 return "error";
			 }
		 }
	  //用户删除
		 @RequestMapping(value = "/hcdelete")
	    public String hcdelete(HttpServletRequest reuqest,@RequestBody Map<String, String> params) {			
			Long userid = Long.valueOf(params.get("userid"));
			int tag = userRepository.deleteuserbyid(userid);
			if(tag == 0) {
				return "error";
			}else {
				return "success";
			}
	    }
		//文章删除
		 @RequestMapping(value = "/articleDelete")
	    public String articleDelete(HttpServletRequest reuqest,@RequestBody Map<String, String> params) {			
			Long articleid = Long.valueOf(params.get("articleid"));
			int tag = articleRepository.deleteArticle(articleid);
			if(tag == 0) {
				return "error";
			}else {
				return "success";
			}
	    }
		//多用户删除
		 @RequestMapping(value = "/hcdeletes")
	    public String hcdeletes(HttpServletRequest reuqest,@RequestBody Map<String, Object> params) {
			 List<Long> userids = (List<Long>) params.get("userids");
			 int tag = userRepository.deleteuserbyids(userids);
			 if(tag == 0) {
					return "error";
				}else {
					return "success";
				}
	    }
		//多文章删除
		 @RequestMapping(value = "/ArticleDeletes")
	    public String ArticleDeletes(HttpServletRequest reuqest,@RequestBody Map<String, Object> params) {
			 List<Long> articleids = (List<Long>) params.get("articleids");
			 int tag = articleRepository.deleteArticles(articleids);
			 if(tag == 0) {
					return "error";
				}else {
					return "success";
				}
	    }
	  //实时信息
		 @RequestMapping(value = "/findInformation")
	    public String findInformation(HttpServletRequest reuqest) {
			 JSONObject result = new JSONObject();
			    result.put("articleSum", articleRepository.findAtricleCOUNT());
			    result.put("userSum", userRepository.findUserCount());
			    result.put("browseSum", webInformationRepository.findSumBrowes());
			    return result.toJSONString();
	    }
		//可视化信息
		 @RequestMapping(value = "/findChange")
	    public List<WebInformation> findChange(HttpServletRequest reuqest) {
			 return webInformationRepository.findAll();
	    }
	  //实时信息
		 @RequestMapping(value = "/getAllType")
	    public String getAllType(HttpServletRequest reuqest) {
			 JSONObject result = new JSONObject();
			   	List<Class> typelist = classRepository.findAll();
			   	for(Class clas : typelist) {
				   	result.put(String.valueOf(clas.getClassid()),clas.getClassname());
			   	}
			    return result.toJSONString();
	    }
	  //Setting信息
		 @RequestMapping(value = "/getAllSetting")
	    public String getAllSetting(HttpServletRequest reuqest) {
			 JSONObject result = new JSONObject();
			   	WebSetting webs = websRepository.findWebSetting();
			   	result.put("fileNum", webs.getFileNum());
			   	result.put("articleNum", webs.getArticleNum());
			   	result.put("collectionNum", webs.getCollectionNum());
			   	result.put("fileSize", webs.getFileSize());
			    return result.toJSONString();
	    }
		//areaSetting信息
		 @RequestMapping(value = "/getAllareaSetting")
	    public String getAllareaSetting(HttpServletRequest reuqest) {
			 JSONObject result = new JSONObject();
			   	AreaSetting as = areaRepository.findAreaSetting();
			   	result.put("website_business",as.getWebsite_business());
			   	result.put("website_class",as.getWebsite_class() );
			   	result.put("website_function", as.getWebsite_function());
			   	result.put("website_publish", as.getWebsite_publish());
			   	result.put("website_relevant", as.getWebsite_relevant());
			    return result.toJSONString();
	    }
		//查询所有用户
			@RequestMapping(value="/findAllUser",produces = "application/json;charset=UTF-8")
			public @ResponseBody String findAllUser(@RequestParam(value="page") String page,
										 @RequestParam(value="limit") String limit,
										 @RequestParam(value="searchParams") String searchParams) throws Exception { 
				JSONObject json=new JSONObject();
				JSONObject jsStr = JSONObject.parseObject(searchParams);
				try{
					//当前页
					Integer tpage=Integer.parseInt(page.trim());
					//每页的数量
					Integer size=Integer.parseInt(limit.trim());
					List<FKUser> userlist = null;
					List<TransferUser> userlistTransfer = new ArrayList<TransferUser>();
					List<Integer> Userids = new ArrayList<Integer>();
					int count;
					if(searchParams == "" || searchParams == null) {
						userlist=userRepository.findAllUserByPage((tpage-1)*size, size,"%%","%%");
						for(FKUser user : userlist) {
								userlistTransfer.add(transferData(user));											
						}
						count=userRepository.findUserCount();
					}else {
						if(jsStr.get("role").equals("")) {
							userlist=userRepository.findAllUserByPage((tpage-1)*size, size,"%"+(String)jsStr.get("username")+"%","%"+(String)jsStr.get("loginname")+"%");
							for(FKUser user : userlist) {							
								userlistTransfer.add(transferData(user));										
							}
							count=userRepository.findUserCount2("%"+(String)jsStr.get("username")+"%","%"+(String)jsStr.get("loginname")+"%");
						}else {
							List<Integer> useridList = userroleRepository.selectUseridByRoleid(Long.parseLong((String) jsStr.get("role")));
							if(useridList.size() ==0) {
								count=0;
							}else {
								userlist=userRepository.findAllUserByPage2((tpage-1)*size, size,"%"+(String)jsStr.get("username")+"%","%"+(String)jsStr.get("loginname")+"%",useridList);
								for(FKUser user : userlist) {							
									userlistTransfer.add(transferData(user));										
								}
								count=userRepository.findUserCount3("%"+(String)jsStr.get("username")+"%","%"+(String)jsStr.get("loginname")+"%",useridList);
							}											
						}						
					}								
					if(userlistTransfer.size() != 0){
						json.put("msg","");
						json.put("code",0);
						json.put("count",count);
						json.put("data",userlistTransfer);
						System.out.println("成功返回");
					}else{
						json.put("msg","无数据");
						json.put("code","1");
						System.out.println("错误返回");
					}
					return json.toString();
				}catch (Exception ex){
					ex.printStackTrace();
					return json.toString();
				}
			}
			//查询所有文章
			@RequestMapping(value="/findAllArticle",produces = "application/json;charset=UTF-8")
			public @ResponseBody String findAllArticle(@RequestParam(value="page") String page,
										 @RequestParam(value="limit") String limit,
										 @RequestParam(value="searchParams") String searchParams) throws Exception {
					JSONObject json=new JSONObject();
					JSONObject jsStr = JSONObject.parseObject(searchParams);
					try {
						//当前页
						Integer tpage=Integer.parseInt(page.trim());
						//每页的数量
						Integer size=Integer.parseInt(limit.trim());
						List<Article> articlelist = null;
						List<TransferArtilce> articlelistTransfer = new ArrayList<TransferArtilce>();
						Long count;
						///System.out.println((String)jsStr.get("date"));
						if(searchParams == "" || searchParams == null) {
							count = articleRepository.findAtricleCOUNT();
							articlelist = articleRepository.findAllArticleByPage((tpage-1)*size, size,"%%","%%","%%");
							for(Article article : articlelist) {							
								articlelistTransfer.add(transferData2(article));										
							}		
						}else {							
							List<Long> typelist =new ArrayList<Long>();
							try {
								typelist = (List<Long>)jsStr.get("type");
							}catch(Exception e) {
								e.printStackTrace();
							}
							System.out.println("获取的参数"+typelist);
							if(typelist.size() != 0) {///// 
								List<Integer> articleid = articleclassRepository.findArticleidByClassid2(typelist);
								System.out.println("筛选前"+articleid);
								List<Long> filterid = new ArrayList<Long>();
								HashMap<Integer,Integer> map = new HashMap<>();
								for(Integer aid : articleid) {
									if(map.get(aid)!=null) {
										   map.put(aid,map.get(aid)+1);
									   }else {
										   map.put(aid,1);
									   }
								}
								for (Integer key : map.keySet()) {
								      if(map.get(key) == typelist.size()) {
								    	  filterid.add(Long.valueOf(key));
								      }
								    }
								System.out.println("我是筛选后的东西"+filterid);
								if(filterid.size() != 0) {
									count = articleRepository.findAtricleCOUNT3("%"+(String)jsStr.get("username")+"%","%"+(String)jsStr.get("date")+"%","%"+(String)jsStr.get("title")+"%",filterid);
									articlelist = articleRepository.findAllArticleByPage2((tpage-1)*size, size,"%"+(String)jsStr.get("username")+"%","%"+(String)jsStr.get("date")+"%","%"+(String)jsStr.get("title")+"%",filterid);
									for(Article article : articlelist) {							
										articlelistTransfer.add(transferData2(article));										
									}
								}else {
									count = 0L;								
								}					
							}else {
								count = articleRepository.findAtricleCOUNT2("%"+(String)jsStr.get("username")+"%","%"+(String)jsStr.get("date")+"%","%"+(String)jsStr.get("title")+"%");
								articlelist = articleRepository.findAllArticleByPage((tpage-1)*size, size,"%"+(String)jsStr.get("username")+"%","%"+(String)jsStr.get("date")+"%","%"+(String)jsStr.get("title")+"%");
								for(Article article : articlelist) {							
									articlelistTransfer.add(transferData2(article));										
								}
							}
						}						
						if(articlelistTransfer.size() != 0){
							json.put("msg","");
							json.put("code",0);
							json.put("count",count);
							json.put("data",articlelistTransfer);
							System.out.println("成功返回");
						}else{
							json.put("msg","无数据");
							json.put("code","1");
							System.out.println("错误返回");
						}
						return json.toString();
					}catch (Exception ex){
						ex.printStackTrace();
						return json.toString();
					}					
				}
			private TransferUser  transferData(FKUser user){
				TransferUser tmp = new TransferUser();
				tmp.setUserid(user.getUserid());
				if(user.getFirstlogintime() != null) {
					tmp.setFirstlogintime(user.getFirstlogintime().getTime().toLocaleString());
				}else {
					tmp.setFirstlogintime(null);
				}
				if(user.getLastlogintime() != null) {
					tmp.setLastlogintime(user.getLastlogintime().getTime().toLocaleString());
				}else {
					tmp.setLastlogintime(null);
				}
				String tmpRole = null;
				for(FKRole role : user.getRoles()) {
					if(tmpRole != null) {
						tmpRole = tmpRole + "," + role.getAuthority();
					}else {
						tmpRole = role.getAuthority();
					}
				}
				tmp.setRoles(tmpRole);
				tmp.setUsername(user.getUsername());
				tmp.setLoginname(user.getLoginname());
				tmp.setAutograph(user.getAutograph());
				tmp.setFanscount(user.getFanscount());
				tmp.setFollowcount(user.getFollowcount());
				tmp.setPassword(user.getPassword());
				return tmp;
			}
			private TransferArtilce  transferData2(Article article){
				TransferArtilce tmp = new TransferArtilce();
				tmp.setArticleid(article.getArticleid());
				tmp.setReleasetime(article.getReleasetime());
				tmp.setTitle(article.getTitle());
				tmp.setUsername(article.getUsername());
				tmp.setCommentcount(article.getCommentCount());
				tmp.setReadcount(article.getReadCount());
				tmp.setAgreedcount(article.getAgreedCount());
				tmp.setArticleurl(article.getArticleurl());
				tmp.setHosturl(article.getHosturl());
				List<Long> Classid = articleclassRepository.findClassidByArticleid2(article.getArticleid());
				if(Classid.size() == 0) {
					tmp.setClassname("");
				}else {
					List<Class> clas = classRepository.findClassidByArticleid(Classid);
					String tmpclass = null;
					for(Class cla : clas) {
						if(tmpclass != null) {
							tmpclass = tmpclass + "," + cla.getClassname();
						}else {
							tmpclass = cla.getClassname();
						}
					}
					tmp.setClassname(tmpclass);
				}				
				return tmp;
			}
}
