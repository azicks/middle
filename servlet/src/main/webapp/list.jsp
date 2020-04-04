<%@ page import="ru.job4j.servlets.ValidateService" %>
<%@ page import="ru.job4j.servlets.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<% final ValidateService service = ValidateService.getInstance(); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users list</title>
</head>
<body>
<%
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
    if (service.getUsers().size() > 0) { %>
<table border="1" cellpadding="5">
    <% for (User u : service.getUsers()) { %>
    <tr>
        <td><%=u.getId()%>
        </td>
        <td>User: <%=u.getName()%>
        </td>
        <td>Login: <%=u.getLogin()%>
        </td>
        <td>Email: <%=u.getEmail()%>
        </td>
        <td>Created: <%=u.getCreateDate()%>
        </td>
        <td>
            <form action="edit" method="post">
                <input type="hidden" name="id" value="<%=u.getId()%>"/>
                <input type="submit" value="Edit" name="action"/>
                <input type="submit" value="Remove" name="action"/>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<% } %>
<br>
<form action="create" method="post">
    <input type="submit" value="Add new user">
</form>
</body>
</html>
