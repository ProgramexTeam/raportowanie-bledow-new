<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Meta: załączanie czcionek, CSS-a, kodowanie i import java.sql -->
    <%@include file="components/meta.jsp" %>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Zaloguj lub zarejestruj | SKLEPZG.PL</title>
</head>
<body>
    <!-- Menu, banner i przyciski logowania/rejestracji -->
    <%@include file="components/menu.jsp" %>
    <%
        if(request.getParameter("registered") != null) { %>
            <div class="center-content">
                <div id="alert" class="alert-box center-content">
                    <a href="javascript:void(0)" onClick="alertOFF()" class="fas fa-times"></a>
                    Zarejestrowano poprawnie. Możesz się teraz zalogować!
                </div>
            </div>
        <% }
        else if ((request.getParameter("loginError") != null) && session.getAttribute("loggedUser") == null) { %>
            <div class="center-content">
                <div id="error" class="alert-box center-content">
                    <a href="javascript:void(0)" onClick="alertOFF()" class="fas fa-times"></a>
                    Niepoprawny login lub hasło!
                </div>
            </div>
        <% }
        else if ((request.getParameter("registerError") != null) && session.getAttribute("loggedUser") == null) { %>
            <div class="center-content">
                <div id="error" class="alert-box center-content">
                    <a href="javascript:void(0)" onClick="alertOFF()" class="fas fa-times"></a>
                    Podany użytkownik już istnieje!
                </div>
            </div>
        <% } %>
    <div id="lewy">
        <!-- Formularz logowania -->
        <jsp:include page="components/login-form.jsp"></jsp:include>
    </div>
    <div id="prawy">
        <form method="POST" action="scripts/waliduj.jsp?register" enctype="text/html;charset=UTF-8">
            <h3>Zarejestruj</h3>
            <input type="text" name="login" id="login" placeholder="Login" maxlength="20" required style="width: 400px;" />
            <div id="sprawdzlogin" class="error"></div>
            <input type="password" id="password" name="password" oninput="sprawdzHaslo()" placeholder="Hasło" maxlength="25" required style="width: 195px;" />
            <input type="password" id="p_password" name="p_password" oninput="sprawdzHaslo()" placeholder="Powtórz hasło" maxlength="25" required style="width: 200px;" /><br />
            <div id="niepasi" class="error"></div>
            <input type="text" name="name" placeholder="Imię" maxlength="20" required style="width: 195px;" />
            <input type="text" name="surname" placeholder="Nazwisko" maxlength="20" required style="width: 200px;" /><br>
            <input type="email" name="email" id="email" placeholder="Adres e-mail" maxlength="50" required style="width: 400px;" pattern="[A-z0-9._%+-]+@[A-z0-9.-]+\.[A-z]{2,}$" /><br>
            <div id="sprawdzemail" class="error"></div>
            <input type="text" name="phone" id="phone" placeholder="Numer telefonu" maxlength="9" required style="width: 400px;" pattern="\d{9}" title="Wpisz nr telefonu w formacie 9-cyfrowym" /><br>
            <div id="sprawdzphone" class="error"></div>
            <input type="text" name="street" placeholder="Ulica" maxlength="35" required style="width: 300px;" />
            <input type="text" name="number" placeholder="Nr domu" maxlength="8" required style="width: 95px;" /><br>
            <input type="text" name="postcode" placeholder="Kod" maxlength="6" required style="width: 95px;" pattern="\d{2}-\d{3}" title="XX-XXX" />
            <input type="text" name="city" placeholder="Miejscowość" maxlength="30" required style="width: 300px;" /><br>
            <input type="submit" value="Zarejestruj" id="Zarejestruj" class="button">
        </form>
        <script src="scripts/sprawdzlogin.js"></script>
    </div>
    <!-- Stopka i skrypt aktualizowania koszyka -->
    <%@include file="components/footer.jsp" %>
</body>
</html>
