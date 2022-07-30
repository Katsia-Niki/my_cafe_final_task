<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 29.05.2022
  Time: 23:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="field.menu_item_price" var="menu_item_price"/>
<fmt:message key="field.menu_item_picture" var="menu_item_picture"/>
<fmt:message key="field.menu_item_description" var="menu_item_description"/>
<fmt:message key="field.add_menu_item" var="add_menu_item"/>
<fmt:message key="message.not_found_menu" var="not_found"/>
<fmt:message key="message.banned" var="banned"/>
<fmt:message key="message.item_added_to_cart" var="item_added_to_cart"/>
<fmt:message key="reference.previous" var="previous"/>
<fmt:message key="reference.next" var="next"/>
<fmt:message key="reference.edit" var="edit"/>
<fmt:message key="reference.to_cart" var="to_cart"/>
<fmt:message key="title.header" var="title"/>
<fmt:message key="title.menu" var="title_menu"/>
<fmt:message key="reference.previous" var="previous"/>
<fmt:message key="reference.next" var="next"/>

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

    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.0.min.js"></script>

</head>
<body>
<div class="page">
    <header>
        <jsp:include page="header/header.jsp"/>
    </header>
    <section class="section" id="offers">
        <div class="container container-fluid text-center">
            <div class="row">
                <div class="col-lg-4 offset-lg-4 text-center">
                    <div class="section-heading">
                        <h2>${title_menu}</h2>
                    </div>
                </div>
            </div>
            <c:if test="${not empty current_user_is_active and current_user_is_active == false}">
            <span class="text-danger text-center">
                    ${banned}</span><br>
            </c:if>
            <c:choose>
                <c:when test="${empty menu_item_available_ses}">
                    ${not_found}
                </c:when>
                <c:otherwise>

                    <c:forEach var="menu" items="${menu_item_available_ses}">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="col-lg-12">
                                    <section class='tabs-content'>
                                        <article id='tabs-1'>
                                            <div class="row">
                                                <div class="col-lg-12">
                                                    <div class="row">
                                                        <div class="list-group-item">
                                                            <div class="col-lg-12">
                                                                <div class="tab-item">
                                                                    <img src="${menu.image}" class="img-thumbnail">
                                                                    <h4>${menu.name}</h4>
                                                                    <p>${menu.description}</p>
                                                                    <div class="price">
                                                                        <h6>${menu.price}</h6>
                                                                    </div>
                                                                    <div>
                                                                        <c:if test="${current_role eq 'CUSTOMER'}">
                                                                            <form method="post"
                                                                                  action="${path}/controller">
                                                                                <input type="hidden" name="command"
                                                                                       value="add_item_to_cart">
                                                                                <input type="hidden"
                                                                                       name="menu_item_id"
                                                                                       value="${menu.menuItemId}">
                                                                                <input type="hidden"
                                                                                       name="page"
                                                                                       value="${current_page_number}">
                                                                                <br>
                                                                                <div class="product_actions">
                                                                                    <div class="number"
                                                                                         data-step="1"
                                                                                         data-min="1" data-max="10">
                                                                                        <input class="number-text"
                                                                                               type="text"
                                                                                               name="menu_item_quantity"
                                                                                               value="1">
                                                                                        <a href="#"
                                                                                           class="number-minus">−</a>
                                                                                        <a href="#"
                                                                                           class="number-plus">+</a>
                                                                                    </div>
                                                                                    <c:choose>
                                                                                        <c:when test="${current_user_is_active == true}">
                                                                                            <button type="submit"
                                                                                                    class="btn btn_type_light js_add-to-cart">${to_cart}</button>
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <button type="submit"
                                                                                                    class="btn btn_type_light js_add-to-cart"
                                                                                                    disabled>${to_cart}</button>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </div>
                                                                            </form>
                                                                            <c:if test="${message_item_added_to_cart && item_label == menu.menuItemId}">
                                                                             <span class="text-success">
                                                                               ${item_added_to_cart}</span>
                                                                                <c:remove var="message_item_added_to_cart"/>
                                                                            </c:if>
                                                                        </c:if>
                                                                        <c:if test="${current_role eq 'ADMIN'}">
                                                                            <form target="_blank" method="get"
                                                                                  action="${path}/controller">
                                                                                <input type="hidden" name="command"
                                                                                       value="go_to_edit_menu_page">
                                                                                <input type="hidden"
                                                                                       name="menu_item_id_to_edit"
                                                                                       value="${menu.menuItemId}">
                                                                                <br>
                                                                                <button class="btn btn-outline-danger my-2 my-sm-0"
                                                                                        type="submit">${edit}</button>
                                                                            </form>
                                                                        </c:if>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </article>
                                    </section>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="container-fluid text-center">
                        <div class="row">
                            <div class="col-sm-2 col-auto col-lg-2 me-2">
                            </div>
                            <div class="col">
                                <nav aria-label="navigation for items">
                                    <ul class="pagination justify-content-center mt-3 mb-4">
                                        <c:if test="${current_page_number != 1}">
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="${path}/controller?command=find_all_available_menu&page=${current_page_number-1}">
                                                        ${previous}
                                                </a>
                                            </li>
                                        </c:if>
                                        <c:forEach begin="1" end="${number_of_pages}" var="i">
                                            <c:choose>
                                                <c:when test="${current_page_number eq i}">
                                                    <li class="page-item active">
                                                        <a class="page-link"> ${i} <span class="sr-only"></span></a>
                                                    </li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="page-item">
                                                        <a class="page-link"
                                                           href="${path}/controller?command=find_all_available_menu&page=${i}">
                                                                ${i}
                                                        </a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <c:if test="${current_page_number lt number_of_pages}">
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="${path}/controller?command=find_all_available_menu&page=${current_page_number+1}">
                                                        ${next}
                                                </a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</div>
<footer>
    <jsp:include page="footer/footer.jsp"/>
</footer>
<script>
    $(document).ready(function () {
        $('body').on('click', '.number-minus, .number-plus', function () {
            var $row = $(this).closest('.number');
            var $input = $row.find('.number-text');
            var step = $row.data('step');
            var val = parseFloat($input.val());
            if ($(this).hasClass('number-minus')) {
                val -= step;
            } else {
                val += step;
            }
            $input.val(val);
            $input.change();
            return false;
        });

        $('body').on('change', '.number-text', function () {
            var $input = $(this);
            var $row = $input.closest('.number');
            var step = $row.data('step');
            var min = parseInt($row.data('min'));
            var max = parseInt($row.data('max'));
            var val = parseFloat($input.val());
            if (isNaN(val)) {
                val = step;
            } else if (min && val < min) {
                val = min;
            } else if (max && val > max) {
                val = max;
            }
            $input.val(val);
        });
    });
</script>
</body>
</html>
