<%-- 
    Document   : hello
    Created on : Sep 21, 2015, 4:40:08 PM
    Author     : changshu.li
--%><%
%><%@page contentType="text/html" pageEncoding="UTF-8"%><%
%><%@include file="_head.jsp" %>
<h1 id="EDIT">定时任务列表</h1>
<div ng-app="task-list" ng-controller="listCtr">
    <div ng-show="isedit">
        <form class="form-horizontal">
            <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" ng-model="task.name" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">运行时目录</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" ng-model="task.rundir" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">状态</label>
                <div class="col-sm-4">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="enable" ng-checked="task.enable==true" ng-model="task.enable"/> 启用
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">定时器</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" ng-model="task.timer"
                           placeholder="秒 分 时 日 月 周 年" />
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <pre>
秒：    0-59, - * /
分：    0-59, - * /
时：    0-23, - * /
天：    (月)1-31, - * ? / L W
月：    1-12 or JAN-DEC, - * /
天：    (周) 1-7 or SUN-SAT, - * ? / L #
年：    (Optional) empty, 1970-2199, - * /
                    </pre>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">命令</label>
                <div class="col-sm-6">
                    <div class="input-group">
                        <input type="text" class="form-control" ng-model="task.cmdval[0].val" />
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="button" ng-click="task.add(0)">
                                <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group" ng-repeat="x in task.cmdval| filter:skipfirst">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-6">
                    <div class="input-group">
                        <input type="text" class="form-control" ng-model="x.val" />
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="button" ng-click="task.add($index + 1)">
                                <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                            </button>
                            <button class="btn btn-default" type="button" ng-click="task.del($index + 1)">
                                <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button ng-click="task.save()" class="btn">保存</button>
                    <button ng-click="isedit = 0" class="btn">隐藏</button>
                </div>
            </div>
        </form>
        <hr/>
    </div>
    <div>
        <table class="table table-bordered">
            <thead><th>id</th><th>name</th><th>cmd</th><th>状态</th><th>time</th>
            <th>操作 <button ng-click="edit()" class="btn btn-sm">添加</button> </th></thead>
        <tr ng-repeat="item in listtask" ng-if="!item.removed">
                <td>{{item.id}}</td><td>{{item.name}}</td><td>{{item.cmd}}</td>
                <td>{{item.enable?'启用':'禁用'}}</td><td>{{item.timer}}</td>
                <td>
                    <button class="btn btn-sm" ng-click="item.edit()">编辑</button>
                    <button class="btn btn-sm" ng-click="remove(item.id)">删除</button>
                </td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    (function (angular) {
        angular.module("task-list", []).controller("listCtr", function ($scope, $http) {
            $scope.skipfirst = function (v, i) {
                return i > 0;
            };
            $scope.remove = function (id) {
                 $http.post("${ROOT}/task/remove", {id:[id]}).success(function(json){
                     var m = {};
                     angular.forEach(json.data, function(v){
                         m[""+v] = true;
                     });
                     angular.forEach($scope.listtask, function(v,i){
                         v.removed = m[v.id+""];
                     });
                 });
            };
            $scope.edit = function (task) {
                $scope.isedit = true;
                $scope.task = task || {};
                var cmds = [];
                angular.forEach($scope.task.cmd, function (v, i) {
                    cmds.push({val: v});
                });
                $scope.task.cmdval = cmds;
                $scope.task = angular.extend({}, {save: $scope.save, del: function (i) {
                        var c = $scope.task.cmdval || [];
                        $scope.task.cmdval = [].concat(c.slice(0, i), c.slice(i + 1));
                    }, add: function (i) {
                        var c = $scope.task.cmdval || [];
                        $scope.task.cmdval = [].concat(c.slice(0, i + 1), [{val: ""}], c.slice(i + 1));
                    }}, $scope.task);
                location.href = "#EDIT";
            };
            $scope.save = function () {
                var cmds = [];
                angular.forEach($scope.task.cmdval, function (v, i) {
                    if (v && v.val)
                        cmds.push(v.val);
                });
                var task = angular.copy($scope.task);
                task.cmd = cmds;
                delete task.cmdval;
                $http.post("${ROOT}/task/" + ($scope.task.id > 0 ? "edit" : "add"), task)
                        .success(function (rsp) {
                            if (rsp.success) {
                                alert("保存成功");
                            } else {
                                alert(rsp.errors.join(","));
                            }
                        });
            };
            $http.get("${ROOT}/task/list/json").success(function (rsp) {
                angular.forEach(rsp, function (v) {
                    v.edit = function () {
                        $scope.edit(angular.copy(v));
                    };
                    v.save = $scope.save;
                    v.editToshow = $scope.editToshow;
                });
                $scope.listtask = rsp;
            });
        });
    })(window.angular);
</script>

<%@include file="_foot.jsp" %>
