<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                <td><a href="?getImage=${user.imageFile}&id=${user.id}">
                    <img src="?getImage=${user.imageFile}&id=${user.id}" width="100px"
                         height="100px"/>
                </a>
                </td>
                <td>
                    <form method="post">
                        <input type="hidden" name="id" value="${user.id}"/>
                        <button name="action" value="gotoEditView" type="submit">Edit</button>
                        <button name="action" value="gotoRemoveView" type="submit">Remove</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<br>
<form method="get" action="create">
    <input type="submit" value="Create new user">
</form>
</body>
</html>
