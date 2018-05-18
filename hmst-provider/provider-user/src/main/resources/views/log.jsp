<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/common/common_quote.jsp"/>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>权限日志管理</title>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>权限日志列表</h5>
                    <div class="ibox-content">
                        <div class="row m-b-sm m-t-sm">
                            <div class="col-md-1">
                                <button type="button" id="loading-example-btn" class="btn btn-white btn-sm"><i
                                        class="fa fa-refresh"></i> 刷新
                                </button>
                            </div>
                            <div class="col-md-11">
                                <div class="input-group">
                                    <input type="text" placeholder="查找日志" class="input form-control" id="keyword">
                            <span class="input-group-btn">
                                        <button type="button" class="btn btn btn-primary" id="search"><i
                                                class="fa fa-search"></i>
                                            搜索
                                        </button>
                                </span>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="clients-list">
                        <ul class="nav nav-tabs">
                            <span class="pull-right small text-muted">总共<span id="total"></span>个日志</span>
                            <li class="active"><a data-toggle="tab" href="#tab-1"><i class="fa fa-user"></i> 日志</a>
                            </li>
                            <%--        <li class=""><a data-toggle="tab" href="#tab-2"><i class="fa fa-briefcase"></i> 部门</a>
                                    </li>--%>
                        </ul>
                        <div class="tab-content">
                            <div id="tab-1" class="tab-pane active">
                                <div class="full-height-scroll">
                                    <div class="table-responsive">
                                        <table class="table table-striped table-hover">
                                            <thead>
                                            <tr>
                                                <th class="table-id" >操作者</th>
                                                <th class="table-type">操作类型</th>
                                                <th class="table-num">操作时间</th>
                                                <th class="table-type ">操作前的值</th>
                                                <th class="table-type ">操作后的值</th>
                                                <th class="table-type ">是否复原过</th>
                                                <th class="table-set">操作</th>
                                            </tr>
                                            </thead>
                                            <tbody id="listTable">
                                            </tbody>
                                        </table>
                                        <div class="btn-group showPages">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script id="listTemplate" type="x-tmpl-mustache">
{{#roleList}}
<tr>
    <td><a data-toggle="tab" href="#contact-1" class="client-link" data-id="{{id}}">{{operator}}</a>
    </td>
    <td>{{showType}}</td>
        <td class="formatData" data-formatStr="yyyy/MM/dd hh:mm:ss">{{operatorTime}}</td>
            <td>{{oldValue}}</td>
                <td>{{newValue}}</td>
    <td class="client-status"><span class="label {{statusClass}}">{{showStatus}}</span>
    <td>
                     <a class="btn btn-white btn-sm recover" data-Id="{{id}}"><i class="fa fa-folder"></i> 复原 </a>

    </td>
</tr>
{{/roleList}}





</script>
<script>
    var roleMap={}; //缓存树形目录
    $(document).ready(function () {
        $("#search").on("click", function () {
            searchRole();
        })
        loadRoleList();
    });

    function searchRole() {
        var keyword = $("#keyword").val();
        loadRoleList(keyword);
    }
    function loadRoleList(keyword) {
        var url = "/sys/log/page.json";
        var data = {
            keyword: keyword
        }
        var listJson = {
            roleList: "",
            "showStatus": function () {
                return this.status == 1 ? "复原过" : (this.status == 0 ? "没有" : "");
            },
            "statusClass": function () {
                return this.status == 1 ? "label-danger" :"label-primary";
            },
            "showType": function () {
                return this.type == 1 ? "部门" : this.type == 2 ? "用户" : this.type == 3 ? "权限模块": this.type == 4 ? "权限": this.type == 5 ? "角色": this.type == 6 ? "角色用户关系": this.type == 7 ? "角色权限关系":"";
            }
        };
        var listTemplate = $("#listTemplate").html();
        var successCallback = function (result) {
            createMustache(listJson, result, listTemplate, $("#listTable"));
            createListMap(result, roleMap);
            $("#total").html(result.data.total);
            bindClick();
            formatData();
        }
        getTableList(url, data, true, successCallback);
    }
    function bindClick() {
        $(".recover").on("click",function () {
            var id = $(this).attr("data-Id");
            recover(id);
        })
    }
    function recover(id) {
        var url = "/sys/log/recover.json";
        var data = {id:id};
        var success = function () {
            alert(1);
        }
        submitAjaxForm(url,data,success)
    }

</script>

</body>

</html>
