<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="../static/common/common_quote.jsp"/>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>权限模块管理</title>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-4">
            <div class="ibox ">
                <div class="ibox-content">
                    <%--        <span class="text-muted small pull-right">最后更新：<i class="fa fa-clock-o"></i> 2015-09-01 12:00</span>--%>
                    <h2>权限模块列表</h2>
                    <div class="ibox-tools">
                        <a onclick="addAclModel()" data-toggle="modal" data-target="#aclModelModal"
                           class="btn btn-primary btn-xs"><i class="fa fa-plus"></i>创建权限模块</a>
                    </div>
                    <p>
                        所有权限模块必须状态正常
                    </p>
                    <div id="aclModelTree" class="dd nestable2"></div>
                    <script id="aclListTemplate" type="x-tmpl-mustache">
                    <ol class="dd-list">
                     {{#aclModuleList}}
                        <li class="dd-item dept-name" data-id="{{id}}" id="aclModel_{{id}}">
                        <div class="dd-handle dd-content" style="text-align: left;">
                            <span class="label label-info"><i class="fa fa-users"></i></span> {{name}}
                          <a  class="fa-hover delAclModel" style="float:right;margin-right: 20px"  data-id="{{id}}"><i class="fa fa-trash-o"></i></a>
                           <a class="fa-hover editAclModel" style="float:right;margin-right: 10px" data-toggle="modal" data-target="#aclModelModal" data-id="{{id}}"><i class="fa fa-edit"></i></a>
                        </div>
                        </li>
                     {{/aclModuleList}}
                     </ol>



                    </script>
                </div>
            </div>
        </div>
        <div class="col-sm-8">
            <div class="ibox">
                <div class="ibox-content">
                    <%--     <span class="text-muted small pull-right">最后更新：<i class="fa fa-clock-o"></i> 2015-09-01 12:00</span>--%>
                    <h2>权限点列表</h2>
                    <p>
                        所有权限点必须状态正常
                    </p>
                    <div class="input-group">
                        <input type="text" placeholder="查找权限点" class="input form-control" id="keyword">
                            <span class="input-group-btn">
                                        <button type="button" class="btn btn btn-primary" id="search"><i
                                                class="fa fa-search"></i>
                                            搜索
                                        </button>
                                </span>
                    </div>
                    <div class="clients-list">
                        <ul class="nav nav-tabs">
                            <span class="pull-right small text-muted">总共<span id="total"></span>个权限点</span>
                            <div class="ibox-tools" style="float: right">
                                <a onclick="addAcl()" class="btn btn-primary btn-xs" data-toggle="modal"
                                   data-target="#aclModal"><i class="fa fa-plus"></i>创建权限点</a>
                            </div>
                            <li class="active"><a data-toggle="tab" href="#tab-1"><i class="fa fa-user"></i> 权限点</a>
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
                                                <th class="table-id">权限名称</th>
                                                <th class="table-title">权限模块</th>
                                                <th class="table-type">类型</th>
                                                <th class="table-num">URL</th>
                                                <th class="table-type ">状态</th>
                                                <th class="table-type ">顺序</th>
                                                <th class="table-set">操作</th>
                                            </tr>
                                            </thead>
                                            <tbody id="listTable">
                                            </tbody>
                                        </table>
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
<div class="modal inmodal" id="aclModelModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="addAclModelTitle">添加权限模块</h4>
                <%--     <small>这里可以显示副标题。--%>
            </div>
            <form method="get" class="form-horizontal" id="aclModelForm">
                <div class="col-md-12 modal-body">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">上级模块</label>
                        <div class="col-sm-10">
                            <button type="button" class="btn btn-primary"
                                    id="addAclModelBtn">
                                选择模块
                            </button>
                            <button type="button" class="btn btn-primary"
                                    id="resetAclModelBtn">
                                清空模块
                            </button>
                            <span style="margin-left: 20px;" id="aclModelName"></span>
                            <input readonly name="id" type="hidden" id="aclModelId">
                            <input readonly name="parentId" type="hidden" id="parentId" value="0">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>
                        <div id="addAclModelTree" class="dd nestable2 col-sm-10" style="display: none"></div>
                        <script id="addAclModelListTemplate" type="x-tmpl-mustache">
                                                        <ol class="dd-list">
                                                                {{#addAclModelTreeList}}
                                                                <li class="dd-item dept-name" data-id="{{id}}" id="addAclModel_{{id}}">
                                                                <div class="dd-handle dd-content" style="text-align: left;">
                                                                    <span class="label label-info"><i class="fa fa-users"></i></span> {{name}}
                                                                </div>
                                                                </li>
                                                             {{/addAclModelTreeList}}
                                                         </ol>



                        </script>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" aria-required="true" required name="name"
                                   id="addAclModelName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">顺序</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="seq" id="addAclModelSeq" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-10">
                            <select class="form-control m-b" id="addAclModelStatus" name="status"
                                    data-placeholder="选择状态">
                                <option value="1">有效</option>
                                <option value="0">无效</option>
                                <option value="2">冻结</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">备注</label>
                        <div class="col-sm-10">
                            <textarea name="remark" id="addAclModelRemark" class="form-control" required=""
                                      aria-required="true"></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-white closeModel" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal inmodal" id="aclModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="addAclTitle">添加权限点</h4>
                <%--     <small>这里可以显示副标题。--%>
            </div>
            <form method="get" class="form-horizontal" id="aclForm">
                <div class="col-md-12 modal-body">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">所属模块</label>
                        <div class="col-sm-10">
                            <button type="button" class="btn btn-primary"
                                    id="addAclBtn">
                                选择模块
                            </button>
                            <span style="margin-left: 20px;" id="addAclName"></span>
                            <input readonly name="aclModuleId" type="hidden" id="addAclId">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>
                        <div id="addAclTree" class="dd nestable2 col-sm-10" style="display: none"></div>
                        <script id="addAclListTemplate" type="x-tmpl-mustache">
                                                        <ol class="dd-list">
                                                                {{#addAclTreeList}}
                                                                <li class="dd-item dept-name" data-id="{{id}}" id="addAcl_{{id}}">
                                                                <div class="dd-handle dd-content" style="text-align: left;">
                                                                    <span class="label label-info"><i class="fa fa-users"></i></span> {{name}}
                                                                </div>
                                                                </li>
                                                             {{/addAclTreeList}}
                                                         </ol>



                        </script>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" aria-required="true" required id="aclName"
                                   name="name">
                            <input type="hidden" name="id" id="aclId"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">类型</label>
                        <div class="col-sm-10">
                            <select class="form-control m-b" id="addAclType" name="type" data-placeholder="选择类型">
                                <option value="1">菜单</option>
                                <option value="2">按钮</option>
                                <option value="3">其他</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">URL</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="url" id="addAclUrl" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-10">
                            <select class="form-control m-b" id="addAclStatus" name="status" data-placeholder="选择状态">
                                <option value="1">有效</option>
                                <option value="0">无效</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">顺序</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="seq" id="addAclSeq" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">备注</label>
                        <div class="col-sm-10">
                            <textarea name="remark" id="addAclRemark" class="form-control" required=""
                                      aria-required="true"></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-white closeModel" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script id="listTemplate" type="x-tmpl-mustache">
{{#aclList}}
<tr>
    <td><a data-toggle="tab" href="#contact-1" class="client-link" data-id="{{id}}">{{name}}</a>
    </td>
      <td>{{showAclModuleName}}</td>
    <td>{{showType}}</td>
     <td>{{url}}</td>
         <td class="client-status"><span class="label {{statusClass}}">{{showStatus}}</span>
    <td>{{seq}}</td>
    <td>
                          <a  class="btn btn-white btn-sm editAcl" data-toggle="modal" data-target="#aclModal"
                                        data-Id="{{id}}"><i class="fa fa-pencil"></i> 编辑 </a>
                     <a class="btn btn-white btn-sm delAcl"><i class="fa fa-folder"></i> 删除 </a>

    </td>
</tr>
{{/aclList}}




</script>
<script>
    var aclModuleList; //缓存树形目录
    var aclModelTreeMap = {};
    var aclMap = {};
    var lastClickAclModelId = -1;
    $(document).ready(function () {
        itemTreeCreate();
        bindBtn();
        $("#search").on("click", function () {
            searchAcl();
        })
        commonValidate("aclForm", updateAcl);
        commonValidate("aclModelForm", updateAclModel);
    });
    function addAcl() {
        $("#addAclId").val(0);
        $("#addAclName").html("");
        $("#aclName").val("");
        $("#addAclType").val("");
        $("#addAclUrl").val("");
        $("#addAclStatus").val("");
        $("#addAclSeq").val("");
        $("#addAclRemark").val("");
        $("#aclId").val("");
    }
    function addAclModel() {
        $("#aclModelId").val("");
        $("#parentId").val(0);
        $("#aclModelName").html("");
        $("#addAclModelName").val("");
        $("#addAclModelSeq").val("");
        $("#addAclModelStatus").val("");
        $("#addAclModelRemark").val("");
    }
    function updateAclModel() {
        var url = (!$("#aclModelId").val()) ? "/sys/aclModule/save.json" : "/sys/aclModule/update.json";
        var data = $("#aclModelForm").serializeArray();
        if ($("#aclModelId").val()) {
            submitAjaxForm(url, data, function (result) {
                console.log(result)
                itemTreeCreate();
                $(".closeModel").click();
            }, function (result) {
                console.log(result)
            });
        } else {
            submitAjaxForm(url, data, function (result) {
                itemTreeCreate();
                $(".closeModel").click();
                console.log(result)
            }, function (result) {
                console.log(result)
            });
        }
    }
    function updateAcl() {
        var url = (!$("#aclId").val()) ? "/sys/acl/save.json" : "/sys/acl/update.json";
        var data = $("#aclForm").serializeArray();
        if ($("#aclId").val()) {
            submitAjaxForm(url, data, function (result) {
                console.log(result)
                $(".closeModel").click();
                searchAcl();
            }, function (result) {
                console.log(result)
            });
        } else {
            submitAjaxForm(url, data, function (result) {
                $(".closeModel").click();
                console.log(result)
            }, function (result) {
                console.log(result)
            });
        }
    }
    function bindBtn() {
        $("#addAclModelBtn").on("click", function () {
            if ($(this).html().trim() == "选择模块") {
                $("#addAclModelTree").show();
                $(this).html("收起模块");
            } else {
                $("#addAclModelTree").hide();
                $(this).html("选择模块");
            }
        })
        $("#addAclBtn").on("click", function () {
            if ($(this).html().trim() == "选择模块") {
                $("#addAclTree").show();
                $(this).html("收起模块");
            } else {
                $("#addAclTree").hide();
                $(this).html("选择模块");
            }
        })
        $("#resetAclModelBtn").on("click", function () {
            $("#parentId").val("0");
            $("#aclModelName").html("");
        })
    }
    function searchAcl() {
        var keyword = $("#keyword").val();
        loadAclList(lastClickAclModelId, keyword);
    }
    function itemTreeCreate() {
        var url = "/sys/aclModule/tree.json";
        loadDpetTree(url);
    }
    var nesBindEvent = function () {
        $("#aclModelTree .dd-handle").click(function () {
            var aclModel = $(this).parent().attr("data-id");
            handleDeptSelected(aclModel, this);
        })
    }
    var nesBindEventAddAcl = function () {
        $("#addAclTree .dd-handle").click(function () {
            var aclModelId = $(this).parent().attr("data-id");
            $("#addAclName").html(aclModelTreeMap[aclModelId].name);
            $("#addAclId").val(aclModelId);
        })
    }
    var nesBindEventAddAclModel = function () {
        $("#addAclModelTree .dd-handle").click(function () {
            var aclModelId = $(this).parent().attr("data-id");
            $("#aclModelName").html(aclModelTreeMap[aclModelId].name);
            $("#parentId").val(aclModelId);
        })
    }
    function handleDeptSelected(aclModelId, ele) {
        debugger;
        if (aclModelId == lastClickAclModelId) {
            $(ele).removeClass("nestable2Selected").addClass("dd-handle");
            lastClickAclModelId = -1;
            loadAclList(lastClickAclModelId)
            return false;
        } else {
            if (lastClickAclModelId != -1 && aclModelId != lastClickAclModelId) {
                var lastDept = $("#aclModel_" + lastClickAclModelId + " .dd-content:first");
                lastDept.removeClass("nestable2Selected").addClass("dd-handle");
            }
        }
        lastClickAclModelId = aclModelId;
        $(ele).removeClass("dd-handle").addClass("nestable2Selected");
        loadAclList(lastClickAclModelId)
    }
    function loadAclList(aclModuleId, keyword) {
        var url = "/sys/acl/page.json";
        var data = {
            aclModuleId: aclModuleId,
            keyword: keyword
        }
        var listJson = {
            aclList: "",
            "showAclModuleName": function () {
                return aclModelTreeMap[this.aclModuleId].name;
            },
            "showStatus": function () {
                return this.status == 1 ? "有效" : "无效";
            },
            "statusClass": function () {
                return this.status == 1 ? "label-primary" : "label-danger";
            },
            "showType": function () {
                return this.type == 1 ? "菜单" : (this.type == 2 ? "按钮" : "其他");
            }
        };
        var listTemplate = $("#listTemplate").html();
        var successCallback = function (result) {
            createMustache(listJson, result, listTemplate, $("#listTable"));
            createListMap(result, aclMap);
            $("#total").html(result.data.total);
            bindEditUserClik();
            formatData();
        }
        getTableList(url, data, false, successCallback);
    }
    function bindEditUserClik() {
        $(".editAcl").on("click", function () {
            $("#addUserTitle").html("编辑权限点");
            var targetAcl = aclMap[$(this).attr("data-Id")];
            if (targetAcl) {
                $("#addAclId").val(targetAcl.aclModuleId);
                $("#addAclName").html(aclModelTreeMap[targetAcl.aclModuleId].name);
                $("#aclName").val(targetAcl.name);
                $("#addAclType").val(targetAcl.type);
                $("#addAclUrl").val(targetAcl.url);
                $("#addAclStatus").val(targetAcl.status);
                $("#addAclSeq").val(targetAcl.seq);
                $("#addAclRemark").val(targetAcl.remark);
                $("#aclId").val(targetAcl.id);
            }
        })
    }
    function bindEditAclModelClik() {
        $(".editAclModel").on("click", function (e) {
            $("#addAclModelTitle").html("编辑权限模块");
            var targetAclModel = aclModelTreeMap[$(this).attr("data-id")];
            if (targetAclModel) {
                $("#aclModelId").val(targetAclModel.id);
                $("#parentId").val(targetAclModel.parentId);
                $("#aclModelName").html(targetAclModel.parentId != 0 ? aclModelTreeMap[targetAclModel.parentId].name : "");
                $("#addAclModelName").val(targetAclModel.name);
                $("#addAclModelSeq").val(targetAclModel.seq);
                $("#addAclModelStatus").val(targetAclModel.status);
                $("#addAclModelRemark").val(targetAclModel.remark);
            }
        })
    }
    function loadDpetTree(url) {
        $.ajax({
            url: path + url,
            success: function (result) {
                aclModuleList = result.data;
                var aclListTemplate = $("#aclListTemplate").html();
                var addAclModelListTemplate = $("#addAclModelListTemplate").html();
                var addAclListTemplate = $("#addAclListTemplate").html();
                createMustacheTree(aclModuleList, aclListTemplate, {aclModuleList: aclModuleList}, $("#aclModelTree"), "#aclModel_", nesBindEvent);
                createMustacheTree(aclModuleList, addAclModelListTemplate, {addAclModelTreeList: aclModuleList}, $("#addAclModelTree"), "#addAclModel_", nesBindEventAddAclModel);
                createMustacheTree(aclModuleList, addAclListTemplate, {addAclTreeList: aclModuleList}, $("#addAclTree"), "#addAcl_", nesBindEventAddAcl);
                loadAclList(lastClickAclModelId);
                bindEditAclModelClik();
            }
        })
    }
    function createMustacheTree(treeList, template, listData, ele, idStr, bindEvent) {
        var ren = Mustache.render(template, listData);
        $(ele).html(ren);
        recursiveRender(treeList, template, idStr, listData);
        nestableTree(bindEvent, ele);
    }
    function recursiveRender(aclTreeList, aclListTemplate, idStr, aclModelTreeListData) {
        if (aclTreeList && aclTreeList.length > 0) {
            $(aclTreeList).each(function (i, item) {
                aclModelTreeMap[item.id] = item;
                if (item.aclModuleList && item.aclModuleList.length > 0) {
                    aclModelTreeListData[getFirstAttr(aclModelTreeListData)] = item.aclModuleList;
                    var rendered = Mustache.render(aclListTemplate, aclModelTreeListData);
                    $(idStr + item.id).append(rendered);
                    recursiveRender(item.aclModuleList, aclListTemplate, idStr, aclModelTreeListData);
                }
            })
        }
    }
</script>

</body>

</html>
