<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="Navbar">
    <a href="${pageContext.request.contextPath}/user/project-manager" title="Przejdź do managera projektów">
        <div class="Navbar__Link">
            <i class="fas fa-users"></i> Manager projektów
        </div>
    </a>
    <a href="${pageContext.request.contextPath}/user/ticket-manager" title="Przejdź do managera ticketów">
        <div class="Navbar__Link">
            <i class="fa fa-tag"></i> Manager ticketów
        </div>
    </a>
    <nav class="Navbar__Items Navbar__Items--right">
        <div class="Navbar__Link display-user-name">
            <div>
                <i class="fas fa-user"></i> <% out.print(session.getAttribute("user_login").toString()); %>
            </div>
            <div class="dropdown-content">
                <ul>
                    <a href="${pageContext.request.contextPath}/logout" title="Wyloguj się z konta"><li><i class="fas fa-sign-out-alt"></i> Wyloguj</li></a>
                </ul>
            </div>
        </div>
    </nav>
</div>