<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 03.05.2022
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.update" var="update"/>
<fmt:message key="field.new_password" var="new_password"/>
<fmt:message key="field.old_password" var="old_password"/>
<fmt:message key="message.complete_changing" var="complete"/>
<fmt:message key="message.error_password" var="error_password"/>
<fmt:message key="message.incorrect_data" var="incorrect_password"/>
<fmt:message key="message.password_rules" var="password_rules"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="title.change_password" var="title"/>


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
    <jsp:include page="header/header.jsp"/>
</header>
<body>
<br><br><br><br><br>
<div class="container text-secondary ">
    <div class="section-heading">
        <h6>${title}</h6>
        <br><br>
    </div>
    <c:choose>
        <c:when test="${not empty change_password_result}">
            ${change_password_result eq true? complete: failed}
        </c:when>
        <c:otherwise>
            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="change_password"/>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${new_password}
                    </label>
                    <div class="col-md-6">
                        <input type="password" minlength="4" maxlength="12" name="new_pass"
                               pattern="[\da-zA-Z\-!«»#\$%&'\(\)\*\+,\./:;=\?@_`\{\|\}~]+"
                               required oninvalid="this.setCustomValidity('${password_rules} \'')"
                               class="form-control">
                    </div>
                </div>
                <div class="row">
                    <div class="col text-danger">
                        <c:if test="${not empty user_data_ses['wrong_new_password_ses']}">
                            ${incorrect_password}${password_rules}
                        </c:if>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${old_password}
                    </label>
                    <div class="col-md-6">
                        <input type="password" minlength="4" maxlength="12" name="pass" id="pass"
                               pattern="[\da-zA-Z\-!«»#\$%&'\(\)\*\+,\./:;=\?@_`\{\|\}~]+"
                               required oninvalid="this.setCustomValidity('${password_rules}')"
                               class="form-control">
                    </div>
                </div>
                <div class="row">
                    <div class="col text-danger">
                        <c:if test="${not empty user_data_ses['wrong_old_password_ses']}">
                            ${incorrect_password}
                        </c:if>
                        <c:if test="${not empty user_data_ses['wrong_password_ses']}">
                            ${error_password}
                        </c:if>
                    </div>
                </div>
                <div class="container text-center">
                    <button type="submit" class="btn btn-secondary">
                            ${update}
                    </button>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>
<br><br><br><br><br><br><br><br>
<footer>
    <jsp:include page="footer/footer.jsp"/>
</footer>
</body>
</html>