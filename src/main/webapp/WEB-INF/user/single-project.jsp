<%@ page import="config.HomePageConfigFile" %>
<%@ page import="dao.CommentDAO" %>
<%@ page import="dao.UserDAO" %>
<%@ page import="objects.Comment" %>
<%@ page import="objects.Project" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="objects.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Nagłówek -->
<jsp:include page="/WEB-INF/user/parts/overall-header.jsp"/>
<!-- Nawigacja sidebar -->
<jsp:include page="/WEB-INF/user/parts/sidebar-menu.jsp"/>
<!-- Kontent -->
<%  HomePageConfigFile file = new HomePageConfigFile(request.getServletContext());
    HashMap<String, String> configuration = file.getMap();
    Project p = null;
    if(request.getAttribute("project")!=null) {
        p = (Project) request.getAttribute("project");
    } %>

<div class="content user-manager">
    <div class="content-inside">
        <p class="info-msg"><% if(request.getAttribute("msg") != null){ out.println(request.getAttribute("msg")); request.setAttribute("msg", null); } %></p>
        <%
            if(request.getAttribute("singleProject") != null) { Project singleProject = (Project) request.getAttribute("singleProject");
        %>
        <h1 class="backend-page-title"><i class="fas fa-users"></i></i> Projekt o nr <%= singleProject.getId() %></h1>
        <p><b>ID:</b> <%= singleProject.getId() %></p>
        <p><b>Autor projektu:</b> <%= UserDAO.getSingleUserLogin(singleProject.getAuthor_id()) %></p>
        <p><b>Tytuł projektu:</b> <%= singleProject.getTitle() %></p>
        <p><b>Opis projektu:</b> <%= singleProject.getDescription() %></p>
        <p><b>Przypisani użytkownicy:</b> <% ArrayList<User> usersInProject = UserDAO.getUsersInProject(singleProject.getId());
                for(User u: usersInProject) {
                out.print("<div>" +u.getUser_login()+"</div><br>");
                } %></p>
        <hr>
        <div id="comments">
            <h3>Komentarze:</h3>
            <form class="input-element" method="post" action="${pageContext.request.contextPath}/user/project-manager/single-project">
                <textarea name="comment" placeholder="Dodaj komentarz..." maxlength="500" required></textarea><br>
                <p class="input-element submit-element"><input type="submit" value="Wyślij"></p>
            </form>
            <%
                List<Comment> comments = CommentDAO.getComments("project", singleProject.getId());
                for (Comment comment: comments) { %>
                    <p>Użytkownik <b><%= UserDAO.getSingleUserLogin(comment.getUser_ID()) %></b> dnia <i><%=comment.getDate() %></i> napisał:</p>
                    <p><%= comment.getText() %></p>
                <% } %>
        </div>
        <% } %>
    </div>
</div>