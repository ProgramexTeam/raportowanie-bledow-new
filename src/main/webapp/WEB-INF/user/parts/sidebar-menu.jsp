<%@ page contentType="text/html;charset=UTF-8" %>
<div class="sidebar-left">
    <div class="backend-navigation">
        <ul class="backend-navigation-list">
            <a href="${pageContext.request.contextPath}/" class="backend-navigation-link" title="Powrót do strony głównej">
                <li class="backend-navigation-element"><i class="fas fa-home"></i> Home</li>
            </a>
            <a href="${pageContext.request.contextPath}/user" class="backend-navigation-link" title="Powrót do strony głównej backendu">
                <li class="backend-navigation-element"><i class="fas fa-igloo"></i> Backend Home</li>
            </a>
            <div class="users-link">
                <a href="${pageContext.request.contextPath}/user/user-manager" class="backend-navigation-link" title="Przejdź do menadżera użytkowników">
                    <li class="backend-navigation-element"><i class="fas fa-users"></i> Użytkownicy</li>
                </a>
                <ul class="dropdown users-dropdown">
                    <a href="${pageContext.request.contextPath}/user/user-manager/add-user" class="backend-navigation-link" title="Dodaj nowego użytkownika">
                        <li><i class="fas fa-user-plus"></i> Dodaj nowego użytkownika</li>
                    </a>
                </ul>
            </div>
            <a href="${pageContext.request.contextPath}/user/general-conf" class="backend-navigation-link" title="Ustawienia główne">
                <li class="backend-navigation-element"><i class="fas fa-cogs"></i> Ustawienia główne</li>
            </a>
            <div class="products-link">
                <a href="${pageContext.request.contextPath}/user/ticket-manager" class="backend-navigation-link" title="Przejdź do menadżera produktów">
                    <li class="backend-navigation-element"><i class="fas fa-shopping-cart"></i> Produkty</li>
                </a>
                <ul class="dropdown ticket-dropdown">
                    <a href="${pageContext.request.contextPath}/user/ticket-manager/add-ticket" class="backend-navigation-link" title="Dodaj nowy produkt">
                        <li><i class="fas fa-cart-plus"></i> Dodaj nowy produkt</li>
                    </a>
                </ul>
            </div>
            <a href="${pageContext.request.contextPath}/user/homepage-editor" class="backend-navigation-link" title="Edytor strony głównej">
                <li class="backend-navigation-element"><i class="fas fa-cogs"></i> Homepage Editor</li>
            </a>
            <div class="project-link">
                <a href="${pageContext.request.contextPath}/user/project-manager" class="backend-navigation-link" title="Menadżer kategorii">
                    <li class="backend-navigation-element"><i class="fa fa-tag" aria-hidden="true"></i> Menadżer kategorii</li>
                </a>
                <ul class="dropdown project-dropdown">
                    <a href="${pageContext.request.contextPath}/user/project-manager/add-project" class="backend-navigation-link" title="Dodaj nową kategorię">
                        <li><i class="fas fa-tags"></i> Dodaj nową kategorię</li>
                    </a>
                </ul>
            </div>
        </ul>
    </div>
</div>