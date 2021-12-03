<!--
<%@ page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
-->
<html>
<head>
    <style type="text/css">
            .loc-btns form {
                display: inline-block;
            }

    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="i18n.locale" var="loc"/>
    <fmt:message bundle="${loc}" key="button.en" var="button_en"/>
    <fmt:message bundle="${loc}" key="button.ch" var="button_ch"/>
    <fmt:message bundle="${loc}" key="button.be" var="button_be"/>
    <fmt:message bundle="${loc}" key="button.ru" var="button_ru"/>
    <fmt:message bundle="${loc}" key="secure.home" var="secure_home"/>
    <fmt:message bundle="${loc}" key="to.main" var="to_main"/>
    <fmt:message bundle="${loc}" key="logination" var="logination"/>
    <fmt:message bundle="${loc}" key="registration" var="registration"/>
    <fmt:message bundle="${loc}" key="submit" var="submit"/>
    <fmt:message bundle="${loc}" key="title.home" var="title_home"/>
    <c:set var="qwerty" scope="request" value="${qwerty}"/>
    <title><c:out value="${title_home}"/></title>
</head>
<body>
<div class="loc-btns">
    <form action="/home" method="get">
        <input type="hidden" name="command" value="change_language"/>
        <button type="submit" name="locale" value="en">
            <c:out value="${button_en}"/>
        </button>
    </form>
    <form action="/home" method="get">
        <input type="hidden" name="command" value="change_language"/>
        <button type="submit" name="locale" value="ch">
            <c:out value="${button_ch}"/>
        </button>
    </form>
    <form action="/home" method="get">
        <input type="hidden" name="command" value="change_language"/>
        <button type="submit" name="locale" value="be">
            <c:out value="${button_be}"/>
        </button>
    </form>
    <form action="/home" method="get">
        <input type="hidden" name="command" value="change_language"/>
        <button type="submit" name="locale" value="ru">
            <c:out value="${button_ru}"/>
        </button>
    </form>
    <br/>
</div>
<hr/>
<div class="header">
    <h2 style="text-transform: capitalize;">
        <c:out value="${secure_home}"/>
    </h2>
    <p>
        <c:out value="${message}"/>
    </p>
    <p>
        <c:out value="${error_message}"/>
    </p>
    <c:if test="${sessionScope.role != null}">
        <p>Authorized=
            <c:out value="${sessionScope.role}"/>
        </p>
        <form method="get" action="/main">
            <input type="hidden" name="command" value="logout"/>
            <button type="submit">Log out</button>
        </form>
    </c:if>
    <c:if test="${user != null}">
        <p>id=
            <c:out value="${user.id}"/>
        </p>
        <p>login=
            <c:out value="${user.login}"/>
        </p>
        <p>firstName=
            <c:out value="${user.firstName}"/>
        </p>
        <p>lastName=
            <c:out value="${user.lastName}"/>
        </p>
    </c:if>
    <hr/>
    <!-- NAGATION -->
    <a href="/main" style="text-transform: capitalize;">[GET]
        <c:out value="${to_main}"/>
    </a>
    <hr/>
    <br/>
</div>

<div class="content">
    <div class="authentication">
        <p style="text-transform: capitalize;">
            <c:out value="${logination}"/>:
        </p>
        <form method="post" action="/main">
            <input type="hidden" name="command" value="login"/>
            <input type="text" name="login" value="">
            <input type="password" name="password" value="">
            <button type="submit">
                <c:out value="${submit}"/>
            </button>
        </form>
        <hr/>
        <br/>
        <p style="text-transform: capitalize;">
            <c:out value="${registration}"/>:
        </p>
        <p>
            <c:out value="${user}" />
        </p>
        <form method="post" action="/">
            <input type="hidden" name="command" value="registration"/>
            <input type="text" name="login" value="">
            <input type="password" name="password_1" value="">
            <input type="password" name="password_2" value="">
            <button type="submit">
                <c:out value="${submit}"/>
            </button>
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