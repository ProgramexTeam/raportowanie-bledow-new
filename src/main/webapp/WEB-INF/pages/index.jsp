<%@ page import="config.HomePageConfigFile" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="dao.TicketDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="objects.Ticket" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/parts/overall-header.jsp"/>
<jsp:include page="/WEB-INF/parts/sloganbar.jsp"/>
<!-- Nawigacja -->
<jsp:include page="/WEB-INF/parts/navigation.jsp"/>
<%  HomePageConfigFile file = new HomePageConfigFile(request.getServletContext());
    HashMap<String, String> configuration = file.getMap() ;%>
<!-- Początek Bannera -->
<div class="banner" title="<% out.print(configuration.get("sliderBackgroundTitle")); %>">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="caption">
                    <h2><% out.print(configuration.get("sliderHeader")); %></h2>
                    <div class="line-dec"></div>
                    <p><% out.print(configuration.get("sliderText")); %></p>
                    <% if(session.getAttribute("user_id") == null) {
                     if(configuration.get("sliderButton").equals("on")) { %>
                        <div class="main-button">
                            <a href="<% out.print(configuration.get("sliderButtonLink"));%>"
                               ref="<% out.print(configuration.get("sliderButtonLinkFollow"));%>"
                               target="<% out.print(configuration.get("sliderButtonLinkTarget"));%>"><% out.print(configuration.get("sliderButtonText"));%></a>
                        </div>
                    <% } }%>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Koniec Bannera -->

<!-- Stopka -->
<jsp:include page="/WEB-INF/parts/overall-footer.jsp"/>