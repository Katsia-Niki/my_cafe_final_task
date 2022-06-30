<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 15.06.2022
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.header" var="title"/>
<fmt:message key="title.create_item" var="title_create_item"/>
<fmt:message key="message.complete_changing" var="complete_changing"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="field.menu_item_type_appetizer" var="appetizer"/>
<fmt:message key="field.menu_item_type_main_course" var="main_course"/>
<fmt:message key="field.menu_item_type_soup" var="soup"/>
<fmt:message key="field.menu_item_type_dessert" var="dessert"/>
<fmt:message key="field.menu_item_type_drink" var="drink"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.item_name_rules" var="item_name_rules"/>
<fmt:message key="message.description_rules" var="description_rules"/>
<fmt:message key="field.menu_item_type" var="menu_item_type"/>
<fmt:message key="field.menu_item_name" var="menu_item_name"/>
<fmt:message key="field.menu_item_description" var="menu_item_description"/>
<fmt:message key="field.menu_item_price" var="menu_item_price"/>
<fmt:message key="field.menu_item_available" var="available"/>
<fmt:message key="button.confirm" var="confirm"/>
<fmt:message key="message.in_menu" var="in_menu"/>
<fmt:message key="message.unavailable" var="unavailable"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap"
          rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@400;500;600;700&display=swap"
          rel="stylesheet">

    <title>${title}</title>
    <!-- Additional CSS Files -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/templatemo-cafe.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style_counter.css">

    <script>
        function preventBack() {
            window.history.forward();
        }

        setTimeout("preventBack()", 0);
        window.onunload = function () {
            null
        };
    </script>
</head>
<body>
<div class="page">
    <header>
        <jsp:include page="../header/header.jsp"/>
    </header>
    <br><br><br><br><br>
    <div class="container">
        <div class="section-heading">
            <h6>${title_create_item}</h6>
            <br>
        </div>


    <c:choose>
        <c:when test="${not empty create_menu_item_result}">
            ${create_menu_item_result eq true? complete_changing: failed}
        </c:when>
        <c:otherwise>
            <div class="row justify-content-md-center text-left">
                <div class="col-6">
                    <form method="post" action="${path}/controller">
                        <input type="hidden" name="command" value="create_menu_item">
                        <div class="form-group row">
                            <label for="type" class="col col-form-label">${menu_item_type}</label>
                            <div class="col">
                                <select id="type" class="form-control" required name="menu_item_type">
                                    <option value="appetizer">${appetizer}</option>
                                    <option value="main_course">${main_course}</option>
                                    <option value="soup">${soup}</option>
                                    <option value="dessert">${dessert}</option>
                                    <option value="drink">${drink}</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="name" class="col col-form-label">${menu_item_name}</label>
                            <div class="col">
                                <input type="text" class="form-control" id="name" name="menu_item_name"
                                       pattern="[A-zА-яЁё\\s]+{2,100}"
                                       value="${menu_item_data_ses['menu_item_name_ses']}"
                                       oninvalid="this.setCustomValidity('${item_name_rules}')"
                                       onchange="this.setAttribute('value', this.value);
                                               this.setCustomValidity(this.validity.patternMismatch ? '${item_name_rules}' : '');"/>
                            </div>
                            <label class="form-label text-danger">
                                <c:if test="${not empty menu_item_data_ses['wrong_menu_item_name']}">
                                    ${incorrect_data} ${item_name_rules}
                                </c:if>
                            </label>
                        </div>
                        <div class="form-group row">
                            <label for="description" class="col col-form-label">${menu_item_description}</label>
                            <div class="col">
                        <textarea id="description" class="form-control" id="description" rows="4" cols="50"
                                  name="menu_item_description">${menu_item_data_ses['menu_item_description_ses']}</textarea>
                            </div>
                            <br>
                            <label class="form-label text-danger">
                                <c:if test="${not empty menu_item_data_ses['wrong_menu_item_description']}">
                                    ${incorrect_data} ${description_rules}
                                </c:if>
                            </label>
                        </div>
                        <div class="form-group row">
                            <label for="price" class="col col-form-label">${menu_item_price}</label>
                            <div class="col">
                                <input required type="number" min="0.50" max="1000" step="0.1" name="menu_item_price"
                                       class="form-control" pattern="\\d{1,5}\\.?\\d{0,2}"
                                       id="price" value="${menu_item_data_ses['menu_item_price_ses']}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="available" class="col col-form-label">${available}</label>
                            <div class="col">
                                <select id="available" class="form-control" required name="menu_item_is_available">
                                    <option selected>${menu_item_data_ses['menu_item_available_ses'] ? in_menu : unavailable}</option>
                                    <c:if test="${menu_item_data_ses['menu_item_available_ses']}">
                                        <option value="false">${unavailable}</option>
                                    </c:if>
                                    <c:if test="${!menu_item_data_ses['menu_item_available_ses']}">
                                        <option value="true">${in_menu}</option>
                                    </c:if>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row text-center">
                            <div class="col">
                            </div>
                            <div class="col">
                                <button class="btn btn-sm btn-outline-danger" type="submit">${confirm}</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    </div>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
