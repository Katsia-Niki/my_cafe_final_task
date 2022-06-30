<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 02.05.2022
  Time: 22:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="language.en" var="en"/>
<fmt:message key="language.ru" var="ru"/>
<fmt:message key="reference.login" var="login"/>
<fmt:message key="reference.logout" var="logout"/>
<fmt:message key="reference.registration" var="registration"/>
<fmt:message key="reference.menu" var="menu"/>
<fmt:message key="reference.contact" var="contact"/>
<fmt:message key="reference.cart" var="cart"/>

<fmt:message key="message.admin" var="admin"/>
<fmt:message key="message.customer" var="customer"/>
<fmt:message key="reference.order_dish" var="order_dish"/>
<fmt:message key="reference.contact" var="contact"/>
<fmt:message key="reference.home" var="home"/>
<fmt:message key="reference.menu" var="menu"/>
<fmt:message key="reference.logout" var="logout"/>
<fmt:message key="title.account" var="account"/>
<fmt:message key="title.change_password" var="change_password"/>
<fmt:message key="title.header" var="title"/>
<fmt:message key="title.order_management" var="order_management"/>
<fmt:message key="title.orders" var="orders"/>
<fmt:message key="title.refill_balance" var="refill_balance"/>
<fmt:message key="title.review_management" var="review_management"/>
<fmt:message key="title.menu_item_management" var="menu_item_management"/>
<fmt:message key="title.user_management" var="user_management"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap"
          rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@400;500;600;700&display=swap"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/font-awesome.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/templatemo-cafe.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/lightbox.css">
</head>
<body>
<header class="header-area header-sticky">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <nav class="main-nav nav-link">
                    <!-- ***** Logo Start ***** -->
                    <a href="${path}/controller?command=go_to_main_page" class="logo">
                        <img src="${pageContext.request.contextPath}/assets/images/red-logo.png" alt=""/>
                    </a>
                    <!-- ***** Logo End ***** -->
                    <!-- ***** Menu Start ***** -->
                    <ul class="nav">
                        <c:choose>
                            <c:when test="${current_role eq 'ADMIN' or current_role eq 'CUSTOMER'}">
                                <li class="submenu nav-link nav-item">
                                    <a href="javascript:">${current_role eq 'ADMIN' ? admin : customer}: ${current_login_ses}</a>
                                    <ul>
                                        <c:if test="${current_role eq 'CUSTOMER'}">
                                            <li class="nav-item"><a class="nav-link" href="${path}/controller?command=go_to_account_page">
                                                    ${account}</a></li>
                                            <li class="nav-item"><a class="nav-link" href="${path}/controller?command=go_to_change_password_page">
                                                    ${change_password}</a></li>
                                            <li class="nav-item"><a class="nav-link" href="${path}/controller?command=go_to_refill_balance_page">
                                                    ${refill_balance}</a></li>
                                            <li class="nav-item"><a class="nav-link" href="${path}/controller?command=find_order_by_user_id">
                                                    ${orders}</a></li>
                                            <li class="nav-item"><a class="nav-link" href="${path}/controller?command=logout">
                                                    ${logout}</a></li>
                                        </c:if>
                                        <c:if test="${current_role eq 'ADMIN'}">
                                            <li class="nav-item"><a class="nav-link" href="${path}/controller?command=go_to_account_page">
                                                    ${account}</a></li>
                                            <li><a class="nav-link" href="${path}/controller?command=go_to_change_password_page">
                                                    ${change_password}</a></li>
                                            <li><a class="nav-link" href="${path}/controller?command=find_all_users">
                                                    ${user_management}</a></li>
                                            <li><a class="nav-link" href="${path}/controller?command=go_to_order_management_page">
                                                    ${order_management}</a></li>
                                            <li><a class="nav-link" href="${path}/controller?command=find_all_menu">
                                                    ${menu_item_management}</a></li>
                                            <li><a class="nav-link" href="${path}/controller?command=logout">
                                                    ${logout}</a></li>
                                        </c:if>
                                    </ul>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="nav-item"><a class="nav-link"
                                                        href="${path}/controller?command=go_to_login_page">${login}</a>
                                </li>
                                <li class="nav-item"><a class="nav-link"
                                                        href="${path}/controller?command=go_to_registration_page">${registration}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${path}/controller?command=find_all_available_menu">${menu}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${path}/controller?command=go_to_contact_page">${contact}</a>
                        </li>
                        <c:if test="${current_role eq 'CUSTOMER'}">
                            <li class="nav-item">
                                <a class="nav-link"
                                   href="${path}/controller?command=go_to_cart_page">${cart}</a>
                            </li>
                        </c:if>
                        <li class="submenu nav-item nav-link text-right">
                            <a href="javascript:;">${locale =='ru_RU'?ru:en}</a>
                            <ul>
                                <li><a href="${path}/controller?command=change_language&language=RU">${ru}</a></li>
                                <li><a href="${path}/controller?command=change_language&language=EN">${en}</a></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</header>
</body>
</html>
