<%-- 
    Document   : hello
    Created on : Sep 21, 2015, 4:40:08 PM
    Author     : changshu.li
--%><%
%><%@page contentType="text/html" pageEncoding="UTF-8"%><%
%><%@include file="_head.jsp" %>
<h1>运行命令</h1>

<div ng-app="cmd-form" ng-controller="cmds">
  <form method="post" class="form-inline">
    <div class="form-group">
      <label> 命令 </label>
      <input type="text" value="" name="full" class="form-control" ng-model="cmdinline" /></div>
    <div class="form-group">
      <input type="hidden" value="{{it}}" name="cmd" ng-repeat="it in cmdinline.split(' ')" />
      <button class="btn btn-default" ng-click="tasksumit()">提交</button>
    </div>
    <div class="form-group"><code>{{cmdinline}}</code></div>
  </form>
  <hr />
  <pre ng-repeat="c in runcmd"><!--
    --><p><span>{{c.cmd}}</span>  <button ng-click="c.shutdown()">shutdown</button> {{c.isshutdown}}</p><!--
      --><ol><li ng-repeat="lg in c.listlog track by $index">{{lg.node}}</li></ol></pre>
</div>
<script type="text/javascript">
  (function (angular) {
    angular.module("cmd-form", []).controller("cmds", function ($scope, $http) {
      $scope.cmdinline = "ping -c 3 www.baidu.com";
      $scope.runcmd = [];
      $scope.tasksumit = function () {
        var strs = $scope.cmdinline.split(" "), dts = [];
        var task ={cmd:$scope.cmdinline};
        $scope.runcmd.unshift(task);
        $http.post("${ROOT}/runing/cmd", {"cmd": strs}).success(function (rsp) {
            if(!rsp.success){
                alert(rsp.errors.join(","));
                return;
            }
          $scope.token = rsp.data.taskKey;
          var from = 0, ok = true;
          var itv = setInterval(function () {
            if (ok) {
              ok = false;
              $http.get("${ROOT}/runing/loging?from=" + from + "&key=" + rsp.data.taskKey).success(function (rsp) {
                dts = dts.concat(rsp.data);
                task.listlog = dts;
                from += rsp.data.length;
                ok = true;
                if (rsp.messages && rsp.messages[0]) {
                  if (rsp.messages[0] === 'closed') {
                    clearInterval(itv);
                  }
                }
              });
            }
          }, 500);
        });
        task.shutdown = function(){
          if(task.isshutdown) return;
          var key = $scope.token;
          if(!key) alert("Not find run task!");
          $http.get("${ROOT}/runing/shutdown?key=" + key).success(function(rsp){
            if(!rsp.success){
              alert(rsp.errors.join(","));
            }else{
              task.isshutdown = "This is shudown !";
            }
          });
        };
      };
    });
  })(window.angular);
</script>

<%@include file="_foot.jsp" %>
