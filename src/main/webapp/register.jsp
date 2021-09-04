<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.lang}"/>

<!DOCTYPE html>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title><fmt:message key="register"/></title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

</head>

<body>

<div class="container" style="width: 330px">
    <form method="post"
          action="${pageContext.request.contextPath}/register?role=<c:out value='<%= request.getParameter("role") %>'/>">
        <h2 class="form-heading"><fmt:message key="createAnAccount"/></h2>
        <span style="color: #ac2925">${error}</span>
        <input name="fullName" type="text" class="form-control" placeholder='<fmt:message key="fullName"/>'
               autofocus="true">
        <span style="color: #ac2925">${fullNameError}</span><br/>
        <input name="username" type="text" class="form-control" placeholder='<fmt:message key="username"/>'>
        <span style="color: #ac2925">${usernameError}</span><br/>
        <input name="email" type="text" class="form-control" placeholder='<fmt:message key="email"/>'>
        <span style="color: #ac2925">${emailError}</span><br/>
        <input name="password" type="password" class="form-control" placeholder='<fmt:message key="password"/>'>
        <span style="color: #ac2925">${passwordError}</span><br/>
        <input name="confirmPassword" type="password" class="form-control"
               placeholder='<fmt:message key="confirmPassword"/>'>
        <input class="btn btn-lg btn-primary btn-block" type="submit" value='<fmt:message key="register"/>'>
    </form>
    <span style="float: right">
    <a href="?sessionLocale=en">en</a>
    |
    <a href="?sessionLocale=ua">ua</a>
    </span>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
