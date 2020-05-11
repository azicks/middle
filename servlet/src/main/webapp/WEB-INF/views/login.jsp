<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Log in</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
    <script
            src="https://code.jquery.com/jquery-3.5.1.js"
            integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <div id="cred" class="row p-1" style="display: none;background-color: mistyrose; margin-left: auto; margin-right: auto">
        <h6 id="credmsg" style="width: 100%; text-align: center; vertical-align: middle">123</h6>
    </div>
    <div class="row">
        <div class="col-md-4">
            <form id="loginForm" method="post" action="/signin">
                <div class="m-2">
                    <b>Please log in</b>
                </div>
                <div class="form-group">
                    <label for="login">Login</label>
                    <input class="form-control" id="login" type="text" name="login" required="required"
                           placeholder="Enter login">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input class="form-control" id="password" type="password" name="password"
                           required="required" placeholder="Enter password">
                </div>
                <input class="btn btn-primary" type="submit"/>
            </form>
        </div>
    </div>
</div>
<script>
    let form = $('#loginForm');
    form.on('submit', function (event) {
        event.preventDefault();
        let login = $('#login').val();
        let password = $('#password').val();

        if (login && password) {
            doLogin(login, password)
        }
    });

    function doLogin(login, password) {
        $.ajax({
            type: 'POST',
            url: '/signin',
            data: {"login": login, "password": password},
            success: function (resp, status, xhr) {
                console.log(xhr.getResponseHeader('AUTH_OK'));
                if(xhr.getResponseHeader('AUTH_OK') === '1') {
                    window.location.href = '/';
                }
            },
            error: function (xhr) {
                console.log(xhr.status)
                $('#cred').show('fast');
                $('#credmsg').text(xhr.responseText);
            }
        });
    }
</script>
</body>
</html>
