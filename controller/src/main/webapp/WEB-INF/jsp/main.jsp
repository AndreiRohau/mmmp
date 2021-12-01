<!--
<%@ page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
-->
<html>
<body>
<div>
    <h2>Secure Main!</h2>
    <c:if test="${sessionScope.role != null}">
        <p>Authorized=
            <c:out value="${sessionScope.role}"/>
        </p>
        <form method="get" action="/main">
            <input type="hidden" name="command" value="logout"/>
            <button type="submit">Log out</button>
        </form>
    </c:if>

    <p>
        <c:out value="${message}"/>
    </p>
    <hr/>
    <!-- NAGATION -->
    <a href="/">[GET] Go to Home.jsp</a>
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
        <form id="show_products" method="get" action="/main">
            <input type="hidden" name="command" value="show_products"/>
            <input type="text" name="type" value="${requestScope.pageable.filter.type}"/>
            <input type="text" name="company" value="${requestScope.pageable.filter.company}"/>
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
                        <c:out value="info"/>
                    </h4>
                </td>
                <td>
                    <h4>
                        <c:out value="id"/>
                    </h4>
                </td>
                <td>
                    <h4>
                        <c:out value="type"/>
                    </h4>
                </td>
                <td>
                    <h4>
                        <c:out value="company"/>
                    </h4>
                </td>
                <td>
                    <h4>
                        <c:out value="name"/>
                    </h4>
                </td>
                <td>
                    <h4>
                        <c:out value="buy"/>
                    </h4>
                </td>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${requestScope.pageable.elements}" var="product">
                <tr>
                    <td>
                        <button type="button">Product Page Button</button>
                        <br/>
                    </td>
                    <td>${product.id}</td>
                    <td>${product.type}</td>
                    <td>${product.company}</td>
                    <td>${product.name}</td>
                    <td>
                    <td>
                        <button type="button">Add To Basket Button</button>
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