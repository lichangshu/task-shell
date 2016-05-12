<%-- 
    Document   : hello
    Created on : Sep 21, 2015, 4:40:08 PM
    Author     : changshu.li
--%><%
%><%@page contentType="text/html" pageEncoding="UTF-8"%><%
%><%@include file="_head.jsp" %>
<h1>Route Demo index</h1>

<ul ng-app="task-list" ng-controller="tlist">
  <li ng-repeat="x in listtask">
    <a href="#/list/{{x.taskKey}}">{{x.cmdText}}</a>
    <span> {{x.time}} </span>
  </li>
</ul>
<script type="text/javascript">
  (function (angular) {
    angular.module("task-list", []).controller("tlist", function ($scope, $http) {
      $http.get("${ROOT}/runing/ajax/history").success(function (rsp) {
        angular.forEach(rsp.data, function (v, k) {
          var dt = new Date(v.createTime || 0);
          v.time = dt.getFullYear() + "-" + (dt.getMonth() + 1) + "-"
                  + dt.getDate() + " " + dt.getHours() + ":"
                  + dt.getMinutes() + ":" + dt.getSeconds();
        });
        $scope.listtask = rsp.data;
      });
    });
  })(window.angular);
</script>

<%@include file="_foot.jsp" %>
