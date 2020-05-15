<%@ page import="config.HomePageConfigFile" %>
<%@ page import="dao.CommentDAO" %>
<%@ page import="dao.UserDAO" %>
<%@ page import="objects.Comment" %>
<%@ page import="objects.Project" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
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
        <h1 class="backend-page-title">Projekt</h1>
        <p class="info-msg"><% if(request.getAttribute("msg") != null){ out.println(request.getAttribute("msg")); request.setAttribute("msg", null); } %></p>
        <%
            if(request.getAttribute("singleProject") != null) { Project singleProject = (Project) request.getAttribute("singleProject");
        %>
        <p>ID: <%= singleProject.getId() %></p>
        <p>Tytuł projektu: <%= singleProject.getTitle() %></p>
        <p>Opis projektu: <%= singleProject.getDescription() %></p>
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