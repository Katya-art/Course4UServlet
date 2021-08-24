<%@ page import="com.example.finalProjectServlet.model.dao.CourseDao" %>
<%@ page import="com.example.finalProjectServlet.model.entity.User" %>
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

    <title>My courses</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="WEB-INF/elements/navbar.jsp"/>
<%
    String condition = request.getParameter("condition");
    int conditionId = 1;
    if (condition.equals("in_progress")) {
        conditionId = 2;
    }
    if (condition.equals("completed")) {
        conditionId = 3;
    }
    request.setAttribute("conditionId", conditionId);
%>
<div class="container">
    <h2><a href="${pageContext.request.contextPath}/students_courses.jsp?condition=not_started">Not started</a> |
        <a href="${pageContext.request.contextPath}/students_courses.jsp?condition=in_progress">In progress</a> |
        <a href="${pageContext.request.contextPath}/students_courses.jsp?condition=completed">Completed</a></h2>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Course name</th>
            <th scope="col">Teacher name</th>
            <th scope="col">Theme</th>
            <th scope="col">Duration</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach
                items='<%=(new CourseDao()).findAllByIdAndCondition(((User)session.getAttribute("user")).getId(),
                Integer.parseInt(String.valueOf(request.getAttribute("conditionId"))))%>' var="course">
            <tr>
                <td>${course.name}</td>
                <td>${course.teacherName}</td>
                <td>${course.theme}</td>
                <td>${course.duration}</td>
            </tr>
        </c:forEach>
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