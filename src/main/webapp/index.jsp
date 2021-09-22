<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.finalProjectServlet.model.dao.CourseDao" %>
<%@ page import="com.example.finalProjectServlet.model.entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "ex" uri = "/WEB-INF/custom.tld"%>

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

    <title>Course4U</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="WEB-INF/elements/navbar.jsp"/>
<%
    int pageId = 1;
    int total = 10;
    String sortField = "name";
    int totalItems = (new CourseDao()).getNumberOfCourses(3, request.getParameter("teacher"), request.getParameter("theme"));
    int totalPages = (int) Math.ceil((float) totalItems / total);
    int currentPage = 1;
    String sortDir = "asc";
    if (request.getParameter("page") != null) {
        currentPage = Integer.parseInt(request.getParameter("page"));
    }
    if (request.getParameter("sortField") != null) {
        sortField = request.getParameter("sortField");
    }
    if (request.getParameter("sortDir") != null) {
        sortDir = request.getParameter("sortDir");
    }
    if (currentPage != 1) {
        pageId = currentPage - 1;
        pageId = pageId * total + 1;
    }
    request.setAttribute("pageId", pageId);
    request.setAttribute("total", total);
    request.setAttribute("sortField", sortField);
    request.setAttribute("totalItems", totalItems);
    request.setAttribute("totalPages", totalPages);
    request.setAttribute("currentPage", currentPage);
    request.setAttribute("sortDir", sortDir);
    request.setAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    request.setAttribute("teacher", request.getParameter("teacher"));
    request.setAttribute("theme", request.getParameter("theme"));
%>
<div class="container">
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col"><a
                    href="index.jsp?page=${currentPage}&sortField=name&sortDir=${reverseSortDir}&teacher=${teacher}&theme=${theme}"><fmt:message
                    key="courseName"/></a></th>
            <th scope="col"><fmt:message key="teacherName"/></th>
            <th scope="col"><fmt:message key="theme"/></th>
            <th scope="col"><a
                    href="index.jsp?page=${currentPage}&sortField=duration&sortDir=${reverseSortDir}&teacher=${teacher}&theme=${theme}"><fmt:message
                    key="duration"/></a>
            </th>
            <th scope="col"><a
                    href="index.jsp?page=${currentPage}&sortField=number_of_students&sortDir=${reverseSortDir}&teacher=${teacher}&theme=${theme}"><fmt:message
                    key="numberOfStudents"/></a></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items='<%=(new CourseDao()).findAllByConditionNotEqual(3, pageId, total, sortField, sortDir,
        request.getParameter("teacher"), request.getParameter("theme"))%>'
                   var="course">
            <jsp:useBean id="currentCourse" class="com.example.finalProjectServlet.model.entity.Course"/>
            <jsp:setProperty name="currentCourse" property="id" value="${course.id}"/>
            <tr>
                <td>${course.name}</td>
                <td>
                    <a href="index.jsp?page=1&sortField=duration&sortDir=${sortDir}&teacher=${course.teacherId}&theme=${theme}">${course.teacherName}</a>
                </td>
                <td>
                    <a href="index.jsp?page=1&sortField=duration&sortDir=${sortDir}&teacher=${teacher}&theme=${course.theme}">${course.theme}</a>
                </td>
                <td>${course.duration}</td>
                <td>${course.numberOfStudents}</td>
                <c:if test='<%=session.getAttribute("user") != null &&
                ((User)session.getAttribute("user")).getRoleId() == 2%>'>
                    <td>
                        <a href="${pageContext.request.contextPath}/edit_course.jsp?id=<c:out value="${course.id}"/>&page=${currentPage}&sortField=${sortField}&sortDir=${sortDir}&teacher=${teacher}&theme=${theme}"
                           class="btn btn-info mt-4"><fmt:message key="edit"/></a></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/delete_course?id=<c:out value='${course.id}'/>&page=${currentPage}&sortField=${sortField}&sortDir=${sortDir}&teacher=${teacher}&theme=${theme}"
                           class="btn btn-danger mt-4"><fmt:message key="delete"/></a></td>
                </c:if>
                <c:if test='<%=session.getAttribute("user") != null &&
                ((User)session.getAttribute("user")).getRoleId() == 1%>'>
                    <c:choose>
                        <c:when test='<%=((User)session.getAttribute("user")).getStatusId() == 1%>'>
                            <c:choose>
                                <c:when test='<%=!(new CourseDao()).checkCourseForStudents(currentCourse.getId(),
                                ((User)session.getAttribute("user")).getId())%>'>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/enroll_course?course_id=<c:out value='${course.id}'/>&student_id=<c:out value='<%=((User)session.getAttribute("user")).getId()%>'/>"
                                           class="btn btn-primary"><fmt:message key="enroll"/></a></td>
                                </c:when>
                                <c:otherwise>
                                    <td><fmt:message key="alreadyEnrolled"/></td>
                                </c:otherwise>
                            </c:choose>

                        </c:when>
                        <c:otherwise>
                            <td style="color: #ac2925"><ex:CustomTag><fmt:message key="accountWasBlocked"/></ex:CustomTag></td>
                        </c:otherwise>
                    </c:choose>

                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${totalPages > 1}">
        <div class="row col-sm-10">
            <div class="col-sm-1">
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <c:choose>
                        <c:when test="${currentPage != i}"><a
                                href="index.jsp?page=${i}&sortField=${sortField}&sortDir=${sortDir}&teacher=${teacher}&theme=${theme}">${i}</a></c:when>
                        <c:otherwise>${i}</c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <div class="col-sm-1">
                <c:choose>
                    <c:when test="${currentPage < totalPages}"><a
                            href="index.jsp?page=${currentPage + 1}&sortField=${sortField}&sortDir=${sortDir}&teacher=${teacher}&theme=${theme}"><fmt:message
                            key="next"/></a></c:when>
                    <c:otherwise>Next</c:otherwise>
                </c:choose>
            </div>
            <div class="col-sm-1">
                <c:choose>
                    <c:when test="${currentPage < totalPages}"><a
                            href="index.jsp?page=${totalPages}&sortField=${sortField}&sortDir=${sortDir}&teacher=${teacher}&theme=${theme}"><fmt:message
                            key="last"/></a></c:when>
                    <c:otherwise>Last</c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:if>
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