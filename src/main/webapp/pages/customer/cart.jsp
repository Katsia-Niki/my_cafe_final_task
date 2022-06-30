<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 05.06.2022
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.header" var="title"/>
<fmt:message key="title.cart" var="title_cart"/>
<fmt:message key="field.cart_name" var="cart_name"/>
<fmt:message key="field.cart_quantity" var="cart_quantity"/>
<fmt:message key="field.cart_price" var="cart_price"/>
<fmt:message key="field.cart_remove" var="remove"/>
<fmt:message key="field.cart_total_sum" var="cart_total_sum"/>
<fmt:message key="button.remove_item" var="remove_item"/>
<fmt:message key="button.place_order" var="place_order"/>
<fmt:message key="message.empty_cart" var="empty_cart"/>


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

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/font-awesome.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/templatemo-cafe.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/lightbox.css">
</head>
<body>
<header>
    <jsp:include page="../header/header.jsp"/>
</header>
<div class="container">
    <br><br><br><br><br>
    <div class="section-heading">
        <h6>${title_cart}</h6>
        <br>
    </div>
    <br>
    <c:if test="${empty cart}">
        ${empty_cart}<br><br><br><br><br><br><br><br><br><br><br><br><br><br>
    </c:if>

    <c:if test="${not empty cart}">
    <div class="row">
        <table class="table table-bordered">
            <thead class="thead-light text-uppercase">
            <tr>
                <th scope="col">N</th>
                <th scope="col">${cart_name}</th>
                <th scope="col">${cart_quantity}</th>
                <th scope="col">${cart_price}</th>
                <th scope="col">${remove}</th>
            </tr>
            </thead>
            <tbody class="text-left">
            <c:forEach var="cart" items="${cart}" varStatus="status">
                <tr>
                    <td scope="row">${status.count}</td>
                    <td>${cart.key.name}</td>
                    <td class="text-right">${cart.value}</td>
                    <td class="text-right">${cart.key.price}</td>
                    <td>
                        <form name="removeItemFromCartForm" method="post" action="${path}/controller">
                            <div class="form-row justify-content-center">
                                <input type="hidden" name="command" value="remove_item_from_cart"/>
                                <input type="hidden" name="menu_item_id_to_remove" value="${cart.key.menuItemId}">
                                <button class="btn btn-outline-danger my-2 my-sm-0" type="submit">
                                        ${remove_item}
                                </button>
                            </div>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr class="text-right">
                <th scope="row" colspan="4">${cart_total_sum}:</th>
                <th colspan="2">${cart_sum}</th>
            </tr>
            </tfoot>
        </table>
        <br>
        <div class="text-right">
        <form name="placeOrderForm" method="get" action="${path}/controller">
            <input type="hidden" name="command" value="go_to_place_order_page"/>
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">${place_order}</button>
        </form>
        </div>
    </div>
</c:if>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>