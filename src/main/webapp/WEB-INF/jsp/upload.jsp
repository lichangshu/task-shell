<%-- 
    Document   : upload
    Created on : May 12, 2016, 6:22:45 PM
    Author     : changshu.li
--%><%
%><%@page contentType="text/html" pageEncoding="UTF-8"%><%
%><%@include file="_head.jsp" %>
<h1>文件上传</h1>
<div>
    <c:if test="${!empty obj}">
        <p> 上传文件路径：<strong>${obj.data}</strong> </p>
        <hr />
    </c:if>
    <form class="form-inline" enctype="multipart/form-data" method="post">
        <div class="form-group">
            <label class="sr-only" for="exampleInputEmail3">Email address</label>
            <input name="file" type="file" class="form-control" id="exampleInputEmail3" placeholder="File">
        </div>
        <div class="form-group">
            <label class="sr-only" for="exampleInputPassword3">Password</label>
            <input name="path" type="text" placeholder="路径，空则为默认路径" class="form-control" id="exampleInputPassword3">
        </div>
        <button type="submit" class="btn btn-default">提交</button>
    </form>
</div>
<%@include file="_foot.jsp" %>
