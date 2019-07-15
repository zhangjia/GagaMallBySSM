<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>详情页</title>
    <link rel="stylesheet" type="text/css" href="${path}/static/layui/css/layui.css"/>
    <script src="${path}/static/layui/layui.js " type="text/javascript" charset="utf-8"></script>
    <jsp:include page="include/public-static-file.jsp"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/proList.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/pro-detail.css"/>
    <script src="${path}/static/js/jquery.SuperSlide.2.1.1.js" type="text/javascript" charset="utf-8"></script>
    <script src="${path}/static/js/pro.js" type="text/javascript" charset="utf-8"></script>
    <script src="${path}/static/js/cart.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        jQuery(".bottom").slide({
            titCell: ".hd ul",
            mainCell: ".bd .likeList",
            autoPage: true,
            autoPlay: false,
            effect: "leftLoop",
            autoPlay: true,
            vis: 1
        });
        //	---------------------------------------------------------------------------------------
        $(function () {

            $.ajax({});
            var selectjson;

            var skuId;


            $(document).on('click', '.btn-allocate.btn-normal', function () {//对于静态和动态创建的标签都好使
                if ($(this).hasClass("active")) {
                    $(this).removeClass("active")
                } else {

                    // $(("#order"+i)+" .btn-allocate.btn-normal").removeClass("active");
                    $(this).parent().children(" .btn-allocate.btn-normal").removeClass("active");

                    // console.log($(this).parent().children())
                    $(this).addClass("active")
                }

                var length = $(".sku").length;
                if ($(".sku dd.active").length == length) {
                    var le = $(".sku").length;
                    var s = "";
                    var j = {};
                    var json = [];
                    //生成sku-name的json字符串
                    $(".sku").each(function () {

                        var name = $(this).children(".sku-key").text();
                        name = name.replace(/ /g, "");
                        name = name.replace(/\n/g, "");
                        console.log(name);
                        j[name] = $(this).children("dd.active").text();
                        json.push(j);
                    });


                    selectjson = j;
                    $.ajax({
                        url: "${path}/queryCommoditySpecs",
                        type: "post",
                        data: {
                            specs: JSON.stringify(j)
                        },
                        success: function (res) {

                           console.log(res);
                            skuId = res.commodity_specs_id;
                           $(".commodity-price").text('￥' + res.commodity_specs_present_price);
                           $(".inventory").text(res.commodity_specs_inventor);
                           $(".sale").text(res.commodity_specs_sales);
                        }
                    });
                } else {

                    if (${requestScope.commodityDetail.commodityMaxPresentPrice != requestScope.commodityDetail.commodityMinPresentPrice}) {
                        $(".commodity-price").text('￥' + '${requestScope.commodityDetail.commodityMinPresentPrice}' + "~￥" + '${requestScope.commodityDetail.commodityMaxPresentPrice}');
                    } else {
                        $(".commodity-price").text('￥' + '${requestScope.commodityDetail.commodityMinPresentPrice}');
                    }
                    $(".inventory").text(${requestScope.commodityDetail.commodityInventory});
                    $(".sale").text(${requestScope.commodityDetail.commoditySales});
                }
            });

            //------------------------------------------加入购物车-----------------------------------------------------
            $(".add-cart").click(function () {


                var sku = JSON.stringify(selectjson);
                var commodityCount = $(".jia-commodity-detail-num").val();
                var length = $(".sku").length;
                if ($(".sku dd.active").length != length) {
                    layer.alert("请先选择商品规格")
                } else {
                    $.ajax({
                        url: "${path}/addCart",
                        type: "get",
                        data: {
                            SKU: sku,
                            commodityCount: commodityCount
                        },
                        success: function (res) {
                            console.log(res);
                            if (res.isLogin === false) {

                                location = "${path}/login?uri=${path}/commodityDetail?commodityId=${param.commodityId}";
                            } else {

                                if (res.success === true) {
                                    layer.alert("添加成功")
                                } else {
                                    alert(res)
                                    layer.alert(res.error)
                                }
                            }


                        }
                    });
                }
            });

            //------------------------------------------加入购物车结束-------------------


            //------------------------------------------更改个数开始-------------------
            function determineTheInventory(action, count, thiss, val) {
                console.log(val);
                var result = false;
                $.ajax({
                    url: "${path}/updateCount2CommodityDetail",
                    type: "get",
                    data: {
                        SKUId: skuId,
                        action: action,
                        count: count,
                        val: val
                    },
                    success: function (res) {
                        result = true;

                        console.log(res + "res");
                        console.log(res.nowInventory + "resss");
                        if (res.isLogin === false) {
                            location = "${path}/login?uri=${path}/commodityDetail?commodityId=${param.commodityId}";
                        } else {
                            if (!res.error) {
                                if (action == "add") {
                                    var nowCartCount = $(thiss).siblings("input").val();
                                    $(thiss).siblings("input").val(++nowCartCount);
                                }
                            } else {
                                if (action == "input") {
                                    $(thiss).val(res.nowInventory);
                                }
                                layer.msg(res.error)


                            }
                        }


                    }
                });

                return result;

            }

            $(".cart-add").click(function () {
                var length = $(".sku").length;
                if ($(".sku dd.active").length != length) {
                    layer.alert("请先选择商品规格")
                } else {
                    var val = $(this).siblings(".jia-commodity-detail-num").val();
                    determineTheInventory("add", 1, $(this), val)
                }


            });


            $(".jia-commodity-detail-num").keyup(function () {
                var length = $(".sku").length;
                if ($(".sku dd.active").length != length) {
                    layer.alert("请先选择商品规格")
                } else {
                    var val = $(this).val();
                    console.log("val" + val)
                    if (val === '') {
                        $(this).val(1)
                    } else {
                        console.log(val + "wer")
                        determineTheInventory("input", 0, $(this), val)
                    }

                }


            });


            $(".cart-sub").click(function () {
                var thiss = $(this);
                var nowCartCount = $(this).siblings("input").val();
                if (nowCartCount - 1 == 0) {
                    layer.msg("不能再少了！")
                } else {
                    $(thiss).siblings("input").val(--nowCartCount);
                }
            });


        });
    </script>

