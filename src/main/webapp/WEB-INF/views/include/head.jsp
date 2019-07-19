<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!------------------------------head.jsp------------------------------>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<div class="head">
    <div class="wrapper clearfix">
        <div class="clearfix" id="top">
            <h1 class="fl"><a href="index.jsp"><img src="https://zhangjia.oss-cn-qingdao.aliyuncs.com/img/web/logo.png"/></a>
            </h1>


            <div class="fr clearfix" id="top1">
               <c:if test="${sessionScope.user == null}">
                   <p class="fl">
                       <a href="${path}/login" id="login">登录</a>
                       <a href="${path}/register" id="reg">注册</a>
               </c:if>

                <c:if test="${sessionScope.user != null}">
                    <p class="fl">
                      欢迎您：  <a href="${path}/personal" id="WelcomeUser">${sessionScope.user.user_nick}</a>
                                            </p>
                </c:if>
                <form action="${path}/commodities" method="get" class="fl">

                    <input type="text" name="commodityName" value="${param.commodityName}" placeholder="搜索"/>
                    <input class="ssimg" type="submit" value="" />
                </form>
                <div class="btn fl clearfix">
                    <a href="${path}/personal"><img src="${path}/static/img/grzx.png"/></a>
                    <a href="#" class="er1"><img src="${path}/static/img/ewm.png"/></a>
                    <a href="${path}/cart"><img src="${path}/static/img/gwc.png"/></a>
<%--             二维码       <p><a href="#"><img src="${path}/static/img/smewm.png"/></a></p>--%>
                </div>
            </div>
        </div>
        <ul class="clearfix" id="bott">
<%--            <li><a href="index.jsp">首页${sessionScope.nav2}</a></li>--%>
            <li><a href="${path}/index">首页</a></li>
            <c:forEach items="${requestScope.nav}" var="nav">
<%--            <c:forEach items="${sessionScope.firstNav}" var="firstNav">--%>

                <li>
                    <a href="commodities?level1MenuId=${nav.level1_menu_id}">${nav.level1_menu_chinese_name}</a>

                    <div class="sList">
                        <div class="wrapper  clearfix">
                            <p hidden > ${nav.level2_menu}</p>
                            <c:forEach items="${nav.level2_menu}" var="secNav">

                                <a href="commodities?level2MenuId=${secNav.level2_menu_id}">
                                    <dl>
                                        <p hidden > ${secNav}</p>
                                        <dd>${secNav.level2_menu_chinese_name}</dd>
                                    </dl>
                                </a>
                            </c:forEach>
                        </div>

                    </div>
                </li>
            </c:forEach>

            <li>
                <a href="${path}/commodities">全部商品</a>

            </li>
        </ul>
    </div>
</div>
