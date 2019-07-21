<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%--<c:set var="order" value="${requestScope.order}"/>--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>layui</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <jsp:include page="include/public-static-file.jsp"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/mygxin.css"/>
    <script src="${path}/static/js/user.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/sou-commodity.css"/>
    <script src="https://cdn.bootcss.com/layer/2.3/layer.js"></script>
    <link rel="stylesheet" type="text/css" href="${path}/static/layui/css/layui.css"/>
    <script src="${path}/static/layui/layui.js " type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/public.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/myorder.css"/>
    <script src="${path}/static/js/public.js" type="text/javascript" charset="utf-8"></script>
    <script src="${path}/static/js/user.js" type="text/javascript" charset="utf-8"></script>
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->

</head>

<body class="layui-layout-body">
<div id="layer-photos-demo" class="layer-photos-demo">
    <img layer-pid="图片id，可以不写" layer-src="https://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D600%2C800/sign=fe2b09040d4f78f0805e92f54901266d/a9d3fd1f4134970a3588877f9bcad1c8a6865de2.jpg" src="https://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D600%2C800/sign=fe2b09040d4f78f0805e92f54901266d/a9d3fd1f4134970a3588877f9bcad1c8a6865de2.jpg" alt="图片名">
    <img layer-pid="图片id，可以不写" layer-src="大图地址" src="缩略图" alt="图片名">
</div>

<script>
    //调用示例
    layer.photos({
        photos: '.layer-photos-demo'
        ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
    });
</script>
</body>
</html>
