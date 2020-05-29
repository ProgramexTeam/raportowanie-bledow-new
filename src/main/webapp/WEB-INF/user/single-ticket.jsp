<%@ page import="config.HomePageConfigFile" %>
<%@ page import="dao.CommentDAO" %>
<%@ page import="dao.UserDAO" %>
<%@ page import="objects.Comment" %>
<%@ page import="objects.Project" %>
<%@ page import="objects.Ticket" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.ProjectDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Nagłówek -->
<jsp:include page="/WEB-INF/user/parts/overall-header.jsp"/>
<!-- Nawigacja sidebar -->
<jsp:include page="/WEB-INF/user/parts/sidebar-menu.jsp"/>
<!-- Kontent -->
<%  HomePageConfigFile file = new HomePageConfigFile(request.getServletContext());
    HashMap<String, String> configuration = file.getMap();
    Ticket p = null;
    if(request.getAttribute("ticket")!=null) {
        p = (Ticket) request.getAttribute("ticket");
    } %>

<div class="content ticket-manager">
    <div class="content-inside">
        <p class="info-msg"><% if(request.getAttribute("msg") != null){ out.println(request.getAttribute("msg")); request.setAttribute("msg", null); } %></p>
        <%
            if(request.getAttribute("singleTicket") != null) { Ticket singleTicket = (Ticket) request.getAttribute("singleTicket");
        %>
        <h1 class="backend-page-title"><i class="fas fa-tag"></i> Ticket o nr <%= singleTicket.getId() %></h1>
        <p><b>ID:</b> <%= singleTicket.getId() %></p>
        <p><b>Autor ticketu:</b> <%= UserDAO.getSingleUserLogin(singleTicket.getAuthor_id()) %></p>
        <p><b>Tytuł ticketu:</b> <%= singleTicket.getTitle() %></p>
        <p><b>Opis ticketu:</b> <%= singleTicket.getDescription() %></p>
        <p><b>Projekt:</b> <%= ProjectDAO.getSingleProjectName(singleTicket.getProject_id()) %></p>
        <p><b>Status:</b> Otwarty</p>
        <hr>
        <div id="comments">
            <h3>Komentarze:</h3>
            <form class="input-element" method="post" action="${pageContext.request.contextPath}/user/ticket-manager/single-ticket">
                <textarea name="comment" placeholder="Dodaj komentarz..." maxlength="500" required></textarea><br>
                <p class="input-element submit-element"><input type="submit" value="Wyślij"></p>
            </form>
            <%
                List<Comment> comments = CommentDAO.getComments("ticket", singleTicket.getId());
                for (Comment comment: comments) { %>
            <p>Użytkownik <b><%= UserDAO.getSingleUserLogin(comment.getUser_ID()) %></b> dnia <i><%=comment.getDate() %></i> napisał:</p>
            <p><%= comment.getText() %></p>
            <% } %>
        </div>
        <% } %>
    </div>
</div>