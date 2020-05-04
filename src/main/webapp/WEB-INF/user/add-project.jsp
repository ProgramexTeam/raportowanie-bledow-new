<%@ page import="objects.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!-- Nagłówek -->
<jsp:include page="/WEB-INF/user/parts/overall-header.jsp"/>
<!-- Nawigacja sidebar -->
<jsp:include page="/WEB-INF/user/parts/sidebar-menu.jsp"/>
<!-- Kontent -->
<div class="content user-manager">
    <div class="content-inside">
        <h1 class="backend-page-title"> Menadżer projektów - dodaj projekt</h1>
        <p class="info-msg"><% if(request.getAttribute("msg") != null){ out.println(request.getAttribute("msg")); request.setAttribute("msg", null); } %></p>
        <div class="form-container">
            <form method="post" action="${pageContext.request.contextPath}/user/project-manager/add-project">
                <div class="input-row">
                    <p class="input-element"><span>Tytuł projektu:</span> <br /> <span style="font-size: 8px">Nazwa projektu musi zawierać minimum 3 znaki.</span> <br />
                        <input type="text" name="title" pattern=".{3,}" title="Tytuł musi zawierać minimum 3 znaki" required></p>
                </div>
                <div class="input-row">
                    <p class="input-element" style="width:100%"><span>Opis:</span> <br /><textarea name="description"></textarea></p>
                </div>
                <% ArrayList<User> usersList = UserDAO.getUsersList(); %>
                <p class="input-element"><span>Użytkownik: </span> <br /> <span style="font-size: 8px">Użytkownika można wybrać wyłącznie z listy zarejestrowanych użytkowników.</span> <br />
                    <select name="user0" title="Użytkownika można wybrać wyłącznie z listy zarejestrowanych użytkowników." required>
                        <option value="-1">---Wybierz użytkownika---</option>
                        <% for (User cat: usersList) { %>
                        <option value="<% out.print(cat.getId()); %>"><% out.print(cat.getUser_login()); %></option>
                        <% } %>
                    </select>
                    <br /> <select name="user1" title="Użytkownika można wybrać wyłącznie z listy zarejestrowanych użytkowników." required>
                        <option value="-1">---Wybierz użytkownika---</option>
                        <% for (User cat: usersList) { %>
                        <option value="<% out.print(cat.getId()); %>"><% out.print(cat.getUser_login()); %></option>
                        <% } %>
                    </select>
                    <br /> <select name="user2" title="Użytkownika można wybrać wyłącznie z listy zarejestrowanych użytkowników." required>
                        <option value="-1">---Wybierz użytkownika---</option>
                        <% for (User cat: usersList) { %>
                        <option value="<% out.print(cat.getId()); %>"><% out.print(cat.getUser_login()); %></option>
                        <% } %>
                    </select>
                    <br /><select name="user3" title="Użytkownika można wybrać wyłącznie z listy zarejestrowanych użytkowników." required>
                        <option value="-1">---Wybierz użytkownika---</option>
                        <% for (User cat: usersList) { %>
                        <option value="<% out.print(cat.getId()); %>"><% out.print(cat.getUser_login()); %></option>
                        <% } %>
                    </select>
                    <div class="plus-button"><i class="fas fa-plus-square"></i></div>
                    <div class="minus-button"><i class="fas fa-minus-square"></i></div>
                </p>
                <p class="input-element submit-element"><input type="submit" value="Dodaj projekt"></p>
            </form>
            <p style="color: red; font-weight: bold">
                <%
                    if(request.getAttribute("msg")!=null){
                        out.println(request.getAttribute("msg"));
                    }
                %>
            </p>
        </div>
    </div>
</div>
<!-- Stopka -->
<jsp:include page="/WEB-INF/user/parts/overall-footer.jsp"/>