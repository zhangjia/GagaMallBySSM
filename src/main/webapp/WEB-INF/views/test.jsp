<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<c:forEach items="${requestScope.commodityDetail.commodityImgs}" var="img">
    ${img}
</c:forEach>--%>
${requestScope.commodityDetail.commodityInventory}
</body>
</html>
