<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="utf-8" />
		<title>个人信息</title>
		<jsp:include page="include/public-static-file.jsp" />
		<link rel="stylesheet" type="text/css" href="${path}/static/layui/css/layui.css"/>
		<script src="${path}/static/layui/layui.js " type="text/javascript" charset="utf-8"></script>
		<link rel="stylesheet" type="text/css" href="${path}/static/css/public.css"/>
		<link rel="stylesheet" type="text/css" href="${path}/static/css/mygrxx.css" />
		<script>
			$(function () {
				//取消修改
				$(".jia-btn-cancle").click(function(){
					$(".mask").hide();
					$(".bj").hide();
					$(".xg").hide();
					$(".remima").hide();
					$(".avatar").hide();
					$(".pj").hide();
					$(".chak").hide();
					$(".xg-pay").hide();
				});
				$(".jia-user-information-save").click(function(){
					$(".mask").hide();
					$(".bj").hide();
					$(".xg").hide();
					$(".remima").hide();
					$(".avatar").hide();
					$(".pj").hide();
					$(".chak").hide();
					$(".xg-pay").hide();
				});
				$(".jia-user-pay-password-save").click(function(){
					$(".mask").hide();
					$(".bj").hide();
					$(".xg").hide();
					$(".remima").hide();
					$(".avatar").hide();
					$(".pj").hide();
					$(".chak").hide();$(".xg-pay").hide();

				});


				$(".jia-div-code").hide()
				$("input[name='tel']").keyup(function () {
					$(".jia-div-code").show()
				});


/*-----------------------------------更改个人信息开始-------------------------------------------------*/


/*-----------------------------------更改个人信息结束-------------------------------------------------*/

			})
		</script>
	</head>
	<body>
	<jsp:include page="include/head.jsp" />
		<!------------------------------idea------------------------------>
		<div class="address mt">
			<div class="wrapper clearfix">
				<a href="index.html" class="fl">首页</a>
				<span>/</span>
				<a href="mygxin.html" class="on">个人信息</a>
			</div>
		</div>
		
		<!------------------------------Bott------------------------------>
		<div class="Bott">
			<div class="wrapper clearfix">
				<div class="zuo fl">

					<jsp:include page="include/personal-left.jsp"/>
				</div>
				<div class="you fl">
					<h2>个人信息</h2>
					<div class="gxin">
						<div class="tx"><a href="#">
							<c:if test="${sessionScope.user.user_avatar_url == null}">
								<img class="jia-userAvatar" src="${path}/static/img/tx.jpg"/>

							</c:if>
							<c:if test="${sessionScope.user.user_avatar_url != null}">
								<img class="jia-userAvatar" src="${sessionScope.user.user_avatar_url}"/>

							</c:if>


							<p class="jia-userAvatar"><%--修改头像--%></p></a></div>
						<div class="xx">
							<h3 class="clearfix"><strong class="fl">基础资料</strong><a href="#" class="fr" id="edit1">编辑</a></h3>
							<div>用户名：${sessionScope.user.user_name}</div>
							<div>昵  称：${sessionScope.user.user_nick}</div>
							<div>性  别：${sessionScope.user.user_gender}</div>
							<div>等  级：<fmt:formatNumber
									value="${sessionScope.user.user_exp / 100}"
									type="NUMBER"/></div>
							<div>生  日：<fmt:formatDate value="${sessionScope.user.user_birthday}" type="DATE" /></div>
							<div>手机号：${sessionScope.user.user_tel}</div>
							<div>邮  箱：${sessionScope.user.user_mail}</div>
							<div>注册时间：<fmt:formatDate value="${sessionScope.user.user_register_time}" type="both" /></div>
							<h3>高级设置</h3>
							<!--<div><span class="fl">银行卡</span><a href="#" class="fr">管理</a></div>-->
							<div><span class="fl">登录密码</span><a href="#" class="fr" id="edit2">修改</a></div>
							<c:if test="${sessionScope.user.user_pay_password == null}">
								<div><span class="fl">支付密码</span><a href="#" class="fr" id="edit3">添加</a></div>

							</c:if>
							<c:if test="${sessionScope.user.user_pay_password != null}">
								<div><span class="fl">支付密码</span><a href="#" class="fr" id="edit4">修改</a></div>

							</c:if>
						</div>
					</div>			
				</div>
			</div>
		</div>
		<!--遮罩-->
		<div class="mask"></div>
		<!--编辑弹框-->
		<div class="bj">
<%--			<div class="clearfix"><a href="#" class="fr gb"><img src="${path}/static/img/icon4.png"/></a></div>--%>
			<h3 style="margin: 10px">编辑基础资料</h3>
	<hr>
			<form class="layui-form" action="">
				<div class="layui-form-item">
					<label class="layui-form-label">昵称</label>
					<div class="layui-input-block">
						<input type="text" name="nickname" required  lay-verify="required" placeholder="${sessionScope.user.user_nick}" autocomplete="off" class="layui-input">
					</div>
				</div>


				<div class="layui-form-item">
					<label class="layui-form-label">生日</label>
					<div class="layui-input-block">
						<input readonly="" type="text" class="layui-input" id="jia-user-birthday"
