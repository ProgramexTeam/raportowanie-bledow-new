<%@ page import="objects.Ticket" %>
<%@ page import="objects.Project" %>
<%@ page import="dao.ProjectDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!-- Nagłówek -->
<jsp:include page="/WEB-INF/user/parts/overall-header.jsp"/>
<!-- Nawigacja sidebar -->
<jsp:include page="/WEB-INF/user/parts/sidebar-menu.jsp"/>
<!-- Kontent -->
<div class="content ticket-manager">
    <div class="content-inside">
        <h1 class="backend-page-title"><i class="fas fa-shopping-cart"></i> Menadżer ticket - edytuj ticket</h1>
        <p class="info-msg"><% if(request.getAttribute("msg") != null){ out.println(request.getAttribute("msg")); request.setAttribute("msg", null); } %></p>
        <div class="form-container">
            <% if(request.getAttribute("singleTicket") != null) { Ticket singleTicket = (Ticket) request.getAttribute("singleTicket"); %>
            <form method="post" action="${pageContext.request.contextPath}/user/ticket-manager/edit-ticket">
                <div class="input-row">
                    <p class="input-element"><span>ID:</span> <br /> <input style="max-width: 70px" type="text" name="ticketId" value="<% out.print(singleTicket.getId()); %>" title="Id nie może zostać zmienione" readonly></p>
                    <p class="input-element"><span>Nazwa ticketu:</span> <br /> <span style="font-size: 10px">Nazwa ticketu musi zawierać minimum 3 znaki.</span> <br />
                        <input type="text" name="title" pattern=".{3,}" value="<% out.print(singleTicket.getTitle()); %>" title="Nazwa ticketu musi zawierać minimum 3 znaki" required></p>
                    <% ArrayList<Project> projectList = ProjectDAO.getProjectsList(); %>
                    <p class="input-element"><span>Projekt: </span> <br /> <span style="font-size: 8px">Projekt można wybrać wyłącznie z listy utworzonych projektów. Jeśli chcesz użyć projektu, który nie znajduje się na liście przejdź do menadżera projektów.</span> <br />
                        <select name="project" title="Projekt można wybrać wyłącznie z listy utworzonych projektów. Jeśli chcesz użyć projektu, który nie znajduje się na liście przejdź do menadżera projektów." required>
                        <% for (Project prj: projectList) { %>
                            <option value="<% out.print(prj.getId()); %>" <%
                                if(prj.getTitle().equals(singleTicket.getStatus()))
                                    out.print("selected");
                            %>><% out.print(prj.getTitle()); %></option>
                            <% } %>
                        </select>
                    </p>
                </div>
                <div class="input-row">
                    <p class="input-element" style="min-width: 50%"><span>Opis:</span> <br />
                        <textarea name="description" style="width:100%"><% if(singleTicket.getDescription()!=null) singleTicket.getDescription(); %></textarea>
                </div>
                <div class="input-row">
                    <p><span>Status:</span><br />
                        <select name="status">
                            <option value="Otwarty">Otwarty</option>
                            <option value="W trakcie naprawy">W trakcie naprawy</option>
                            <option value="Naprawiony">Naprawiony</option>
                            <option value="Odrzucony">Odrzucony</option>
                            <option value="Oczekujący na retestowanie">Oczekujący na retestowanie</option>
                        </select>
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