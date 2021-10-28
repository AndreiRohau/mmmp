<!--
<%@ page language="java" contentType="text\html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
-->
<html>
    <body>
        <div>
            <h2>Home!</h2>
            <c:if test="${sessionScope.role != null}">
                <p>Authorized=<c:out value="${sessionScope.role}" /></p>
                <form method="get" action="/main" >
                    <input type="hidden" name="command" value="logout"/>
                    <button type="submit">Log out</button>
                </form>
            </c:if>

            <p><c:out value="${message}" /></p>
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
            <input type="password" name="password1" value="">
            <input type="password" name="password2" value="">
            <button type="submit">Submit</button>
        </form>
        <hr/>
        <br/>

    </body>
</html>