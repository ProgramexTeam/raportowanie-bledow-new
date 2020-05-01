<%@ page import="java.util.ArrayList" %>
<%@ page import="objects.Ticket" %>
<%@ page import="dao.TicketDAO" %>
<%@ page import="objects.Project" %>
<%@ page import="dao.ProjectDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<jsp:include page="/WEB-INF/parts/overall-header.jsp"/>
    <jsp:include page="/WEB-INF/parts/sloganbar.jsp"/>
    <!-- Nawigacja -->
    <jsp:include page="/WEB-INF/parts/navigation.jsp"/>
    <!-- Początek produktów -->
    <div class="featured-page">
      <div class="container">
        <div class="row">
          <div class="col-md-3 col-sm-12">
            <div class="section-heading">
              <div class="line-dec"></div>
              <h1 id="PageTitle">Portal</h1>
            </div>
          </div>
          <div class="col-md-9 col-sm-12">
            <div id="filters" class="button-group">
                <input type="hidden" id="ListOrder" name="ListOrder" value="1">
                <button class="btn btn-primary order-button" onclick="changeListOrder(1); setActive(this);">Od A do Z</button>
                <button class="btn btn-primary order-button" onclick="changeListOrder(2); setActive(this);">Od Z do A</button>
                <button class="btn btn-primary order-button" onclick="changeListOrder(3); setActive(this);">Od Najnowszego</button>
                <button class="btn btn-primary order-button" onclick="changeListOrder(4); setActive(this);">Od Najstarszego</button>
                <button class="btn btn-primary order-button" onclick="changeListOrder(5); setActive(this);">Od Najtańszego</button>
                <button class="btn btn-primary order-button" onclick="changeListOrder(6); setActive(this);">Od Najdroższego</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="featured container no-gutter">
        <div class="row">
            <div class="col-md-3">
                <div class="filters-sidebar">
                    <div class="filters-container">
                        <h1>Filtry</h1>
                        <p class="filter-row search-input"><input id="searchByProductName" type="text" value="<% if(request.getAttribute("searchByProductName")!=null) out.print(request.getAttribute("searchByProductName"));%>" name="searchByProductName" placeholder="Szukaj" onfocusout="checkIfProperSearchValue(this);"></p>
                        <label class="filter-row price-filter">Cena od <input id="minPrice" type="number" min="0" value="<% if(request.getAttribute("minPrice")!=null) { out.print(request.getAttribute("minPrice")); } else { out.print("0.00");};%>" step="0.01"  name="minPrice" onfocusout="checkIfProperPriceValue(this);"></label>
                        <label class="filter-row price-filter">do <input id="maxPrice" type="number" min="0" value="<% if(request.getAttribute("maxPrice")!=null) { out.print(request.getAttribute("maxPrice")); } else { out.print("0.00");};%>" step="0.01"  name="maxPrice" onfocusout="checkIfProperPriceValue(this);"></label>
                        <label class="filter-row checkbox-paragraph">Jest na Stanie <input id="isInStock" type="checkbox" name="isInStock" value="true" <% if(request.getAttribute("isInStock")!=null) { if(request.getAttribute("isInStock").equals("true")) { out.print("checked"); } } %>><span class="checkmark"></span></label>
                        <label class="filter-button-container"><button class="filter-button" type="button" onclick="sendAjax()" >Filtruj</button></label>
                    </div>
                    <div class="categories-container">
                        <h1>Kategorie</h1>
                        <input type="hidden" id="chosenCategory" name="chosenCategory" value="">
                        <ul class="categories-list">
                            <li class="project-button project-button-0 all-categories"><button onclick="changeCategory(null, 'project-button-0')">Wszystko</button></li>
                            <% ArrayList<Project> projectList = ProjectDAO.getCategoriesList();
                                for(Project project : projectList) { %>
                            <li class="project-button project-button-<% out.print(project.getId()); %>"><button onclick="changeCategory('<% out.print(project.getCategoryURL()); %>', 'project-button-<% out.print(project.getId()); %>')"><% out.print(project.getCategoryName()); %></button></li>
                            <% } %>
                        </ul>
                    </div>
                    <div class="layout-container">
                        <h1>Układ produktów</h1>
                        <input type="hidden" id="ChosenLayout" value="2">
                        <div class="styles-row">
                            <button class="style-button col-md-4" id="style-1" onclick="changeStyle(1, 'style-1');">
                                <img src="/assets/images/simple.png" alt="Style - simple">
                                <div class="tooltip-content">
                                    <div class="tooltip-header">
                                        <img src="/assets/images/simple.png" alt="Style - simple">
                                    </div>
                                    <div class="tooltip-body">
                                        <p>Styl prosty - jedna kolumna produktów wraz z opisem.</p>
                                    </div>
                                </div>
                            </button>
                            <button class="style-button col-md-4" id="style-2" onclick="changeStyle(2, 'style-2');">
                                <img src="/assets/images/grid-3-elements.png" alt="Style - grid 3 columns">
                                <div class="tooltip-content">
                                    <div class="tooltip-header">
                                        <img src="/assets/images/grid-3-elements.png" alt="Style - grid 3 columns">
                                    </div>
                                    <div class="tooltip-body">
                                        <p>Siatka - trzy kolumny. Brak opisów produktów.</p>
                                    </div>
                                </div>
                            </button>
                            <button class="style-button col-md-4" id="style-3" onclick="changeStyle(3, 'style-3');">
                                <img src="/assets/images/grid-4-elements.png" alt="Style - grid 4 columns">
                                <div class="tooltip-content">
                                    <div class="tooltip-header">
                                        <img src="/assets/images/grid-4-elements.png" alt="Style - grid 4 columns">
                                    </div>
                                    <div class="tooltip-body">
                                        <p>Siatka - cztery kolumny. Brak opisów produktów.</p>
                                    </div>
                                </div>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div id="ListOfProducts" class="col-md-9 row">
            </div>
        </div>
    </div>

    <div class="page-navigation">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <ul>
                        <label>Wyników na stronę:
                            <select id="perPage">
                                <option>9</option>
                                <option>18</option>
                                <option>27</option>
                            </select>
                        </label><br/>
                        <li class="current-page"><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#"><i class="fa fa-angle-right"></i></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- Koniec produktów -->

    <!-- Stopka -->
    <jsp:include page="/WEB-INF/parts/overall-footer.jsp"/>