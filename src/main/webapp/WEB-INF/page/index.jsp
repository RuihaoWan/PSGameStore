<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Shopping</title>
<link rel="icon" type="image/x-icon"
	href="<c:url value="/resource/images/favicon.png"/>" />
</head>
<body>
	<%-- load other webpages --%>
	<%@ include file="navbar.jsp"%>
	<%@ include file="slider.jsp"%>
	<jsp:include page="productGrid.jsp"><jsp:param
			name="products" value="${products}" /></jsp:include>
	<div id="footer">
			<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>