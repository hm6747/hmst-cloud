//全局事件
if(window.jQuery){
    //ajax预处理 后置处理
    jQuery(document).bind("ajaxSend", function(event, request, settings){
        var token =   window.localStorage.getItem("token");
        console.log(token)
//config_contextPath 为需要设置token的 全局host,严格判断防止 token发送到其他站点被盗取
        if(token){
            var headers = settings.headers || {};
            headers["token"] = token;
            request.setRequestHeader("token", token);
            settings.headers = headers;
        }
    }).bind("ajaxComplete", function(event, xhr, settings){
        if(settings.url && (settings.dataType === 'JSON' || settings.dataType === 'json')){
            if(xhr.status == 200 && xhr.responseText){
                try{
                    var reObj = JSON.parse(xhr.responseText);
                    if(reObj.status==40301){
                        alert("请登录")
                        window.location.href="login.html";
                    }
                }catch (e){console.error(e)}
            }
        }
    });
}
//列表展示
function getTableList(url,data,showPage,successCallback,failCallback) {
    var pageData = {
        pageNo: current,
        pageSize: pageSize
    }
    if(showPage){
        $.extend(data,pageData)
    }
    $.ajax({
        url: path+url,
        data: data,
        dataType: 'json',
        success: function (result) {
            if(result.ret){
                if(successCallback){
                    successCallback(result);
                }
                if(showPage){
                    createPage(url,result.data,data,successCallback);
                }
            }else{
                if(failCallback){
                    failCallback(result);
                }
            }
        },
    })
}
function createMustache(listJson,result,listTemplate,ele) {
    var firstKey = getFirstAttr(listJson);
    var resultJson = {};
    resultJson[firstKey] = result.data.data;
    $.extend(listJson, resultJson)
    Mustache.parse(listTemplate);
    var rendered = Mustache.render(listTemplate, listJson);
    $(ele).empty();
    $(ele).append(rendered);
}
function createListMap(result,successMap) {
    $.each(result.data.data, function(i, item) {
        successMap[item[getFirstAttr(item)]] = item;
    })
}
function getFirstAttr(obj) {
    for (var k in obj) return k;
}
function createPage(url,result,data,successCallback) {
      pageNum = parseInt(result.total) / pageSize + 1;
    $(".showPages").html("").append('<div class="zxf_pagediv"></div>');
    $(".zxf_pagediv").createPage({
        pageNum: pageNum,
        current: current,
        backfun: function (e) {
            current = e.current;
            getTableList(url,data,true,successCallback);
        }
    });
}
//日期格式化
function formatData() {
    $(".formatData").each(function () {
        var fmt = $(this).attr("data-formatStr");
        var date = $(this).html();
        $(this).html(new Date(parseInt(date)).format(fmt));
    })
}

Date.prototype.format = function (format) {
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
}

//nestable树形tree
function nestableTree(nesBindEvent, ele) {
    var updateOutput = function (e) {
        var list = e.length ? e : $(e.target),
            output = list.data('output');
        if (window.JSON) {
            output.val(window.JSON.stringify(list.nestable('serialize'))); //, null, 2));
        } else {
            output.val('浏览器不支持');
        }
    };

    // activate Nestable for list 2
    $(ele).nestable({
        group: 1
    }).on('change', updateOutput);
    $(ele).find('.dd').nestable('collapseAll');
    // output initial serialised data
    updateOutput($(ele).data('output', $('.nestable2-output')));

    $('#nestable-menu').on('click', function (e) {
        var target = $(e.target),
            action = target.data('action');
        if (action === 'expand-all') {
            $(ele).find('.dd').nestable('expandAll');
        }
        if (action === 'collapse-all') {
            $(ele).find('.dd').nestable('collapseAll');
        }
    });
    if (nesBindEvent) {
        $(ele).unbind("mousedown");
        nesBindEvent();
    }
}
function submitAjaxForm(url,data,successCallback, failCallback) {
    $.ajax({
        url: path+url,
        data: data,
        dataType: 'json',
        type: 'POST',
        success: function (result) {
            if (result.ret) {
                if (successCallback) {
                    successCallback(result);
                }
            } else {
                if (failCallback) {
                    failCallback(result);
                }
            }
        }
    })
}