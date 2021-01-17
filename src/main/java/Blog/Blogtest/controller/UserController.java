package Blog.Blogtest.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import Blog.Blogtest.bean.FKUser;
import Blog.Blogtest.bean.Follow;
import Blog.Blogtest.bean.Picture;
import Blog.Blogtest.bean.Agreed;
import Blog.Blogtest.bean.Article;
import Blog.Blogtest.bean.Article_class;
import Blog.Blogtest.bean.Class;
import Blog.Blogtest.bean.Collection;
import Blog.Blogtest.bean.Comment;
import Blog.Blogtest.bean.Diary;
import Blog.Blogtest.bean.User_role;
import Blog.Blogtest.repository.ArticleRepository;
import Blog.Blogtest.repository.FollowRepository;
import Blog.Blogtest.service.AgreedService;
import Blog.Blogtest.service.ArticleService;
import Blog.Blogtest.service.Article_classService;
import Blog.Blogtest.service.ClassService;
import Blog.Blogtest.service.CollectionService;
import Blog.Blogtest.service.CommentService;
import Blog.Blogtest.service.DiaryService;
import Blog.Blogtest.service.FollowService;
import Blog.Blogtest.service.PictureService;
import Blog.Blogtest.service.UserService;
import Blog.Blogtest.service.User_roleService;
import Blog.Blogtest.utils.CalendarUtil;
import Blog.Blogtest.utils.MapUtil;
//主要用于用户的增删改查和进入用户主页
@Controller
@RequestMapping("/user")
public class UserController {

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
	//通过登录名查找用户
	 @RequestMapping("/findbyloginname")
	@ResponseBody
    public String findbyloginname(String name) {
		FKUser user=userservice.getUserByLoginname(name);
		if(user!=null) {
			return "true";
		}else {
			return "flase";
		}
    }
	 //通过用户名查找用户
	 @RequestMapping("/findbyusername")
		@ResponseBody
	    public String findbyusername(String name) {
		 String username="";
		 	try {
				username=URLDecoder.decode(name, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			FKUser user=userservice.getUserByUsername(username);
			if(user!=null) {
				return "true";
			}else {
				return "flase";
			}
	    }
	 //注册用户
	 @RequestMapping("/register")
	 @ResponseBody
	 public String register(String name,String account,String password) {
		 FKUser user=new FKUser();
		 user.setLoginname(name);
		 user.setUsername(account);
		 user.setPassword(password);
		 user.setHosturl("/user/page?loginame="+name);
		 user.setLastlogintime(Calendar.getInstance());
		 FKUser users=userservice.userregister(user);
		 if(users!=null)
		 {
			 Long Userid=users.getUserid();
			 User_role userrole=new User_role();
			 userrole.setUserid(Userid);
			 userrole.setRoleid(3l);
			 user_role.saveuserrole(userrole);
			 return "true";
		 }else {
			 return "false";
		 }
	 }
	 @RequestMapping("/page")
	 public String userpage(String loginame,WebRequest webrequest,@RequestParam(value="pageid",defaultValue="1")int pageid,Article article,Long articleid,String tags)  {
		 FKUser user=new FKUser();
		 try {			 
	     webrequest.setAttribute("user", getUsername(),webrequest.SCOPE_REQUEST);
		 webrequest.setAttribute("role", getAuthority(),webrequest.SCOPE_REQUEST);
		 user=userservice.getUserByLoginname(loginame);		 
		 webrequest.setAttribute("username", user.getUsername(), webrequest.SCOPE_REQUEST);//用户名
		 webrequest.setAttribute("userid", user.getUserid(), webrequest.SCOPE_REQUEST);//用户ID
		 webrequest.setAttribute("graph", user.getAutograph(), webrequest.SCOPE_REQUEST);//座右铭
		 webrequest.setAttribute("userurl", user.getHosturl(), webrequest.SCOPE_REQUEST);//用户主页链接
		 String tips=CalendarUtil.checktime(user.getFirstlogintime()); 
		 webrequest.setAttribute("hostage", tips, webrequest.SCOPE_REQUEST);//用户园龄
		 webrequest.setAttribute("fanscount", user.getFanscount(), webrequest.SCOPE_REQUEST);//用户粉丝数
		 webrequest.setAttribute("followcount", user.getFollowcount(), webrequest.SCOPE_REQUEST);//用户关注数
		 //获取用户标签数量
		 List<Long> articleids=articleservice.findArticleidByUserid(user.getUserid());
		 List<Long> arclass=arclassservice.findClassidByArticleid(articleids);
		 List<Class> classname=classservice.findclassnameByclassid(arclass);
		 HashMap<String, Integer> map = new HashMap<String,Integer>();
		 for(int  i=0;  i<arclass.size();  i++)  {  
			   Number  a  =  arclass.get(i);  
			   MapUtil.add(map, String.valueOf(a), 1);
			 }
		 HashMap<String, Integer> newmap=MapUtil.translate(map, classname);
		 HashMap<String, Integer> newsmap= MapUtil.sortHashMap(newmap);
		 webrequest.setAttribute("userarticlelable", newsmap, webrequest.SCOPE_REQUEST);
		 //获取阅读量排行榜
		 List<Article> areadsort=articleservice.findbyuseridreadmax(user.getUserid());
		 webrequest.setAttribute("readsortarticle", areadsort, webrequest.SCOPE_REQUEST);
		 //评论排行
		 List<Article> acommentsort=articleservice.findbyuseridcommentmax(user.getUserid());
		 webrequest.setAttribute("commentsortarticle", acommentsort, webrequest.SCOPE_REQUEST);
		 //分页查询用户最近文章
		 Sort sort = new Sort(Sort.Direction.DESC, "Articleid");
			Pageable page = PageRequest.of(pageid - 1, 8, sort);
			Page<Article> articleDatas = articleservice.findByUseridpage(user.getUserid(),page);
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
			//分页链接修饰
			webrequest.setAttribute("userdot","&loginame="+user.getLoginname(), webrequest.SCOPE_REQUEST);
			//关注按钮的动态变化
			Long followuserid=getuser().getUserid();
			String username=getuser().getUsername();
			webrequest.setAttribute("username1",username,webrequest.SCOPE_REQUEST);
			webrequest.setAttribute("followuserid",followuserid,webrequest.SCOPE_REQUEST);//登录用户的id
			if(getUsername() != null && getUsername() !="anonymousUser") {
				if(followuserid != user.getUserid() ) {
					if(followservice.findByUseridAndFollowuserid(user.getUserid(), followuserid) != null) {
						//已关注
						webrequest.setAttribute("followtag","false", webrequest.SCOPE_REQUEST);
					}else {
						//无关注关系
						webrequest.setAttribute("followtag","open", webrequest.SCOPE_REQUEST);
					}
				}else {
					//登录人与主页人一样
					webrequest.setAttribute("followtag","falseAndopen", webrequest.SCOPE_REQUEST);
				}
			}else {
				//未登录处理
				webrequest.setAttribute("followtag","open", webrequest.SCOPE_REQUEST);
			}
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
			////////////////////////////////////////////处理多种附加页面
			//文章详情页
			if(articleid != null) {
				//显示文章具体信息，分页文章和分页栏不显示
				webrequest.setAttribute("articlebyidlisttag","false", webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("articlebyidtag","false", webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("diarylisttag","false", webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("diarytag","false", webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("commentarticletag","open", webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("fenyearticletag","false", webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("fenyetag","false", webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("articledetailtag","open", webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("articleid",articleid, webrequest.SCOPE_REQUEST);//文章id
				Article ar=articleservice.findByArticleid(articleid);
				String articleurl=ar.getArticleurl();
				String charEncode = java.net.URLEncoder.encode(articleurl);
				webrequest.setAttribute("articledetail",ar, webrequest.SCOPE_REQUEST);//文章详情
				webrequest.setAttribute("articleurl",charEncode, webrequest.SCOPE_REQUEST);//文章url
				List<Article> ar1=articleservice.findByUserid(user.getUserid());
				int index=ar1.indexOf(ar);				
				try {			
					Article next=ar1.get(index-1);
				if(next != null) {
					webrequest.setAttribute("next",next, webrequest.SCOPE_REQUEST);//上文章
				}else {
					webrequest.setAttribute("next",null, webrequest.SCOPE_REQUEST);//上文章
				}
				}catch(Exception e) {}
				try {
					Article last=ar1.get(index+1);
				if(last != null) {
					webrequest.setAttribute("last",last, webrequest.SCOPE_REQUEST);//下文章
				}else {
					webrequest.setAttribute("last",null, webrequest.SCOPE_REQUEST);//下文章
				}
				}catch(Exception e) {}
				try {
					//分页显示评论
					Sort sort = new Sort(Sort.Direction.DESC, "Commentid");
					Pageable page = PageRequest.of(pageid - 1, 8, sort);
				Page<Comment> co=commentservice.findByCommenthostid(articleid,page);
				List<Comment> coo=co.getContent();		
				webrequest.setAttribute("commentlist",coo, webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("total",co.getTotalPages(), webrequest.SCOPE_REQUEST);
				webrequest.setAttribute("ele",co.getNumber() + 1, webrequest.SCOPE_REQUEST);
				//分页链接修饰
				webrequest.setAttribute("userdot","&loginame="+user.getLoginname()+"&articleid="+articleid, webrequest.SCOPE_REQUEST);
				
				}catch(Exception e) {
					
				}
			}else {
				//标记页
				if(tags != null) {
					switch(tags){
				    case "diary" :
				    	webrequest.setAttribute("articlebyidlisttag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("articlebyidtag","false", webrequest.SCOPE_REQUEST);
				    	webrequest.setAttribute("picturetag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("picturelisttag","false", webrequest.SCOPE_REQUEST);
				    	webrequest.setAttribute("diarylisttag","open", webrequest.SCOPE_REQUEST);
				    	webrequest.setAttribute("commentarticletag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("fenyearticletag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("fenyetag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("articledetailtag","false", webrequest.SCOPE_REQUEST);	
						webrequest.setAttribute("diarytag","open", webrequest.SCOPE_REQUEST);
						Sort sort = new Sort(Sort.Direction.DESC, "Diaryid");
						Pageable page = PageRequest.of(pageid - 1, 8, sort);
					Page<Diary> co=diaryservice.findByUserid(user.getUserid(),page);
					List<Diary> coo=co.getContent();
					webrequest.setAttribute("diarylist",coo, webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("total",co.getTotalPages(), webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("ele",co.getNumber() + 1, webrequest.SCOPE_REQUEST);
					//分页链接修饰
					webrequest.setAttribute("userdot","&loginame="+user.getLoginname()+"&tags=diary", webrequest.SCOPE_REQUEST);
				       break; //可选
				    case "picture" :
				    	webrequest.setAttribute("articlebyidlisttag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("articlebyidtag","false", webrequest.SCOPE_REQUEST);
				    	webrequest.setAttribute("diarylisttag","false", webrequest.SCOPE_REQUEST);
				    	webrequest.setAttribute("commentarticletag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("fenyearticletag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("fenyetag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("articledetailtag","false", webrequest.SCOPE_REQUEST);	
						webrequest.setAttribute("diarytag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("picturetag","open", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("picturelisttag","open", webrequest.SCOPE_REQUEST);
						Sort sort1 = new Sort(Sort.Direction.DESC, "Pictureid");
						Pageable page1 = PageRequest.of(pageid - 1, 4, sort1);
						Page<Picture> pt =pictureservice.findBypictureid(user.getUserid(), page1);
						List<Picture> ptt=pt.getContent();
						webrequest.setAttribute("picturelist",ptt, webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("total",pt.getTotalPages(), webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("ele",pt.getNumber() + 1, webrequest.SCOPE_REQUEST);
						//分页链接修饰
						webrequest.setAttribute("userdot","&loginame="+user.getLoginname()+"&tags=picture", webrequest.SCOPE_REQUEST);
				       //语句
				       break; //可选
				    //你可以有任意数量的case语句
				    default : //可选
				       //语句
				    	webrequest.setAttribute("diarylisttag","false", webrequest.SCOPE_REQUEST);
				    	webrequest.setAttribute("commentarticletag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("fenyearticletag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("fenyetag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("articledetailtag","false", webrequest.SCOPE_REQUEST);	
						webrequest.setAttribute("diarytag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("picturetag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("picturelisttag","false", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("articlebyidlisttag","open", webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("articlebyidtag","open", webrequest.SCOPE_REQUEST);
				    	try {
				    	Long classid=classservice.findClassidByClassname(tags);
				    	Sort sort2 = new Sort(Sort.Direction.DESC, "articleid");
						Pageable page2 = PageRequest.of(pageid - 1, 8, sort2);
						Page<Article> test1=articleservice.test(user.getUserid(), classid,page2);
						List<Article> test2=test1.getContent();
						webrequest.setAttribute("articles",test2, webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("total",test1.getTotalPages(), webrequest.SCOPE_REQUEST);
						webrequest.setAttribute("ele",test1.getNumber() + 1, webrequest.SCOPE_REQUEST);
						//分页链接修饰
						webrequest.setAttribute("userdot","&loginame="+user.getLoginname()+"&tags="+tags, webrequest.SCOPE_REQUEST);
				    	}catch(Exception e) {}
				    	break;
				}
				}else {
					webrequest.setAttribute("articlebyidlisttag","false", webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("articlebyidtag","false", webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("picturetag","false", webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("picturelisttag","false", webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("diarylisttag","false", webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("commentarticletag","false", webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("fenyearticletag","open", webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("fenyetag","open", webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("articledetailtag","false", webrequest.SCOPE_REQUEST);
					webrequest.setAttribute("diarytag","false", webrequest.SCOPE_REQUEST);
				}			
			}
		 return "page";
	 }
	 @RequestMapping("/addfollow")
	 @ResponseBody
	 public String addfollow(Long userid,Long followuserid) {
		 Follow follow=new Follow();
		 follow.setUserid(userid);
		 follow.setFollowuserid(followuserid);
		 Follow newfollow=followservice.savefollow(follow);
		 if(newfollow!=null)
		 {
			 return "true";
		 }else {
			 return "false";
		 }
	 }
	 @RequestMapping("/addagreed")
	 @ResponseBody
	 public String addagreed(Long articleid,Long userid) {
		 Agreed ar=agreedservice.findByArticleidAndUserid(articleid, userid);
		 if(ar != null)
		 {
			 return "true";
		 }else {
			 return "false";
		 }
	 }
	 @RequestMapping("/addagreedinfo")
	 @ResponseBody
	 public String addagreedinfo(Long articleid,Long userid) {
		 Agreed at=new Agreed();
		 at.setArticleid(articleid);
		 at.setUserid(userid);
		 Agreed ar=agreedservice.saveAgreed(at);
		 int tag=articleservice.updateagreecount(articleid);
		 if(ar != null && tag==1)
		 {
			 return "true";
		 }else {
			 return "false";
		 }
	 }
	 @RequestMapping("/collection")
	 @ResponseBody
	 public String collection(Long articleid,Long userid) {
		 Collection cl=collectionservice.findByArticleidAndUserid(articleid, userid);
		 if(cl != null) {
			 return "true";
		 }else {
			 return "false";
		 }
	 }
	 @RequestMapping(value="/savecollection")
	 @ResponseBody
	 public String savecollection(String Title,
             String Label,
             String abstracts,Long articleid,Long userid,String articleurl) {
		 Collection cll=new Collection();
		 try {
		     Collection cl=new Collection();
		     cl.setUserid(userid);
		     cl.setArticleid(articleid);
		     cl.setLabel(Label);
		     cl.setTitle(Title);
		     cl.setAbstracts(abstracts);
		     cl.setImageurl(getuser().getImageurl());
		     String charEncode = java.net.URLDecoder.decode(articleurl);
		     cl.setArticleurl(charEncode);
		     cll=collectionservice.saveCollection(cl);
		 }catch(Exception e) {
			 System.out.println("收藏出错！");
		 }
		 if(cll.getId() != null) {
			 return "true";
		 }else {
			 return "false";
		 }
	 }
	 @RequestMapping(value="/addcomment")
	 @ResponseBody
	 public String addcomment(Long userid,String username,Long followuserid,String followusername,Long articleid,String content) {
		 Comment co=new Comment();
		 co.setCommenthostid(userid);
		 co.setCommentuserid(followuserid);
		 co.setReplyName(followusername);
		 co.setBeReplyName(username);
		 co.setArticleid(articleid);
		 co.setContent(content);
		 Date d = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String str = sdf.format(d);
		 co.setTime(str);
		 Comment coo=commentservice.savecomment(co);
		 if(coo != null) {
			 return "true";
		 }else {
			 return "false";
		 }
	 }
	 @RequestMapping(value="/addcommentchild")
	 @ResponseBody
	 public String addcommentchild(Long userid,String username,Long followuserid,String followusername,Long articleid,String content,Long parent_commentid) {
		 Date d = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String str = sdf.format(d);
		 int tag=commentservice.savecommentchild(userid, username, followuserid, followusername, articleid, content, parent_commentid,str);
		 if(tag != 0) {
			 return "true";
		 }else {
			 return "false";
		 }
	 }

	 @RequestMapping(value="/download")
	 public ResponseEntity<byte[]> download(HttpServletRequest request,
			 @RequestParam("filename") String filename,
			 @RequestHeader("User-Agent") String userAgent,
			 Model model)throws Exception{
		// 下载文件路径
		 String path = "D:\\Eclipse\\eclipse\\work\\Blogtest\\src\\main\\resources\\static\\testuserpicture\\";
		// 构建File
		File file = new File(path+filename);
		// ok表示Http协议中的状态 200
       BodyBuilder builder = ResponseEntity.ok();
       // 内容长度
       builder.contentLength(file.length());
       // application/octet-stream ： 二进制流数据（最常见的文件下载）。
       builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
       // 使用URLDecoder.decode对文件名进行解码
       filename = URLEncoder.encode(filename, "UTF-8");
       // 设置实际的响应文件名，告诉浏览器文件要用于【下载】、【保存】attachment 以附件形式
       // 不同的浏览器，处理方式不同，要根据浏览器版本进行区别判断
       if (userAgent.indexOf("MSIE") > 0) {
               // 如果是IE，只需要用UTF-8字符集进行URL编码即可
               builder.header("Content-Disposition", "attachment; filename=" + filename);
       } else {
               // 而FireFox、Chrome等浏览器，则需要说明编码的字符集
               // 注意filename后面有个*号，在UTF-8后面有两个单引号！
               builder.header("Content-Disposition", "attachment; filename*=UTF-8''" + filename);
       }
       return builder.body(FileUtils.readFileToByteArray(file));
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
