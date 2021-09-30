<html>
    <body>
        <h2>Home!</h2>
        <hr/>
        <!-- NAGATION -->
        <a href="/main">[GET] Go to Main.jsp</a>
        <p>Message from server (requestScope.serverMessage): ${requestScope.serverMessage}</p>
        <p>Message from server (requestScope.users): ${requestScope.users}</p>
        <p>Message from server (requestScope.users): ${requestScope.users[0].login}</p>
        <p>Message from server (requestScope.users): ${requestScope.users[1].login}</p>
        <p>Message from server (requestScope.users): ${requestScope.users[2].login}</p>
    </body>
</html>