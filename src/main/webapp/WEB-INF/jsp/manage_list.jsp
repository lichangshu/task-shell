<%-- 
    Document   : hello
    Created on : Sep 21, 2015, 4:40:08 PM
    Author     : changshu.li
--%><%
%><%@page contentType="text/html" pageEncoding="UTF-8"%><%
%><%@include file="_head.jsp" %>
<h1>查看运行日志</h1>

<div class="row" ng-app="task-list" ng-controller="tlist">
    <div class="col-lg-8">
        <div class="col-lg-offset-1">
            <p class="pull-right"> <button class="btn btn-default btn-sm" ng-click="remove_log()">删除日志</button> </p>
            <ul class="list-unstyled pull-left">
                <li><label> <input type="checkbox" ng-click="select_close($event)" /> closed </label></li>
            </ul>
        </div>
        <table class="table table-bordered">
            <thead> <th>状态</th> <th>命令</th> <th>运行时间</th> <th>操作</th> </thead>
        <tr ng-cloak ng-repeat="x in listtask|orderBy:'-createTime'" ng-hide="x.rm_hiden" >
                <td>
                    <label>
                        <input type="checkbox" ng-if="!x.running" name="deltoken" ng-model="x.isRemove" />
                        <span ng-class="{true: 'label-success', false: 'label-default'}[x.running]" class="label">{{x.running?'running':'closed'}}</span>
                    </label>
                </td>
                <td><a href="${ROOT}/runing/logs?key={{x.taskKey}}">{{x.cmdText}}</a></td>
                <td><span>{{x.createTime|date:'yyyy-MM-dd hh:mm:ss'}}</span></td>
                <td></td>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript">
    (function (angular) {
      angular.module("task-list", []).controller("tlist", function ($scope, $http) {
        $http.get("${ROOT}/runing/ajax/list").success(function (rsp) {
          $scope.listtask = rsp.data;
        });
        $scope.remove_log = function(){
            var rms = [];
            angular.forEach($scope.listtask, function(v){
                if(v.isRemove){
                    rms.push(v.taskKey);
                }
            });
            $http.post("${ROOT}/runing/ajax/remove", {rm:rms}).success(function(json){
                var rmd = json.data, jmap = {};
                for(var i in rmd){jmap[rmd[i]] = true;}
                angular.forEach($scope.listtask, function(v){
                    if(jmap[v.taskKey]){
                        v.rm_hiden = true;
                    }
                });
            });
        };
        $scope.select_close = function($e){
                angular.forEach($scope.listtask, function(v){
                    v.isRemove = $e.target.checked;
                });
        };
      });
    })(window.angular);
</script>

<%@include file="_foot.jsp" %>
