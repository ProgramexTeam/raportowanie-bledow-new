<%@ page import="java.util.HashMap" %><%@ page import="config.GeneralConfigFile" %><%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  GeneralConfigFile file = new GeneralConfigFile(request.getServletContext());
  HashMap<String, String> configuration = file.getMap() ;%>
    <div class="footer">
      <!-- Hook after <div class="footer"> -->
      <% out.print(configuration.get("hookAfterFooter")); %>

      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <div class="logo">
              <a class="navbar-brand" href="${pageContext.request.contextPath}/"><img src="/<% out.print(configuration.get("logo")); %>" alt="<% out.print(configuration.get("logoAlt")); %>" title="<% out.print(configuration.get("logoTitle")); %>"></a>
            </div>
          </div>
        </div>
      </div>

      <!-- Hook before end of <div class="footer"> -->
      <% out.print(configuration.get("hookBeforeFooterEnd")); %>
    </div>
    <!-- Copyrights -->
	<jsp:include page="/WEB-INF/parts/copyrights.jsp"/>
    <!-- Załączone .js -->
    <jsp:include page="/WEB-INF/parts/footer-js.jsp"/>
    </body>
</html>