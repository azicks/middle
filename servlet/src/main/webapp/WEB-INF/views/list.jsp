<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="ru.job4j.servlets.Role" %>
<%@ page import="ru.job4j.servlets.User" %>
<%@ page import="ru.job4j.servlets.UserStorage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Users list</title>
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
    <div class="row p-3">
        <h3>Welcome, <c:out value="${sessionScope.name}"/> (<c:out value="${sessionScope.role.value()}"/>)</h3>
        <form class="ml-2" method="post" action="edit">
            <% User u = UserStorage.getInstance().findByLogin((String) request.getSession().getAttribute("login")); %>
            <input type="hidden" name="id" value="<%=u.getId()%>">
            <button class="btn btn-outline-secondary" name="action" value="gotoEditView" type="submit">Edit</button>
        </form>
    </div>

    <div class="row">
        <table class="table table-sm table-bordered text-center">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Role</th>
                <th scope="col">Name</th>
                <th scope="col">Login</th>
                <th scope="col">Email</th>
                <th scope="col">Created</th>
                <th scope="col">Address</th>
                <th scope="col">Picture</th>
                <c:if test="${sessionScope.role.equals(Role.ADMIN)}">
                    <th scope="col">Action</th>
                </c:if>
            </tr>
            </thead>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td class="align-middle"><c:out value="${user.id}"/>
                    </td>
                    <td class="align-middle"><c:out value="${user.role.value()}"/>
                    </td>
                    <td class="align-middle"><c:out value="${user.name}"/>
                    </td>
                    <td class="align-middle"><c:out value="${user.login}"/>
                    </td>
                    <td class="align-middle"><c:out value="${user.email}"/>
                    </td>
                    <td class="align-middle"><c:out value="${user.createDate}"/>
                    </td>
                    <td class="align-middle"><c:out value="${user.country} ${user.city}"/>
                    </td>
                    <td class="align-middle">
                        <c:if test="${user.hasImage(path)}">
                            <a href="?getImage=${user.imageFile}&id=${user.id}">
                                <img src="?getImage=${user.imageFile}&id=${user.id}" width="100px"
                                     height="100px"/>
                            </a>
                        </c:if>
                    </td>
                    <c:if test="${sessionScope.role.equals(Role.ADMIN)}">
                        <td class="align-middle">
                            <div class="mt-3">
                                <form method="post">
                                    <div class="form-group">
                                        <input type="hidden" name="id" value="${user.id}"/>
                                        <button class="btn btn-outline-info" name="action" value="gotoEditView"
                                                type="submit">Edit
                                        </button>
                                        <button class="btn btn-outline-danger" name="action" type="button"
                                                data-toggle="modal" data-target="#deleteModal"
                                                data-id="${user.id}">Remove
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </div>

    <c:if test="${sessionScope.role.equals(Role.ADMIN)}">
        <div class="row">
            <form method="get" action="create">
                <input class="btn btn-outline-success" type="submit" value="Create new user">
            </form>
        </div>
    </c:if>

    <div class="row mt-3">
        <form method="post">
            <button class="btn btn-outline-warning" name="action" value="logout" type="submit">Logout</button>
        </form>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Sure to delete?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <form method="post">
                    <input id="modal_id" type="hidden" name="id">
                    <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" name="action" value="delete" class="btn btn-outline-danger">Delete</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    $('#deleteModal').on('show.bs.modal', function (event) {
        let button = $(event.relatedTarget);
        let id = button.data('id');
        let modal = $(this);
        modal.find('.modal-body').text('Are you sure to delete user with id = ' + id);
        $("#modal_id").val(id);
    });
</script>
</body>
</html>
