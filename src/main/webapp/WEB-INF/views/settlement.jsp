<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head lang="en">
    <style>
        .bj-order-pay{
            width: 410px;
            position: absolute;
            top: 30%;
            left: 50%;
            margin-top: -180px;
            margin-left: -220px;
            z-index: 30;
            background: #fff;
            border-radius: 10px;
            padding: 15px;
            display: none;
        }
    </style>
    <meta charset="utf-8"/>
    <title>order</title>

    <link rel="stylesheet" type="text/css" href="${path}/static/layui/css/layui.css"/>
    <script src="${path}/static/layui/layui.js " type="text/javascript" charset="utf-8"></script>
    <jsp:include page="include/public-static-file.jsp"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/public.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/proList.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/mygxin.css"/>
    <script src="https://cdn.bootcss.com/vue/2.4.2/vue.min.js"></script>
    <script type="text/javascript"
            src="https://webapi.amap.com/maps?v=1.4.8&key=b03bbaf1ff4c80e018561e5eb1210906"></script>
    <script src="${path}/static/js/city.js" type="text/javascript" charset="utf-8"></script>
    <script>
        function pay(){

                $(".bj-order-pay").show();
                $(".mask-order-pay").show();

        }

        $(function () {

            $(".testjia").click(function () {
                alert($("#note").val())
            });


            
            $(".pay").click(function () {
                if ($(".addre.fl.on").length === 0) {
                    layer.msg("先选择地址")
                } else {


                    var addreddId = $(".addre.fl.on").children("p:hidden").text();
                    <%--var url = "${path}/pay?"--%>
                    var url = "${path}/placeOrder?"
                    $(".msg ul").children("input:hidden").each(function () {
                        url += "commoditySpecsId=" + this.value + "&";

                    });
                    url += "addressId=" + addreddId;
                    url +=  "&note=" + $("#note").val();
                    console.log(url);
                    $.ajax({
                        url: url,
                        type: "get",

                        success: function (res) {
                            <%--location = "${path}/pay";--%>
                            if(res.success === true ){
                                pay();
                            // layer.msg("下单成功")
                                console.log("下单成功")
                            } else  {
                                layer.msg("下单失败")
                            }
                        }
                    });
                }


            });
        });
    </script>
</head>
<body>
<!----------------------------------------order------------------>
<jsp:include page="include/head.jsp"></jsp:include>
<jsp:include page="include/order-pay.jsp"></jsp:include>

<div class="order cart mt">
    <!-----------------site------------------->
    <div class="site">
        <p class="wrapper clearfix">
            <span class="fl">订单确认</span>
            <img class="top" src="${path}/static/img/temp/cartTop02.png">
        </p>
    </div>
    <!-----------------orderCon------------------->
    <div class="orderCon wrapper clearfix">
        <div class="orderL fl">
            <jsp:include page="include/address.jsp"></jsp:include>
            <h3 class="testjia">输入备注</h3>
<%--            <!--------way---------------->--%>
<%--            <div class="way clearfix">--%>
<%--                <img class="on jia-pay-style-banlance" src="${path}/static/img/temp/way01.jpg">--%>
<%--                <img src="${path}/static/img/temp/way02.jpg">--%>
<%--                <img src="${path}/static/img/temp/way03.jpg">--%>
<%--                <img src="${path}/static/img/temp/way04.jpg">--%>
<%--            </div>--%>
                    <div class="layui-form-item layui-form-text">
                        <div style="margin:10px" class="layui-input-block">
                            <textarea id="note" style=" min-height: 10px; margin-top: 20px" name="note" placeholder="请输入备注" class="layui-textarea"></textarea>
                        </div>
                    </div>

        </div>
        <div class="orderR fr">
            <div class="msg">
                <h3>订单内容<a href="cart.html" class="fr">返回购物车</a></h3>
                <!--------ul---------------->
                <c:forEach items="${requestScope.commoditySpecs}" var="commoditySpecs">

                    <ul class="clearfix">
                        <li class="fl">
                            <img src="${commoditySpecs.img_url}" style="width: 87px;height: auto;">
                        </li>
                        <input type="hidden" value="${commoditySpecs.commodity_specs_id}"/>
                        <li class="fl">
                            <p class="settlement-ellipsis">${commoditySpecs.commodity_name}</p>
                            <p>
                                <c:set var="string"
                                       value="${fn:replace(fn:replace(fn:replace(fn:replace(commoditySpecs.commodity_specs_value, '\"', ''), '{', ''), '}', ''), ',', '    ')}"></c:set>
                                    ${string}
                            </p>
                            <p>数量：${commoditySpecs.commodity_count}</p>
                        </li>
                        <li class="fr">￥${commoditySpecs.commodity_specs_present_price}</li>
                    </ul>
                </c:forEach>
            </div>
            <!--------tips---------------->
            <div class="tips">
                <p><span class="fl">商品金额：</span><span
                        class="fr">￥${requestScope.total.sum_commodity_present_price}</span></p>
                <p><span class="fl">优惠金额：</span><span class="fr">￥0.00</span></p>
                <c:if test="${requestScope.total.sum_commodity_present_price >= 1000}">
                    <p><span class="fl">运费：</span><span class="fr">免运费</span></p>
                </c:if>
                <c:if test="${requestScope.total.sum_commodity_present_price < 1000}">
                    <p><span class="fl">运费：</span><span class="fr">10元</span></p>
                </c:if>
            </div>
            <!--------tips count---------------->
            <div class="count tips">
                <p><span class="fl">商品总数：</span><span class="fr">${requestScope.total.sum_commodity_count}</span></p>
                <p><span class="fl">支付金额：</span><span class="fr">￥${requestScope.total.sum_commodity_pay_price}</span>
                </p>
            </div>
            <!--<input type="button" name="" value="去支付"> -->
            <a id="" href="javascript:;" class="pay">去支付</a>
        </div>
    </div>
</div>
<!--编辑弹框-->
<!--遮罩-->

<%--------------------------------------------------------------------------%>



<%----------------------------------------------------------------------%>
<!--返回顶部-->
<jsp:include page="include/right-sidebar.jsp"></jsp:include>
<!--footer-->
<jsp:include page="include/bottom.jsp"></jsp:include>

<script src="${path}/static/js/public.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/static/js/pro.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/static/js/user.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>
