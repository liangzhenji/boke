package Blog.Blogtest.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import Blog.Blogtest.bean.Article;
import Blog.Blogtest.bean.Class;
import Blog.Blogtest.bean.FKUser;
import Blog.Blogtest.bean.User_role;
import Blog.Blogtest.bean.Wxtmplogin;
import Blog.Blogtest.repository.AreaRepository;
import Blog.Blogtest.service.AgreedService;
import Blog.Blogtest.service.ArticleService;
import Blog.Blogtest.service.ClassService;
import Blog.Blogtest.service.CommentService;
import Blog.Blogtest.service.FollowService;
import Blog.Blogtest.service.UserService;
import Blog.Blogtest.service.User_roleService;
import Blog.Blogtest.service.WxtmploginService;
import Blog.Blogtest.utils.CheckoutUtil;
//页面跳转控制器
@Controller
public class AppController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private AreaRepository areaRepository;
	
	@Resource
	private ArticleService articleService;
	
	@Resource
	private UserService userservice;
	
	@Resource
	private ClassService classservice;
	
	@Resource
	private WxtmploginService wxtmp;
	
	@Resource
	private CommentService commentservice;
	
	@Resource
	private User_roleService user_role;
	
	@Resource
	private FollowService followservice;
	
	@Resource
	private AgreedService agreedservice;
	//根路径跳转主页面
	@RequestMapping("/")
    public String indexs() {
        return "home";
    }
	//替代微信二维码的登录链接
	@GetMapping(value = "/wxLogin")
    public String get() {
		String wx_token_url= environment.getProperty("wx_token_url");
		String wx_appid= environment.getProperty("wx_appid");
		String wx_redirect_url= environment.getProperty("wx_redirect_url");
        String url = wx_token_url + "appid=" + wx_appid + "&redirect_uri=" + wx_redirect_url + "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redi";
        return "redirect:" + url;
    }
	//微信登录回调控制器，获取用户信息
	@RequestMapping(value = "/getcode")
	@ResponseBody
    public void getCode(String code){
        // 根据Code获取Openid
		String wx_token_url= environment.getProperty("wx_token_url");
		String wx_appid= environment.getProperty("wx_appid");
		String wx_redirect_url= environment.getProperty("wx_redirect_url");
		String wx_openid_url= environment.getProperty("wx_openid_url");
		String wx_secret= environment.getProperty("wx_secret");
		String wx_userinfo_url= environment.getProperty("wx_userinfo_url");
        String openidUrl = wx_openid_url + "appid=" + wx_appid + "&secret=" + wx_secret + "&code=" + code + "&grant_type=authorization_code";
        String openidMsg = CheckoutUtil.doPost(openidUrl, "", "UTF-8");
        // 解析返回信息
        JSONObject result = JSON.parseObject(openidMsg);
        // 网页授权接口调用凭证
        String access_token = result.getString("access_token");
        // 用户刷新access_token
        String refresh_token = result.getString("refresh_token");
        // 用户唯一标识
        String openid = result.getString("openid");
        // 拉取用户信息
        String userInfoUrl = wx_userinfo_url + "access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
        String userInfoMsg = CheckoutUtil.doPost(userInfoUrl, "", "UTF-8");
        // 解析返回信息
        JSONObject userInfo = JSON.parseObject(userInfoMsg);
        //微信临时登录用户
        Wxtmplogin tmplogin=new Wxtmplogin();
        tmplogin.setId(1L);
        tmplogin.setLoginName(userInfo.getString("openid"));
        tmplogin.setPassword("123456");
        tmplogin.setUserName(userInfo.getString("nickname"));
        wxtmp.savewxtmplogin(tmplogin);
        //查询用户是否存在
        try {
        FKUser ownuser=userservice.getUserByLoginname(userInfo.getString("openid"));
        FKUser user=new FKUser();
        //用户不存在则创建，存在则更新信息
        if(ownuser == null) {
        //创建用户
        user.setPassword("e10adc3949ba59abbe56e057f20f883e");
        user.setLoginname(userInfo.getString("openid"));
        user.setUsername(userInfo.getString("nickname"));
        user.setHosturl("/user/page?loginame="+userInfo.getString("openid"));
        user.setLastlogintime(Calendar.getInstance());
        user.setImageurl(userInfo.getString("headimgurl"));
        user.setFirstlogintime(Calendar.getInstance());
        FKUser userown1=userservice.userregister(user);
        //创建用户权限
        Long Userid=userown1.getUserid();
        User_role userrole=new User_role();
		 userrole.setUserid(Userid);
		 userrole.setRoleid(3l);
		 user_role.saveuserrole(userrole);
        }else {
        	 //更新用户信息
        	 Long userid=ownuser.getUserid();
        	 userservice.updatewxuser(userid,new Date(),userInfo.getString("nickname"),userInfo.getString("headimgurl"));
        }
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }
     //登录页面
	 @RequestMapping(value = "/login")
    public String login(WebRequest webrequest) {
		 try { 
				Wxtmplogin tmplogin=wxtmp.findwxtmplogin();
				String password=tmplogin.getPassword();
				String loginname=tmplogin.getLoginName();
				if(password!=null && loginname!=null) {
					webrequest.setAttribute("password", tmplogin.getPassword(),webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("loginname", tmplogin.getLoginName(),webrequest.SCOPE_REQUEST);
				}
				}catch(Exception e) {
					
				}
        return "login";
    }
	 //测试页面
	 @RequestMapping(value = "/index")
	    public String index() {
	        return "index";
	    }
	 //网站注册条约
	 @RequestMapping(value="/agreement")
	 public String agreetment() {
		 return "agreement";
	 }
	 //注册页面
	 @RequestMapping(value="/register")
	 public String register() {
		 return "register";
	 }
	//用户已关注博客文章
		 @RequestMapping("/follow")
		 public String homePagefollow(WebRequest webrequest,@RequestParam(value="pageid",defaultValue="1")int pageid,Article article) {
			 //用户ID
			 try {
			    	webrequest.setAttribute("user", getUsername(),webrequest.SCOPE_REQUEST);
			    	webrequest.setAttribute("role", getAuthority(),webrequest.SCOPE_REQUEST);
			    	FKUser user=getuser();
			    	webrequest.setAttribute("userhost", user.getHosturl(),webrequest.SCOPE_REQUEST);
			    	Long userid=user.getUserid();
			    	//用户已赞页文章信息
			    	String catearticlename="已关注博客文章";
			    	webrequest.setAttribute("pagesort","follow",webrequest.SCOPE_REQUEST);
			    	webrequest.setAttribute("pagesort1","",webrequest.SCOPE_REQUEST);
			    	webrequest.setAttribute("articlepage",catearticlename,webrequest.SCOPE_REQUEST);
			    	Sort sort = new Sort(Sort.Direction.DESC, "Articleid");
					Pageable page = PageRequest.of(pageid - 1, 8, sort);
					
					List<Long> followuserid=followservice.findfollowuseridByuserid(userid);
					
					Page<Article> articleDatas = articleService.findByUserid(followuserid, page);
					// 查询出的结果数据集合
					List<Article> articles = articleDatas.getContent();
					System.out.println("查询当前页面的集合:" + articles);
					//修剪文章过长
					for(Article ar : articles){
						String Context=ar.getContext();
						String Title=ar.getTitle();
						String Username=ar.getUsername();
						ar.setContext(Context.length() > 300 ? Context.substring(0, 300)+"...":Context);
						ar.setTitle(Title.length()>20 ? Title.substring(0, 20)+"...":Title);
						ar.setUsername(Username.length()>20 ? Username.substring(0, 20)+"..." :Username);
						 }		
					webrequest.setAttribute("articles",articles, webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("total",articleDatas.getTotalPages(), webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("ele",articleDatas.getNumber() + 1, webrequest.SCOPE_REQUEST);
			    	}catch(Exception e){
			    	}
			    	//热点分类信息
			    	List<Class> hostclass=classservice.findhotclass();
			    	webrequest.setAttribute("hostclass",hostclass,webrequest.SCOPE_REQUEST);
			    	//阅读最多
			    	List<Article> readarticle=articleService.findreadmax_article();
			    	webrequest.setAttribute("readarticle",readarticle,webrequest.SCOPE_REQUEST);
			    	//评论最多
			    	List<Article> commentarticle=articleService.findcommetntmax_article();
			    	webrequest.setAttribute("commentarticle",commentarticle,webrequest.SCOPE_REQUEST);
			 return "home";
		 }
	 //用户已点赞文章
	 @RequestMapping("/agreed")
	 public String homePageagreed(WebRequest webrequest,@RequestParam(value="pageid",defaultValue="1")int pageid,Article article) {
		 //用户ID
		 try {
		    	webrequest.setAttribute("user", getUsername(),webrequest.SCOPE_REQUEST);
		    	webrequest.setAttribute("role", getAuthority(),webrequest.SCOPE_REQUEST);
		    	FKUser user=getuser();
		    	webrequest.setAttribute("userhost", user.getHosturl(),webrequest.SCOPE_REQUEST);
		    	Long userid=user.getUserid();
		    	//用户已赞页文章信息
		    	String catearticlename="已赞文章";
		    	webrequest.setAttribute("pagesort","agreed",webrequest.SCOPE_REQUEST);
		    	webrequest.setAttribute("pagesort1","",webrequest.SCOPE_REQUEST);
		    	webrequest.setAttribute("articlepage",catearticlename,webrequest.SCOPE_REQUEST);
		    	Sort sort = new Sort(Sort.Direction.DESC, "Articleid");
				Pageable page = PageRequest.of(pageid - 1, 8, sort);
				
				List<Long> articleid=agreedservice.findArticleidByuserid(userid);
				
				Page<Article> articleDatas = articleService.findByArticleid(articleid, page);
				// 查询出的结果数据集合
				List<Article> articles = articleDatas.getContent();
				System.out.println("查询当前页面的集合:" + articles);
				//修剪文章过长
				for(Article ar : articles){
					String Context=ar.getContext();
					String Title=ar.getTitle();
					String Username=ar.getUsername();
					ar.setContext(Context.length() > 300 ? Context.substring(0, 300)+"...":Context);
					ar.setTitle(Title.length()>20 ? Title.substring(0, 20)+"...":Title);
					ar.setUsername(Username.length()>20 ? Username.substring(0, 20)+"..." :Username);
					 }		
				webrequest.setAttribute("articles",articles, webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("total",articleDatas.getTotalPages(), webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("ele",articleDatas.getNumber() + 1, webrequest.SCOPE_REQUEST);
		    	}catch(Exception e){
		    	}
		    	//热点分类信息
		    	List<Class> hostclass=classservice.findhotclass();
		    	webrequest.setAttribute("hostclass",hostclass,webrequest.SCOPE_REQUEST);
		    	//阅读最多
		    	List<Article> readarticle=articleService.findreadmax_article();
		    	webrequest.setAttribute("readarticle",readarticle,webrequest.SCOPE_REQUEST);
		    	//评论最多
		    	List<Article> commentarticle=articleService.findcommetntmax_article();
		    	webrequest.setAttribute("commentarticle",commentarticle,webrequest.SCOPE_REQUEST);
		 return "home";
	 }
	 //用户已评文章列表
	 @RequestMapping("/evaluate")
	 public String homePageEvaluate(WebRequest webrequest,@RequestParam(value="pageid",defaultValue="1")int pageid,Article article) {
		 //用户ID
		 try {
		    	webrequest.setAttribute("user", getUsername(),webrequest.SCOPE_REQUEST);
		    	webrequest.setAttribute("role", getAuthority(),webrequest.SCOPE_REQUEST);
		    	FKUser user=getuser();
		    	webrequest.setAttribute("userhost", user.getHosturl(),webrequest.SCOPE_REQUEST);
		    	Long userid=user.getUserid();
		    	//用户已评分页文章信息
		    	String catearticlename="已评文章";
		    	webrequest.setAttribute("pagesort","evaluate",webrequest.SCOPE_REQUEST);
		    	webrequest.setAttribute("pagesort1","",webrequest.SCOPE_REQUEST);
		    	webrequest.setAttribute("articlepage",catearticlename,webrequest.SCOPE_REQUEST);
		    	Sort sort = new Sort(Sort.Direction.DESC, "Articleid");
				Pageable page = PageRequest.of(pageid - 1, 8, sort);
				List<Long> Articleid=commentservice.findCommentArticleid(userid);
				Page<Article> articleDatas = articleService.findByArticleid(Articleid, page);
				// 查询出的结果数据集合
				List<Article> articles = articleDatas.getContent();
				System.out.println("查询当前页面的集合:" + articles);
				//修剪文章过长
				for(Article ar : articles){
					String Context=ar.getContext();
					String Title=ar.getTitle();
					String Username=ar.getUsername();
					ar.setContext(Context.length() > 300 ? Context.substring(0, 300)+"...":Context);
					ar.setTitle(Title.length()>20 ? Title.substring(0, 20)+"...":Title);
					ar.setUsername(Username.length()>20 ? Username.substring(0, 20)+"..." :Username);
					 }		
				webrequest.setAttribute("articles",articles, webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("total",articleDatas.getTotalPages(), webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("ele",articleDatas.getNumber() + 1, webrequest.SCOPE_REQUEST);
		    	}catch(Exception e){
		    	}
		    	//热点分类信息
		    	List<Class> hostclass=classservice.findhotclass();
		    	webrequest.setAttribute("hostclass",hostclass,webrequest.SCOPE_REQUEST);
		    	//阅读最多
		    	List<Article> readarticle=articleService.findreadmax_article();
		    	webrequest.setAttribute("readarticle",readarticle,webrequest.SCOPE_REQUEST);
		    	//评论最多
		    	List<Article> commentarticle=articleService.findcommetntmax_article();
		    	webrequest.setAttribute("commentarticle",commentarticle,webrequest.SCOPE_REQUEST);
		 return "home";
	 }
	 //网站文章分类列表
	 @RequestMapping("cate")
	 public String homePageBycate(WebRequest webrequest,@RequestParam(value="pageid",defaultValue="1")int pageid,Article article,Long cate) {
		 try {
		    	webrequest.setAttribute("user", getUsername(),webrequest.SCOPE_REQUEST);
		    	webrequest.setAttribute("role", getAuthority(),webrequest.SCOPE_REQUEST);
		    	FKUser user=getuser();
		    	webrequest.setAttribute("userhost", user.getHosturl(),webrequest.SCOPE_REQUEST);
		    	}catch(Exception e){
		    	}
		    	//热点分类信息
		    	List<Class> hostclass=classservice.findhotclass();
		    	webrequest.setAttribute("hostclass",hostclass,webrequest.SCOPE_REQUEST);
		    	//阅读最多
		    	List<Article> readarticle=articleService.findreadmax_article();
		    	webrequest.setAttribute("readarticle",readarticle,webrequest.SCOPE_REQUEST);
		    	//评论最多
		    	List<Article> commentarticle=articleService.findcommetntmax_article();
		    	webrequest.setAttribute("commentarticle",commentarticle,webrequest.SCOPE_REQUEST);
		    	//分类分页文章信息
		    	webrequest.setAttribute("pagesort","cate",webrequest.SCOPE_REQUEST);
		    	webrequest.setAttribute("pagesort1","&cate="+cate,webrequest.SCOPE_REQUEST);
		    	Class catearticle=classservice.findByClassid(cate);
		    	String catearticlename=catearticle.getClassname();
		    	webrequest.setAttribute("articlepage",catearticlename,webrequest.SCOPE_REQUEST);
		    	Sort sort = new Sort(Sort.Direction.DESC, "Articleid");
				Pageable page = PageRequest.of(pageid - 1, 8, sort);
				Page<Article> articleDatas = articleService.findarticlebycate(cate, page);
				// 查询出的结果数据集合
				List<Article> articles = articleDatas.getContent();
				System.out.println("查询当前页面的集合:" + articles);
				//修剪文章过长
				for(Article ar : articles){
					String Context=ar.getContext();
					String Title=ar.getTitle();
					String Username=ar.getUsername();
					ar.setContext(Context.length() > 300 ? Context.substring(0, 300)+"...":Context);
					ar.setTitle(Title.length()>20 ? Title.substring(0, 20)+"...":Title);
					ar.setUsername(Username.length()>20 ? Username.substring(0, 20)+"..." :Username);
					 }		
				webrequest.setAttribute("articles",articles, webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("total",articleDatas.getTotalPages(), webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("ele",articleDatas.getNumber() + 1, webrequest.SCOPE_REQUEST);
		 return "home";
	 }
	 //主页面
    @RequestMapping("/home")
    public String homePage(WebRequest webrequest,@RequestParam(value="pageid",defaultValue="1")int pageid,Article article,@RequestParam(value="content",defaultValue="")String content){
    	//用户信息
    	try {
    	webrequest.setAttribute("user", getUsername(),webrequest.SCOPE_REQUEST);
    	webrequest.setAttribute("role", getAuthority(),webrequest.SCOPE_REQUEST);
    	FKUser user=getuser();
    	webrequest.setAttribute("userhost", user.getHosturl(),webrequest.SCOPE_REQUEST);
    	}catch(Exception e){
    	}
    	//热点分类信息
    	List<Class> hostclass=classservice.findhotclass();
    	webrequest.setAttribute("hostclass",hostclass,webrequest.SCOPE_REQUEST);
    	//阅读最多
    	List<Article> readarticle=articleService.findreadmax_article();
    	webrequest.setAttribute("readarticle",readarticle,webrequest.SCOPE_REQUEST);
    	//评论最多
    	List<Article> commentarticle=articleService.findcommetntmax_article();
    	webrequest.setAttribute("commentarticle",commentarticle,webrequest.SCOPE_REQUEST);
    	//最新分页文章信息
    	String catearticlename="最新发布";
    	webrequest.setAttribute("pagesort","home",webrequest.SCOPE_REQUEST);
    	webrequest.setAttribute("pagesort1","",webrequest.SCOPE_REQUEST);
    	webrequest.setAttribute("articlepage",catearticlename,webrequest.SCOPE_REQUEST);
    	Sort sort = new Sort(Sort.Direction.DESC, "Articleid");
		Pageable page = PageRequest.of(pageid - 1, 8, sort);
		Page<Article> articleDatas = articleService.findAll(page);
		// 查询出的结果数据集合
		List<Article> articles = articleDatas.getContent();
		System.out.println("查询当前页面的集合:" + articles);
		//修剪文章过长
		for(Article ar : articles){
			String Context=ar.getContext();
			String Title=ar.getTitle();
			String Username=ar.getUsername();
			ar.setContext(Context.length() > 300 ? Context.substring(0, 300)+"...":Context);
			ar.setTitle(Title.length()>20 ? Title.substring(0, 20)+"...":Title);
			ar.setUsername(Username.length()>20 ? Username.substring(0, 20)+"..." :Username);
			 }
		webrequest.setAttribute("articles",articles, webrequest.SCOPE_REQUEST);
		webrequest.setAttribute("total",articleDatas.getTotalPages(), webrequest.SCOPE_REQUEST);
		webrequest.setAttribute("ele",articleDatas.getNumber() + 1, webrequest.SCOPE_REQUEST);
		System.out.println(articleDatas.getNumber() + 1);
		
        return "home";
    }
  //管理员界面
    @RequestMapping(value = "/search")
    public String search(WebRequest webrequest,@RequestParam(value="content",defaultValue="")String content) {
    	
        return "redirect:/home?content="+content;
    }
    //管理员界面
    @RequestMapping(value = "/admin")
    public String adminPage(WebRequest webrequest) {
    	try {
        	webrequest.setAttribute("user", getUsername(),webrequest.SCOPE_REQUEST);
        	webrequest.setAttribute("role", getAuthority(),webrequest.SCOPE_REQUEST);
        	}catch(Exception e){
        	}
        return "admin";
    }
    //拒绝访问页面
    @RequestMapping(value = "/accessDenied")
	public String accessDeniedPage(Model model) {
		model.addAttribute("user", getUsername());
		model.addAttribute("role", getAuthority());
		return "accessDenied";
	}
     //退出页面控制器 
   @RequestMapping(value="/logout")
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	   // Authentication是一个接口，表示用户认证信息
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// 如果用户认知信息不为空，注销
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		// 重定向到login页面
		return "redirect:/login?logout";
	}
   /**
    * 获得当前用户名称
    * */
   private String getUsername(){
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			System.out.println("username = " + username);
			return username;
		}catch(Exception e) {
			return null;
		}
		}
	    /**
	     * 获得当前用户权限
	     * */
	    private String getAuthority(){
			// 获得Authentication对象，表示用户认证信息。
	    	try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			List<String> roles = new ArrayList<String>();
			// 将角色名称添加到List集合
			for (GrantedAuthority a : authentication.getAuthorities()) {
				roles.add(a.getAuthority());
			}
			System.out.println("role = " + roles);
			return roles.toString();
	    	}catch(Exception e) {
	    		return null;
	    	}
		}
    //获取对应用户
    private FKUser getuser() {
    	try {
	    	String username=getUsername();
	    	FKUser user= userservice.getUserByUsername(username);
	    	return user;
	    	}catch(Exception e) {
	    		return null;
	    	}
    }
  //website_class
    @RequestMapping(value="/website_class")
	 public String website_classt(WebRequest webrequest) {
    	String data = areaRepository.findwebsite_class();
    	webrequest.setAttribute("data", data, webrequest.SCOPE_REQUEST);
		 return "website_class";
	 }
  //website_publish
    @RequestMapping(value="/website_publish")
	 public String website_publish(WebRequest webrequest) {
    	String data = areaRepository.findwebsite_publish();
    	webrequest.setAttribute("data", data, webrequest.SCOPE_REQUEST);
		 return "website_publish";
	 }
  //website_function
    @RequestMapping(value="/website_function")
	 public String website_function(WebRequest webrequest) {
    	String data = areaRepository.findwebsite_function();
    	webrequest.setAttribute("data", data, webrequest.SCOPE_REQUEST);
		 return "website_function";
	 }
  //website_relevant
    @RequestMapping(value="/website_relevant")
	 public String website_relevant(WebRequest webrequest) {
    	String data = areaRepository.findwebsite_relevant();
    	webrequest.setAttribute("data", data, webrequest.SCOPE_REQUEST);
		 return "website_relevant";
	 }
  //website_business
    @RequestMapping(value="/website_business")
	 public String website_business(WebRequest webrequest) {
    	String data = areaRepository.findwebsite_business();
    	webrequest.setAttribute("data", data, webrequest.SCOPE_REQUEST);
		 return "website_business";
	 }
    //article_detail
    @RequestMapping(value="/article_detail")
	 public String article_detail() {
		 return "article_detail";
	 }
    //测试页面
    @RequestMapping(value="/article")
	 public String article() {
		 return "article";
	 }
    //测试页面
    @RequestMapping(value="/moodList")
	 public String moodList() {
		 return "moodList";
	 }
    //测试页面
    @RequestMapping(value="/comment")
	 public String comment() {
		 return "comment";
	 }
}
