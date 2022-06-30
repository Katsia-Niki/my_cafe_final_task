<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 15.05.2022
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.update" var="update"/>
<fmt:message key="field.amount" var="amount"/>
<fmt:message key="field.balance" var="balance"/>
<fmt:message key="message.balance_rules" var="balance_rules"/>
<fmt:message key="message.complete_refill" var="complete"/>
<fmt:message key="message.error_password" var="error_password"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.incorrect_amount_oversize" var="incorrect_amount_oversize"/>
<fmt:message key="message.no_data" var="no_data"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="title.refill_balance" var="title"/>

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
<div class="container text-secondary ">
    <div class="section-heading">
        <h6>${title}</h6>
        <br><br>
    </div>
    <c:choose>
        <c:when test="${not empty not_found_ses}">
            ${not_found}
        </c:when>
        <c:when test="${not empty refill_balance_result}">
            ${refill_balance_result eq true? complete: failed}
        </c:when>
        <c:when test="${empty balance_data_ses}">
            ${no_data}
        </c:when>
        <c:otherwise>
            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="refill_balance"/>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${balance}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${balance_data_ses['balance_ses']}" class="form-control" disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${amount}
                    </label>
                    <div class="col-md-10">
                        <input type="number" step="0.01" min="0" max="9999999.99" name="refill_amount"
                               required oninvalid="this.setCustomValidity('${balance_rules}')"
                               class="form-control">
                    </div>
                    <div class="row">
                        <div class="col text-danger">
                            <c:if test="${not empty balance_data_ses['wrong_amount_ses']}">
                                ${incorrect_data}${balance_rules}
                            </c:if>
                            <c:if test="${not empty balance_data_ses['wrong_amount_oversize_ses']}">
                                ${incorrect_amount_oversize}${balance_rules}
                            </c:if>
                        </div>
                    </div>
                    <div class="container text-center">
                        <button type="submit" class="btn btn-secondary">
                                ${update}
                        </button>
                    </div>
                </div>
            </form>
            <br><br>
        </c:otherwise>
    </c:choose>
</div>
<br><br><br><br><br><br>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
