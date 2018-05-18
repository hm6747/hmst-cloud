<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="/common/common_quote.jsp"/>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>角色管理</title>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>角色列表</h5>
                    <div class="ibox-tools">
                        <a onclick="addRole()" class="btn btn-primary btn-xs" data-toggle="modal"
                           data-target="#roleModal"><i class="fa fa-plus"></i>创建新角色</a>
                    </div>
                    <div class="ibox-content">
                        <div class="row m-b-sm m-t-sm">
                            <div class="col-md-1">
                                <button type="button" id="loading-example-btn" class="btn btn-white btn-sm"><i
                                        class="fa fa-refresh"></i> 刷新
                                </button>
                            </div>
                            <div class="col-md-11">
                                <div class="input-group">
                                    <input type="text" placeholder="查找角色" class="input form-control" id="keyword">
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
                            <span class="pull-right small text-muted">总共<span id="total"></span>个角色</span>
                            <li class="active"><a data-toggle="tab" href="#tab-1"><i class="fa fa-user"></i> 角色</a>
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
                                                <th class="table-id">角色名称</th>
                                                <th class="table-type">角色类型</th>
                                                <th class="table-num">角色状态</th>
                                                <th class="table-type ">更新时间</th>
                                                <th class="table-type ">更新人</th>
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
<div class="modal inmodal" id="roleModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="addRoleTitle">添加角色</h4>
                <%--     <small>这里可以显示副标题。--%>
            </div>
            <form method="get" class="form-horizontal" id="roleForm">
                <div class="col-md-12 modal-body">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" aria-required="true" required id="roleName"
                                   name="name">
                            <input type="hidden" name="id" id="roleId"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">类型</label>
                        <div class="col-sm-10">
                            <select class="form-control m-b" id="addRoleType" name="type" data-placeholder="选择类型">
                                <option value="1">管理员</option>
                                <option value="2">游客</option>
                                <option value="3">其他</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">状态</label>
                        <div class="col-sm-10">
                            <select class="form-control m-b" id="addRoleStatus" name="status"
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
                            <textarea name="remark" id="addRoleRemark" class="form-control" required=""
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
<div class="modal inmodal" id="roleAclModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated fadeIn">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span
                        aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">角色授权</h4>
                <%--     <small>这里可以显示副标题。--%>
            </div>
            <form method="get" class="form-horizontal" id="roleAclForm">
                <input type="hidden" id="updateAclRoleId">
                <div class="col-md-12 modal-body">
                    <div class="form-group">
                        <ul id="roleAclTree" class="ztree"></ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-white closeModel" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="roleAclUpdate">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script id="listTemplate" type="x-tmpl-mustache">
{{#roleList}}
<tr>
    <td><a data-toggle="tab" href="#contact-1" class="client-link" data-id="{{id}}">{{name}}</a>
    </td>
    <td>{{showType}}</td>
    <td class="client-status"><span class="label {{statusClass}}">{{showStatus}}</span>
     <td class="formatData" data-formatStr="yyyy/MM/dd hh:mm:ss">{{operatorTime}}</td>
    <td>{{operator}}</td>
    <td>
                          <a  class="btn btn-white btn-sm editRole" data-toggle="modal" data-target="#roleModal"
                                        data-Id="{{id}}"><i class="fa fa-pencil"></i> 编辑 </a>
                     <a class="btn btn-white btn-sm delRole" data-Id="{{id}}"><i class="fa fa-folder"></i> 删除 </a>
               <a class="btn btn-white btn-sm aclRole" data-Id="{{id}}" data-toggle="modal" data-target="#roleAclModal"><i class="fa fa-power-off" ></i> 权限 </a>

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
        commonValidate("roleForm",updateRole);
        $("#roleAclUpdate").on("click",function () {
            updateRoleAcl();
        })
    });

    function updateRole() {
        var url = (!$("#roleId").val()) ? "/sys/role/save.json" : "/sys/role/update.json";
        var data = $("#roleForm").serializeArray();
            submitAjaxForm(url, data, function (result) {
                console.log(result)
                $(".closeModel").click();
                searchRole();
            }, function (result) {
                console.log(result)
            });
    }
    function searchRole() {
        var keyword = $("#keyword").val();
        loadRoleList(keyword);
    }
    function loadRoleList(keyword) {
        var url = "/sys/role/list.json";
        var data = {
            keyword: keyword
        }
        var listJson = {
             roleList: "",
            "showStatus": function () {
                return this.status == 1 ? "有效" : (this.status == 0 ? "无效" : "冻结");
            },
            "statusClass": function () {
                return this.status == 1 ? "label-primary" : this.status == 0?"label-warning":"label-danger";
            },
            "showType": function () {
                return this.type == 1 ? "管理员" : (this.type == 2 ? "游客" : "其他");
            }
        };
        var listTemplate = $("#listTemplate").html();
        var successCallback = function (result) {
            createMustache(listJson, result, listTemplate, $("#listTable"));
            createListMap(result, roleMap);
           $("#total").html(result.data.total);
            bindRoleListClik();
            formatData();
        }
        getTableList(url, data, true, successCallback);
    }
    function addRole() {
        $("#roleName").val("");
        $("#addRoleType").val("");
        $("#addRoleStatus").val("");
        $("#addRoleRemark").val("");
        $("#roleId").val("");
        $("#addRoleTitle").html("添加角色");
    }
    function bindRoleListClik() {
        $(".editRole").on("click", function () {
            $("#addRoleTitle").html("编辑权角色");
            var targetRole = roleMap[$(this).attr("data-Id")];
            if (targetRole) {
                $("#roleId").val(targetRole.id);
                $("#addRoleRemark").val(targetRole.remark);
                $("#addRoleStatus").val(targetRole.status);
                $("#addRoleType").val(targetRole.type);
                $("#roleName").val(targetRole.name);
            }
        })
        $(".aclRole").on("click",function () {
            createAclRoleTree($(this).attr("data-Id"));
        })
    }


    // zTree
    <!-- 树结构相关 开始 -->
    var zTreeObj = [];
    var modulePrefix = 'm_';
    var aclPrefix = 'a_';
    var nodeMap = {};

    var setting = {
        check: {
            enable: true,
            chkDisabledInherit: true,
            chkboxType: {"Y": "ps", "N": "ps"}, //auto check 父节点 子节点
            autoCheckTrigger: true
        },
        data: {
            simpleData: {
                enable: true,
                rootPId: 0
            }
        },
        callback: {
            onClick: onClickTreeNode
        }
    };

    function onClickTreeNode(e, treeId, treeNode) { // 绑定单击事件
        var zTree = $.fn.zTree.getZTreeObj("roleAclTree");
        zTree.expandNode(treeNode);
    }

    function createAclRoleTree(roleId) {
        if (roleId == -1) {
            return;
        }
        $.ajax({
            url: path+"/sys/role/roleTree.json",
            data : {
                roleId: roleId
            },
            type: 'POST',
            success: function (result) {
                if (result.ret) {
                    renderRoleTree(result.data);
                    $("#updateAclRoleId").val(roleId);
                } else {

                }
            }
        });
    }
    function renderRoleTree(aclModuleList) {
        zTreeObj = [];
        recursivePrepareTreeData(aclModuleList);
        for(var key in nodeMap) {
            zTreeObj.push(nodeMap[key]);
        }
        $.fn.zTree.init($("#roleAclTree"), setting, zTreeObj);
    }

    function recursivePrepareTreeData(aclModuleList) {
        // prepare nodeMap
        if (aclModuleList && aclModuleList.length > 0) {
            $(aclModuleList).each(function(i, aclModule) {
                var hasChecked = false;
                if (aclModule.aclList && aclModule.aclList.length > 0) {
                    $(aclModule.aclList).each(function(i, acl) {
                        zTreeObj.push({
                            id: aclPrefix + acl.id,
                            pId: modulePrefix + acl.aclModuleId,
                            name: acl.name + ((acl.type == 1) ? '(菜单)' : ''),
                            chkDisabled: !acl.hasAcl,
                            checked: acl.checked,
                            dataId: acl.id
                        });
                        if(acl.checked) {
                            hasChecked = true;
                        }
                    });
                }
                if ((aclModule.aclModuleList && aclModule.aclModuleList.length > 0) ||
                        (aclModule.aclList && aclModule.aclList.length > 0)) {
                    nodeMap[modulePrefix + aclModule.id] = {
                        id : modulePrefix + aclModule.id,
                        pId: modulePrefix + aclModule.parentId,
                        name: aclModule.name,
                        open: hasChecked
                    };
                    var tempAclModule = nodeMap[modulePrefix + aclModule.id];
                    while(hasChecked && tempAclModule) {
                        if(tempAclModule) {
                            nodeMap[tempAclModule.id] = {
                                id: tempAclModule.id,
                                pId: tempAclModule.pId,
                                name: tempAclModule.name,
                                open: true
                            }
                        }
                        tempAclModule = nodeMap[tempAclModule.pId];
                    }
                }
                recursivePrepareTreeData(aclModule.aclModuleList);
            });
        }
    }
    function getZTreeSelectedId(ele,prefix) {
        var treeObj = $.fn.zTree.getZTreeObj(ele);
        var nodes = treeObj.getCheckedNodes(true);
        var v = "";
        for(var i = 0; i < nodes.length; i++) {
            if(nodes[i].id.startsWith(prefix)) {
                v += "," + nodes[i].dataId;
            }
        }
        return v.length > 0 ? v.substring(1): v;
    }
    function updateRoleAcl() {
        var roleId= $("#updateAclRoleId").val();
        var url = "/sys/role/changeAcls.json";
        var data = {
            roleId:roleId,
            aclIds: getZTreeSelectedId("roleAclTree","a_")
        };
        submitAjaxForm(url, data, function (result) {
            $(".closeModel").click();
        }, function (result) {

        });
    }
</script>

</body>

</html>
