/**
 * 引用JS和CSS头文件
 */
var rootPath = getRootPath(); //项目路径

/**
 * 动态加载CSS和JS文件
 */
var dynamicLoading = {
    css: function(path){
        if(!path || path.length === 0){
            throw new Error('argument "path" is required!');
        }
        document.write('<link rel="stylesheet" type="text/css" href="' + path + '">');
    },
    js: function(path, charset){
        if(!path || path.length === 0){
            throw new Error('argument "path" is required!');
        }
        document.write('<script charset="' + (charset ? charset : "utf-8") + '" src="' + path + '"></script>');
    }
};
/**
 * 取得项目路径
 * @author wul
 */
function getRootPath() {
    //取得当前URL
    var path = window.document.location.href;
    //取得主机地址后的目录
    var pathName = window.document.location.pathname;
    var post = path.indexOf(pathName);
    //取得主机地址
    var hostPath = path.substring(0, post);
    //取得项目名
    var name = pathName.substring(0, pathName.substr(1).indexOf("/") + 1);
    return hostPath + name + "/";
}

//动态加载项目 JS文件
dynamicLoading.js( "../static/js/jquery.min.js?v=2.1.4", "utf-8");
dynamicLoading.js( "../static/js/mustache.js", "utf-8");
dynamicLoading.js("../static/js/plugins/validate/jquery.validate.min.js", "utf-8");
dynamicLoading.js("../static/common/common_param.js", "utf-8");
dynamicLoading.js("../static/common/common_base.js", "utf-8");
dynamicLoading.js("../static/common/common_validate.js", "utf-8");
dynamicLoading.js("../static/js/bootstrap.min.js?v=3.3.6", "utf-8");
dynamicLoading.js("../static/js/zxf_page.js", "utf-8");
// dynamicLoading.js("../static/js/vue.js", "utf-8");
dynamicLoading.js("../static/js/plugins/nestable/jquery.nestable.js", "utf-8");
dynamicLoading.js("../static/js/plugins/metisMenu/jquery.metisMenu.js", "utf-8");
dynamicLoading.js("../static/js/plugins/slimscroll/jquery.slimscroll.min.js", "utf-8");
dynamicLoading.js("../static/js/plugins/layer/layer.min.js", "utf-8");
//加解密 RSA
// dynamicLoading.js("../static/js/code/rsa.js", "utf-8");
// dynamicLoading.js("../static/js/code/rng.js", "utf-8");
// dynamicLoading.js("../static/js/code/prng4.js", "utf-8");
// dynamicLoading.js("../static/js/code/jsbn.js", "utf-8");
// dynamicLoading.js("../static/js/code/jsencrypt.min.js", "utf-8");
//第三方插件
dynamicLoading.js("../static/js/plugins/pace/pace.min.js", "utf-8");
dynamicLoading.js("../static/js/plugins/ztree/jquery.ztree.all.min.js", "utf-8");
//导航栏js
dynamicLoading.js("../static/js/hplus.js?v=4.1.0", "utf-8");
dynamicLoading.js("../static/js/contabs.js", "utf-8");

//动态加载项目 CSS文件
dynamicLoading.css("../static/css/bootstrap.min.css?v=3.3.6");
dynamicLoading.css("../static/css/font-awesome.css?v=4.4.0");
dynamicLoading.css("../static/css/plugins/iCheck/custom.css");
dynamicLoading.css("../static/css/animate.css");
dynamicLoading.css("../static/css/plugins/summernote/summernote.css");
dynamicLoading.css("../static/css/plugins/fileinput/fileinput.min.css");
dynamicLoading.css("../static/css/style.css?v=4.1.0");
dynamicLoading.css("../static/css/custom.css");
dynamicLoading.css("../static/js/plugins/ztree/zTreeStyle.css");
dynamicLoading.css("../static/css/zxf_page.css");
dynamicLoading.css("../static/css/login.css");
