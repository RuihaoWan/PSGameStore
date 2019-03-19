<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="<c:url value="/resource/bootstrap/css/bootstrap.min.css"/>">
<script src="<c:url value="/resource/js/jquery.js"/>"></script>
<script src="<c:url value="/resource/bootstrap/js/bootstrap.min.js"/>"></script>
<link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<style>
body, h1, h2, h3, h4, h5 {
	font-family: "Raleway", sans-serif
}

.w3-sidenav a, .w3-sidenav h4 {
	font-weight: bold
}
</style>
</head>
<body>
	<%
		int count = 0;
	%>
		<c:forEach items="${products}" var="prod">
			<c:if test="${prod.unitStock>0}">
			<%
				count++;
				if(count%3==1){
			%>
			<hr>
				<div class="w3-row-padding" style="padding: 0px 30px; margin-bottom: 20px">
			<%} %>
				<div class="w3-third w3-container w3-margin-bottom" style="text-align:center">
					<img src="/onlineShopProduct/${prod.id}.jpg" alt="No Picture"
						style="width: 70%" class="w3-hover-opacity"/>
					
					<div class="w3-container w3-white">
					<br>
						<p>
							<b>${prod.productName}</b>
						</p>
						<p>
						<ul style="text-align:left;margin-left:10%">
							<li><b>Platform:</b>&nbsp;${prod.platform}</li>
							<li><b>Quality:</b>&nbsp;${prod.quality}</li>
							<li><b>Remain:</b>&nbsp;${prod.unitStock}</li>
							<li><b>Price:</b>&nbsp;${prod.productPrice}</li>
						</ul>
						</p>
					</div>
				</div>
			<%if(count%3==0){ %>
			</div>
			<% }%>
			</c:if>
		</c:forEach>
		<%if(count%3!=0) {%>
		</div>
		<%} %>
</body>
</html>