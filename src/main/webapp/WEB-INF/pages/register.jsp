<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Zarejestruj się w portalu</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
</head>
<body>
<div id="login-form-container">
    <div class="container-inner">
        <h1>Rejestracja</h1>
        <form action="${pageContext.request.contextPath}/register" method="post">
            <p><span>Login:</span> <br /> <span style="font-size: 8px">Login musi zawierać minimum 6 znaków.</span> <br />
                <input type="text" name="userlogin" pattern=".{6,}" title="Login musi zawierać minimum 6 znaków" required></p>
            <p><span>Hasło </span> <br /> <span style="font-size: 8px">Hasło musi zawierać przynajmniej jedną cyfrę, jeden znak specjalny, jedną wielką i jedną małą literę.<br>Dodatkowo hasło
                musi składać się z minimum 8 znaków.</span> <br /> <input type="password" name="pwd" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" title="
                Hasło musi zawierać przynajmniej jedną cyfrę jedną wielką i jedną małą literę. Dodatkowo hasło musi składać się z minimum 8 znaków." required></p>
            <p><span>Imię:</span> <br /> <input type="text" name="firstname" required></p>
            <p><span>Nazwisko:</span> <br /> <input type="text" name="lastname" required></p>
            <p><span>Email:</span> <br /> <input type="email" name="email" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" required></p>
            <p><span>Rola:</span><br />
                <select name="role" id="role">
                    <option value="developer">developer</option>
                    <option value="tester">tester</option>
                    <option value="analyst">analityk</option>
                </select>
            </p>
            <p><input type="submit" value="Zarejestruj" required></p>
            <p style="color: red">Wszystkie pola są wymagane!</p>
        </form>
        <p style="color: red; font-weight: bold">
            <%
                if(request.getAttribute("message")!=null){
                    out.println(request.getAttribute("message"));
                }
            %>
        </p>
    </div>
</div>
</body>
</html>
