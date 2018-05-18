(function($){
	var zp = {
		init:function(obj,pageinit){
			return (function(){
				zp.addhtml(obj,pageinit);
				zp.bindEvent(obj,pageinit);
			}());
		},
		addhtml:function(obj,pageinit){
			return (function(){
				obj.empty();
				/*上一页*/
				if (parseInt(pageinit.current) > 1) {
					obj.append('<a href="javascript:;" class="zxf_prebtn">上一页</a>');
				} else{
					obj.remove('.zxf_prevPage');
					obj.append('<span class="zxf_disabled">上一页</span>');
				}
				/*中间页*/
				if (parseInt(pageinit.current) >7 && parseInt(pageinit.pageNum) > 7) {
					obj.append('<a href="javascript:;" class="zxf_zxfPagenum">'+1+'</a>');
					obj.append('<a href="javascript:;" class="zxf_zxfPagenum">'+2+'</a>');
					obj.append('<span>...</span>');
				}
				if (parseInt(pageinit.current) > 7 && parseInt(pageinit.current) <= parseInt(pageinit.pageNum - 5)) {
					var start  =parseInt( pageinit.current) - 2,end = parseInt(pageinit.current)  + 2;
				}else if(parseInt(pageinit.current) >7 && parseInt(pageinit.current)  > parseInt(pageinit.pageNum - 5) ){
					var start  = parseInt(pageinit.pageNum  - 4) ,end = parseInt(pageinit.pageNum) ;
				}else{
					var start = 1,end = 9;
				}
				for (;start <= end;start++) {
					if (start <= parseInt(pageinit.pageNum)  && start >=1) {
						if (start ==parseInt(pageinit.current) ) {
							obj.append('<span class="zxf_current">'+ start +'</span>');
						} else if(start == parseInt(pageinit.current)+1){
							obj.append('<a href="javascript:;" class="zxf_zxfPagenum zxf_nextpage">'+ start +'</a>');
						}else{
							obj.append('<a href="javascript:;" class="zxf_zxfPagenum">'+ start +'</a>');
						}
					}
				}

				if (parseInt(end) <parseInt(pageinit.pageNum)) {
					obj.append('<span>...</span>');
				}
				/*下一页*/
				if (parseInt(pageinit.current) >= parseInt(pageinit.pageNum)) {
					obj.remove('.nextbtn');
					obj.append('<span class="zxf_disabled">下一页</span>');
				} else{
					obj.append('<a href="javascript:;" class="zxf_nextbtn">下一页</a>');
				}
				/*尾部*/
				obj.append('<span>'+'共'+'<b>'+parseInt(pageinit.pageNum)+'</b>'+'页，'+'</span>');
				obj.append('<span>'+'到第'+'<input type="text" class="zxf_zxfinput" value="1" style="width: 35px;display: -webkit-inline-box"/>'+'页'+'</span>');
				obj.append('<span class="zxf_zxfokbtn">'+'确定'+'</span>');
			}());
		},
		bindEvent:function(obj,pageinit){
			return (function(){
				//上一页点击
				obj.on("click","a.zxf_prebtn",function(){
					var cur = parseInt(obj.children("span.zxf_current").text());
					var current = $.extend(pageinit, {"current":cur-1});
					zp.addhtml(obj,current);
					if (typeof(pageinit.backfun)=="function") {
						pageinit.backfun(current);
					}
				});
                //数字点击
				obj.on("click","a.zxf_zxfPagenum",function(){
					var cur = parseInt($(this).text());
					var current = $.extend(pageinit, {"current":cur});
					zp.addhtml(obj,current);
					if (typeof(pageinit.backfun)=="function") {
						pageinit.backfun(current);
					}
				});
                //下一页点击
				obj.on("click","a.zxf_nextbtn",function(){
					var cur = parseInt(obj.children("span.zxf_current").text());
					var current = $.extend(pageinit, {"current":cur+1});
					zp.addhtml(obj,current);
					if (typeof(pageinit.backfun)=="function") {
						pageinit.backfun(current);
					}
				});
				////确定点击
				obj.on("click","span.zxf_zxfokbtn",function(){
					if(isNaN($("input.zxf_zxfinput").val())){
						$("input.zxf_zxfinput").val(1);
					}
					var cur = parseInt($("input.zxf_zxfinput").val());
					var current = "";
					if(cur>pageinit.pageNum){
						current = $.extend(pageinit, {"current":1});
					}else{
						current = $.extend(pageinit, {"current":cur});
					}
					zp.addhtml(obj,{"current":cur,"pageNum":pageinit.pageNum});
					if (typeof(pageinit.backfun)=="function") {
						pageinit.backfun(current);
					}
				});
			}());
		}
	}
	$.fn.createPage = function(options){
		var pageinit = $.extend({
			pageNum : 10,
			current : 1,
			backfun : function(){}
		},options);
		zp.init(this,pageinit);
	}
}(jQuery));