</head>
<body>
<!------------------------------head------------------------------>
<jsp:include page="include/head.jsp"/>
<!-----------------address------------------------------->
<div class="address">
    <div class="wrapper clearfix">
        <a href="index.html">首页</a>
        <span>/</span>
        <%-- <a href="${path}/list?firstMenuId=${requestScope.commodityDetail.firstMenuId}&page=1">${requestScope.commodityDetail.firstMenuChineseName}</a>
         <span>/</span>
         <a href="${path}/list?secMenuId=${requestScope.commodityDetail.secMenuId}&page=1">${requestScope.commodityDetail.secMenuChineseName}</a>
         <span>/</span>--%>
        <a href="#" class="on">${requestScope.commodityDetail.commodityName}</a>
    </div>
</div>
<!-----------------------Detail------------------------------>
<div class="detCon">
    <div class="proDet wrapper">
        <div class="proCon clearfix">
            <div class="proImg fl">
                <c:forEach items="${requestScope.commodityDetail.commodityImgs}" var="commodityImg" varStatus="i">

                    <c:if test="${commodityImg.img_order == 1}">
                        <img class="det" src="${commodityImg.img_url}"/>
                    </c:if>
                </c:forEach>


                <div class="smallImg clearfix">
                    <%--					<img src="${path}/static/img/temp/proDet01.jpg" data-src="${path}/static/img/temp/proDet01_big.jpg">--%>
                    <%--					<img src="${path}/static/img/temp/proDet02.jpg" data-src="${path}/static/img/temp/proDet02_big.jpg">--%>
                    <%--					<img src="${path}/static/img/temp/proDet03.jpg" data-src="${path}/static/img/temp/proDet03_big.jpg">--%>
                    <%--					<img src="${path}/static/img/temp/proDet04.jpg" data-src="${path}/static/img/temp/proDet04_big.jpg">--%>
                    <c:forEach items="${requestScope.commodityDetail.commodityImgs}" var="commodityImg" varStatus="i">

                        <c:if test="${commodityImg.img_order == i.count}">
                            <img src="${commodityImg.img_url}" data-src="${commodityImg.img_url}">
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="fr intro">
                <div class="title">
                    <h4>${requestScope.commodityDetail.commodity_name}</h4>
                    <%--					<p>【破损补寄】【适合放室内、卧室、书房、餐桌、办公室、客厅、电视柜等地方】【无理由退换货】【包邮】【白色现代简约花瓶】</p>--%>
                    <p></p>
                    <c:if test="${requestScope.commodityDetail.commodity_max_present_price == requestScope.commodityDetail.commodity_min_present_price}">
                        <span class="commodity-price">￥${requestScope.commodityDetail.commodity_max_present_price}</span>
                    </c:if>
                    <c:if test="${requestScope.commodityDetail.commodity_max_present_price != requestScope.commodityDetail.commodity_min_present_price}">
                        <span class="commodity-price">￥${requestScope.commodityDetail.commodity_min_present_price}~￥${requestScope.commodityDetail.commodity_max_present_price}</span>
                    </c:if>
                </div>
                <div class="proIntro">
                    <%--<div class="smallImg clearfix categ">
                            <p class="fl"><img src="${path}/static/img/temp/prosmall01.jpg" alt="白瓷花瓶+20支快乐花" data-src="${path}/static/img/temp/proBig01.jpg"></p>
                            <p class="fl"><img src="${path}/static/img/temp/prosmall02.jpg" alt="白瓷花瓶+20支兔尾巴草" data-src="${path}/static/img/temp/proBig02.jpg"></p>
                            <p class="fl"><img src="${path}/static/img/temp/prosmall03.jpg" alt="20支快乐花" data-src="${path}/static/img/temp/proBig03.jpg"></p>
                            <p class="fl"><img src="${path}/static/img/temp/prosmall04.jpg" alt="20支兔尾巴草" data-src="${path}/static/img/temp/proBig04.jpg"></p>
                        </div>--%>
                    <div class="skus">

                        <%--

                        {
                         "颜色":[  ：${specs.key}
                             "黑色",
                             "蓝色"
                         ],
                         "版本":[
                             "6GB + 64GB",
                             "8GB + 128GB"
                         ]
                        }
                        --%>
                        <c:forEach items="${requestScope.specs}" var="specs">
                            <dl class="sku" id="order">
                                <dt class="sku-key">
                                        ${specs.key}
                                </dt>
                                <c:forEach items="${specs.value}" var="value">
                                    <dd class="btn-allocate btn-normal">${value}</dd>
                                </c:forEach>

                            </dl>
                        </c:forEach>

                    </div>

                    <div class="commodityCount">
                        <p class="inventorys">库存 <span
                                class="inventory">${requestScope.commodityDetail.commodity_inventory}</span> 件
                        </p>

                        <p class="sales">销量 <span class="sale">${requestScope.commodityDetail.commodity_sales}</span>件
                        </p>
                    </div>

                    <div class="num clearfix">
                        <%--                        <img class="fl sub" src="${path}/static/img/temp/sub.jpg">--%>
                        <%--                        <span class="fl cart-commodity-count" contentEditable="true">1</span>--%>
                        <%--                        <img class="fl add" src="${path}/static/img/temp/add.jpg">--%>
                        <%--                        <p class="please fl">请选择商品属性!</p>--%>
                        <button type="button" class="layui-btn layui-btn-primary layui-btn layui-btn-xs cart-sub">
                            <i class="layui-icon">&#xe603;</i>
                        </button>

                        <input type="number" value="1" autocomplete="off"
                               class="jia-commodity-detail-num" style="text-align:center ">
                        <button type="button" class="layui-btn layui-btn-primary layui-btn layui-btn-xs cart-add">
                            <i class="layui-icon">&#xe602;</i>
                        </button>
                    </div>

                </div>
                <div class="btns clearfix">
                    <a href="#2"><p class="buy fl">立即购买</p></a>
                    <%--                    <a href="javascript:;"><p class="cart fr add-cart">加入购物车</p></a>--%>
                    <a href="javascript:;"><span class="add-cart">加入购物车</span></a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="introMsg wrapper clearfix">
    <div class="msgL fl">
        <div class="msgTit clearfix">
            <a class="on">商品详情</a>
            <a>所有评价</a>
        </div>
        <div class="msgAll">
            <div class="msgImgs">
                <c:forEach items="${requestScope.commodityDetail.commodityDetailImgs}" var="commodityDetailImg"
                           varStatus="i">

                    <c:if test="${commodityDetailImg.img_order == i.count}">
                        <img class="zj" src="${commodityDetailImg.img_url}"/>

                    </c:if>
                </c:forEach>

            </div>
            <div class="eva">
                <div class="per clearfix">
                    <img class="fl" src="${path}/static/img/temp/per01.jpg">
                    <div class="perR fl">
                        <p>馨***呀</p>
                        <p>不好意思评价晚了，产品很好，价格比玻璃品便宜，没有我担心的杂色，发货快，包装好，全5分</p>
                        <div class="clearfix">
                            <p><img src="${path}/static/img/temp/eva01.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva02.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva03.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva04.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva05.jpg"></p>
                        </div>
                        <p><span>2016年12月27日08:31</span><span>颜色分类：大中小三件套（不含花）</span></p>
                    </div>
                </div>
                <div class="per clearfix">
                    <img class="fl" src="${path}/static/img/temp/per02.jpg">
                    <div class="perR fl">
                        <p>么***周</p>
                        <p>花瓶超级棒，我看图以为是光面的，收货发现是磨砂，但感觉也超有质感，很喜欢。磨砂上面还有点纹路，不过觉得挺自然的，不影响美观。包装也很好，绝对不会磕碎碰坏，好评！</p>
                        <p><span>2016年12月27日08:31</span><span>颜色分类：大中小三件套（不含花）</span></p>
                    </div>
                </div>
                <div class="per clearfix">
                    <img class="fl" src="${path}/static/img/temp/per01.jpg">
                    <div class="perR fl">
                        <p>馨***呀</p>
                        <p>不好意思评价晚了，产品很好，价格比玻璃品便宜，没有我担心的杂色，发货快，包装好，全5分</p>
                        <div class="clearfix">
                            <p><img src="${path}/static/img/temp/eva01.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva02.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva03.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva04.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva05.jpg"></p>
                        </div>
                        <p><span>2016年12月27日08:31</span><span>颜色分类：大中小三件套（不含花）</span></p>
                    </div>
                </div>
                <div class="per clearfix">
                    <img class="fl" src="${path}/static/img/temp/per02.jpg">
                    <div class="perR fl">

                        <p>么***周</p>
                        <p>花瓶超级棒，我看图以为是光面的，收货发现是磨砂，但感觉也超有质感，很喜欢。磨砂上面还有点纹路，不过觉得挺自然的，不影响美观。包装也很好，绝对不会磕碎碰坏，好评！</p>
                        <p><span>2016年12月27日08:31</span><span>颜色分类：大中小三件套（不含花）</span></p>
                    </div>
                </div>
                <div class="per clearfix">
                    <img class="fl" src="${path}/static/img/temp/per01.jpg">
                    <div class="perR fl">
                        <p>馨***呀</p>
                        <p>不好意思评价晚了，产品很好，价格比玻璃品便宜，没有我担心的杂色，发货快，包装好，全5分</p>
                        <div class="clearfix">
                            <p><img src="${path}/static/img/temp/eva01.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva02.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva03.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva04.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva05.jpg"></p>
                        </div>
                        <p><span>2016年12月27日08:31</span><span>颜色分类：大中小三件套（不含花）</span></p>
                    </div>
                </div>
                <div class="per clearfix">
                    <img class="fl" src="${path}/static/img/temp/per02.jpg">
                    <div class="perR fl">
                        <p>么***周</p>
                        <p>花瓶超级棒，我看图以为是光面的，收货发现是磨砂，但感觉也超有质感，很喜欢。磨砂上面还有点纹路，不过觉得挺自然的，不影响美观。包装也很好，绝对不会磕碎碰坏，好评！</p>
                        <p><span>2016年12月27日08:31</span><span>颜色分类：大中小三件套（不含花）</span></p>
                    </div>
                </div>
                <div class="per clearfix">
                    <img class="fl" src="${path}/static/img/temp/per01.jpg">
                    <div class="perR fl">
                        <p>馨***呀</p>
                        <p>不好意思评价晚了，产品很好，价格比玻璃品便宜，没有我担心的杂色，发货快，包装好，全5分</p>
                        <div class="clearfix">
                            <p><img src="${path}/static/img/temp/eva01.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva02.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva03.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva04.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva05.jpg"></p>
                        </div>
                        <p><span>2016年12月27日08:31</span><span>颜色分类：大中小三件套（不含花）</span></p>
                    </div>
                </div>
                <div class="per clearfix">
                    <img class="fl" src="${path}/static/img/temp/per02.jpg">
                    <div class="perR fl">
                        <p>么***周</p>
                        <p>花瓶超级棒，我看图以为是光面的，收货发现是磨砂，但感觉也超有质感，很喜欢。磨砂上面还有点纹路，不过觉得挺自然的，不影响美观。包装也很好，绝对不会磕碎碰坏，好评！</p>
                        <p><span>2016年12月27日08:31</span><span>颜色分类：大中小三件套（不含花）</span></p>
                    </div>
                </div>
                <div class="per clearfix">
                    <img class="fl" src="${path}/static/img/temp/per01.jpg">
                    <div class="perR fl">
                        <p>馨***呀</p>
                        <p>不好意思评价晚了，产品很好，价格比玻璃品便宜，没有我担心的杂色，发货快，包装好，全5分</p>
                        <div class="clearfix">
                            <p><img src="${path}/static/img/temp/eva01.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva02.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva03.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva04.jpg"></p>
                            <p><img src="${path}/static/img/temp/eva05.jpg"></p>
                        </div>
                        <p><span>2016年12月27日08:31</span><span>颜色分类：大中小三件套（不含花）</span></p>
                    </div>
                </div>
                <div class="per clearfix">
                    <img class="fl" src="${path}/static/img/temp/per02.jpg">
                    <div class="perR fl">
                        <p>么***周</p>
                        <p>花瓶超级棒，我看图以为是光面的，收货发现是磨砂，但感觉也超有质感，很喜欢。磨砂上面还有点纹路，不过觉得挺自然的，不影响美观。包装也很好，绝对不会磕碎碰坏，好评！</p>
                        <p><span>2016年12月27日08:31</span><span>颜色分类：大中小三件套（不含花）</span></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="msgR fr">
        <h4>为你推荐</h4>
        <div class="seeList">
            <a href="#">
                <dl>
                    <dt><img src="${path}/static/img/temp/see01.jpg"></dt>
                    <dd>【最家】复古文艺风玻璃花瓶</dd>
                    <dd>￥193.20</dd>
                </dl>
            </a>
            <a href="#">
                <dl>
                    <dt><img src="${path}/static/img/temp/see02.jpg"></dt>
                    <dd>【最家】复古文艺风玻璃花瓶</dd>
                    <dd>￥193.20</dd>
                </dl>
            </a>
            <a href="#">
                <dl>
                    <dt><img src="${path}/static/img/temp/see03.jpg"></dt>
                    <dd>【最家】复古文艺风玻璃花瓶</dd>
                    <dd>￥193.20</dd>
                </dl>
            </a>
            <a href="#">
                <dl>
                    <dt><img src="${path}/static/img/temp/see04.jpg"></dt>
                    <dd>【最家】复古文艺风玻璃花瓶</dd>
                    <dd>￥193.20</dd>
                </dl>
            </a>
        </div>

    </div>
