<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Список еды</h2>
<table>
    <tr>
        <th>Дата, время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach var="mealTo" items="${mealList}">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr bgcolor=${mealTo.excess ? "FF0000" : "00FF00"}>
            <td class="data">
                    ${mealTo.dateTime.format(dateTimeFormatter)}
            </td>
            <td class="data">${mealTo.description}</td>
            <td class="data">${mealTo.calories}</td>
            <td class="action"><a href="meals?action=update&id=${mealTo.id}">Изменить</a></td>
            <td class="action"><a href="meals?action=delete&id=${mealTo.id}">Удалить</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>