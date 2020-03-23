<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<!-- Meta: załączanie czcionek, CSS-a, kodowanie i import java.sql -->
		<%@include file="components/meta.jsp" %>
		<title>Produkty | SKLEPZG.PL</title>
	</head>
	<body>
		<!-- Menu, banner i przyciski logowania/rejestracji -->
		<%@include file="components/menu.jsp" %>
		<%
		String sortowanie, kolor;
		String min = "";
		String max = "";
		String kategoria = null;
		boolean dostepnosc;
		if(request.getParameter("sortowanie") != null)
			sortowanie = request.getParameter("sortowanie");
		else
			sortowanie = "najnowsze";

		if(request.getParameter("kategoria") != null)
		    kategoria = request.getParameter("kategoria");

		if(request.getParameter("kolor") != null)
		    kolor = request.getParameter("kolor");
		else
		    kolor = "";

		if(request.getParameter("dostepnosc") != null)
		    dostepnosc = true;
		else
		    dostepnosc = false;

		if(request.getParameter("min") != null)
		    min = request.getParameter("min");
		if(request.getParameter("max") != null)
            max = request.getParameter("max");

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

        Statement stmt;
        ResultSet rs;
        String query;

        query = "SELECT Kategoria FROM produkty GROUP BY Kategoria";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
		%>
        <div id="filtry">
            <div id="tytul" style="padding: 7px;">KATEGORIE</div>
            <% while(rs.next()) { %>
            <a href="produkty.jsp?kategoria=<%= rs.getString("Kategoria")+"&sortowanie="+sortowanie %>"
                    <% if(rs.getString("Kategoria").equals(kategoria)) out.print("style=\"font-weight: bold;\"");
                    %>>
                <%
                    if(rs.getString("Kategoria").equals("Ubior"))
                        out.print("Ubiór");
                    else
                        out.print(rs.getString("Kategoria"));
                %>
            </a>
            <% } %>
            <div id="tytul" style="padding: 7px;">FILTROWANIE</div>
            <form>
                Cena:<br> <input type="number" min="0" max="999" name="min" step="0.01" placeholder="Od" <% if(min != null) out.print("value = "+min); %> />
                            <input type="number" min="0" step="0.01"  max="999" name="max" maxlength="3" placeholder="Do" <% if(max != null) out.print("value = "+max); %> />
                Kolor:<br>
                    <input type="radio" id="kolor_bialy" name="kolor" value="bialy" <% if(kolor.equals("bialy")) out.print("checked"); %> /> <label for="kolor_bialy">Biały</label><br>
                    <input type="radio" id="kolor_czerwony" name="kolor" value="czerwony" <% if(kolor.equals("czerwony")) out.print("checked"); %> /> <label for="kolor_czerwony">Czerwony</label><br>
                    <input type="radio" id="kolor_niebieski" name="kolor" value="niebieski" <% if(kolor.equals("niebieski")) out.print("checked"); %> /> <label for="kolor_niebieski">Niebieski</label><br>
                    <input type="radio" id="kolor_pomaranczowy" name="kolor" value="pomaranczowy" <% if(kolor.equals("pomaranczowy")) out.print("checked"); %> /> <label for="kolor_pomaranczowy">Pomarańczowy</label><br>
                    <input type="radio" id="kolor_zielony" name="kolor" value="zielony" <% if(kolor.equals("zielony")) out.print("checked"); %> /> <label for="kolor_zielony">Zielony</label><br>
                    <input type="radio" id="kolor_żółty" name="kolor" value="zolty" <% if(kolor.equals("zolty")) out.print("checked"); %> /> <label for="kolor_żółty">Żółty</label><br>
                    <input type="radio" id="kolor_szary" name="kolor" value="szary" <% if(kolor.equals("szary")) out.print("checked"); %> /> <label for="kolor_szary">Szary</label><br>
                    <input type="radio" id="kolor_czarny" name="kolor" value="czarny" <% if(kolor.equals("czarny")) out.print("checked"); %> /> <label for="kolor_czarny">Czarny</label><br>
                <input type="checkbox" id="dostepnosc" name="dostepnosc" <% if(dostepnosc) out.print("checked"); %> /> <label for="dostepnosc">Pokaż tylko dostępne</label>
                <% if(kategoria != null) { %>
                <input type="hidden" name="kategoria" value="<%= kategoria %>" />
                <% } %>
                <div id="sub"><input type="submit" value="Filtruj" class="button" /></div>
            </form>
        </div>
		<div id="lista">
			<div id="tytul">
				<form action="produkty.jsp" method="GET">
					<label for="sortowanie">SORTOWANIE:</label>
					<select name="sortowanie" id="sortowanie" onChange="this.form.submit()">
						<option value="najnowsze" <% if(sortowanie.equals("najnowsze")) out.print("selected"); %>>Najnowsze</option>
						<option value="cenamaleje" <% if(sortowanie.equals("cenamaleje")) out.print("selected"); %>>Cena malejąco</option>
						<option value="cenarosnie" <% if(sortowanie.equals("cenarosnie")) out.print("selected"); %>>Cena rosnąco</option>
					</select>
                    <% if(kategoria != null) { %>
                        <input type="hidden" name="kategoria" value="<%= kategoria %>" />
                    <% }
                    if(!kolor.equals("")) { %>
                    <input type="hidden" name="kolor" value="<%= kolor %>" />
                    <% }
                    if(dostepnosc) { %>
                    <input type="hidden" name="dostepnosc" value="on" />
                    <% }
                    if(!min.equals("")) { %>
                    <input type="hidden" name="min" value="<%= min %>" />
                    <% }
                    if(!max.equals("")) { %>
                    <input type="hidden" name="max" value="<%= max %>" />
                    <% } %>
				</form>
				<span>STRONA: 1 z 1</span>
			</div>
			<%
            query = "SELECT * FROM produkty";

            if(kategoria != null)
                query += (" WHERE Kategoria = '"+kategoria+"'");

            if(!kolor.equals("")) {
                if(query.contains("WHERE"))
                    query += (" AND Kolor = '"+kolor+"'");
                else
                    query += (" WHERE Kolor = '"+kolor+"'");
            }

            if(dostepnosc) {
                if(query.contains("WHERE"))
                    query += (" AND Ilosc_sztuk > 0");
                else
                    query += (" WHERE Ilosc_sztuk > 0");
            }

            if(!min.equals("")) {
                if(query.contains("WHERE"))
                    query += (" AND Cena > '"+min+"'");
                else
                    query += (" WHERE Cena > '"+min+"'");
            }

            if(!max.equals("")) {
                if(query.contains("WHERE"))
                    query += (" AND Cena < '"+max+"'");
                else
                    query += (" WHERE Cena < '"+max+"'");
            }

			if(sortowanie.equals("najnowsze"))
				query += " ORDER BY ID DESC";
			else if(sortowanie.equals("cenamaleje"))
				query += " ORDER BY cena DESC";
			else if(sortowanie.equals("cenarosnie"))
				query += " ORDER BY cena";

            //Uruchamiamy zapytanie do bazy danych
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) { %>
                <a href="oferta.jsp?id=<%= rs.getString("ID") %>">
                    <div id="lista_produkt">
                        <img src="<%= rs.getString("Zdjęcie") %>">
                        <p id="lista_nazwa"><%=rs.getString("Nazwa")%></p>
                        <p id="lista_cena">
                        <% if (rs.getInt("Ilosc_sztuk") == 0) {
                            %>
                            <span style="background-color: red;" title="Produkt niedostępny"></span>
                            <%
                        }
                        else if(rs.getInt("Ilosc_sztuk") > 0 && rs.getInt("Ilosc_sztuk") < 10) {
                            %>
                            <span style="background-color: orange;" title="Ilość: mało"></span>
                            <%
                        }
                        else {
                            %>
                            <span style="background-color: green;" title="Ilość: dużo"></span>
                            <%
                        }
                        %>
                        <%=rs.getString("Cena")%> PLN
                        </p>
                    </div>
                </a>
                <hr> <%
             }
             conn.close();
			%>
		</div>
        <!-- Stopka i skrypt aktualizowania koszyka -->
        <%@include file="components/footer.jsp" %>
	</body>
</html>