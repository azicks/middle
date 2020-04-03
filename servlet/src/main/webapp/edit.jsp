<%@ page import="ru.job4j.servlets.ValidateService" %>
<%@ page import="ru.job4j.servlets.User" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<% final ValidateService service = ValidateService.getInstance();%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit user</title>
</head>
<body>
<%
    String action = request.getParameter("action");
    String id = request.getParameter("id");
    User u = service.getUser(Integer.parseInt(id));

    if ("Remove".equals(action)) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/delete.jsp");
        dispatcher.forward(request, response);
    }
%>
<form action="dispatch" method="post">
    <b><p>Edit user wit id = <%=id%>
    </p></b>
    <div>
        <label>Name: <input type="text" name="name" value="<%=u.getName()%>"></label>
    </div>
    <div>
        <label>Login: <input type="text" name="login" value="<%=u.getLogin()%>"></label>
    </div>
    <div>
        <label>Email: <input type="text" name="email" value="<%=u.getEmail()%>"></label>
    </div>
    <div>
        <input type="hidden" name="id" value="<%=id%>">
    </div>
    <div>
        <button name="action" value="update" type="submit">Ok</button>
    </div>
</form>
</body>
</html>
