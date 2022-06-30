<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 12.05.2022
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="message.footer" var="footer"/>
<fmt:message key="message.author" var="author"/>
<html>
<head>
    <meta charset="utf-8">

    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap"
          rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@400;500;600;700&display=swap"
          rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="${path}/assets/css/bootstrap.min.css">

    <link rel="stylesheet" type="text/css" href="${path}/assets/css/font-awesome.css">

    <link rel="stylesheet" href="${path}/assets/css/templatemo-cafe.css">

    <link rel="stylesheet" href="${path}/assets/css/lightbox.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-4 col-xs-12">
            <div class="right-text-content">
                <div class="logo">
                    <img src="${path}/assets/images/white-logo.png" alt=""></a>
                </div>
            </div>
        </div>
        <div class="col-lg-8 col-xs-12">
            <div class="left-text-content">
                <p>${footer}
                    <br> ${author}
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>


<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>--%>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>

<%--<fmt:setLocale value="${locale}" scope="session"/>--%>
<%--<fmt:setBundle basename="properties.pagecontent"/>--%>

<%--<fmt:message key="message.footer" var="footer"/>--%>

<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <meta charset="UTF-8"/>--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1">--%>
<%--    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">--%>
<%--    <link rel="stylesheet" href="${path}/assets/css/templatemo-cafe.css">--%>

<%--</head>--%>
<%--<body>--%>
<%--<div class="container position-relative p-3 mb-2 bg-secondary bg-opacity-75 text-white text-end" id="foot_position">--%>
<%--    <div>${footer}</div>--%>
<%--</div>--%>
<%--<script type="text/javascript">--%>
<%--    if (document.body.scrollHeight > window.innerHeight) {--%>
<%--        document.getElementById("foot_position").className = "container position-relative p-3 mb-2 bg-secondary bg-opacity-75 text-white text-end";--%>
<%--    } else {--%>
<%--        document.getElementById("foot_position").className = "container position-absolute fixed-bottom p-3 mb-2 bg-secondary bg-opacity-75 text-white text-end";--%>
<%--    }--%>
<%--</script>--%>
<%--</body>--%>
<%--</html>--%>
