<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8" />
    <title>最家</title>
    <jsp:include page="include/public-static-file.jsp" />
    <link rel="stylesheet" type="text/css" href="${path}/static/css/proList.css" />
    <link rel="stylesheet" type="text/css" href="${path}/static/css/mygxin.css" />
    <script src="${path}/static/js/pro.js" type="text/javascript" charset="utf-8"></script>
    <script src="${path}/static/js/user.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://cdn.bootcss.com/vue/2.4.2/vue.min.js"></script>
    <script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.8&key=b03bbaf1ff4c80e018561e5eb1210906"></script>
    <script src="${path}/static/js/city.js" type="text/javascript" charset="utf-8"></script>


</head>
<body>
<jsp:include page="include/head.jsp" />

<!------------------------------idea------------------------------>
<div class="address mt">
    <div class="wrapper clearfix">
        <a href="index.html" class="fl">首页</a>
        <span>/</span>
        <a href="mygxin.html">个人中心</a>
        <span>/</span>
        <a href="address.html" class="on">地址管理</a>
    </div>
</div>

<!------------------------------Bott------------------------------>
<div class="Bott order cart mt">
    <div class="wrapper clearfix orderCon ">
        <div class="zuo fl">

            <jsp:include page="include/personal-left.jsp"/>
        </div>
        <div class="you fl orderL fl">
            <jsp:include page="include/address.jsp"/>
        </div>
    </div>
</div>


<!--返回顶部-->
<jsp:include page="include/right-sidebar.jsp"></jsp:include>


<!--footer-->
<jsp:include page="include/bottom.jsp"></jsp:include>

</body>
</html>
