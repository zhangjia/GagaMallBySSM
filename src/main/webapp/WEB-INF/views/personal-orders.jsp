<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8"/>
    <style>

        .bj-order-pay {
            width: 410px;
            position: fixed;
            top: 50%;
            left: 50%;
            margin-top: -180px;
            margin-left: -220px;
            z-index: 30;
            background: #fff;
            border-radius: 10px;
            padding: 15px;
            display: none;
        }


        .bj-order-review {
            width: 810px;
            position: fixed;
            top: 20%;
            /*bottom: 20%;*/
            left: 50%;
            margin-top: -100px;
            margin-left: -400px;
            z-index: 30;
            background: #fff;
            border-radius: 10px;
            padding: 15px;
            display: none;
        }
    </style>
    <title>最家</title>
    <link rel="stylesheet" type="text/css" href="${path}/static/layui/css/layui.css"/>
<%--    <jsp:include page="include/public-static-file.jsp"></jsp:include>--%>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/public.css"/>

    <%-------------------------------------------------------js----------------------------------------------%>
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/layer/2.3/layer.js"></script>
    <script src="${path}/static/layui/layui.js " type="text/javascript" charset="utf-8"></script>

    <script src="${pageContext.request.contextPath}/static/js/public.js" type="text/javascript" charset="utf-8"></script>
    <script src="${pageContext.request.contextPath}/static/js/nav.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/public.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/myorder.css"/>

    <script src="${path}/static/js/user.js" type="text/javascript" charset="utf-8"></script>

    <script>

        $(function () {

            $(".jia-do-receive").click(function () {
                console.log("aab")
                var orderId = $(this).parent().parent().siblings(".word.clearfix").children("ul").children(".order-num").children("span").text();
                ;
                console.log(orderId);

                layer.confirm('确认收货？', function (index) {
                    $.ajax({
                        url: "${path}/doReceive",
                        data: {
                            orderId: orderId
                        },
                        success: function (res) {

                            if (res.success) {
                                layer.msg('确认收货成功', {time: 1000, anim: 2, icon: 6});
                                location.reload();
                            }

                        }
                    });
                    layer.close(index);
                });

            });

        });

    </script>
</head>
<body>
<!------------------------------head------------------------------>
<jsp:include page="include/head.jsp"></jsp:include>
<jsp:include page="include/order-pay.jsp"></jsp:include>
<jsp:include page="include/review.jsp"></jsp:include>

<!------------------------------idea------------------------------>
<div class="address mt">
    <div class="wrapper clearfix">
        <a href="${path}/index" class="fl">首页</a>
        <span>/</span>
        <a href="${path}/personal">个人中心</a>
        <span>/</span>
        <a href="myorderq.html" class="on">我的订单</a>
    </div>
</div>

<!------------------------------Bott------------------------------>
<div class="Bott">
    <div class="wrapper clearfix">
        <div class="zuo fl">

            <%--            <jsp:include page="include/personal-left.jsp" />--%>
            <%@include file="include/personal-left.jsp" %>
        </div>
        <div class="you fl">
            <div class="my clearfix">
                <h2 class="fl">我的订单</h2>
                <%--                <a href="#" class="fl">请谨防钓鱼链接或诈骗电话，了解更多&gt;</a>--%>
            </div>
            <div class="dlist clearfix">
                <ul class="fl clearfix" id="wa">
                    <li class="on"><a href="#2">全部有效订单</a></li>
                    <li><a href="#2">待支付</a></li>
                    <li><a href="#2l">待收货</a></li>
                    <li><a href="#2">已关闭</a></li>
                </ul>
                <form action="#" method="get" class="fr clearfix">
                    <input type="text" name="" id="id1" value="" placeholder="请输入商品名称、订单号"/>
                    <input type="button" name="" id="id2" value=""/>
                </form>
            </div>
            <c:forEach items="${requestScope.orders}" var="order" varStatus="i">
                <div class="dkuang">
                    <p class="one"></p>
                    <div class="word clearfix">
                        <ul class="fl clearfix">
                            <li><fmt:formatDate value="${order.order_create_time}" type="both"/></li>
                            <li>${order.address.address_name}</li>
                            <li class="order-num">订单号:<span>${order.order_id}</span></li>


                            <li>${order.order_pay_type}</li>
                        </ul>
                        <p class="fr">订单金额：<span>${order.order_pay_price}</span>元</p>

                    </div>
                    <c:forEach items="${order.order_detail}" var="commodity">
                        <div class="shohou clearfix">
                            <a href="#" class="fl"><img src="${commodity.order_detail_commodity_img}"/></a>

                            <p class="fl">
                                <span hidden>${commodity.order_detail_commodity_name}</span>
                                <a href="#">${commodity.order_detail_commodity_name}
                                </a>
                                <a href="#">
                                    <c:set var="string"
                                           value="${fn:replace(fn:replace(fn:replace(fn:replace(commodity.order_detail_commodity_specs_value, '\"', ''), '{', ''), '}', ''), ',', '')}">
                                    </c:set>
                                        ${string}
                                </a>

                                <a href="#">¥${commodity.order_detail_commodity_price}×${commodity.order_detail_commodity_count}</a>
                            </p>
<%--                            <li class="order-commodity-id"><span>${commodity.commodity_specs_id}</span></li>--%>
                            <li hidden class="order-commodity-specs-id"><span>${commodity.commodity_specs_id}</span></li>
                            <li hidden class="order-detail-id"><span>${commodity.order_detail_id}</span></li>
                            <p class="fr">

                                <c:if test="${order.order_status == 1}">
                                    <a href="javascript:;">待发货</a>
                                </c:if>
                                <c:if test="${order.order_status == 2}">
                                    <a class="jia-do-receive" href="javascript:;">待收货</a>
                                </c:if>
                                <c:if test="${order.order_status == 3 and commodity.order_detail_status!=6 }">
                                    <a class="jia-id-myorder-review" href="javascript:;">待评价</a>
                                </c:if>
                                <c:if test="${order.order_status == 4}">
                                    <a class="jia-id-myorder-pay" href="javascript:;">待支付</a>
                                </c:if>
                                <c:if test="${order.order_status == 5}">
                                    <a href="myprod.html">已关闭</a>
                                </c:if>
                                <c:if test="${order.order_status == 3 and commodity.order_detail_status==6}">
                                    <a href="javascript:;">已完成</a>
                                </c:if>
                                <a href="${path}/orderDetail?orderId=${order.order_id}">订单详情</a>
                            </p>
                        </div>
                    </c:forEach>

                </div>
            </c:forEach>

            <div class="fenye clearfix">
                <a href="#"><img src="${path}/static/img/zuo.jpg"/></a>
                <a href="#">1</a>
                <a href="#"><img src="${path}/static/img/you.jpg"/></a>
            </div>
        </div>
    </div>
</div>
<!--返回顶部-->
<jsp:include page="include/right-sidebar.jsp"></jsp:include>
<!--footer-->
<jsp:include page="include/bottom.jsp"></jsp:include>

</body>
</html>
