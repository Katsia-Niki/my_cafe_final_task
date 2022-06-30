<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 14.06.2022
  Time: 7:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>
<fmt:message key="title.orders" var="title"/>
<fmt:message key="title.cancel_order" var="title_cancel_order"/>
<fmt:message key="message.complete_cancel_order" var="complete"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="order.id" var="id_table"/>
<fmt:message key="order.creation_date" var="creation_date_table"/>
<fmt:message key="order.payment_type" var="payment_type_table"/>
<fmt:message key="order.pick_up_time" var="pick_up_time_table"/>
<fmt:message key="order.status" var="status_table"/>
<fmt:message key="order.cost" var="order_cost_table"/>
<fmt:message key="order.is_paid" var="is_paid_table"/>
<fmt:message key="reference.cancel" var="cancel_table"/>
<fmt:message key="button.cancel" var="cancel"/>


<html>
<head>
    <
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>
        ${title_cancel_order}
    </title>
</head>
<header>
    <jsp:include page="../header/header.jsp"/>
</header>
<body>
<br><br><br><br><br>
<div class="container text-secondary">
    <div class="section-heading">
        <h6>${title_cancel_order}</h6>
        <br>
    </div>
    <c:choose>
        <c:when test="${not empty update_order_result}">
            ${update_order_result eq true ? complete : failed}
        </c:when>
        <c:otherwise>
            <table class="table text-secondary border-secondary">
                <tbody>
                <tr>
                    <td>${id_table}</td>
                    <td>${order_ses.orderId}</td>
                </tr>
                <tr>
                    <td>${creation_date_table}</td>
                    <td>${order_ses.creationDate}</td>
                </tr>
                <tr>
                    <td>${payment_type_table}</td>
                    <td>${order_ses.paymentType}</td>
                </tr>
                <tr>
                    <td>${pick_up_time_table}</td>
                    <td>${order_ses.pickUpTime}</td>
                </tr>
                <tr>
                    <td>${status_table}</td>
                    <td>${order_ses.status}</td>
                </tr>
                <tr>
                    <td>${order_cost_table}</td>
                    <td>${order_ses.orderCost}</td>
                </tr>
                </tbody>
            </table>
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="cancel_order"/>
                <div class="container text-center">
                    <button type="submit" class="btn btn-secondary text-center">
                            ${cancel}
                    </button>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
