<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% if(session == null || session.getAttribute("loggedUser") == null) {
    response.sendRedirect("index.jsp");
}%>
<html>
<head>
    <!-- Meta: załączanie czcionek, CSS-a, kodowanie i import java.sql -->
    <%@include file="components/meta.jsp" %>
    <title>Historia zamówień | SKLEPZG.PL</title>
</head>
<body>
<!-- Menu, banner i przyciski logowania/rejestracji -->
<%@include file="components/menu.jsp" %>
<h3>Historia zamówień użytkownika <i><%= session.getAttribute("loggedUser").toString() %></i></h3>
<%
        String polaczenieURL = "jdbc:mysql://localhost/projekt?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&user=root&password=";
        Connection conn = null;

        //Ustawiamy sterownik MySQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Ustawiamy dane dotyczące podłączenia
        conn = DriverManager.getConnection(polaczenieURL);

        String query;

        query = "SELECT zamowienia.ID, zamowienia.ID_klienta, produkty_has_zamowienia.*, produkty.ID, produkty.Nazwa, produkty.Cena, produkty.Zdjęcie, klienci.ID " +
                "FROM zamowienia, produkty_has_zamowienia, produkty, klienci WHERE klienci.ID = zamowienia.ID_klienta AND klienci.Login = ? " +
                "AND produkty_has_zamowienia.zamowienia_ID_zamowienia = zamowienia.ID AND produkty_has_zamowienia.Produkty_ID_produktu = produkty.ID " +
                "ORDER BY zamowienia.ID DESC";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, session.getAttribute("loggedUser").toString());
        final ResultSet rs = preparedStmt.executeQuery();

        int k = 0;
        while (rs.next()) {
        if(k != rs.getInt("zamowienia.ID"))
        { out.print("<h4>Zamówienie nr " +rs.getInt("zamowienia.ID") + "</h4>");
        k = rs.getInt("zamowienia.ID");
        } %>

    <a href="oferta.jsp?id=<%= rs.getString("produkty.ID") %>">

        <div id="lista_produkt">
            <img src="<%= rs.getString("Zdjęcie") %>">
            <p id="lista_nazwa"><%=rs.getString("Nazwa")%></p>
            <p id="lista_cena" style="text-align: right;">
                Ilość sztuk: <%=rs.getInt("produkty_has_zamowienia.Ilosc_sztuk")%><br>
                Cena: <%=rs.getDouble("Cena") * rs.getInt("Ilosc_sztuk")%> PLN
            </p>
        </div>
    </a>
    <hr> <%
        }
        conn.close();
%>
<!-- Stopka i skrypt aktualizowania koszyka -->
<%@include file="components/footer.jsp" %>
</body>
</html>