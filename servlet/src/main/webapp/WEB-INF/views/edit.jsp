<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ru.job4j.servlets.Role" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit user</title>
</head>
<body>
<form method="post">
    <b><p>Edit user wit id = ${user.id}
    </p></b>
    <div>
        <label>Name: <input type="text" name="name" value="${user.name}"></label>
    </div>
    <div>
        <label>Login: <input type="text" name="login" value="${user.login}"></label>
    </div>
    <div>
        <label>Email: <input type="text" name="email" value="${user.email}"></label>
    </div>
    <c:if test="${sessionScope.role.equals(Role.ADMIN)}">
        <div>
            <select name="role">
                <c:forEach items="${roles}" var="role">
                    <option value="${role}">${role}</option>
                </c:forEach>
            </select>
        </div>
    </c:if>
    <div>
        <input type="hidden" name="id" value="${user.id}">
    </div>
    <div>
        <button name="action" value="update" type="submit">Ok</button>
    </div>
</form>
</body>
</html>