</div>
<div class="like">
    <h4>猜你喜欢</h4>
    <div class="bottom">
        <div class="hd">
            <span class="prev"><img src="${path}/static/img/temp/prev.png"></span>
            <span class="next"><img src="${path}/static/img/temp/next.png"></span>
        </div>
        <div class="${path}/static/imgCon bd">
            <div class="likeList clearfix">
                <div>
                    <a href="proDetail.html">
                        <dl>
                            <dt><img src="${path}/static/img/temp/like01.jpg"></dt>
                            <dd>【最家】复古文艺风玻璃花瓶</dd>
                            <dd>￥193.20</dd>
                        </dl>
                    </a>
                    <a href="proDetail.html">
                        <dl>
                            <dt><img src="${path}/static/img/temp/like02.jpg"></dt>
                            <dd>【最家】复古文艺风玻璃花瓶</dd>
                            <dd>￥193.20</dd>
                        </dl>
                    </a>
                    <a href="proDetail.html">
                        <dl>
                            <dt><img src="${path}/static/img/temp/like03.jpg"></dt>
                            <dd>【最家】复古文艺风玻璃花瓶</dd>
                            <dd>￥193.20</dd>
                        </dl>
                    </a>
                    <a href="proDetail.html">
                        <dl>
                            <dt><img src="${path}/static/img/temp/like04.jpg"></dt>
                            <dd>【最家】复古文艺风玻璃花瓶</dd>
                            <dd>￥193.20</dd>
                        </dl>
                    </a>
                    <a href="proDetail.html" class="last">
                        <dl>
                            <dt><img src="${path}/static/img/temp/like05.jpg"></dt>
                            <dd>【最家】复古文艺风玻璃花瓶</dd>
                            <dd>￥193.20</dd>
                        </dl>
                    </a>
                </div>
                <div>
                    <a href="proDetail.html">
                        <dl>
                            <dt><img src="${path}/static/img/temp/like01.jpg"></dt>
                            <dd>【最家】复古文艺风玻璃花瓶</dd>
                            <dd>￥193.20</dd>
                        </dl>
                    </a>
                    <a href="proDetail.html">
                        <dl>
                            <dt><img src="${path}/static/img/temp/like02.jpg"></dt>
                            <dd>【最家】复古文艺风玻璃花瓶</dd>
                            <dd>￥193.20</dd>
                        </dl>
                    </a>
                    <a href="proDetail.html">
                        <dl>
                            <dt><img src="${path}/static/img/temp/like03.jpg"></dt>
                            <dd>【最家】复古文艺风玻璃花瓶</dd>
                            <dd>￥193.20</dd>
                        </dl>
                    </a>
                    <a href="proDetail.html">
                        <dl>
                            <dt><img src="${path}/static/img/temp/like04.jpg"></dt>
                            <dd>【最家】复古文艺风玻璃花瓶</dd>
                            <dd>￥193.20</dd>
                        </dl>
                    </a>
                    <a href="proDetail.html" class="last">
                        <dl>
                            <dt><img src="${path}/static/img/temp/like05.jpg"></dt>
                            <dd>【最家】复古文艺风玻璃花瓶</dd>
                            <dd>￥193.20</dd>
                        </dl>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<!--返回顶部-->
<jsp:include page="include/right-sidebar.jsp"></jsp:include>
<div class="msk"></div>
<!--footer-->
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
        违法和不良信息举报电话：188-0130-1238，本网站所列数据，除特殊说明，所有数据均出自我司实验室测试</p>
</div>

</body>
</html>

