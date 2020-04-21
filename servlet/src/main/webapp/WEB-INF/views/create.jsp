<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create user</title>
</head>
<body>
<form method="post" action="create" enctype="multipart/form-data">
    <b><p>Create new user: </p></b>
    <div>
        <label>Name: <input type="text" name="name"></label>
    </div>
    <div>
        <label>Login: <input type="text" name="login"></label>
    </div>
    <div>
        <label>Password: <input type="password" name="password"></label>
    </div>
    <div>
        <label>Email: <input type="text" name="email"></label>
    </div>
    <div>
        <select name="role">
            <c:forEach items="${roles}" var="role">
                <option value="${role}">${role}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        Add image for user:<br>
        <input type="file" name="image" accept="image/*">
    </div>
    <br>
    <button name="action" value="add" type="submit">Create user</button>
</form>
</body>
</html>
