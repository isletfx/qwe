<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="${pageContext.request.contextPath }/jsp/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath }/jsp/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath }/jsp/js/bootstrap.min.js" type="text/javascript"></script>
<body>
<%@ include file="menu.jsp" %>

	<div class="container">
		<h1>${msg }</h1>
		<h1><a href="${pageContext.request.contextPath}/index.jsp">首页</a></h1>
		<h1><a href="${pageContext.request.contextPath }/jsp/login.jsp">登录</a></h1>
		<h1><a href="${pageContext.request.contextPath}/UserServlet?method=registerUI">注册</a></h1>
	</div>

	<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath }/jsp/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 传智商城 版权所有
	</div>

</body>
</html>