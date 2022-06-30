<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 29.03.2022
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.header" var="title"/>
<fmt:message key="title.home" var="title_home"/>
<fmt:message key="message.welcome" var="welcome"/>
<fmt:message key="message.cafe_info" var="cafe_info"/>
<fmt:message key="message.cafe_info_admin" var="cafe_info_admin"/>
<fmt:message key="reference.menu" var="menu"/>
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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap"
          rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@400;500;600;700&display=swap"
          rel="stylesheet">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Additional CSS Files -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/font-awesome.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/templatemo-cafe.css">
</head>
<body>
<header>
    <jsp:include page="header/header.jsp"/>
</header>
<br><br><br>

<div class="container">
    <div class="row">
        <div class="col-lg-6 align-self-center">
            <div class="left-text-content">
                <div class="section-heading">
                    <h6>${title_home}</h6>
                    <br>
                    <h4>${welcome}</h4>
                    <br>
                </div>
                <c:if test="${current_role eq 'CUSTOMER'}">
                    <div class="row">
                        <div class="col-lg-6">
                                ${cafe_info}
                            <a class="link" href="${path}/controller?command=find_all_available_menu">
                                    ${menu.toLowerCase()}.
                            </a>
                        </div>
                </c:if>
                <c:if test="${current_role eq 'ADMIN'}">
                <div class="row">
                    <div class="col-lg-6">
                            ${cafe_info_admin}
                        <a class="link" href="${path}/controller?command=find_all_available_menu">
                                ${menu.toLowerCase()}.
                        </a>
                    </div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="col-lg-6">
            <div class="img-fill">
                <img src="${path}/assets/images/home.jpg">
            </div>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="footer/footer.jsp"/>
</footer>
</body>
</html>

