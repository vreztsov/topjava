<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>EditForm</title>
</head>
<body>
<div><h2>Редактирование</h2></div>
<div>
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <form method="post" action="meals">
        <br>
        <input hidden type="number" name="id" value="${meal.id}"/>
        <br>
        <div>
            Дата, время
        </div>
        <input type="datetime-local" name="dateTime" value="${meal.dateTime}"/>
        <br>
        <div>
            Описание
        </div>
        <input type="text" name="description" value="${meal.description}"/>
        <br>
        <div>
            Калории
        </div>
        <input type="number" name="calories" value="${meal.calories}"/>
        <br>
        <br>
        <div>
            <input type="submit" name="save" value="save"/>
        </div>
    </form>
</div>
</body>
</html>