<%@ page import="com.example.finalProjectServlet.model.dao.UserDao" %>
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

    <title><fmt:message key="addCourse"/>e</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

</head>

<body>

<div class="container" style="width: 330px">
    <form method="post" action="${pageContext.request.contextPath}/add_course">
        <h2 class="form-heading"><fmt:message key="addCourse"/></h2>
        <span style="color: #ac2925">${error}</span>
        <input name="name" type="text" class="form-control" placeholder='<fmt:message key="courseName"/>'
               autofocus="true">
        <span style="color: #ac2925">${nameError}</span><br/>
        <input name="theme" type="text" class="form-control" placeholder='<fmt:message key="theme"/>'>
        <span style="color: #ac2925">${themeError}</span><br/>
        <input name="duration" type="number" class="form-control" placeholder='<fmt:message key="duration"/>'>
        <span style="color: #ac2925">${durationError}</span><br/>
        <select id="teacher" name="teacher">
            <option value="0"><fmt:message key="selectTeacher"/></option>
            <c:forEach items="<%=(new UserDao()).findAllByRole(3)%>" var="teacher">
                <option value="${teacher.id}">${teacher.fullName}</option>
            </c:forEach>
        </select>
        <input class="btn btn-lg btn-primary btn-block" type="submit" value='<fmt:message key="save"/>'>
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

