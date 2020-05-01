<%@ page import="objects.Ticket" %>
<%@ page import="dao.TicketDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="config.HomePageConfigFile" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/parts/overall-header.jsp"/>
<jsp:include page="/WEB-INF/parts/sloganbar.jsp"/>
<!-- Nawigacja -->
<jsp:include page="/WEB-INF/parts/navigation.jsp"/>

<!-- Początek zawartości strony -->
<%  HomePageConfigFile file = new HomePageConfigFile(request.getServletContext());
    HashMap<String, String> configuration = file.getMap();
    Ticket p = null;
    if(request.getAttribute("ticket")!=null) {
        p = (Ticket) request.getAttribute("ticket");
    } %>

<div class="single-ticket">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="section-heading">
                    <div class="line-dec"></div>
                    <h1><% out.print(p.getTitle());%></h1>
                  </div>
            </div>
            <div class="col-md-6">
                <div class="right-content">
                    <h4><% out.print(p.getTitle()); %></h4>
                    <div class="add-to-cart-form">
                        <form action="${pageContext.request.contextPath}/portal/ticket?id=<% out.print(p.getId()); %>" method="post">
                        </form>
                    </div>
                    <div class="down-content">
                        <div class="categories">
                            <h6>Kategoria: <span>
                                <% out.print("<a href=\"#\">" + p.getProject_id() + "</a>"); %>
                            </span>
                            </h6>
                        </div>
                        <p><% out.print(p.getDescription()); %></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Koniec zawartości strony -->

<!-- Początek podobnych pozycji -->
<div class="featured-items">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="section-heading">
                    <div class="line-dec"></div>
                    <h1><% out.print(configuration.get("featuredHeader")); %></h1>
                </div>
            </div>
            <div class="col-md-12">
                <div class="owl-carousel owl-theme">
                    <%  ArrayList<Ticket> lista = TicketDAO.getFeaturedProductsList(10);
                        for (Ticket produkt: lista) { %>
                    <a href="${pageContext.request.contextPath}/portal/ticket?id=<% out.print(produkt.getId()); %>">
                    </a>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Koniec podobnych pozycji -->

<!-- Stopka -->
<jsp:include page="/WEB-INF/parts/overall-footer.jsp"/>