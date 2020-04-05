<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create user</title>
</head>
<body>
<form method="post">
    <b><p>Create new user: </p></b>
    <div>
        <label>Name: <input type="text" name="name"></label>
    </div>
    <div>
        <label>Login: <input type="text" name="login"></label>
    </div>
    <div>
        <label>Email: <input type="text" name="email"></label>
    </div>
    <button name="action" value="add" type="submit">Ok</button>
</form>
</body>
</html>
