<%--
  Created by IntelliJ IDEA.
  User: Maciej Korcz
  Date: 12.11.2019
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <!-- Meta: załączanie czcionek, CSS-a, kodowanie i import java.sql -->
    <%@include file="components/meta.jsp" %>
    <title>Oferta | SKLEPZG.PL</title>
</head>
<body>
<jsp:useBean id="user" class="javastart.SklepZG.model.User"
             scope="session"></jsp:useBean>

    <jsp:setProperty property="*" name="user" />

    <jsp:useBean id="dataSource"
                 class="javastart.SklepZG.model.DataSource" scope="session"></jsp:useBean>

    Nazwa: <%= user.getName() %><br />

    <% String result = "Dane niepoprawne";
        if(dataSource.userExists(user)) {
            result = "Poprawny użytkownik oraz hasło";
        }
    %>
    <%= result %>
</body>
</html>
