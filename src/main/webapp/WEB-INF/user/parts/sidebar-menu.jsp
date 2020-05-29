<%@ page contentType="text/html;charset=UTF-8" %>
<div class="sidebar-left">
    <div class="backend-navigation">
        <ul class="backend-navigation-list">
            <a href="${pageContext.request.contextPath}/" class="backend-navigation-link" title="Powrót do strony głównej">
                <li class="backend-navigation-element"><i class="fas fa-home"></i> Home</li>
            </a>
            <div class="users-link">
                <a href="${pageContext.request.contextPath}/user/project-manager" class="backend-navigation-link" title="Przejdź do menadżera projektów">
                    <li class="backend-navigation-element"><i class="fas fa-users"></i> Projekty</li>
                </a>
                <% String user_role = null;
                    if (session.getAttribute("user_role") != null) {
                    user_role = session.getAttribute("user_role").toString();
                    if(user_role.equals("developer") || user_role.equals("ADMIN")){%>
                <ul class="dropdown users-dropdown">
                    <a href="${pageContext.request.contextPath}/user/project-manager/add-project" class="backend-navigation-link" title="Dodaj nowy projekt">
                        <li><i class="fas fa-user-plus"></i> Dodaj nowy projekt</li>
                    </a>
                </ul>
                   <% }}%>
            </div>
            <% if(user_role.equals("ADMIN")){%>
            <a href="${pageContext.request.contextPath}/user/general-conf" class="backend-navigation-link" title="Ustawienia główne">
                <li class="backend-navigation-element"><i class="fas fa-cogs"></i> Ustawienia główne</li>
            </a>
            <% } %>
            <div class="products-link">
                <a href="${pageContext.request.contextPath}/user/ticket-manager" class="backend-navigation-link" title="Przejdź do menadżera ticketów">
                    <li class="backend-navigation-element"><i class="fas fa-tag"></i></i> Tickety</li>
                </a>
               <% if(!user_role.equals("analyst")){%>
                <ul class="dropdown ticket-dropdown">
                    <a href="${pageContext.request.contextPath}/user/ticket-manager/add-ticket" class="backend-navigation-link" title="Dodaj nowy ticket">
                        <li><i class="fas fa-tags"></i> Dodaj nowy ticket</li>
                    </a>
                </ul>
                <% }%>
            </div>
                <% if(user_role.equals("ADMIN")){%>
            <a href="${pageContext.request.contextPath}/user/homepage-editor" class="backend-navigation-link" title="Edytor strony głównej">
                <li class="backend-navigation-element"><i class="fas fa-cogs"></i> Homepage Editor</li>
            </a>
            <div class="project-link">
                <a href="${pageContext.request.contextPath}/user/user-manager" class="backend-navigation-link" title="Menadżer kategorii">
                    <li class="backend-navigation-element"><i class="fa fa-tag" aria-hidden="true"></i> Menadżer użytkowników</li>
                </a>
            </div>
            <% } %>
            <div class="project-link">
                <a href="${pageContext.request.contextPath}/logout" class="backend-navigation-link" title="Wyloguj się z konta">
                    <li class="backend-navigation-element"><i class="fas fa-sign-out-alt"></i> Wyloguj</li>
                </a>
            </div>
        </ul>
    </div>
</div>