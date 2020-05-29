<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Zaloguj się do portalu</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
</head>
<body>
<div id="login-form-container">
    <div class="container-inner">
        <h1>Zaloguj się do portalu</h1>
        <form action="login" method="post">
            <p><span>Login:</span> <br /> <input type="text" name="user"></p>
            <p><span>Hasło:</span> <br /> <input type="password" name="pwd"></p>
            <p><span><input type="checkbox" name="remember_me" id="remember_me" /><label for="remember_me">Zapamiętaj mnie</label></span></p>
            <p><input type="submit" value="Zaloguj"></p>
        </form>
        <% if(request.getAttribute("msg")!=null) {
            out.print("<p>" + request.getAttribute("msg") + "</p>");
        } %>
    </div>
</div>
</body>
</html>