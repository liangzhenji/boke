package Blog.Blogtest.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import Blog.Blogtest.bean.Article;
import Blog.Blogtest.bean.Article_class;
import Blog.Blogtest.bean.FKUser;
import Blog.Blogtest.bean.Letter;
import Blog.Blogtest.bean.Picture;
import Blog.Blogtest.bean.Class;
import Blog.Blogtest.bean.Collection;
import Blog.Blogtest.bean.Diary;
import Blog.Blogtest.service.AgreedService;
import Blog.Blogtest.service.ArticleService;
import Blog.Blogtest.service.Article_classService;
import Blog.Blogtest.service.ClassService;
import Blog.Blogtest.service.CollectionService;
import Blog.Blogtest.service.CommentService;
import Blog.Blogtest.service.DiaryService;
import Blog.Blogtest.service.FollowService;
import Blog.Blogtest.service.LetterService;
import Blog.Blogtest.service.PictureService;
import Blog.Blogtest.service.UserService;
import Blog.Blogtest.service.User_roleService;
import Blog.Blogtest.utils.CalendarUtil;

@Controller
public class ManageController {
	@Resource
	private UserService userservice;
	
	@Resource
	private FollowService followservice;
	
	@Resource
	private ArticleService articleservice;
	
	@Resource
	private Article_classService arclassservice;
	
	@Resource
	private ClassService classservice;
	
	@Resource
	private User_roleService user_role;
	
	@Resource
	private CollectionService collectionservice;
	
	@Resource
	private AgreedService agreedservice;
	
	@Resource
	private DiaryService diaryservice;
	
	@Resource
	private CommentService commentservice;
	
	@Resource
	private PictureService pictureservice;
	
	@Resource
	private Article_classService article_classservice ;
	
