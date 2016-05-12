<%@page contentType="text/html" pageEncoding="UTF-8"%><%
%><%@include file="WEB-INF/jsp/_env.jsp" %><%
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>task run</title>
    </head>
    <body>
        <h1> This Is Task Run System! </h1>
        <h2> Task </h2>
        <ul>
          <li><a href="${ROOT}/runing/cmd">执行命令</a></li>
          <li><a href="${ROOT}/runing/list">查看日志</a></li>
          <li><a href="${ROOT}/runing/history">命令历史</a></li>
          <li><a href="${ROOT}/task/list">定时任务</a></li>
        </ul>
        <h2> 文件上传 </h2>
        <ul>
          <li><a href="${ROOT}/upload/file">文件上传</a></li>
        </ul>
    </body>
</html>
