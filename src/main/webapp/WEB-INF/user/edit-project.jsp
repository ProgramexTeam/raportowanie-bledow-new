<%@ page import="dao.CommentDAO" %>
<%@ page import="objects.Comment" %>
<%@ page import="objects.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!-- Nagłówek -->
<jsp:include page="/WEB-INF/user/parts/overall-header.jsp"/>
<!-- Nawigacja sidebar -->
<jsp:include page="/WEB-INF/user/parts/sidebar-menu.jsp"/>
<!-- Kontent -->
<div class="content user-manager">
    <div class="content-inside">
        <h1 class="backend-page-title"> Menadżer projektów - edytuj projekt</h1>
        <p class="info-msg"><% if(request.getAttribute("msg") != null){ out.println(request.getAttribute("msg")); request.setAttribute("msg", null); } %></p>
        <div class="form-container">
            <% if(request.getAttribute("singleProject") != null) { Project singleProject = (Project) request.getAttribute("singleProject"); %>
            <form method="post" action="${pageContext.request.contextPath}/user/project-manager/edit-project">
                <div class="input-row">
                    <p class="input-element"><span>ID:</span> <br /> <input style="max-width: 70px" type="text" name="projectId" value="<% out.print(singleProject.getId()); %>" title="Id nie może zostać zmienione" readonly></p><br />
                    <p class="input-element"><span>Tytuł projektu:</span> <br /> <span style="font-size: 10px">Nazwa projektu musi zawierać minimum 3 znaki.</span> <br />
                        <input type="text" name="project_title" pattern=".{3,}" value="<% out.print(singleProject.getTitle()); %>" title="Nazwa kategorii musi zawierać minimum 3 znaki" required>
                    </p><br />
                    <p class="input-element"><span>Opis projektu: </span> <br />
                        <textarea name="project_description" required><% out.print(singleProject.getDescription()); %></textarea>
                    </p>
                </div>
                <p class="input-element submit-element"><input type="submit" value="Zapisz zmiany"></p>
            </form>
            <% } %>
        </div>
    </div>
</div>
<!-- Stopka -->
<jsp:include page="/WEB-INF/user/parts/overall-footer.jsp"/>