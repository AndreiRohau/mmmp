<!--
<%@ page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
-->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="i18n.locale" var="loc"/>
    <fmt:message bundle="${loc}" key="secure.main" var="secure_main"/>
    <fmt:message bundle="${loc}" key="log.out" var="log_out"/>
    <fmt:message bundle="${loc}" key="to.home" var="to_home"/>
    <fmt:message bundle="${loc}" key="filter" var="filter"/>
    <fmt:message bundle="${loc}" key="show.products" var="show_products"/>
    <fmt:message bundle="${loc}" key="info" var="info"/>
    <fmt:message bundle="${loc}" key="id" var="id"/>
    <fmt:message bundle="${loc}" key="type" var="type"/>
    <fmt:message bundle="${loc}" key="company" var="company"/>
    <fmt:message bundle="${loc}" key="name" var="name"/>
    <fmt:message bundle="${loc}" key="action" var="action"/>
    <fmt:message bundle="${loc}" key="to.basket" var="to_basket"/>
    <fmt:message bundle="${loc}" key="see.details" var="see_details"/>
    <fmt:message bundle="${loc}" key="add.to.basket" var="add_to_basket"/>
    <fmt:message bundle="${loc}" key="title.main" var="title_main"/>
    <title><c:out value="${title_main}"/></title>
</head>
<body>
<div>
    <h2 style="text-transform: capitalize;">
        <c:out value="${secure_main}"/>
    </h2>
    <c:if test="${sessionScope.role != null}">
        <p>Authorized=
            <c:out value="${sessionScope.role}"/>
        </p>
        <form method="get" action="/main">
            <input type="hidden" name="command" value="logout"/>
            <button type="submit" style="text-transform: capitalize;">
                <c:out value="${log_out}"/>
            </button>
        </form>
    </c:if>

    <p>
        <c:out value="${message}"/>
    </p>
    <hr/>
    <!-- NAGATION -->
    <a href="/home" style="text-transform: capitalize;">[GET]
        <c:out value="${to_home}"/>
    </a>
    <hr/>
    <br/>
</div>
<c:if test="${sessionScope.role != null}">
    <c:if test="${pageable.totalElements != 0 && Math.round(Math.ceil(pageable.totalElements / pageable.limit)) < pageable.pageNumber}">
        <p style="color:red">
            <c:out value="Trying to open page ${pageable.pageNumber},
            when only ${Math.round(Math.ceil(pageable.totalElements / pageable.limit))} can be accessible!"/>
        </p>
    </c:if>
    <c:if test="${pageable.totalElements == 0}">
        <p style="color:red">
            <c:out value="Nothing found!"/>
        </p>
    </c:if>
    <div>
        <p style="text-transform: capitalize;">
            <c:out value="${filter}"/>
            :
        </p>
        <form id="show_products" method="get" action="/main">
            <input type="hidden" name="command" value="show_products"/>
            <label>
                <c:out value="${type}"/>
            </label>
            <input type="text" name="type" value="${requestScope.pageable.filter.type}"/>
            <label>
                <c:out value="${company}"/>
            </label>
            <input type="text" name="company" value="${requestScope.pageable.filter.company}"/>
            <label>
                <c:out value="${name}"/>
            </label>
            <input type="text" name="name" value="${requestScope.pageable.filter.name}"/>
            <button form="show_products" type="submit">Show products</button>
        </form>
    </div>
    <div>
        <table>
            <thead>
            <tr>
                <td>
                    <h4>
                        <c:out value="${info}"/>
                    </h4>
                </td>
                <td>
                    <h4>
                        <c:out value="${id}"/>
                    </h4>
                </td>
                <td>
                    <h4>
                        <c:out value="${type}"/>
                    </h4>
                </td>
                <td>
                    <h4>
                        <c:out value="${company}"/>
                    </h4>
                </td>
                <td>
                    <h4>
                        <c:out value="${name}"/>
                    </h4>
                </td>
                <td>
                    <h4>
                        <c:out value="${action}"/>
                    </h4>
                </td>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${requestScope.pageable.elements}" var="product">
                <tr>
                    <td>
                        <button type="button">
                            <c:out value="${see_details}"/>
                        </button>
                        <br/>
                    </td>
                    <td>${product.id}</td>
                    <td>${product.type}</td>
                    <td>${product.company}</td>
                    <td>${product.name}</td>
                    <td>
                    <td>
                        <button type="button">
                            <c:out value="${add_to_basket}"/>
                        </button>
                        <br/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div style="margin-left: center">
            <c:forEach begin="1" end="${Math.ceil(pageable.totalElements / pageable.limit)}" var="i">
                <c:if test="${i == pageable.pageNumber}">
                    <span>
                        <button style="color:red" form="show_products" type="submit" name="currentPage" value="${i}">${i}</button>
                    </span>
                </c:if>
                <c:if test="${i != pageable.pageNumber}">
                    <span>
                        <button form="show_products" type="submit" name="currentPage" value="${i}">${i}</button>
                    </span>
                </c:if>
            </c:forEach>
        </div>
    </div>
</c:if>
</body>
</html>