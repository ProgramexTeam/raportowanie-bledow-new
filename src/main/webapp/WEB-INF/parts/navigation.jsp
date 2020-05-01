<%@ page import="java.util.HashMap" %><%@ page import="config.GeneralConfigFile" %><%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%  GeneralConfigFile file = new GeneralConfigFile(request.getServletContext());
	HashMap<String, String> configuration = file.getMap() ;%>
	<div class="before-nav">
		<div class="container">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/"><img src="/<% out.print(configuration.get("logo")); %>" alt="<% out.print(configuration.get("logoAlt")); %>" title="<% out.print(configuration.get("logoTitle")); %>"></a>
		</div>
	</div>
<nav class="navbar center navbar-expand-lg navbar-light bg-light">
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse navbar-justify" id="navbarSupportedContent">
		<ul class="navbar-nav">
			<li class="nav-item">
				<a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="${pageContext.request.contextPath}/o-nas">O nas</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="${pageContext.request.contextPath}/kontakt">Kontakt</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="${pageContext.request.contextPath}/login">Logowanie</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="${pageContext.request.contextPath}/register">Rejestracja</a>
			</li>
		</ul>
	</div>
</nav>