	@Resource
	private LetterService letterservice;
	 @RequestMapping("/manage/pagemanage")
	 public String pagemanage(WebRequest webrequest,@RequestParam(value="tag",defaultValue="")String tag,@RequestParam(value="pageid",defaultValue="1")int pageid,@RequestParam(value="pagetag",defaultValue="article")String pagetag) {
		 FKUser user=new FKUser();
		 try {			 
	     webrequest.setAttribute("user", getUsername(),webrequest.SCOPE_REQUEST);
		 webrequest.setAttribute("role", getAuthority(),webrequest.SCOPE_REQUEST);
		 user=userservice.getUserByUsername(getUsername());
		 webrequest.setAttribute("username", user.getUsername(), webrequest.SCOPE_REQUEST);//用户名
		 webrequest.setAttribute("userid", user.getUserid(), webrequest.SCOPE_REQUEST);//用户ID
		 webrequest.setAttribute("graph", user.getAutograph(), webrequest.SCOPE_REQUEST);//座右铭
		 webrequest.setAttribute("userurl", user.getHosturl(), webrequest.SCOPE_REQUEST);//用户主页链接
		 webrequest.setAttribute("Imageurl", user.getImageurl(), webrequest.SCOPE_REQUEST);
		 String tips=CalendarUtil.checktime(user.getFirstlogintime()); 
		 webrequest.setAttribute("hostage", tips, webrequest.SCOPE_REQUEST);//用户园龄
		 webrequest.setAttribute("fanscount", user.getFanscount(), webrequest.SCOPE_REQUEST);//用户粉丝数
		 webrequest.setAttribute("followcount", user.getFollowcount(), webrequest.SCOPE_REQUEST);//用户关注数
		 Long articlecount=articleservice.findCountByUserid(user.getUserid());
		 webrequest.setAttribute("articlecount", articlecount, webrequest.SCOPE_REQUEST);//文章总数
		 List<Class> cl=classservice.findAllClass();
		 webrequest.setAttribute("classlist", cl, webrequest.SCOPE_REQUEST);//文章标签
		 //留言板块
		 	Sort sort = new Sort(Sort.Direction.DESC, "Letterid");
			Pageable page2 = PageRequest.of(pageid - 1, 4, sort);
			Page<Letter> test1=letterservice.findByUserid(user.getUserid(), page2);
			List<Letter> test2=test1.getContent();
			webrequest.setAttribute("letterlist",test2, webrequest.SCOPE_REQUEST);
			webrequest.setAttribute("total",test1.getTotalPages(), webrequest.SCOPE_REQUEST);
			webrequest.setAttribute("ele",test1.getNumber() + 1, webrequest.SCOPE_REQUEST);
			webrequest.setAttribute("pagetag", pagetag, webrequest.SCOPE_REQUEST);
			webrequest.setAttribute("userdot", "&pagetag=letter", webrequest.SCOPE_REQUEST);
			//关注板块
			List<Long> fo=followservice.findfollowuseridByuserid(user.getUserid());
			Sort sort3 = new Sort(Sort.Direction.DESC, "Userid");
			Pageable page3 = PageRequest.of(pageid - 1, 4, sort3);
			Page<FKUser> test3=userservice.findByuserid(fo, page3);
			List<FKUser> test31=test3.getContent();
			webrequest.setAttribute("userlist",test31, webrequest.SCOPE_REQUEST);
			webrequest.setAttribute("total1",test3.getTotalPages(), webrequest.SCOPE_REQUEST);
			webrequest.setAttribute("ele1",test3.getNumber() + 1, webrequest.SCOPE_REQUEST);
			webrequest.setAttribute("userdot1", "&pagetag=follow", webrequest.SCOPE_REQUEST);
			//收藏板块
			List<Collection> coll=collectionservice.findByUserid(user.getUserid());
			HashMap<String, List<Collection>> newmap = new HashMap<String,List<Collection>>();
			for(Collection col : coll) {
				String sorts=col.getLabel();
				if(newmap.get(sorts)!=null) {
					List<Collection> oldcol=newmap.get(sorts);
					oldcol.add(col);
					newmap.put(sorts,oldcol);
				   }else {
					   List<Collection> newcol=new ArrayList<Collection>();
					   newcol.add(col);
					   newmap.put(sorts,newcol);
				   }
			}
			webrequest.setAttribute("collectionmap", newmap, webrequest.SCOPE_REQUEST);
		 }catch(Exception e) {
			 
		 }
		 webrequest.setAttribute("tag", tag, webrequest.SCOPE_REQUEST);//发布标志
		 return "pagemanage";
	 }
	 @RequestMapping(value="/manage/articlerelease")
	 public String articlerelease(WebRequest webrequest,String Title,String context,Long[] tag) {
		 Article arr=new Article();
		 int classtag=1;
		 try{Article ar=new Article();
		 ar.setTitle(Title);
		 ar.setContext(context);
		 Date d = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String str = sdf.format(d);
		 FKUser user=getuser();
		 ar.setReleasetime(str);
		 ar.setUserid(user.getUserid());
		 ar.setUsername(user.getUsername());
		 ar.setImageurl(user.getImageurl());
		 Long maxid=articleservice.findArticleMaxid()+1;
		 ar.setArticleurl("/user/page?loginame="+user.getLoginname()+"&articleid="+maxid);
		 ar.setHosturl(user.getHosturl());
		 ar.setAgreedCount(0L);
		 ar.setCommentCount(0L);
		 ar.setReadCount(0L);
		 arr=articleservice.saveArticle(ar);
		 for(Long tag1 : tag) {
			 article_classservice.savearticleclass(maxid, tag1);
			 classtag=0;
		 }
		 		 }catch(Exception e) {
			 
		 }
		 if(arr != null && classtag != 1) {
			 return "redirect:/manage/pagemanage?tag=true&pagetag=article";
		 }else {
			 return "redirect:/manage/pagemanage?tag=false&pagetag=article";
		 }
	 }
	 @RequestMapping(value="/manage/diaryrelease")
	 public String diaryrelease(WebRequest webrequest,String Title,String context) {
		 Diary dia=new Diary();
		 try{
			 FKUser user=getuser();
			 Diary di=new Diary();
			 di.setContext(context);
			 di.setUserid(user.getUserid());
			 di.setTitle(Title);
			 Date d = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String str = sdf.format(d);
			 di.setReleasetime(str);
			 di.setUsername(user.getUsername());
			 dia=diaryservice.savediary(di);
		 		 }catch(Exception e) {
			 
		 }
		 if(dia != null) {
			 return "redirect:/manage/pagemanage?tag=true&pagetag=diary";
		 }else {
			 return "redirect:/manage/pagemanage?tag=false&pagetag=diary";
		 }
	 }
	 @PostMapping(value="/manage/picturerelease")
	 public String picturerelease(HttpServletRequest reuqest,@RequestParam("Title") String Title,
			 @RequestParam("context") String context,
			 @RequestParam("visible") String visible,
			 @RequestParam("file") MultipartFile file) {
		 Boolean filetemp=false;
		 Picture pii=new Picture();
		 try{
			 if(!file.isEmpty()) {
				 String path = "D:\\Eclipse\\eclipse\\work\\Blogtest\\src\\main\\resources\\static\\testuserpicture";
				 String filename=file.getOriginalFilename();
				 File filepath=new File(path,filename);
				 if(!filepath.getParentFile().exists()) {
					 filepath.getParentFile().mkdirs();
				 }
				 file.transferTo(new File(path+File.separator+filename));
				 filetemp=true;
			 }else {
				 filetemp=false;
			 }
			 FKUser user=getuser();
		 		Date d = new Date();
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 String str = sdf.format(d);
				 Picture pi=new Picture();
		 		pi.setReleasetime(str);
		 		pi.setDescribe(context);
		 		String filename=file.getOriginalFilename();
		 		pi.setImageurl(filename);
		 		pi.setTitle(Title);
		 		pi.setVisible(visible=="true"?1L:0L);
		 		pi.setUserid(user.getUserid());
		 		pi.setUsername(user.getUsername());
		 		pii=pictureservice.savepicture(pi);
		 		 }catch(Exception e) {
		 		 
		 		 }		 		
		 if(pii != null && filetemp != null) {
			 return "redirect:/manage/pagemanage?tag=true&pagetag=picture";
		 }else {
			 return "redirect:/manage/pagemanage?tag=false&pagetag=picture";
		 }
	 }
	 @PostMapping(value="/manage/personal")
	 public String personalmanage(HttpServletRequest reuqest,@RequestParam(value="Username",defaultValue="default") String Username,
			 @RequestParam(value="Autograph",defaultValue="default") String Autograph,
			 @RequestParam(value="Password",defaultValue="default") String Password,
			 @RequestParam("file") MultipartFile file) {
		 Boolean filetemp=false;
		 int usernametag=0;
		 int autographtag=0;
		 int passwordtag=0;
		 try{
			 if(!file.isEmpty()) {
				 String path = "D:\\Eclipse\\eclipse\\work\\Blogtest\\src\\main\\resources\\static\\img\\userimg";
				 String filename=file.getOriginalFilename();
				 File filepath=new File(path,filename);
				 if(!filepath.getParentFile().exists()) {
					 filepath.getParentFile().mkdirs();
				 }
				 file.transferTo(new File(path+File.separator+filename));
				 int imgtag=userservice.updateuserimgurl(filename, getuser().getUserid());
				 filetemp=true;
				 if(imgtag == 0) {
					 filetemp=false;
				 }
			 }else {
				 filetemp=false;
			 }
			 	if(!Username.equals("default")) {
			 		usernametag=userservice.updateusername(Username, getuser().getUserid());
			 	}
			 	
			 	if(!Autograph.equals("default")) {
			 		autographtag=userservice.updateuserautograph(Autograph, getuser().getUserid());
			 	}
			 	
			 	if(!Password.equals("default")) {
			 		passwordtag=userservice.updateuserpassword(Password, getuser().getUserid());
			 	}
			    
		 		 }catch(Exception e) {
		 		 
		 		 }		 		
		 if(filetemp != null || usernametag==0 || autographtag==0 || passwordtag==0) {
			 return "redirect:/logout";
		 }else {
			 return "redirect:/logout";
		 }
	 }
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
}
