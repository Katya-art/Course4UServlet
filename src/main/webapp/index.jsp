<%@ page import="com.example.finalProjectServlet.model.dao.CourseDao" %>
<%@ page import="com.example.finalProjectServlet.model.dao.UserDao" %>
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

    <title>Welcome</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="WEB-INF/elements/navbar.jsp"/>
<div class="container">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Course name</th>
            <th scope="col">Teacher name</th>
            <th scope="col">Theme</th>
            <th scope="col">Duration</th>
            <th scope="col">Number of students</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="<%=(new CourseDao()).findAllByCondition(3)%>" var="course">
            <tr>
                <td>${course.name}</td>
                <td>${course.teacherName}</td>
                <td>${course.theme}</td>
                <td>${course.duration}</td>
                <td>0</td>
                <c:if test='<%=session.getAttribute("user") != null && ((User)session.getAttribute("user")).getRoleId() == 2%>'>
                    <td><a href="${pageContext.request.contextPath}/edit_course"
                           class="btn btn-info mt-4">Edit</a></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/delete_course?id=<c:out value='${course.id}'/>"
                           class="btn btn-danger mt-4">Delete</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>