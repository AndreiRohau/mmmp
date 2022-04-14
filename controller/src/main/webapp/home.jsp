<!--
<%@ page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
-->
<html>
    <head>
        <c:set var="qwerty" scope="request" value="${qwerty}"/>
    </head>
    <body>
    <div class="header">
        <c:if test="${userId != null}">
            <p>User_id=<c:out value="${userId}" /></p>
        </c:if>
        <h2>Home!</h2>
        <p><c:out value="${message}" /></p>
        <p><c:out value="${error_message}" /></p>
        <c:if test="${sessionScope.role != null}">
            <p>Authorized=<c:out value="${sessionScope.role}" /></p>
            <form method="get" action="/main" >
                <input type="hidden" name="command" value="logout"/>
                <button type="submit">Log out</button>
            </form>
        </c:if>
        <c:if test="${user != null}">
            <p>id=<c:out value="${user.id}" /></p>
            <p>login=<c:out value="${user.login}" /></p>
            <p>firstName=<c:out value="${user.firstName}" /></p>
            <p>lastName=<c:out value="${user.lastName}" /></p>
        </c:if>
        <hr/>
        <!-- NAGATION -->
        <a href="/main">[GET] Go to Main.jsp</a>
        <hr/>
        <br/>
    </div>

    <div class="content">
        <div class="authentication">
            <p>Logination form</p>
            <form method="post" action="/main" >
                <input type="hidden" name="command" value="login"/>
                <input type="text" name="login" value="">
                <input type="password" name="password" value="">
                <button type="submit">Submit</button>
            </form>
            <hr/>
            <br/>
            <p>Registration form</p>
            <p><c:out value="${user}" /></p>
            <form method="post" action="/" >
                <input type="hidden" name="command" value="registration"/>
                <input type="text" name="login" value="">
                <input type="password" name="password_1" value="">
                <input type="password" name="password_2" value="">
                <button type="submit">Submit</button>
            </form>
            <hr/>
            <br/>
        </div>
        <div class="greetings">

        </div>
        <div class="latest-news">

        </div>
    </div>

    <di class="footer">

    </di>
    </body>
</html>