<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 03.05.2022
  Time: 20:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="message.cafe_name" var="name"/>
<fmt:message key="message.cafe_address" var="address"/>
<fmt:message key="message.cafe_phone_number" var="phone_number"/>
<fmt:message key="message.cafe_email" var="email"/>
<fmt:message key="message.contact" var="contact"/>
<fmt:message key="message.contact_info" var="contact_info"/>
<fmt:message key="field.phone_number" var="phone"/>
<fmt:message key="field.email_cafe" var="email_cfe"/>
<fmt:message key="title.contacts" var="title"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
<%--    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>


    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap"
          rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@400;500;600;700&display=swap"
          rel="stylesheet">
    <!-- Additional CSS Files -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/css/font-awesome.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/templatemo-cafe.css">
    <title>${title}</title>
</head>
<header>
    <jsp:include page="header/header.jsp"/>
</header>
<body>
<br><br><br><br><br>
<div class="container">
    <div class="row">
        <div class="col-lg-6 align-self-center">
            <div class="left-text-content">
                <div class="section-heading">
                    <h6>${title}</h6>
                    <br>
                    <h4>${contact}</h4>
                    <br>
                </div>
                <p>${contact_info}</p>
                <br>
                <div class="row">
                    <div class="col-lg-6">
                        <div class="phone">
                            <br>
                            <h5>${phone}</h5>
                            <span><i class="fa fa-phone"></i>  ${phone_number}</span>
                        </div>
                    </div>
                    <br>
                    <div class="col-lg-6">
                        <div class="message">
                            <br>
                            <h5>${email_cfe}</h5>
                            <span><i class="fa fa-envelope"></i>  ${email}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-lg-6">
            <div class="img-fill">
            <img src="${path}/assets/images/contacts.jpg">
        </div>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="footer/footer.jsp"/>
</footer>
</body>
</html>
