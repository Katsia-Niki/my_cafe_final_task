<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 19.06.2022
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.update_order" var="title"/>
<fmt:message key="button.update" var="update"/>
<fmt:message key="message.not_found_menu" var="not_found"/>
<fmt:message key="message.complete_changing" var="complete"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="order.id" var="id_table"/>
<fmt:message key="order.creation_date" var="creation_date_table"/>
<fmt:message key="order.payment_type" var="payment_type_table"/>
<fmt:message key="order.pick_up_time" var="pick_up_time_table"/>
<fmt:message key="order.status" var="status_table"/>
<fmt:message key="order.cost" var="order_cost_table"/>
<fmt:message key="order.is_paid" var="is_paid_table"/>
<fmt:message key="reference.continue_editing" var="continue_editing"/>


<html>
<head>
<script>
    function preventBack() {
        window.history.forward();
    }

    setTimeout("preventBack()", 0);
    window.onunload = function () {
        null
    };
</script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
<title>${title}</title>
</head>
<header>
    <jsp:include page="../header/header.jsp"/>
</header>
<body>
<div class="container">
    <br><br><br><br><br>
    <div class="section-heading">
        <h6>${title}</h6>
        <br>
    </div>
    <c:choose>
        <c:when test="${not empty wrong_order_id}">
            ${not_found}
        </c:when>
        <c:when test="${not empty update_order_result}">
            <div class="container">
                <div class="row">
                    <div class="col mb-3">
                            ${update_order_result eq true? complete: failed} <br><br>
                        <a class="link" href="${path}/controller?command=go_to_order_management_page"/>
                            ${continue_editing}
                        </a>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <table class="table text-secondary border-secondary">
                <thead>
                <tr>
                    <th scope="col">${title}</th>
                </tr>
                </thead>
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
                <input type="hidden" name="command" value="update_order"/>
                <div class="mb-3">
                    <label class="form-label">
                            ${status_table}
                    </label>
                    <select class="form-select" name="order_status">
                        <option value="${order_ses.status}" label="${order_ses.status}" selected></option>
                        <c:forEach var="current_status" items="${available_order_statuses_ses}">
                            <option value="${current_status}">
                                    ${current_status.toString()}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="container text-center">
                    <button type="submit" class="btn btn-secondary text-center">
                            ${update}
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
