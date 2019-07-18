<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8"/>
    <title>个人信息</title>
    <jsp:include page="include/public-static-file.jsp"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/mygxin.css"/>
    <script src="${path}/static/js/user.js" type="text/javascript" charset="utf-8"></script>

</head>
<body>
<jsp:include page="include/head.jsp"/>
<!------------------------------idea------------------------------>
<div class="address mt" id="add">
    <div class="wrapper clearfix">
        <a href="index.html" class="fl">首页</a>
        <span>/</span>
        <a href="mygxin.html" class="on">个人中心</a>
    </div>
</div>

<!------------------------------Bott------------------------------>
<div class="Bott">
    <div class="wrapper clearfix">
        <div class="zuo fl">

            <jsp:include page="include/personal-left.jsp" />
        </div>
        <div class="you fl">
            <div class="tx clearfix">
                <div class="fl clearfix">
                    <a href="#" class="fl">
                        <c:if test="${sessionScope.user.imgUrl == null}">
                            <img class="jia-userAvatar" src="${path}/static/img/tx.jpg"/>

                        </c:if>
                        <c:if test="${sessionScope.user.imgUrl != null}">
                            <img class="jia-userAvatar" src="${sessionScope.user.imgUrl}"/>

                        </c:if>
                    </a>
                    <p class="fl"><span>${sessionScope.user.user_name}</span><a href="${path}/userInfo?userId=${sessionScope.user.user_id}">修改个人信息></a></p>
                </div>
                <c:if test="${sessionScope.user.user_mail == null}">
                    <div class="fr">绑定邮箱：未绑定</div>
                </c:if>
                <c:if test="${sessionScope.user.user_mail != null}">
                    <div class="fr">绑定邮箱：${sessionScope.user.user_mail}</div>

                </c:if>

            </div>
            <div class="bott">
                <div class="clearfix">
                    <a href="#" class="fl"><img src="${path}/static/img/gxin1.jpg"/></a>
                    <p class="fl"><span>待支付的订单：<strong>0</strong></span>
                        <a href="personal-orders.jsp">查看待支付订单></a>
                    </p>
                </div>
                <div class="clearfix">
                    <a href="#" class="fl"><img src="${path}/static/img/gxin2.jpg"/></a>
                    <p class="fl"><span>待收货的订单：<strong>0</strong></span>
                        <a href="personal-orders.jsp">查看待收货订单></a>
                    </p>
                </div>
                <div class="clearfix">
                    <a href="#" class="fl"><img src="${path}/static/img/gxin3.jpg"/></a>
                    <p class="fl"><span>待评价的订单：<strong>0</strong></span>
                        <a href="myprod.jsp">查看待评价订单></a>
                    </p>
                </div>
                <div class="clearfix">
                    <a href="#" class="fl"><img src="${path}/static/img/gxin4.jpg"/></a>
                    <p class="fl"><span>喜欢的商品：<strong>0</strong></span>
                        <a href="#">查看喜欢的商品></a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>


<!--返回顶部-->
<jsp:include page="include/right-sidebar.jsp"></jsp:include>
<div class="footer">
    <div class="top">
        <div class="wrapper">
            <div class="clearfix">
                <a href="#2" class="fl"><img src="${path}/static/img/foot1.png"/></a>
                <span class="fl">7天无理由退货</span>
            </div>
            <div class="clearfix">
                <a href="#2" class="fl"><img src="${path}/static/img/foot2.png"/></a>
                <span class="fl">15天免费换货</span>
            </div>
            <div class="clearfix">
                <a href="#2" class="fl"><img src="${path}/static/img/foot3.png"/></a>
                <span class="fl">满599包邮</span>
            </div>
            <div class="clearfix">
                <a href="#2" class="fl"><img src="${path}/static/img/foot4.png"/></a>
                <span class="fl">手机特色服务</span>
            </div>
        </div>
    </div>
    <p class="dibu">最家家居&copy;2013-2017公司版权所有 京ICP备080100-44备0000111000号<br/>
        违法和不良信息举报电话：400-800-8200，本网站所列数据，除特殊说明，所有数据均出自我司实验室测试</p>
</div>

</body>
</html>
