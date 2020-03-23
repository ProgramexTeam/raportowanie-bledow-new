<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Meta: załączanie czcionek, CSS-a, kodowanie i import java.sql -->
    <%@include file="components/meta.jsp" %>
    <title>Złóż reklamację | SKLEPZG.PL</title>
</head>
<body>
<!-- Menu, banner i przyciski logowania/rejestracji -->
<%@include file="components/menu.jsp" %>
<%
    if(request.getParameter("send") != null) { %>
<div class="center-content">
    <div id="alert" class="alert-box center-content">
        <a href="javascript:void(0)" onClick="alertOFF()" class="fas fa-times"></a>
        Reklamacja została przyjęta!
    </div>
</div>
    <% } %>
<div class="center-content">
    <h3>Reklamacja</h3>
    <form action="scripts/complaint.jsp" method="POST">
        <% if(session == null || session.getAttribute("loggedUser") == null) { %>
            <input type="text" name="name" placeholder="Imię" maxlength="20" required style="width: 195px;" />
            <input type="text" name="surname" placeholder="Nazwisko" maxlength="20" required style="width: 200px;" /><br>
            <input type="email" name="email" placeholder="Adres e-mail" maxlength="50" required style="width: 400px;" pattern="[A-z0-9._%+-]+@[A-z0-9.-]+\.[A-z]{2,}$" /><br>
            <input type="text" name="phone" placeholder="Numer telefonu" maxlength="9" required style="width: 400px;" pattern="\d{9}" title="Wpisz nr telefonu w formacie 9-cyfrowym" /><br>
            <input type="text" name="order" placeholder="Numer zamówienia" maxlength="5" required style="width: 400px;" /><br>
        <% } else { %>
            <input list="order" name="order" placeholder="Numer zamówienia" maxlength="5" required style="width: 400px;" /><br>
            <datalist id="order">
                <%
                String polaczenieURL = "jdbc:mysql://localhost/projekt?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&user=root&password=";
                Connection conn = DriverManager.getConnection(polaczenieURL);

                String query = "SELECT zamowienia.ID " +
                        "FROM zamowienia, klienci WHERE klienci.Login = ? AND klienci.ID = zamowienia.ID_klienta " +
                        "ORDER BY zamowienia.ID DESC";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, session.getAttribute("loggedUser").toString());
                ResultSet rs = ps.executeQuery();
                while(rs.next()) { %>
                    <option value="<%= rs.getInt("zamowienia.ID") %>" />
                <% }
                conn.close(); %>
            </datalist>
        <% } %>
        <textarea name="description" placeholder="Opis" maxlength="500" required></textarea><br>
        <input type="submit" class="button" value="Złóż reklamację" />
    </form>
</div>

<!-- Stopka i skrypt aktualizowania koszyka -->
<%@include file="components/footer.jsp" %>
</body>
</html>