placeholder="<fmt:formatDate value="${sessionScope.user.user_birthday}" type="DATE" />">
						<input type="hidden" class="jia-select-birthday" value="" name="birthday">
					</div>
				</div>
				<script>
					layui.use('laydate', function(){
						var laydate = layui.laydate;

						//执行一个laydate实例
						laydate.render({
							elem: '#jia-user-birthday' ,//指定元素
							position:'fixed',
							value: '2019-06-25',
							isInitValue: false //是否允许填充初始值，默认为 true
							,done: function(value, date, endDate){
								$('.jia-select-birthday').val(value)
								console.log(value); //得到日期生成的值，如：2017-08-18
								console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
								console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
							}
						});

					});
				</script>
				<div class="layui-form-item">
					<label class="layui-form-label">单选框</label>
					<div class="layui-input-block">
						<input type="radio" name="sex" value="男" title="男">
						<input type="radio" name="sex" value="女" title="女" checked>
					</div>
				</div>

				<div class="layui-form-item">
					<div class="layui-input-block">
						<button class="layui-btn jia-user-information-save" lay-submit lay-filter="formDemo" lay-filter="jia-edit-user-information">保存</button>
						<button type="button" class="layui-btn layui-btn-primary jia-btn-cancle">取消</button>
					</div>
				</div>
			</form>

			<script>
				//Demo
				layui.use('form', function(){
					var form = layui.form;

					//监听提交
					form.on('submit(formDemo)', function(data){

						console.log(data.field)
						$.ajax({
							url:"${path}/editUserInfo",
							type:"post",
							data:{
								nickname:data.field.nickname,
								gender:data.field.sex,
								birthday:data.field.birthday
							},
							success:function (res) {
								if(res.success === true){
									layer.msg('修改成功', {time: 1000, anim: 2, icon: 6},function () {
										location.reload();
									});


								} else {
									layer.msg("修改失败")
								}

							}



						})
						console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
						return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
					});
				});
			</script>
		</div>
		<!--修改密码-->
		<div class="xg">
			<div class="clearfix"><a href="#" class="fr gb"></a></div>
			<h3 style="margin: 10px">修改密码</h3>
			<br/>
			<form class="layui-form" action="">
				<div class="layui-form-item">
					<label class="layui-form-label">密码</label>
					<div class="layui-input-block">
						<input type="text" name="password" required  lay-verify="required" placeholder="请输入新密码" autocomplete="off" class="layui-input">
					</div>
				</div>




				<div class="layui-form-item">
					<div class="layui-input-block">
						<button class="layui-btn jia-user-password-save" lay-submit  lay-filter="jia-edit-user-password">保存</button>
						<button type="button" class="layui-btn layui-btn-primary jia-btn-cancle">取消</button>
					</div>
				</div>
			</form>

			<script>
				//Demo
				layui.use('form', function(){
					var form = layui.form;

					//监听提交
					form.on('submit(jia-edit-user-password)', function(data){
						console.log("修改密码")
						console.log(data.field)
						$.ajax({
							<%--url:"${path}/editUserPassword",--%>
							url:"${path}/editUserInfo",
							type:"post",
							data:{
								password:data.field.password
							},
							success:function (res) {
								if(res.success === true){
									layer.msg('修改成功', {time: 1000, anim: 2, icon: 6},function () {
										location = "${path}/logout";
									});

								} else {
									layer.msg("修改失败")
								}

							}



						})
						console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
						return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
					});
				});
			</script>
		</div>
<%--	修改支付秘密码--%>
	<div class="xg-pay">
		<div class="clearfix"><a href="#" class="fr gb"></a></div>
		<h3 style="margin: 10px">修改支付密码</h3>
		<br/>
		<form class="layui-form" action="">
			<div class="layui-form-item">
				<label class="layui-form-label">支付密码</label>
				<div class="layui-input-block">
					<input type="text" name="payPassword" required  lay-verify="required" placeholder="请输入支付密码" autocomplete="off" class="layui-input">
				</div>
			</div>




			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn jia-user-pay-password-save" lay-submit  lay-filter="jia-edit-user-pay-password">保存</button>
					<button type="button" class="layui-btn layui-btn-primary jia-btn-cancle">取消</button>
				</div>
			</div>
		</form>

		<script>
			//Demo
			layui.use('form', function(){
				var form = layui.form;

				//监听提交
				form.on('submit(jia-edit-user-pay-password)', function(data){

					console.log(data.field)
					$.ajax({
						<%--url:"${path}/editUserPayPassword",--%>
						url:"${path}/editUserInfo",
						type:"post",
						data:{
							payPassword:data.field.payPassword
						},
						success:function (res) {
							if(res.success === true){
								layer.msg('修改成功', {time: 1000, anim: 2, icon: 6},function () {
									location.reload()
								});

							} else {
								layer.msg("修改失败")
							}

						}



					})
					console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
					return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
				});
			});
		</script>
	</div>

		
		<!--返回顶部-->
	<jsp:include page="include/right-sidebar.jsp"></jsp:include>
		<!--footer-->
	<jsp:include page="include/bottom.jsp"></jsp:include>

	<script src="${path}/static/js/public.js" type="text/javascript" charset="utf-8"></script>
		<script src="${path}/static/js/user.js" type="text/javascript" charset="utf-8"></script>
	</body>
</html>
