<%@ page import="com.example.finalProjectServlet.model.entity.User" %>
<%@ page import="com.example.finalProjectServlet.model.entity.Role" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>My account</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="WEB-INF/elements/navbar.jsp"/>
<%
    User user = (User) session.getAttribute("user");
    request.setAttribute("user", user);
%>
<div class="container">
    <h2 style="text-align: center">${user.username}</h2>
    <h4 style="text-align: center"><%=Role.getRole(user)%>
    </h4>
    <c:if test="${user.roleId == 1 && user.statusId == 2}">
        <h5 style="text-align: center">Your account was blocked</h5>
    </c:if>
    <table class="table">
        <tbody>
        <tr>
            <td>Full name</td>
            <td>${user.fullName}</td>
        </tr>
        <tr>
            <td>Email</td>
            <td>${user.email}</td>
        </tr>
        </tbody>
    </table>

    <span style="float: right">
    <a href="?lang=en">en</a>
    |
    <a href="?lang=ua">ua</a>
</span>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>