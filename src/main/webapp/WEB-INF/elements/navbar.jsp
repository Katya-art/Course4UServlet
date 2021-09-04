<%@ page import="com.example.finalProjectServlet.model.entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<nav class="navbar navbar-default navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Course4U</a>
            <c:if test='<%=session.getAttribute("user") != null && ((User)session.getAttribute("user")).getRoleId() == 1%>'>
                <a class="navbar-brand" href="students_courses.jsp?condition=not_started"><fmt:message
                        key="myCourses"/></a>
            </c:if>
            <c:if test='<%=session.getAttribute("user") != null && ((User)session.getAttribute("user")).getRoleId() == 2%>'>
                <a class="navbar-brand" href="register.jsp?role=teacher"><fmt:message key="addTeacher"/></a>
                <a class="navbar-brand" href="add_course.jsp"><fmt:message key="addCourse"/></a>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/students_list"><fmt:message
                        key="listOfStudents"/></a>
            </c:if>
            <c:if test='<%=session.getAttribute("user") != null && ((User)session.getAttribute("user")).getRoleId() == 3%>'>
                <a class="navbar-brand" href="teachers_courses.jsp"><fmt:message key="myCourses"/></a>
            </c:if>
        </div>
        <div class="navbar-header navbar-right">
            <c:choose>
                <c:when test='<%=session.getAttribute("user") == null%>'>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/login.jsp"><fmt:message
                            key="login"/></a>
                    <a class="navbar-brand" href="register.jsp?role=student"><fmt:message key="register"/></a>
                </c:when>
                <c:otherwise>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/user.jsp">
                        <%=((User) session.getAttribute("user")).getUsername()%>
                    </a>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/logout"><fmt:message
                            key="logout"/></a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
