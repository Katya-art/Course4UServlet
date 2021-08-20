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

  <title>List of students</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="WEB-INF/elements/navbar.jsp"/>
<div class="container">
  <table class="table">
    <tbody>
    <c:forEach items="${studentsList}" var="student">
      <tr>
        <td>${student.fullName}</td>
        <c:choose>
          <c:when test="${student.statusId == 1}">
            <td><a href="change_student_status?action=block&id=<c:out value='${student.id}'/>"
                   class="btn btn-info mt-4">Block student</a></td>
          </c:when>
          <c:otherwise>
            <td><a href="change_student_status?action=unlock&id=<c:out value='${student.id}'/>"
                   class="btn btn-info mt-4">Unlock student</a></td>
          </c:otherwise>
        </c:choose>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
