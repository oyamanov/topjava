<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
    <head>
        <title>Meals</title>
    </head>

    <body>
        <h1>Meals</h1>
        <br/>
        <table align="center">
            <thead>
                <tr>
                    <th>Дата/Время</th>
                    <th>Описание</th>
                    <th>Калории</th>
                    <th>Редактировать</th>
                    <th>Удалить</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${mealTos}" var="mealTo">
                    <tr style="background-color:${mealTo.excess ? 'red' : '#556b2f'}">
                        <td><javatime:format pattern="yyyy-MM-dd HH:mm" value="${mealTo.dateTime}" /></td>
                        <td>${mealTo.description}</td>
                        <td>${mealTo.calories}</td>
                        <td><a href="${pageContext.request.contextPath}/meals?action=edit&mealId=${mealTo.id}">Edit</a></td>
                        <td><a href="${pageContext.request.contextPath}/meals?action=delete&mealId=${mealTo.id}">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div style="text-align: center;"><button type="button" onclick="window.location = '${pageContext.request.contextPath}/meals?action=add';">Добавить</button></div>
    </body>
</html>