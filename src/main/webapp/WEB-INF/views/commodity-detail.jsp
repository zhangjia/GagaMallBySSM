<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
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
    <meta charset="UTF-8">
    <title>详情页</title>
    <link rel="stylesheet" type="text/css" href="${path}/static/layui/css/layui.css"/>

    <jsp:include page="include/public-static-file.jsp"/>
    <script src="${path}/static/layui/layui.js " type="text/javascript" charset="utf-8"></script>
    <script src="https://cdn.bootcss.com/layer/2.3/layer.js"></script>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/proList.css"/>
    <link rel="stylesheet" type="text/css" href="${path}/static/css/pro-detail.css"/>
    <script src="${path}/static/js/jquery.SuperSlide.2.1.1.js" type="text/javascript" charset="utf-8"></script>
    <script src="${path}/static/js/pro.js" type="text/javascript" charset="utf-8"></script>
    <script src="${path}/static/js/cart.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">

        layer.photos({
            photos: '#jia-review-photos'
            ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
        });


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

            var commoditySpecsId;


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
                            specs: JSON.stringify(j),
                            commodityId:${param.commodityId}
                        },
                        success: function (res) {

                            console.log(res);
                            commoditySpecsId = res.commodity_specs_id;
                            $(".commodity-price").text('￥' + res.commodity_specs_present_price);
                            $(".inventory").text(res.commodity_specs_inventor);
                            $(".sale").text(res.commodity_specs_sales);

                            $(".jia-commodity-detail-specs-id").val(res.commodity_specs_id);
                        }
                    });
                } else {

                    if (${requestScope.commodityDetail.commodity_max_present_price != requestScope.commodityDetail.commodity_min_present_price}) {
                        $(".commodity-price").text('￥' + '${requestScope.commodityDetail.commodity_min_present_price}' + "~￥" + '${requestScope.commodityDetail.commodity_max_present_price}');
                    } else {
                        $(".commodity-price").text('￥' + '${requestScope.commodityDetail.commodity_min_present_price}');
                    }
                    $(".inventory").text(${requestScope.commodityDetail.commodity_inventory});
                    $(".sale").text(${requestScope.commodityDetail.commodity_sales});
                }
            });

            //------------------------------------------加入购物车-----------------------------------------------------
            $(".add-cart").click(function () {


                var commoditySpecsValue = JSON.stringify(selectjson);
                var commodityCount = $(".jia-commodity-detail-num").val();
                var length = $(".sku").length;
                if ($(".sku dd.active").length != length) {
                    layer.alert("请先选择商品规格")
                } else {
                    $.ajax({
                        url: "${path}/addCart",
                        type: "get",
                        data: {
                            commoditySpecsValue: commoditySpecsValue,
                            commodityCount: commodityCount,
                            commodityId:${param.commodityId}
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
                        commoditySpecsId: commoditySpecsId,
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
                    $(this).val(1)
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

            $(".jia-commodity-buy-now").click(function () {
                var length = $(".sku").length;
                if ($(".sku dd.active").length != length) {
                    layer.alert("请先选择商品规格")

                } else {

                    if('${sessionScope.user}' == ''){
                        console.log("a")

                        location = "${path}/login?uri=/commodityDetail?commodityId=${param.commodityId}"
                    } else {
                        var commodityCount = $(".jia-commodity-detail-num").val();
                        var commoditySpecsId = $(".jia-commodity-detail-specs-id").val();

                        console.log(commodityCount)
                        console.log(commoditySpecsId)
                        console.log('${path}')
                        location.replace("${path}" + "/settlement?commoditySpecsId=" + commoditySpecsId + "&commodityCount=" + commodityCount + "&type=buyNow");

                    }

                }
                <%--$.ajax({--%>
                <%--    url:"${path}/settlement",--%>
                <%--    type:"get",--%>
                <%--    data:{--%>
                <%--        commoditySpecsId:$(".jia-commodity-detail-specs-id").val(),--%>
                <%--        commodityCount:$(".jia-commodity-detail-num").val(),--%>
                <%--        type:'buyNow'--%>
                <%--    }--%>
                <%--});--%>
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
                    <input type="hidden" value="" class="jia-commodity-detail-specs-id">
                    <a class="jia-commodity-buy-now" href="javascript:;"><p class="buy fl">立即购买</p></a>
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
                <c:forEach items="${requestScope.reviews}" var="review" >
                <div class="per clearfix">
                    <img class="fl jia-review-avatar" src="${review.uses.user_avatar_url}">
                    <div class="perR fl">
                        <span>

                            <c:if test="${review.review_is_anonymity == 0}">
                                ${review.uses.user_name}
                            </c:if>

                              <c:if test="${review.review_is_anonymity != 0}">
                                  匿名用户
                              </c:if>

                        </span>
                         <span style="margin-left: 20px;color: red;">
                             <c:if test="${review.review_grade == 3}" >
                                [ 好评 ]
                             </c:if>
                             <c:if test="${review.review_grade == 2}" >
                                 [ 中评 ]
                             </c:if>
                             <c:if test="${review.review_grade == 1}" >
                                 [ 差评 ]
                             </c:if>
                         </span>
                        <p>${review.review_content}</p>


                        <div  class="layer-photos-demo">
                            <c:forEach items="${review.reviewImgs}" var="img" >
                                <img class="jia-review-img" layer-src="${img.img_url}" src="${img.img_url}" alt="图片名">
                            </c:forEach>
                        </div>

                        <script>

                            layer.photos({
                                photos: '.layer-photos-demo'
                                ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
                            });
                        </script>

                        <p><span><fmt:formatDate value="${review.review_time}" type="both" /></span><span>${review.commoditySpecs.commodity_specs_value}</span></p>
                    </div>
                </div>
                </c:forEach>

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
<jsp:include page="include/bottom.jsp"></jsp:include>


</body>
</html>

