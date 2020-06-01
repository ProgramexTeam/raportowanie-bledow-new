<%@ page import="java.util.HashMap" %>
<%@ page import="config.GeneralConfigFile" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% GeneralConfigFile file = new GeneralConfigFile(request.getServletContext());
    HashMap<String, String> configuration = file.getMap();%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Paweł Górski, Maciej Korcz, Michał Ochniowski, Sylwester Wrzesiński">
    <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700" rel="stylesheet">

    <title><% out.print(configuration.get("seoTitle")); %></title>
    <meta name="description" content="<% out.print(configuration.get("seoDesc")); %>">

    <%
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("user_login"))
                    session.setAttribute("user_login", cookie.getValue());
                else if(cookie.getName().equals("user_role"))
                    session.setAttribute("user_role", cookie.getValue());
                else if(cookie.getName().equals("user_email"))
                    session.setAttribute("user_email", cookie.getValue());
                else if(cookie.getName().equals("user_id"))
                    session.setAttribute("user_id", cookie.getValue());
            }
        }
    %>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Pliki CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/fa/css/all.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/additional.jsp">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/flex-slider.css">

    <%if (session.getAttribute("user_id") != null) {
                %> <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bar-user.css"> <%
        }
    %>
    <% if (request.getAttribute("isHomepage") == "is") { %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/additionalHomepage.jsp">
    <% } %>

    <!-- Hook before </head> -->
    <% out.print(configuration.get("hookBeforeHeadEnd")); %>
</head>
<body>
    <!-- Hook after <body> -->
    <% out.print(configuration.get("hookAfterBody")); %>

    <%  if (session.getAttribute("user_id") != null){ %>
    <jsp:include page="/WEB-INF/parts/bar-user.jsp"/>
    <% } %>
