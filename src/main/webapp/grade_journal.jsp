<%@ page import="com.example.finalProjectServlet.model.dao.CourseDao" %>
<%@ page import="com.example.finalProjectServlet.model.entity.Mark" %>
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

  <title>Journal</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="WEB-INF/elements/navbar.jsp"/>
<div class="container">
  <form method="POST" action="${contextPath}/save_journal?id=<c:out value='<%=request.getParameter("id")%>'/>">
    <table class="table">
      <thead class="thead-dark">
      <tr>
        <th scope="col">Student name</th>
        <th scope="col">Mark</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach items='<%=(new CourseDao()).findCourseStudentsAndMarks(Long.parseLong(request.getParameter("id")))%>' var="student">
        <tr>
          <input type="hidden" name="students" value="${student.key.username}" />
          <td>${student.key.fullName}</td>
          <td>
            <select id="marks" name="marks">
              <c:forEach items='<%=Mark.values()%>' var="mark">
                <c:choose>
                  <c:when test="${mark.name() == student.value.name()}">
                    <option value="${mark.name()}" selected="selected">${mark.name().toUpperCase()}</option>
                  </c:when>
                  <c:otherwise>
                    <option value="${mark.name()}">${mark.name()}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
          </td>
        </tr>
      </c:forEach>

      </tbody>
    </table>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Save</button>
  </form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
