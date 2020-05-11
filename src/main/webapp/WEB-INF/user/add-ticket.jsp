<%@ page import="java.util.ArrayList" %>
<%@ page import="objects.Project" %>
<%@ page import="dao.ProjectDAO" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!-- Nagłówek -->
<jsp:include page="/WEB-INF/user/parts/overall-header.jsp"/>
<!-- Nawigacja sidebar -->
<jsp:include page="/WEB-INF/user/parts/sidebar-menu.jsp"/>
<!-- Kontent -->
<div class="content ticket-manager">
    <div class="content-inside">
        <h1 class="backend-page-title"> Dodawanie ticketów</h1>
        <p class="info-msg"><% if(request.getAttribute("msg") != null){ out.println(request.getAttribute("msg")); request.setAttribute("msg", null); } %></p>
        <div class="form-container">
            <form method="post" action="${pageContext.request.contextPath}/user/ticket-manager/add-ticket">
                <div class="input-row" style="width: 100%">
                    <p class="input-element"><span>Tytuł ticketu:</span> <br /> <span style="font-size: 10px">Nazwa ticketu musi zawierać minimum 3 znaki.</span> <br />
                        <input type="text" name="title" pattern=".{3,}" title="Nazwa ticketu musi zawierać minimum 3 znaki" required></p>
                    <% ArrayList<Project> projectList = ProjectDAO.getProjectsList(); %>
                    <p class="input-element"><span>Projekt: </span> <br /> <span style="font-size: 10px">Projekt można wybrać wyłącznie z listy utworzonych projektów. Jeśli chcesz użyć projektu, który nie znajduje się na liście przejdź do menadżera projektów.</span> <br />
                        <select name="project_id" title="Projekt można wybrać wyłącznie z listy utworzonych projektów. Jeśli chcesz użyć projektu, który nie znajduje się na liście przejdź do menadżera projektów." required>
                            <% for (Project cat: projectList) { %>
                                <option value="<% out.print(cat.getId()); %>"><% out.print(cat.getTitle()); %></option>
                            <% } %>
                        </select>
                    </p>
                </div>
                <p class="input-element"><span>Pliki: </span> <br /> <span style="font-size: 10px">Tutaj możesz załączyć pliki (maksymalnie 4) do ticketu:</span> <br />
                    <input type="file" name="file0" class="element" title="Kliknij tutaj by wybrać plik." required><br />
                    <input type="file" name="file1" class="element" title="Kliknij tutaj by wybrać plik." required><br />
                    <input type="file" name="file2" class="element" title="Kliknij tutaj by wybrać plik." required><br />
                    <input type="file" name="file3" class="element" title="Kliknij tutaj by wybrać plik." required>
                <div class="plus-button"><i class="fas fa-plus-square"></i></div>
                <div class="minus-button"><i class="fas fa-minus-square"></i></div>
                </p>
                <div class="input-row">
                    <p class="input-element" style="width:100%"><span>Opis:</span> <br /><textarea name="description"></textarea></p>
                </div>
                <div class="input-row">
                    <p><span>Rola:</span><br />
                        <select name="status">
                            <option value="Otwarty">Otwarty</option>
                            <option value="W trakcie naprawy">W trakcie naprawy</option>
                            <option value="Naprawiony">Naprawiony</option>
                            <option value="Odrzucony">Odrzucony</option>
                            <option value="Oczekujący na retestowanie">Oczekujący na retestowanie</option>
                        </select>
                    </p>
                </div>
                <div class="input-row">
                    <p class="input-element submit-element"><input type="submit" value="Dodaj ticket"></p>
                </div>
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