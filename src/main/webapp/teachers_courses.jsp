<%@ page import="com.example.finalProjectServlet.model.dao.CourseDao" %>
<%@ page import="com.example.finalProjectServlet.model.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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

    <title><fmt:message key="myCourses"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="WEB-INF/elements/navbar.jsp"/>
<div class="container">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col"><fmt:message key="courseName"/></th>
            <th scope="col"><fmt:message key="numberOfStudents"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items='<%=(new CourseDao()).findAllByTeacherId(((User)session.getAttribute("user")).getId())%>'
                   var="course">
            <jsp:useBean id="currentCourse" class="com.example.finalProjectServlet.model.entity.Course"/>
            <jsp:setProperty name="currentCourse" property="id" value="${course.id}"/>
            <tr>
                <td>${course.name}</td>
                <td><%=(new CourseDao()).getNumberOfStudents(currentCourse.getId())%>
                </td>
                <c:if test="${course.conditionId == 1}">
                    <td><a href="${pageContext.request.contextPath}/start_course?id=<c:out value="${course.id}"/>"
                           class="btn btn-info mt-4"><fmt:message key="startCourse"/></a></td>
                </c:if>
                <c:if test="${course.conditionId == 2}">
                    <td><a href="${pageContext.request.contextPath}/grade_journal.jsp?id=<c:out value="${course.id}"/>"
                           class="btn btn-info mt-4"><fmt:message key="gradebook"/></a></td>
                    <td><a href="${pageContext.request.contextPath}/finish_course?id=<c:out value="${course.id}"/>"
                           class="btn btn-info mt-4"><fmt:message key="finishCourse"/></a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>

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
