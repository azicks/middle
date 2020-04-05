<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sure to remove?</title>
</head>
<body>
<form method="post">
    <b><p>Are you sure to delete user with id = ${id}</p></b>
    <input type="hidden" name="id" value="${id}">
    <button name="action" value="delete" type="submit">Yes</button>
    <input type="submit" value="No" name="action">
    </form>
</body>
</html>
