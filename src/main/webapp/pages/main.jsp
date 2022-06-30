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

<fmt:message key="reference.login" var="login"/>
<fmt:message key="reference.menu" var="menu"/>
<fmt:message key="reference.order_dish" var="order_dish"/>
<fmt:message key="reference.contact" var="contact"/>
<fmt:message key="reference.home" var="home"/>
<fmt:message key="reference.our_dishes" var="our_dishes"/>
<fmt:message key="reference.logout" var="logout"/>
<fmt:message key="reference.login_offer" var="login_offer"/>
<fmt:message key="title.account" var="account"/>
<fmt:message key="title.change_password" var="change_password"/>
<fmt:message key="title.header" var="title"/>
<fmt:message key="title.header_additional" var="title_add"/>
<fmt:message key="title.order_management" var="order_management"/>
<fmt:message key="title.orders" var="orders"/>
<fmt:message key="title.refill_balance" var="refill_balance"/>
<fmt:message key="title.review_management" var="review_management"/>
<fmt:message key="title.menu_item_management" var="menu_item_management"/>
<fmt:message key="title.user_management" var="user_management"/>

<!DOCTYPE html>
<html lang="en_EN">

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

</head>
<body>
<header>
    <jsp:include page="header/header.jsp"/>
</header>
<div id="top">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-4">
                <div class="left-content">
                    <div class="inner-content">
                        <h4>${title}</h4>
                        <h6>${title_add}</h6>
                        <div class="main-white-button scroll-to-section">
                            <a href="${path}/controller?command=go_to_login_page">${login_offer}</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-8">
                <div class="main-banner header-text">
                    <div class="Modern-Slider">
                        <div class="item">
                            <div class="img-fill">
                                <img src="${path}/assets/images/slide-01.jpg" alt="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="footer/footer.jsp"/>
</footer>
</body>
</html>