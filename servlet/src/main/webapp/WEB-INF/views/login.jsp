<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Log in</title>
</head>
<body>
<c:if test="${error != ''}">
    <div style="background-color: mistyrose">
        <c:out value="${error}"/>
    </div>
</c:if>
    <form method="post" action="/signin">
        <b><p>Please log in
        </p></b>
        <div>
            <label>Login: <input type="text" name="login"></label>
        </div>
        <div>
            <label>Password: <input type="password" name="password"></label>
        </div>
        <div>
            <input type="submit"/>
        </div>
    </form>
</body>
</html>
