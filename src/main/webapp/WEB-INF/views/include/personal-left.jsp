<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>
    function editAvatar(url) {
        $.ajax({
            url:"${path}/editAvatar",
            data:{
                url:url
            },
            type:"get",
            success:function (res) {
                layer.msg('修改成功', {time: 1500, anim: 2, icon: 6});

            }
        });
    }

    layui.use('upload', function(){
        var $ = layui.jquery
            ,upload = layui.upload;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#test1'
            ,url: '${path}/upload'
            ,before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('.jia-userAvatar').attr('src', result); //图片链接（base64）
                });
            }
            ,done: function(res){
                console.log(res)
                //如果上传失败
                if(res.success){
                    editAvatar(res.url);
                    return;
                } else {
                    return layer.msg('上传失败');
                }
                //上传成功
            }
            ,error: function(){
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                    uploadInst.upload();
                });
            }
        });



    });
</script>
<h3 class="left-h3">

    <c:if test="${sessionScope.user.user_avatar_url == null}">
        <a id="test1" href="javascript:;"><img class="jia-userAvatar" src="${path}/static/img/tx.jpg"/></a>
    </c:if>
    <c:if test="${sessionScope.user.user_avatar_url != null}">
        <a id="test1" href="javascript:;"><img class="jia-userAvatar" src="${sessionScope.user.user_avatar_url}"/></a>
    </c:if>
    <p class="clearfix"><span class="fl jia-person-username">[${sessionScope.user.user_nick}]</span><span class="fr"><a
            href="${path}/logout">[退出登录]</a></span></p>
</h3>
<div>
    <h4 style="font-weight: 900">我的交易</h4>
    <ul>
        <li><a href="${path}/cart">我的购物车</a></li>
        <li><a href="${path}/orders">我的订单</a></li>
        <li><a href="${path}/review">评价晒单</a></li>
    </ul>
    <h4 style="font-weight: 900">个人中心</h4>
    <ul>
        <li class="on"><a href="${path}/personal">我的中心</a></li>

        <li><a href="${path}/wallet">我的钱包</a></li>
        <li><a href="${path}/addressManage">地址管理</a></li>
    </ul>
    <h4 style="font-weight: 900">账户管理</h4>
    <ul>
        <li><a href="${path}/userInfo?userId=${sessionScope.user.user_id}">个人信息</a></li>
    </ul>
<%--    <c:if test="${sessionScope.user.userStatus == 2}">--%>
<%--        <h4>商品管理</h4>--%>
<%--        <ul>--%>
<%--            <li><a href="${path}/saveOrUpdateCommodity">添加商品</a></li>--%>
<%--            <li><a href="remima.jsp">修改商品</a></li>--%>
<%--            <li><a href="remima.jsp">删除商品</a></li>--%>
<%--        </ul>--%>
<%--    </c:if>--%>
</div>
