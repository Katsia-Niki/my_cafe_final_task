<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 30.05.2022
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="message.admin" var="admin"/>
<fmt:message key="message.customer" var="customer"/>
<fmt:message key="title.header" var="title"/>
<fmt:message key="title.user_list" var="user_list"/>
<fmt:message key="reference.ban" var="ban"/>
<fmt:message key="reference.unban" var="unban"/>
<fmt:message key="reference.make_admin" var="make_admin"/>
<fmt:message key="reference.make_customer" var="make_customer"/>
<fmt:message key="reference.continue_editing" var="continue_editing"/>
<fmt:message key="field.action" var="action"/>
<fmt:message key="field.email" var="email"/>
<fmt:message key="field.login" var="login"/>
<fmt:message key="field.first_name" var="first_name"/>
<fmt:message key="field.last_name" var="last_name"/>
<fmt:message key="field.role" var="role"/>
<fmt:message key="field.user_id" var="user_id"/>
<fmt:message key="field.user_status" var="user_status"/>
<fmt:message key="field.user_active" var="user_active"/>
<fmt:message key="field.user_blocked" var="user_blocked"/>
<fmt:message key="message.complete_changing" var="complete_changing"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="reference.previous" var="previous"/>
<fmt:message key="reference.next" var="next"/>


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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>${title}</title>
</head>
<header>
    <jsp:include page="../header/header.jsp"/>
</header>
<body>
<div class="container">
    <br><br><br><br><br>
    <div class="section-heading">
        <h6>${user_list}</h6>
        <br>
    </div>
    <br>
    <c:choose>
        <c:when test="${not empty update_user_result}">
            <div class="container">
                <div class="row">
                    <div class="col mb-3">
                            ${update_user_result eq true ? complete_changing : failed} <br><br>
                        <a class="link" href="${path}/controller?command=find_all_users"/>
                            ${continue_editing}
                        </a>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>${user_id} </th>
                    <th>${login} </th>
                    <th>${last_name} </th>
                    <th>${first_name} </th>
                    <th>${email}</th>
                    <th>${role} </th>
                    <th>${user_status} </th>
                    <th>${action} </th>
                    <th>${action} </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users_list}">
                    <tr>
                        <td><c:out value="${user.userId}"/></td>
                        <td><c:out value="${user.login}"/></td>
                        <td><c:out value="${user.lastName}"/></td>
                        <td><c:out value="${user.firstName}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.role eq 'ADMIN' ? admin : customer}"/></td>
                        <td><c:out value="${user.active eq true ? user_active : user_blocked}"/></td>
                        <td>
                            <button type="button" class="btn btn-light" style="text-decoration-color: #6a1a21">
                                <a class="text-secondary text-decoration-none"
                                   href="${path}/controller?command=update_user_status&user_id=${user.userId}&user_is_active=${user.active}">
                                    <c:if test="${user.role eq 'CUSTOMER'}">
                                        ${user.active eq true ? ban : unban}
                                    </c:if>
                                </a>
                            </button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-light">
                                <a class="text-secondary text-decoration-none"
                                   href="${path}/controller?command=update_user_role&user_id=${user.userId}&user_role=${user.role}">

                                    <c:if test="${user.role eq 'CUSTOMER'}">
                                        ${user.active eq true ? make_admin : " "}
                                    </c:if>
                                    <c:if test="${user.role eq 'ADMIN'}">
                                        ${make_customer}
                                    </c:if>
                                </a>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-2 col-auto col-lg-2 me-2">
                    </div>
                    <div class="col">
                        <nav aria-label="navigation for items">
                            <ul class="pagination justify-content-center mt-3 mb-4">
                                <c:if test="${current_page_number != 1}">
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="${path}/controller?command=find_all_users&page=${current_page_number-1}">
                                                ${previous}
                                        </a>
                                    </li>
                                </c:if>
                                <c:forEach begin="1" end="${number_of_pages}" var="i">
                                    <c:choose>
                                        <c:when test="${current_page_number eq i}">
                                            <li class="page-item active">
                                                <a class="page-link"> ${i} <span class="sr-only"></span></a>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="${path}/controller?command=find_all_users&page=${i}">
                                                        ${i}
                                                </a>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <c:if test="${current_page_number lt number_of_pages}">
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="${path}/controller?command=find_all_users&page=${current_page_number+1}">
                                                ${next}
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
