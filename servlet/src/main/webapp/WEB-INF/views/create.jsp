<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Create user</title>
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
    <script src="scripts/autocomplete.js"></script>
    <link rel="stylesheet" href="css/autocomplete.css">
</head>
<body>
<div class="container">
    <div class="row mt-3">
        <h4>Create new user</h4>
    </div>
    <div class="row mt-3">
        <form method="post" action="create" enctype="multipart/form-data">
            <div class="row">
                <div class="col-md">
                    <div class="form-group">
                        <label for="name">Name</label>
                        <input class="form-control" id="name" type="text" name="name" required="required">
                    </div>
                    <div class="form-group">
                        <label for="login">Login</label>
                        <input class="form-control" id="login" type="text" name="login" required="required">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input class="form-control" id="password" type="password" name="password" required="required">
                    </div>
                </div>
                <div class="col-md">
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input class="form-control" id="email" type="text" name="email">
                    </div>
                    <div class="form-group">
                        <label for="email">Country</label>
                        <input class="form-control" id="country" type="text" name="country">
                    </div>
                    <div class="form-group autocomplete">
                        <label for="email">City</label>
                        <input class="form-control" autocomplete="disabled" id="city" type="text" name="city">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <select class="form-control" name="role">
                    <c:forEach items="${roles}" var="role">
                        <option value="${role}">${role}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="file">Add image for user</label>
                <input id="file" class="form-control-file" type="file" name="image" accept="image/*">
            </div>
            <br>
            <button class="btn btn-outline-success" name="action" value="add" type="submit">Create user</button>
        </form>
    </div>
</div>

<script>
    $(document).ready(function () {
        let cities = [];
        $.ajax({
            type: 'GET',
            url: '/addr',
            dataType: 'json',
            success: function (data) {
                cities = data['cities'];
                autocomplete(document.getElementById("city"), cities);
            },
            error: function (xhr, error) {
                alert(error + ': ' + xhr.responseText);
            }
        });
    })
</script>

</body>
</html>
