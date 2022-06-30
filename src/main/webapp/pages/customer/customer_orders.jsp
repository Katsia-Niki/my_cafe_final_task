<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 13.06.2022
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.find_all_orders" var="find_all"/>
<fmt:message key="title.orders" var="title"/>
<fmt:message key="title.your_orders" var="title_orders"/>
<fmt:message key="field.cart_name" var="cart_name"/>
<fmt:message key="field.cart_quantity" var="cart_quantity"/>
<fmt:message key="field.cart_price" var="cart_price"/>
<fmt:message key="field.cart_total_sum" var="cart_total_sum"/>
<fmt:message key="order.id" var="id_table"/>
<fmt:message key="order.creation_date" var="creation_date_table"/>
<fmt:message key="order.payment_type" var="payment_type_table"/>
<fmt:message key="order.pick_up_time" var="pick_up_time_table"/>
<fmt:message key="order.status" var="status_table"/>
<fmt:message key="order.cost" var="order_cost_table"/>
<fmt:message key="order.is_paid" var="is_paid_table"/>
<fmt:message key="reference.cancel" var="cancel_table"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>
        ${title}
    </title>
</head>
<header>
    <jsp:include page="../header/header.jsp"/>
</header>
<body>
<br><br><br><br><br>
<div class="container text-secondary">
    <div class="section-heading">
        <h6>${title_orders}</h6>
        <br>
    </div>
    <div class="row">
        <div class="col">
            <table class="table text-secondary border-secondary">
                <thead>
                <tr>
                    <th scope="col">${id_table}</th>
                    <th scope="col">${creation_date_table}</th>
                    <th scope="col">${payment_type_table}</th>
                    <th scope="col">${pick_up_time_table}</th>
                    <th scope="col">${status_table}</th>
                    <th scope="col">${order_cost_table}</th>
                    <th scope="col">${cancel_table}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="element" items="${order_list}">
                    <tr>
                        <td>${element.orderId}</td>
                        <td>${element.creationDate}</td>
                        <td>${element.paymentType.toString()}</td>
                        <td>${element.pickUpTime}</td>
                        <td>${element.status.toString()}</td>
                        <td>${element.orderCost}</td>
                        <c:choose>
                            <c:when test="${can_be_cancelled_map[element.orderId]}">
                                <td><a class="text-secondary text-decoration-none"
                                       href="${path}/controller?command=go_to_cancel_order_page&order_id=${element.orderId}">
                                        ${cancel_table}</a></td>
                            </c:when>
                            <c:otherwise>
                                <td></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>


        </div>
    </div>

</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
