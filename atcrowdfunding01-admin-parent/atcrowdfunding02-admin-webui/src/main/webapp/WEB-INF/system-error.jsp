<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2021/1/12
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/login.css">
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(function(){
        $("button").click(function(){
            // 调用 back()方法类似于点击浏览器的后退按钮
            window.history.back();
        });
    });
</script>
<body>
    <div class="container" style="text-align: center;">
        <h1>出错了！！！</h1>
        <h4>${requestScope.exception.message }</h4>
        <button style="width: 300px;margin: 0px auto 0px auto;" class="btn btn-lg btn-success btn-block">返回</button>
    </div>
</body>
</html>
