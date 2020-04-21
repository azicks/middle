<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="ru.job4j.servlets.Role" %>
<%@ page import="ru.job4j.servlets.User" %>
<%@ page import="ru.job4j.servlets.UserStorage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users list</title>
</head>
<body>
<table cellpadding="5">
    <tr>
        <td>
            <h2>Welcome, <c:out value="${sessionScope.name}"/> (<c:out value="${sessionScope.role.value()}"/>)</h2>
        </td>
        <td>
            <form method="post" action="edit">
                <% User u = UserStorage.getInstance().findByLogin((String) request.getSession().getAttribute("login")); %>
                <input type="hidden" name="id" value="<%=u.getId()%>">
                <button name="action" value="gotoEditView" type="submit">Edit</button>
            </form>
        </td>
    </tr>
</table>
<c:if test="${users.size() > 0}">
    <table border="1" cellpadding="5">
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.id}"/>
                </td>
                <td><c:out value="${user.role.value()}"/>
                </td>
                <td>Name: <c:out value="${user.name}"/>
                </td>
                <td>Login: <c:out value="${user.login}"/>
                </td>
                <td>Email: <c:out value="${user.email}"/>
                </td>
                <td>Created: <c:out value="${user.createDate}"/>
                </td>
                <td><a href="?getImage=${user.imageFile}&id=${user.id}">
                    <img src="?getImage=${user.imageFile}&id=${user.id}" width="100px"
                         height="100px"/>
                </a>
                </td>
                <c:if test="${sessionScope.role.equals(Role.ADMIN)}">
                    <td>
                        <form method="post">
                            <input type="hidden" name="id" value="${user.id}"/>
                            <button name="action" value="gotoEditView" type="submit">Edit</button>
                            <button name="action" value="gotoRemoveView" type="submit">Remove</button>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</c:if>
<br>
<c:if test="${sessionScope.role.equals(Role.ADMIN)}">
    <form method="get" action="create">
        <input type="submit" value="Create new user">
    </form>
</c:if>
<br>
<form method="post" action="/">
    <button name="action" value="logout" type="submit">Logout</button>
</form>
</body>
</html>
