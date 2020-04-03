<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sure to remove?</title>
</head>
<body>
<%
    String id = request.getParameter("id");
%>
<form method="post" action="dispatch">
    <b><p>Are you sure to delete user with id = <%=id%></p></b>
    <input type="hidden" name="id" value="<%=id%>">
    <button name="action" value="delete" type="submit">Yes</button>
    <input type="submit" value="No" name="action">
    </form>
</body>
</html>
