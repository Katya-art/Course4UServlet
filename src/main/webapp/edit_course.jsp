<%@ page import="com.example.finalProjectServlet.model.dao.UserDao" %>
<%@ page import="com.example.finalProjectServlet.model.dao.CourseDao" %>
<%@ page import="com.example.finalProjectServlet.model.entity.Course" %>
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

    <title>Edit course</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

</head>

<body>

<div class="container" style="width: 330px">
    <%
        Course course = (new CourseDao()).findById(Long.parseLong(request.getParameter("id")));
        request.setAttribute("course", course);
    %>
    <form method="POST" action="${contextPath}/edit_course?id=<c:out value='<%=request.getParameter("id")%>'/>"
          class="form-signin">
        <h2 class="form-heading">Edit course</h2>

        <span style="color: #ac2925">${error}</span>
        <input name="name" type="text" class="form-control" placeholder="Course name" value="${course.name}"
               autofocus="true">
        <span style="color: #ac2925">${nameError}</span><br/>
        <input name="theme" type="text" class="form-control" placeholder="Theme" value="${course.theme}">
        <span style="color: #ac2925">${themeError}</span><br/>
        <input name="duration" type="number" class="form-control" placeholder="Duration" value="${course.duration}">
        <span style="color: #ac2925">${durationError}</span><br/>
        <select id="teacher" name="teacher" title="${course.teacherName}">
            <c:forEach items="<%=(new UserDao()).findAllByRole(3)%>" var="teacher">
                <c:choose>
                    <c:when test="${teacher.id == course.teacherId}">
                        <option value="${teacher.id}" selected="selected">${teacher.fullName}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${teacher.id}">${teacher.fullName}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Save">

    </form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
