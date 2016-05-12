<%-- 
    Document   : hello
    Created on : Sep 21, 2015, 4:40:08 PM
    Author     : changshu.li
--%><%
%><%@page contentType="text/html" pageEncoding="UTF-8"%><%
%><%@include file="_head.jsp" %>
<h1>Run task Loger!</h1>
<div ng-app="cmd-form" ng-controller="cmds">
    <hr />
    <pre ng-repeat="c in runcmd"><!--
    --><p><span>{{c.cmd}}</span>  <button ng-click="c.shutdown()">shutdown</button> {{c.isshutdown}}</p><!--
      --><ol><li ng-repeat="lg in c.listlog track by $index">{{lg.node}}</li></ol></pre>
</div>
<div style="display: none;" id="encodecmd">${fn:escapeXml(fn:join(obj.cmd, ' '))}</div>
<script type="text/javascript">
    (function (angular) {
      angular.module("cmd-form", []).controller("cmds", function ($scope, $http) {
        var from = 0, dts = [], ok = true;
        $scope.runcmd = [{
            cmd: document.getElementById("encodecmd").innerHTML,
            token: "${param.key}"
          }];
        var task = $scope.runcmd[0];
        var itv = setInterval(function () {
          if (!ok)
            return;
          $http.get("${ROOT}/runing/loging?from=" + from + "&key=${param.key}").success(function (rsp) {
            if (!rsp.success) {
              clearInterval(itv);
              alert(rsp.errors.join(","));
              return;
            }
            ok = true;
            dts = dts.concat(rsp.data);
            task.listlog = dts;
            from += rsp.data.length;
            if (rsp.messages && rsp.messages[0]) {
              if (rsp.messages[0] === 'closed') {
                task.isshutdown = "This is shudown !";
                clearInterval(itv);
              }
            }
          });
        }, 500);
        task.shutdown = function () {
          if (task.isshutdown)
            return;
          var key = task.token;
          if (!key)
            alert("Not find run task!");
          $http.get("${ROOT}/runing/shutdown?key=" + key).success(function (rsp) {
            if (!rsp.success) {
              alert(rsp.errors.join(","));
            } else {
              task.isshutdown = "This is shudown !";
            }
          });
        };
      });
    })(window.angular);
</script>

<%@include file="_foot.jsp" %>
