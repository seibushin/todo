<%--
    Created by Sebastian Meyer (s.meyer@seibushin.de)
    (2019-02-13)
 --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date"/>
<jsp:useBean id="dao" class="de.seibushin.todo.dao.TodoDao"/>
<fmt:formatDate var="now" value="${now}" pattern="yyyy-MM-dd"/>
<html lang="en">
<head>
    <title>To-Do</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css"
          integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js"
            crossorigin="anonymous"></script>
    <script src="js/todo.js"></script>
</head>
<body>
<main class="w3-white w3-container w3-content w3-center w3-padding-64">
    <div class="create-todo">
        <h1>To-Do List</h1>
        <div>
            <form id="create" method="post" action="create">
                <input class="input w3-border" type="text" required placeholder="Title" name="title"/>
                <input class="input w3-border" type="text" placeholder="Description" name="description"/>
                <input class="input w3-border" type="date" required value="${now}" min="${now}" name="dueDate"/>
                <input class="w3-button w3-black" type="submit" value="ADD"/>
            </form>
        </div>
    </div>

    <div class="search-todo w3-padding-16">
        <h2>Your To-Do's</h2>
        <div id="search-w">
            <input id="search" class="input w3-white w3-border" type="text" value="" placeholder="Search"
                   name="search"/>
            <div>
                Done?
                <span class="search-done">
                <i class="check icon far"></i>
            </span>
            </div>
        </div>
    </div>
    <div id="todo-list" class="w3-center-left w3-padding-16">
        <c:forEach items="${dao.todos}" var="todo">
            <div class="to-do w3-padding-8">
                <div class="w3-right w3-margin-left">
                    <i class="delete icon fas fa-times"></i>
                </div>
                <div class="">
                    <span class="check-w">
                        <i class="done check icon far ${todo.done}"></i>
                    </span>
                    <span class="title">${todo.title}</span>
                    <span class="dueDate">${todo.dueDate}</span>
                    <form class="edit" method="post" action="update">
                        <div class="description-edit-w">
                            <input class="description description-edit input w3-white" type="text"
                                   value="${todo.description}"
                                   placeholder="No description" name="description"/>
                            <i class="update icon fas fa-pencil-alt"></i>
                        </div>
                        <input class="id" type="hidden" name="id" value="${todo.id}"/>
                    </form>
                </div>
            </div>
        </c:forEach>
        <div class="dummy to-do w3-padding-8">
            <div class="w3-right w3-margin-left">
                <i class="delete icon fas fa-times"></i>
            </div>
            <div class="">
                    <span class="check-w">
                        <i class="done check icon far"></i>
                    </span>
                <span class="title"></span>
                <span class="dueDate"></span>
                <form class="edit" method="post" action="update">
                    <div class="description-edit-w">
                        <input class="description description-edit input w3-white" type="text"
                               value=""
                               placeholder="No description" name="description"/>
                        <i class="update icon fas fa-pencil-alt"></i>
                    </div>
                    <input class="id" type="hidden" name="id" value=""/>
                </form>
            </div>
        </div>
    </div>
</main>

<footer class="w3-container w3-padding-64 w3-center w3-opacity w3-light-grey w3-xlarge">
    <p class="w3-medium">
        Created by Sebastian Meyer
    </p>
    <p class="w3-small">
        <a href="mailto:s.meyer@seibushin.de)">s.meyer@seibushin.de</a>
    </p>
</footer>
</body>
</html>
