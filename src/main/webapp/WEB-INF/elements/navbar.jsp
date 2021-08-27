<%@ page import="com.example.finalProjectServlet.model.entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-default navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">Course4U</a>
            <c:if test='<%=session.getAttribute("user") != null && ((User)session.getAttribute("user")).getRoleId() == 1%>'>
                <a class="navbar-brand" href="students_courses.jsp?condition=not_started">My courses</a>
            </c:if>
            <c:if test='<%=session.getAttribute("user") != null && ((User)session.getAttribute("user")).getRoleId() == 2%>'>
                <a class="navbar-brand" href="register.jsp?role=teacher">Add teacher</a>
                <a class="navbar-brand" href="add_course.jsp">Add course</a>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/students_list">List of students</a>
            </c:if>
            <c:if test='<%=session.getAttribute("user") != null && ((User)session.getAttribute("user")).getRoleId() == 3%>'>
                <a class="navbar-brand" href="teachers_courses.jsp">My courses</a>
            </c:if>
        </div>
        <div class="navbar-header navbar-right">
            <c:choose>
                <c:when test='<%=session.getAttribute("user") == null%>'>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/login.jsp">Login</a>
                    <a class="navbar-brand" href="register.jsp?role=student">Register</a>
                </c:when>
                <c:otherwise>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/user.jsp">
                        <%=((User)session.getAttribute("user")).getUsername()%></a>
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/logout">Logout</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
