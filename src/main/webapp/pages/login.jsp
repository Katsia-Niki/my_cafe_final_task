<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 01.05.2022
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.login_form" var="login_form"/>
<fmt:message key="button.sign_in" var="sign_in"/>

<fmt:message key="placeholder.login" var="placeholder_login"/>
<fmt:message key="placeholder.password" var="placeholder_password"/>
<fmt:message key="reference.registration" var="registration"/>
<fmt:message key="reference.back_to_main" var="back_to_main"/>
<fmt:message key="message.login_rules" var="login_rules"/>
<fmt:message key="message.password_rules" var="password_rules"/>
<fmt:message key="message.incorrect_login_or_password" var="incorrect_login_or_password"/>
<fmt:message key="message.not_found" var="not_found"/>

<!DOCTYPE html>
<html>
<head>
    <script>
        function preventBack() {
            window.history.forward();
        }
        setTimeout("preventBack()", 0);
        window.onunload = function() {
            null
        };
    </script>

    <meta charset="UTF-8">
    <title>${login_form}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"/>

</head>

<body>

<!DOCTYPE html>
<!--[if lt IE 7 ]>
<html lang="en" class="ie6 ielt8"> <![endif]-->
<!--[if IE 7 ]>
<html lang="en" class="ie7 ielt8"> <![endif]-->
<!--[if IE 8 ]>
<html lang="en" class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html lang="en"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/style.css"/>
</head>
<body>
<div class="container">
    <section id="content">
        <form method="post" action="${path}/controller">
            <input type="hidden" name="command" value="login"/>
            <h1>${login_form}</h1>
            <div>
                <input type="text" name="login" value="${user_data_ses['login_ses']}"  maxlength="45"
                       pattern="[\w-]{2,45}" required oninvalid="this.setCustomValidity('${login_rules}')"
                       placeholder="${placeholder_login}" onchange="this.setAttribute('value', this.value);
                        this.setCustomValidity(this.validity.patternMismatch ? '${login_rules}' : '');"/>
            </div>
            <div>
                <input type="password" name="pass" value="${user_data_ses['password_ses']}"
                       minlength="3" maxlength="15"
                       pattern="^[\wА-я\.\-]{3,45}$"
                       required oninvalid="this.setCustomValidity('${password_rules}')"
                       placeholder="${placeholder_password}"
                       onchange="this.setAttribute('value', this.value);
                               this.setCustomValidity(this.validity.patternMismatch ? '${login_rules}' : '');"/>
            </div>
            <div>
                <input type="submit" value="${sign_in}">
                <a href="${path}/controller?command=go_to_registration_page">${registration}</a>
            </div>
        </form><!-- form -->
        <br>
        <div class="mb-3">
            <a class="link-secondary text-decoration-none" href="${path}/controller?command=go_to_main_page">
                ${back_to_main}</a>
        </div>
        <div class="mb-3 text-danger">

        </div>
        <label class="form-label text-danger">
            <c:if test="${not empty user_data_ses['wrong_email_or_password_ses']}">
                ${incorrect_login_or_password}
            </c:if>
            <c:if test="${not empty user_data_ses['not_found_ses']}">
                ${not_found}
            </c:if>
        </label>
    </section><!-- content -->
</div><!-- container -->
</body>
</html>


</body>
</html>