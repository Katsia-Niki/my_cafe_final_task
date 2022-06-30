<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 30.03.2022
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="reference.back_to_main" var="back_to_main"/>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <title>404</title>
</head>
<body>
<div class="container-fluid" style="height: 100%; position: absolute; margin: 0;">

    <div class="row align-items-center" style="height: 100%">
        <div class="col">
            <h3 class="display-4">Упс, тут нет такой страницы...</h3>
            <hr class="my-4">
            <form class="form-inline" action="controller">
                <input type="hidden" name="command" value="go_to_main_page">
                <button type="submit" class="btn btn-outline-primary my-2 my-sm-0">Вернуться на главную страницу</button>
            </form>
        </div>
        <div class="col">
            <img src="${pageContext.request.contextPath}/assets/images/404-error.png" alt="">
        </div>
    </div>
</div>
</body>
</html>
