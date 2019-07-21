<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="utf-8"/>
    <title>嘎嘎商城|家庭必需品</title>

    <%-------------------------------------------------------其他页面导入----------------------------------------------%>
    <jsp:include page="include/public-static-file.jsp"/>
    <%--		<%@ include file="public-static-file.jsp"%>--%>
    <%--		-----------------------------------------------------css----------------------------------------------%>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/index.css"/>


    <%-------------------------------------------------------js----------------------------------------------%>

    <script src="https://cdn.bootcss.com/flexslider/2.7.2/jquery.flexslider-min.js"></script>


    <script type="text/javascript">
        $(function () {
            $('#home_slider').flexslider({
                animation: 'slide',
                controlNav: true,
                directionNav: true,
                animationLoop: true,
                slideshow: true,
                slideshowSpeed: 2000,
                useCSS: false
            });

        });
    </script>

</head>
<body>
<%--		<%@ include file="head.jsp"%>--%>
<jsp:include page="include/head.jsp"/>


<!-------------------------banner--------------------------->
<div class="block_home_slider" style="margin-bottom: 100px">
    <div id="home_slider" class="flexslider">
        <ul class="slides">
            <li>
                <div class="slide">
                    <img src="https://zhangjia.oss-cn-qingdao.aliyuncs.com/img/web/banner1.png"/>
                </div>
            </li>
            <li>
                <div class="slide">
                    <img src="https://zhangjia.oss-cn-qingdao.aliyuncs.com/img/web/banner3.png"/>
                </div>
            </li>
        </ul>
    </div>
</div>

<!------------------------------thImg------------------------------>
<%--<div class="thImg">
    <%--<div class="clearfix">
        <a href="vase_proList.jsp"><img src="${path}/static/img/i1.jpg"/></a>
        <a href="commodities.jsp"><img src="${path}/static/img/i2.jpg"/></a>
        <a href="#2"><img src="${path}/static/img/i3.jpg"/></a>
    </div>
</div>--%>


<!------------------------------people------------------------------>
<div hidden class="people">
    <div class="wrapper">
<%--        <h2><img src="${path}/static/img/ih3.jpg"/></h2>--%>
        <div class="pList clearfix tran">
            <c:forEach items="${commodities4Index}" var="com" varStatus="i">


                <c:if test="${i.count <= 4}">
                    <a href="${path}/commodityDetail?commodityId=${com.commodity_id}">
                        <dl>
                            <dt>
                                <span class="abl"></span>
                                <img class="jia-index-commodity-img"  src="${com.commodityImgs[0].img_url}"/>
                                <span class="abr"></span>
                            </dt>
                            <dd class="jia-index-commodity-name">${com.commodity_name}</dd>
                            <dd><span>￥${com.commodity_min_present_price}</span></dd>
                        </dl>
                    </a>
                </c:if>

            </c:forEach>
        </div>

        <div class="pList clearfix tran">
            <c:forEach items="${commodities4Index}" var="com" varStatus="i">


                <c:if test="${i.count > 4 and i.count <=8}">
                    <a href="${path}/commodityDetail?commodityId=${com.commodity_id}">
                        <dl>
                            <dt>
                                <span class="abl"></span>
                                <img class="jia-index-commodity-img"  src="${com.commodityImgs[0].img_url}"/>
                                <span class="abr"></span>
                            </dt>
                            <dd class="jia-index-commodity-name">${com.commodity_name}</dd>
                            <dd><span>￥${com.commodity_min_present_price}</span></dd>
                        </dl>
                    </a>
                </c:if>

            </c:forEach>
        </div>
        <div class="pList clearfix tran">
            <c:forEach items="${commodities4Index}" var="com" varStatus="i">


                <c:if test="${i.count > 8 && i.count <= 12}">
                    <a href="${path}/commodityDetail?commodityId=${com.commodity_id}">
                        <dl>
                            <dt>
                                <span class="abl"></span>
                                <img class="jia-index-commodity-img"  src="${com.commodityImgs[0].img_url}"/>
                                <span class="abr"></span>
                            </dt>
                            <dd class="jia-index-commodity-name">${com.commodity_name}</dd>
                            <dd><span>￥${com.commodity_min_present_price}</span></dd>
                        </dl>
                    </a>
                </c:if>

            </c:forEach>
        </div>
    </div>
</div>
<!--返回顶部-->
<jsp:include page="include/right-sidebar.jsp"></jsp:include>
<jsp:include page="include/bottom.jsp"></jsp:include>




</body>
</html>
