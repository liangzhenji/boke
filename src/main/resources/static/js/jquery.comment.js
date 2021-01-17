(function($){
	function crateCommentInfo(obj){
		/*
		 * <div class="comment-info">
			<header><img src="./images/img.jpg"></header>
			<div class="comment-right">
				<h3>匿名</h3>
				<div class="comment-content-header"><span><i class="glyphicon glyphicon-time"></i> 2017-10-17 11:42:53</span><span><i class="glyphicon glyphicon-map-marker"></i>深圳</span></div>
				<p class="content">mongodb 副本集配置副本集概念：就我的理解就是和主从复制 差不多，就是在主从复制的基础上多加了一个选举的机制。
				复制集 特点：数据一致性 主是唯一的，没有Mysql 那样的双主结构大多数原则，集群存活节点小于二分之一是集群不可写，
				只可读从库无法写入数据自动容灾通过下面的一个图来简单的了解下
				 配置过程：一、安装mongodb安装过程略，不懂得可以看前面的教程二、创建存储目录与配置文件创...</p>
				<div class="comment-content-footer">
					<div class="row">
						<div class="col-md-10">
							<span><i class="glyphicon glyphicon-pushpin"></i> 来自:win10 </span><span><i class="glyphicon glyphicon-globe"></i> chrome 55.0.2883.87</span>
						</div>
						<div class="col-md-2"><span class="reply-btn">回复</span></div>
					</div>
				</div>
				<div class="reply-list">
					<div class="reply">
						<div><a href="javascript:void(0)">匿名</a>:<a href="javascript:void(0)">@匿名</a><span>这写的是什么鬼东西。。。。</span></div>
						<p><span>2017-10-17 11:42:53</span> <span class="reply-list-btn">回复</span></p>
					</div>
				</div>
			</div>
		</div>
		 * */
		if(typeof(obj.time) == "undefined" || obj.time == ""){
			obj.time = getNowDateFormat();
		}
		
		var el = "<div class='comment-info' commentid='"+obj.commentid+"' commenthostid='"+obj.commenthostid+"' replyName='"+obj.replyName+"'><div class='comment-left'><h5 style='color:#CF6B21'>"+obj.replyName+"</h5>"
				+"<div class='comment-content-header'><span style='color:#CF6B21'><i class='glyphicon glyphicon-time'></i>"+obj.time+"</span></div>";
		
		el = el+"</div><p class='content'>"+obj.content+"</p><div class='comment-content-footer'><div class='row'><div class='col-md-10'>";
		
		el = el + "</div><div class='col-md-2'><span class='reply-btn' style='color:#CF6B21;cursor: pointer;'>回复</span></div></div></div><div class='reply-list'>";
		if(obj.replyBody != "" && obj.replyBody.length > 0){
			var arr = obj.replyBody;
			for(var j=0;j<arr.length;j++){
				var replyObj = arr[j];
				el = el+createReplyComment(replyObj);
			}
		}
		el = el+"</div></div></div>";
		return el;
	}
	
	//返回每个回复体内容
	function createReplyComment(reply){
		var replyEl = "<div class='reply' commenthostid='"+reply.commenthostid+"' replyName='"+reply.replyName+"'><div><a href='javascript:void(0)' class='replyname'>"+reply.replyName+"</a>:<a href='javascript:void(0)'>@"+reply.beReplyName+"</a><span>&nbsp;&nbsp;"+reply.content+"</span></div>"
						+ "<p style='color:#CF6B21'><span>"+reply.time+"</span> <span class='reply-list-btn' >回复</span></p></div>";
		return replyEl;
	}
	function getNowDateFormat(){
		var nowDate = new Date();
		var year = nowDate.getFullYear();
		var month = filterNum(nowDate.getMonth()+1);
		var day = filterNum(nowDate.getDate());
		var hours = filterNum(nowDate.getHours());
		var min = filterNum(nowDate.getMinutes());
		var seconds = filterNum(nowDate.getSeconds());
		return year+"-"+month+"-"+day+" "+hours+":"+min+":"+seconds;
	}
	function filterNum(num){
		if(num < 10){
			return "0"+num;
		}else{
			return num;
		}
	}
	function replyClick(el){
		el.parent().parent().append("<div class='replybox' style='padding-left:16px'><textarea cols='80' rows='50' placeholder='来说几句吧......' class='mytextarea' ></textarea><span class='send' style='color:#CF6B21;'>发送</span></div>")
		.find(".send").click(function(){
			var content = $(this).prev().val();
			var parent_commentid = $(this).prev().parent().parent().parent().parent().attr("commentid");
			if(content != ""){
				//传数据后台
				try{
				var userids=$(this).prev().parent().parent().attr("commenthostid");
				var username=$(this).prev().parent().parent().attr("replyName");
				}catch(err){
					console.log(err);
				}
				if(userids ==null && username ==null){
				var userids=$(this).prev().parent().parent().parent().parent().attr("commenthostid");
				var username=$(this).prev().parent().parent().parent().parent().attr("replyName");
				}
				$.get("/user/addcommentchild?userid="+userids+"&username="+username+"&followuserid="+userid+"&followusername="+username1+"&articleid="+articleid+"&content="+content+"&parent_commentid="+parent_commentid,function(data,status){
			  	    if(data == "true"){    	
			  	    	location.reload();
			  	    }else{
			  	    	alert("回复评论失败");
			  	    }
			  	  })				 
			}else{
				alert("空内容");
			}
		}	
		);
	}
	//专门处理新添加的评论
	
	$.fn.addCommentList=function(options){
		var defaults = {
			data:[],
			add:""
		}
		var option = $.extend(defaults, options);
		//加载数据
		if(option.data.length > 0){
			var dataList = option.data;
			var totalString = "";
			for(var i=0;i<dataList.length;i++){
				var obj = dataList[i];
				var objString = crateCommentInfo(obj);
				totalString = totalString+objString;
			}
			$(this).append(totalString).find(".reply-btn").click(function(){
				if($(this).parent().parent().find(".replybox").length > 0){
					$(".replybox").remove();
				}else{
					$(".replybox").remove();
					replyClick($(this));
				}
			});
			$(".reply-list-btn").click(function(){
				if($(this).parent().parent().find(".replybox").length > 0){
					$(".replybox").remove();
				}else{
					$(".replybox").remove();
					replyClick($(this));
				}
			})
		}
		
		//添加新数据
		if(option.add != ""){
			if(username1 == null){
				window.location.href="/login";
			}else
				{
				var content=$("#content").val();
				$.get("/user/addcomment?userid="+userids+"&username="+username+"&followuserid="+userid+"&followusername="+username1+"&articleid="+articleid+"&content="+content,function(data,status){
			  	    if(data == "true"){    	
			  	    	location.reload();
			  	    }else{
			  	    	alert("添加评论失败");
			  	    }
			  	  })
				}
			//更新数据库信息
			//重新返回页面定位到评论顶部
			
		}
	}
	
	
})(jQuery);