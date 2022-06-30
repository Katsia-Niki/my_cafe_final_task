<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 18.06.2022
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.order_management" var="title"/>
<fmt:message key="button.find_all_orders" var="find_all_orders"/>
<fmt:message key="reference.find" var="find"/>
<fmt:message key="field.date_from" var="from"/>
<fmt:message key="field.date_to" var="to"/>
<fmt:message key="order.status" var="status"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="button.update" var="update"/>
<fmt:message key="message.not_found_orders" var="not_found"/>
<fmt:message key="message.new_search" var="new_search"/>
<fmt:message key="order.id" var="id_table"/>
<fmt:message key="order.creation_date" var="creation_date_table"/>
<fmt:message key="order.payment_type" var="payment_type_table"/>
<fmt:message key="order.pick_up_time" var="pick_up_time_table"/>
<fmt:message key="order.status" var="status_table"/>
<fmt:message key="order.cost" var="order_cost_table"/>
<fmt:message key="order.is_paid" var="is_paid_table"/>


<html>
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
    <br>
    <div class="row">
        <div class="col-sm-3 mb-3" style="background-color: #fb5849">
            <div class="mb-3">
                <form method="get" action="${path}/controller">
                    <input type="hidden" name="command" value="find_all_orders"/><br>
                    <button type="submit" class="btn btn-light text-center">
                        ${find_all_orders}
                    </button>
                </form>
            </div>

            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_orders_by_status"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${status}
                    </label>
                    <div class="row">
                        <div class="col">
                            <select class="form-select" name="order_status">
                                <option value="" label="" selected></option>
                                <c:forEach var="current_status" items="${all_order_statuses_ses}">
                                    <option value="${current_status}"
                                        ${search_parameters_atr['order_status_atr'] eq current_status ? 'selected' : ' '}>
                                            ${current_status}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-light">
                                ${find}
                            </button>
                        </div>
                    </div>
                </div>
            </form>

            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_orders_by_date_range"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${from}
                    </label>
                    <input type="date" max=${today} name="date_from" value="${search_parameter_atr['date_from_atr']}"
                           class="form-control">
                    <br>
                    <label class="form-label">
                        ${to}
                    </label>
                    <div class="row">
                        <div class="col">
                            <input type="date" max=${today} name="date_to"
                                   value="${search_parameters_atr['date_to_atr']}"
                                   class="form-control">
                            <label class="form-label text-dark">
                                <c:if test="${not empty search_parameters_atr['wrong_date_range_atr']}">
                                    ${incorrect_data}
                                </c:if>
                            </label>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-auto">
                            <button type="submit" class="btn btn-light">
                                ${find}
                            </button>
                        </div>
                    </div>
                </div>
            </form>

        </div>
        <div class="col-sm-9 mb-3 text-center">
            <c:choose>
                <c:when test="${not empty search_atr and empty order_list}">
                    ${new_search}
                </c:when>
                <c:when test="${empty search_atr and empty order_list}">
                    ${not_found}
                </c:when>
                <c:otherwise>
                    <table class="table text-secondary border-secondary">
                        <thead>
                        <tr>
                            <th scope="col">${id_table}</th>
                            <th scope="col">${creation_date_table}</th>
                            <th scope="col">${payment_type_table}</th>
                            <th scope="col">${pick_up_time_table}</th>
                            <th scope="col">${status_table}</th>
                            <th scope="col">${order_cost_table}</th>
                            <th scope="col">${update}</th>
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
                                    <c:when test="${can_be_updated_map[element.orderId] eq true}">
                                        <td>  <button type="button" class="btn btn-light">
                                            <a class="text-secondary text-decoration-none"
                                               href="${path}/controller?command=go_to_update_order_page&order_id=${element.orderId}">
                                                ${update}</a>
                                        </button></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td></td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
