<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 06.06.2022
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.header" var="title"/>
<fmt:message key="title.place_order" var="title_place_order"/>
<fmt:message key="field.cart_name" var="cart_name"/>
<fmt:message key="field.cart_quantity" var="cart_quantity"/>
<fmt:message key="field.cart_price" var="cart_price"/>
<fmt:message key="field.cart_total_sum" var="cart_total_sum"/>
<fmt:message key="message.choose_type" var="choose_type"/>
<fmt:message key="message.get_points_account" var="get_points_account"/>
<fmt:message key="message.get_points_cash" var="get_points_cash"/>
<fmt:message key="message.get_points2" var="get_points2"/>
<fmt:message key="message.use_points" var="use_points"/>
<fmt:message key="message.when_pick_up" var="when_pick_up"/>
<fmt:message key="button.confirm" var="confirm"/>
<fmt:message key="message.complete_order" var="complete"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="message.not_enough_balance" var="not_enough_balance"/>
<fmt:message key="message.not_enough_loyalty_points" var="not_enough_loyalty_points"/>
<fmt:message key="field.your_loyalty_points" var="your_loyalty_points"/>
<fmt:message key="field.your_balance" var="your_balance"/>

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
        <h6>${title_place_order}</h6>
        <br>
    </div>
</div>
<div class="container text-center justify-content-center">
    <div class="row justify-content-center">
        <c:choose>
            <c:when test="${not empty order_confirmed_message}">
                <c:choose>
                    <c:when test="${order_confirmed_message eq true}">
                        ${complete}
                    </c:when>
                    <c:otherwise>
                        ${failed}
                        <c:if test="${not empty order_data_ses['not_enough_money_ses']}">
                            ${not_enough_balance}
                        </c:if>
                        <c:if test="${not empty order_data_ses['not_enough_loyalty_points_ses']}">
                            ${not_enough_loyalty_points}
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <div class="col">
                    <table class="table table-hover table-bordered">
                        <thead class="thead-light text-uppercase">
                        <tr>
                            <th scope="col">N</th>
                            <th scope="col">${cart_name}</th>
                            <th scope="col">${cart_quantity}</th>
                            <th scope="col">${cart_price}</th>
                        </tr>
                        </thead>
                        <tbody class="text-left">
                        <c:forEach var="cart" items="${cart}" varStatus="status">
                            <tr>
                                <td scope="row">${status.count}</td>
                                <td>${cart.key.name}</td>
                                <td class="text-right">${cart.key.price}</td>
                                <td class="text-right"> ${cart.value}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr class="text-right">
                            <th scope="row" colspan="3">${cart_total_sum}:</th>
                            <th>${cart_sum} </th>
                        </tr>
                        </tfoot>
                    </table>
                    <br>
                    <div>
                        <h6>${your_balance} ${current_balance}<br>
                                ${your_loyalty_points} ${current_loyalty_points}</h6>
                        <br>
                    </div>
                    <form name="OrderInputInfoForm" method="post" action="${path}/controller">
                        <h4>${choose_type}</h4>
                        <input type="hidden" name="command" value="confirm_order"/>
                        <div class="row justify-content-center">
                            <div class="col-5">
                                <div class="custom-control custom-radio">
                                    <input type="radio" id="customRadio1" name="payment_type"
                                           class="custom-control-input"
                                           value="account" required>
                                    <label class="custom-control-label"
                                           for="customRadio1">${get_points_account} ${order_data_ses['points_for_account']}
                                            ${get_points2}</label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input type="radio" id="customRadio2" name="payment_type"
                                           class="custom-control-input"
                                           value="cash"
                                           required>
                                    <label class="custom-control-label"
                                           for="customRadio2">${get_points_cash} ${order_data_ses['points_for_cash']} ${get_points2}</label>
                                </div>
                                <div class="custom-control custom-radio">
                                    <input type="radio" id="customRadio3" name="payment_type"
                                           class="custom-control-input"
                                           value="loyalty_points" required>
                                    <label class="custom-control-label" for="customRadio3">${use_points}</label>

                                </div>
                            </div>
                        </div>
                        <br><br>
                        <h4>${when_pick_up}</h4>
                        <div class="row justify-content-center">
                            <div class="col-3">
                                <label>
                                    <input type="datetime-local" class="form-control" name="pick_up_time"
                                           max="${order_data_ses['max_pick_up_time']}"
                                           min="${order_data_ses['min_pick_up_time']}"
                                           required>
                                </label>
                            </div>
                        </div>
                        <br>
                        <hr style="border-color: #fb5849">
                        <button class="btn btn-outline-danger" type="submit">${confirm}</button>
                    </form>
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
