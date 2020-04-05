<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users list</title>
</head>
<body>
<c:if test="${users.size() > 0}">
<table border="1" cellpadding="5">
    <c:forEach items="${users}" var="user">
    <tr>
        <td><c:out value="${user.id}"/>
        </td>
        <td>Name: <c:out value="${user.name}"/>
        </td>
        <td>Login: <c:out value="${user.login}"/>
        </td>
        <td>Email: <c:out value="${user.email}"/>
        </td>
        <td>Created: <c:out value="${user.createDate}"/>
        </td>
        <td>
            <form method="post">
                <input type="hidden" name="id" value="${user.id}"/>
                <input type="submit" value="Edit" name="action"/>
                <input type="submit" value="Remove" name="action"/>
            </form>
        </td>
    </tr>
    </c:forEach>
</table>
</c:if>
<br>
<form method="post">
    <button name="action" value="Create" type="submit">Create new user</button>
</form>
</body>
</html>
