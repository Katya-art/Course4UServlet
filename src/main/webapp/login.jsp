<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <title><fmt:message key="login"/></title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

</head>

<body>

<div class="container" style="width: 330px">

    <form method="POST" action="${contextPath}/login" class="form-signin">
        <h2 class="form-heading"><fmt:message key="login"/></h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="username" type="text" class="form-control" placeholder='<fmt:message key="username"/>'
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder='<fmt:message key="password"/>'/>
            <span style="color: #ac2925">${error}</span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="login"/></button>
            <h4 class="text-center"><a href="${contextPath}/register"><fmt:message key="createAnAccount"/></a></h4>
        </div>
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