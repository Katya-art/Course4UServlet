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

    <title>Register your account</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

</head>

<body>

<div class="container" style="width: 330px">
    <form method="post" action="${pageContext.request.contextPath}/register?role=<c:out value='<%= request.getParameter("role") %>'/>">
        <h2 class="form-heading">Create new account</h2>
        <span style="color: #ac2925">${error}</span>
        <input name="fullName" type="text" class="form-control" placeholder="Full name"
               autofocus="true">
        <span style="color: #ac2925">${fullNameError}</span><br/>
        <input name="username" type="text" class="form-control" placeholder="Username">
        <span style="color: #ac2925">${usernameError}</span><br/>
        <input name="email" type="text" class="form-control" placeholder="Email">
        <span style="color: #ac2925">${emailError}</span><br/>
        <input name="password" type="password" class="form-control" placeholder="Password">
        <span style="color: #ac2925">${passwordError}</span><br/>
        <input name="confirmPassword" type="password" class="form-control" placeholder="Confirm password">
        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Register">
    </form>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
