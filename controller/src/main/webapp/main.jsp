<html>
<head>
    <script src="static//index.js"></script>
</head>
    <body>
        <h2>Main!</h2>
        <hr/>
        <!-- NAGATION -->
        <a href="/">[GET] Go to Home.jsp</a>
        <p>Message from server (requestScope.serverMessage): ${requestScope.serverMessage}</p>
        <p>Message from server (requestScope.users): ${requestScope.users}</p>
        <p>Message from server (requestScope.users): ${requestScope.user[0]}</p>
        <p></p>
    </body>
</html>