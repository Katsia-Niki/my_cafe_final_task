<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 31.03.2022
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="reference.back_to_main" var="back_to_main"/>

<html>
<header>
    <jsp:include page="../header/header.jsp"/>
</header>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Error 403</title>
</head>
<body>
<br><br><br><br><br>
<div class="container">
    <table class="table text-secondary border-secondary">
        <thead>
        <tr>
            <th scope="col">Error 403</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Request from ${pageContext.errorData.requestURI} is failed<br>
                Servlet name: ${pageContext.errorData.servletName}<br>
                Status code: ${pageContext.errorData.statusCode}<br>
                Message: Access is denied
            </td>
        </tr>
        </tbody>
    </table>
    <a class="link-secondary text-decoration-none"
       href="${path}/controller?command=go_to_main_page">${back_to_main}</a>
    <br><br><br><br><br><br><br><br><br>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
