<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<style>
    /*遮罩*/
    .mask-order-review {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.5);
        z-index: 20;
        display: none;
    }
    .layui-form-radio>i:hover, .layui-form-radioed>i {
        color: #ffca42;
    }

     .layui-upload {
         margin-left: 110px;
     }
    .layui-quote-nm {
        border-style: solid;
        border-width: 1px 1px 1px 1px;
        background: 0 0;
    }

    .layui-form-select dl dd.layui-this {
        background-color: #1E9FFF;;
        color: #fff;
    }

    .layui-upload-list img{
        width: 70px;
        height:70px;
        object-fit: cover;
    }
    .layui-form-onswitch {
        border-color: #ffb800;
        background-color: #ffb800;
    }


</style>

<script>

    $(function () {
        $(".jia-btn-cancle").click(function () {
            $(".bj-order-review").hide();
            $(".mask-order-review").hide();

            $(".remima").hide();
            $(".avatar").hide();
            $(".chak").hide();

        });

        $
    })
</script>
<div class="mask-order-review"></div>
<!--编辑弹框-->
<div class="bj-order-review">

    <form class="layui-form jia-form-review" action="">
        <div class="layui-tab-item layui-show" <%--style="float: left"--%>>


            <div style="height: 160px">
                    <div float: left;height:160>
                        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
                            <legend>评价等级</legend>
                        </fieldset>
                        <div class="layui-input-block">
                            <input type="hidden" name="orderId" value="">
                            <input type="hidden" name="commoditySpecsId" value="">
                            <input type="hidden" name="orderDetailId" value="">
                            <input type="radio" name="reviewGrade" value="3" title="好评" checked>
                            <input type="radio" name="reviewGrade" value="2" title="中评">
                            <input type="radio" name="reviewGrade" value="1" title="差评">
                        </div>
                        <div style="padding-left: 110px;font-size: 16px">
                            宝贝描述：
                            <input type="hidden" name="description"/>
                            <input type="hidden" name="service"/>
                            <input type="hidden" name="express"/>
                            <div id="jia-review_description" style="display: inline-block;margin: 10px;"></div>
                            商家服务：
                            <div id="jia-review_service" style="display: inline-block;margin: 10px;"></div>
                            物流服务
                            <div id="jia-review-express" style="display: inline-block;margin: 10px;"></div>
                        </div>
                    </div>


            </div>

            <fieldset class="layui-elem-field layui-field-title" >
                <legend>晒图</legend>
            </fieldset>
            <div class="layui-upload">
                <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top: 10px;">
                    预览图：
                    <div class="layui-upload-list" id="demo2"></div>
                </blockquote>
                <button type="button" class="layui-btn layui-btn-warm" id="jia-review-img-upload">上传图片</button>
            </div>
            <fieldset class="layui-elem-field layui-field-title" >
                <legend>评价内容</legend>
            </fieldset>
            <div class="layui-form-item layui-form-text" style="margin-top:5px">
                <label class="layui-form-label"></label>
                <div class="layui-input-block">
                    <textarea name="content" placeholder="请输入内容" class="layui-textarea"></textarea>
                </div>
            </div>

        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">匿名评价</label>
            <div class="layui-input-block">
                <input type="checkbox" name="anonymity" lay-skin="switch" lay-text="ON|OFF">
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn layui-btn-warm jia-user-order-review" lay-submit lay-filter="jia-filter-user-order-review">评价
                </button>
                <button type="button" class="layui-btn layui-btn-primary jia-btn-cancle">取消</button>

            </div>
        </div>


    </form>

    <script>

        layui.use(['rate'], function () {
            var rate = layui.rate;
            //基础效果
            rate.render({
                elem: '#jia-review_description',
                choose: function(value){
                   $("input[name='description']").val(value);
                }
            })
            rate.render({
                elem: '#jia-review_service'  ,
                choose: function(value){
                    $("input[name='service']").val(value);
                }
            })
            rate.render({
                elem: '#jia-review-express' ,
                choose: function(value){
                    $("input[name='express']").val(value);
                }
            })
        });



        layui.use('upload', function () {
            var $ = layui.jquery
                , upload = layui.upload;


            //商品图上传
            upload.render({
                elem: '#jia-review-img-upload'
                , url: '${path}/upload'
                , multiple: true
                , before: function (obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function (index, file, result) {
                        $('#demo2').append('<img src="' + result + '" alt="' + file.name + '" class="layui-upload-img">')
                    });
                }
                , done: function (res) {
                    //上传完毕
                    if (res.success) {
                        addReviewImgUrl4Input(res.url)
                        return  layer.msg('上传成功', {time: 1000, anim: 2, icon: 6});
                    }
                }
            });



            function addReviewImgUrl4Input(url) {
                var htm = "";

                htm += "<input type='hidden' name='reviewImgs' value='" + url + "' required lay-verify='required'  autocomplete='off' class='layui-input'>"


                $("#jia-review-img-upload").before(htm);
            }


        });

        $(function () {
            $(".jia-id-myorder-review").click(function () {
                var orderId = $(this).parent().parent().siblings(".word.clearfix").children("ul").children(".order-num").children("span").text();
                $("input[name='orderId']").val(orderId);

                var commoditySpecsId =  $(this).parent().siblings(".order-commodity-specs-id").children("span").text();
                $("input[name='commoditySpecsId']").val(commoditySpecsId);

                var orderDetailId =  $(this).parent().siblings(".order-detail-id").children("span").text();

                $("input[name='orderDetailId']").val(orderDetailId);
                $(".bj-order-review").show();
                $(".mask-order-review").show();
            });

        });
        //Demo
        layui.use('form', function () {
            var form = layui.form;

            //监听提交
            form.on('submit(jia-filter-user-order-review)', function (data) {
                console.log(data.field)



                var datas = $(".jia-form-review").serialize();
                $.ajax({
                    url: "${path}/doReview",
                    type: "post",
                    data:datas,
                    success: function (res) {
                        console.log(res);

                       if(res.success) {
                           layer.msg("评价成功")
                           location.reload();
                       } else {
                           layer.msg("评价失败")
                           return;
                       }

                    }
                });
                console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });
        });
    </script>
</div>