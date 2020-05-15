<%@ page import="objects.Project" %>
<%@ page import="objects.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!-- Nagłówek -->
<jsp:include page="/WEB-INF/user/parts/overall-header.jsp"/>
<!-- Nawigacja sidebar -->
<jsp:include page="/WEB-INF/user/parts/sidebar-menu.jsp"/>
<!-- Kontent -->
<% ArrayList<User> usersList = UserDAO.getUsersList(); %>
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
                <div>Użytkownicy przypisani do projektu: </div><br />
                <% ArrayList<User> usersInProject = UserDAO.getUsersInProject(singleProject.getId());
                    for(User u: usersInProject) {
                        out.print("<div>" +u.getUser_login()+"</div><br>");
                    } %>
                <p class="input-element"><span>Użytkownik: </span> <br /> <span style="font-size: 10px">Zmień użytkowników przypisanych do projektu: </span> <br />
                    <select name="user0" class="element"  title="Użytkownika można wybrać wyłącznie z listy zarejestrowanych użytkowników." required>
                        <option value="-1">---Wybierz użytkownika---</option>
                        <% for (User cat: usersList) { %>
                        <option value="<% out.print(cat.getId()); %>"><% out.print(cat.getUser_login()); %></option>
                        <% } %>
                    </select>
                    <br /> <select name="user1" class="element" title="Użytkownika można wybrać wyłącznie z listy zarejestrowanych użytkowników." required>
                        <option value="-1">---Wybierz użytkownika---</option>
                        <% for (User cat: usersList) { %>
                        <option value="<% out.print(cat.getId()); %>"><% out.print(cat.getUser_login()); %></option>
                        <% } %>
                    </select>
                    <br /> <select name="user2" class="element" title="Użytkownika można wybrać wyłącznie z listy zarejestrowanych użytkowników." required>
                        <option value="-1">---Wybierz użytkownika---</option>
                        <% for (User cat: usersList) { %>
                        <option value="<% out.print(cat.getId()); %>"><% out.print(cat.getUser_login()); %></option>
                        <% } %>
                    </select>
                    <br /><select name="user3" class="element" title="Użytkownika można wybrać wyłącznie z listy zarejestrowanych użytkowników." required>
                        <option value="-1">---Wybierz użytkownika---</option>
                        <% for (User cat: usersList) { %>
                        <option value="<% out.print(cat.getId()); %>"><% out.print(cat.getUser_login()); %></option>
                        <% } %>
                    </select>
                <div class="plus-button"><i class="fas fa-plus-circle"></i></div>
                <div class="minus-button"><i class="fas fa-minus-circle"></i></div>
                </p>
                <p class="input-element submit-element"><input type="submit" value="Dodaj projekt"></p>
            </form>
            <% } %>
        </div>
    </div>
</div>
<!-- Stopka -->
<jsp:include page="/WEB-INF/user/parts/overall-footer.jsp"/>
