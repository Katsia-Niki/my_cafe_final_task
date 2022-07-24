<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 03.05.2022
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.update" var="update"/>
<fmt:message key="field.balance" var="user_balance"/>
<fmt:message key="field.loyalty_points" var="user_loyalty_points"/>
<fmt:message key="field.email" var="user_email"/>
<fmt:message key="field.first_name" var="first_name"/>
<fmt:message key="field.last_name" var="last_name"/>
<fmt:message key="field.role" var="user_role"/>
<fmt:message key="field.user_status" var="user_status_name"/>
<fmt:message key="message.complete_changing" var="complete_changing"/>
<fmt:message key="message.error_password" var="error_password"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.email_rules" var="email_rules"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.incorrect_email_exist" var="incorrect_email_exist"/>
<fmt:message key="message.incorrect_login_exist" var="incorrect_login_exist"/>
<fmt:message key="message.name_rules" var="name_rules"/>
<fmt:message key="message.need_password" var="need_password"/>
<fmt:message key="message.no_data" var="no_data"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="message.password_rules" var="password_rules"/>
<fmt:message key="message.user_active" var="active"/>
<fmt:message key="message.user_banned" var="banned"/>
<fmt:message key="title.account" var="title"/>
<fmt:message key="user.status" var="user_is_active"/>

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
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
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
        <h6>${title}</h6>
        <br>
    </div>
    <c:choose>
        <c:when test="${not empty not_found_ses}">
            ${not_found}
        </c:when>
        <c:when test="${not empty update_personal_data_result}">
            ${update_personal_data_result eq true? complete_changing: failed}
            <br><br><br><br><br><br><br><br><br><br><br><br><br><br>
        </c:when>
        <c:when test="${empty user_data_ses}">
            ${no_data}
        </c:when>
        <c:otherwise>
            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="update_personal_data"/>
                <input type="hidden" name="role" value="${current_role}">
                <input type="hidden" name="user_is_active" value="${user_data_ses['is_active_ses']}">
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_email}
                    </label>
                    <div class="col-md-10">
                        <input type="text" name="email" value="${not empty user_data_ses['new_email_ses'] ? user_data_ses['new_email_ses'] : user_data_ses['email_ses']}" class="form-control"
                               pattern="[\dA-z]([\dA-z_\-\.]*)[\dA-z_\-]@[\dA-z_\-]{2,}\.[A-z]{2,6}"
                               required oninvalid="this.setCustomValidity('${email_rules}')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '${email_rules}' : '');"
                               class="form-control"/>
                        <label class="form-label text-danger">
                            <label class="form-label text-danger">
                                <c:if test="${not empty user_data_ses['wrong_email_ses']}">
                                    ${incorrect_data} ${email_rules}
                                </c:if>
                            </label>
                            <c:if test="${not empty user_data_ses['wrong_email_exists_ses']}">
                                ${incorrect_email_exist}
                            </c:if>
                        </label>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${first_name}
                    </label>
                    <div class="col-md-10">
                        <input type="text" name="first_name" value="${user_data_ses['first_name_ses']}"
                               pattern="[\wА-яЁё\s]{2,20}"
                               required oninvalid="this.setCustomValidity('${name_rules}')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '${name_rules}' : '');"
                               class="form-control"/>
                    </div>
                    <label class="form-label text-danger">
                        <c:if test="${not empty user_data_ses['wrong_first_name_ses']}">
                            ${incorrect_data} ${name_rules}
                        </c:if>
                    </label>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${last_name}
                    </label>
                    <div class="col-md-10">
                        <input type="text" name="last_name" value="${user_data_ses['last_name_ses']}"
                               pattern="[\wА-яЁё\s]{2,20}"
                               required oninvalid="this.setCustomValidity('${name_rules}')"
                               onchange="this.setAttribute('value', this.value);
                                       this.setCustomValidity(this.validity.patternMismatch ? '${name_rules}' : '');"
                               class="form-control"/>
                    </div>
                </div>
                <label class="form-label text-danger">
                    <c:if test="${not empty user_data_ses['wrong_last_name_ses']}">
                        ${incorrect_data} ${name_rules}
                    </c:if>
                </label>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_role}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${user_data_ses['role_name_ses']}" class="form-control" disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_is_active}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${user_data_ses['is_active_ses'] ? active : banned}"
                               class="form-control" disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_balance}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${user_data_ses['balance_ses']}" class="form-control" disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_loyalty_points}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${user_data_ses['loyalty_points_ses']}" class="form-control" disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-4 col-form-label mb-3">
                            ${need_password}
                    </label>
                    <div class="col-md-6">
                        <input type="password" minlength="3" maxlength="15" name="pass" id="pass"
                               value="${user_data_ses['password_ses']}"
                               pattern="^[\wА-я\.\-]{3,45}$"
                               required oninvalid="this.setCustomValidity('${password_rules} \'')"
                               class="form-control">
                    </div>
                </div>
                <div class="row">
                    <div class="col text-danger">
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
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>