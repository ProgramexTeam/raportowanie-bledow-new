<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="Navbar">
	<a href="${pageContext.request.contextPath}/user" title="Przejdź do panelu użytkownika">
		<div class="Navbar__Link">
			<i class="fas fa-igloo"></i> Panel użytkownika
		</div>
	</a>
	<nav class="Navbar__Items Navbar__Items--right">
		<div class="Navbar__Link display-user-name">
			<div>
				<i class="fas fa-user"></i> <% out.print(session.getAttribute("user_login").toString()); %>
			</div>
			<div class="dropdown-content">
				<ul>
					<a href="${pageContext.request.contextPath}/profil" title="Wyświetl ustawienia Twojego profilu"><li><i class="fas fa-user-cog"></i> Ustawienia</li></a>
					<a href="${pageContext.request.contextPath}/logout" title="Wyloguj się z konta"><li><i class="fas fa-sign-out-alt"></i> Wyloguj</li></a>
				</ul>
			</div>
		</div>
	</nav>
</div>