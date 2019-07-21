<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <style>
        .jia-review-avatar {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            object-fit: cover;
        }

        .jia-review-img {
            width: 40px;
            height: 40px;
            object-fit: cover;
        }

    </style>
    <meta charset="utf-8"/>
    <title>最家</title>
    <link rel="stylesheet" type="text/css" href="${path}/static/layui/css/layui.css"/>
    <jsp:include page="include/public-static-file.jsp"></jsp:include>
    <script src="${path}/static/layui/layui.js " type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/myorder.css"/>
    <script src="${path}/static/js/user.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<!------------------------------head------------------------------>
<jsp:include page="include/head.jsp"></jsp:include>

<!------------------------------idea------------------------------>
<div class="address mt">
    <div class="wrapper clearfix">
        <a href="index.html" class="fl">首页</a>
        <span>/</span>
        <a href="mygxin.html" class="on">个人中心</a>
        <span>/</span>
        <a href="myprod.html" class="on">待评价商品</a>
    </div>
</div>


<!------------------------------Bott------------------------------>
<div class="Bott">
    <div class="wrapper clearfix">
        <div class="zuo fl">
            <%@include file="include/personal-left.jsp" %>

        </div>
        <div class="you fl">
            <div class="my clearfix">
                <h2 class="fl">商品评价</h2>
            </div>
            <div class="dlist">
                <ul class="clearfix" id="pro">
                    <%--							<li class="on"><a href="#2">待评价商品</a></li>--%>
                    <%--							<li><a href="#2">已评价商品</a></li>--%>
                    <%--							<li><a href="#2">评价失效商品</a></li>--%>
                </ul>
            </div>
            <div class="clearfix">

                <c:forEach items="${requestScope.reviews}" var="review" varStatus="i">

                    <c:forEach items="${review.orderDetail}" var="orderDetail">
                        <c:if test="${orderDetail.order_detail_status == 6}">
                            <div class="dkuang">
                                <p class="one"></p>
                                <div class="word clearfix">
                                    <ul class="fl clearfix">
                                        <li>评价时间：<fmt:formatDate value="${review.review_time}" type="both"/></li>

                                        <li class="order-num">订单号：<span>${review.order.order_id}</span></li>
                                        <li>
                                        <span style="margin-left: 20px;color: red;">
                                         <c:if test="${review.review_grade == 3}">
                                             [ 好评 ]
                                         </c:if>
                                         <c:if test="${review.review_grade == 2}">
                                             [ 中评 ]
                                         </c:if>
                                         <c:if test="${review.review_grade == 1}">
                                             [ 差评 ]
                                         </c:if>
                                        </span>
                                        </li>

                                    </ul>


                                </div>

                                <div class="shohou clearfix">
                                    <a href="#" class="fl"><img src="${orderDetail.order_detail_commodity_img}"/></a>

                                    <div class="jia-my-review-left" style="width: 200px; float:left;">

                                        <span hidden>${orderDetail.order_detail_commodity_name}</span>
                                        <a href="#"> <span>${orderDetail.order_detail_commodity_name}</span>
                                        </a>
                                        <a href="#">
                                            <c:set var="string"
                                                   value="${fn:replace(fn:replace(fn:replace(fn:replace(orderDetail.order_detail_commodity_specs_value, '\"', ''), '{', ''), '}', ''), ',', '')}">
                                            </c:set>
                                                ${string}
                                        </a>

                                        <a href="#">¥${orderDetail.order_detail_commodity_price}×${orderDetail.order_detail_commodity_count}</a>

                                    </div>

                                    <div class="jia-my-review-right"
                                         style="float: right; width: 400px; border-left: 1px solid lightgray; padding-left: 30px; ">
                                        <p>${review.review_content}</p>


                                        <div class="layer-photos-demo">
                                            <c:forEach items="${review.reviewImgs}" var="img">
                                                <img class="jia-review-img" layer-src="${img.img_url}"
                                                     src="${img.img_url}" alt="图片名">
                                            </c:forEach>

                                        </div>
                                    </div>

                                    <script>

                                        layer.photos({
                                            photos: '.layer-photos-demo'
                                            , anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
                                        });
                                    </script>

                                </div>


                            </div>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </div>
        </div>
    </div>
</div>


<jsp:include page="include/right-sidebar.jsp"></jsp:include>
<!--footer-->
<jsp:include page="include/bottom.jsp"></jsp:include>

</body>
</html>
