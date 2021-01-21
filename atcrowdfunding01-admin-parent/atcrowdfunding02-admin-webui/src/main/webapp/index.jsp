<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<jsp:forward page="/admin/to/login/page.html"></jsp:forward>
<script type="text/javascript">
    $(function (){
        $("#btn4").click(function (){
            layer.msg("这里是 layer 弹出的消息！");
        });
        $("#btn3").click(function(){
            var array=[5,8,12];
            var json=JSON.stringify(array);
            $.ajax({
                url:"send/array3.json",
                type:"post",
                data: json,
                contentType:"application/json;charset=utf-8",
                dataType:"json",
                success:function(response){
                    console.log(response);
                },
                error:function (response){
                    console.log(response);
                }
            });
        });
        $("#btn1").click(function(){
            $.ajax({
                url:"send/array1.html",
                type:"post",
                data: {
                    "array":[5,8,12]
                },
                dataType:"text",
                success:function(response){
                    alert(response);
                },
                error:function (response){
                    alert(response);
                }
            });
        });
    });
</script>
<body>
    <a href="test/ssm.html">测试</a>
    <br/>
    <button id="btn1">发送数组1</button>
    <button id="btn2">发送数组2</button>
    <button id="btn3">发送数组3</button>
    <button id="btn4">弹框</button>
</body>
</html>