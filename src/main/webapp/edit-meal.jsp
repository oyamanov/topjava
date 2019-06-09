<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
    <head>
        <title>Edit meal</title>
    </head>

    <body>
        <h1>Edit meal</h1>
        <form method="POST" action="${pageContext.request.contextPath}/meals">
            <input type="hidden" name="id" value="${mealTo.id}">
            <input type="hidden" name="action" value="update">
            <table align="center">
                <tr>
                    <td><label for="datetime">Дата/Время</label></td>
                    <td><input type="datetime-local" id="datetime" name="datetime" value="<javatime:format pattern="yyyy-MM-dd HH:mm" value="${mealTo.dateTime}"/>"></td>
                </tr>
                <tr>
                    <td><label for="description">Описание</label></td>
                    <td><input type="text" id="description" name="description" value="${mealTo.description}"></td>
                </tr>
                <tr>
                    <td><label for="calories">Калории</label></td>
                    <td><input type="number" id="calories" name="calories" value="${mealTo.calories}"></td>
                </tr>
            </table>

            <table align="center">
                <tr>
                    <td><button type="submit">Сохранить</button></td>
                    <td><button type="button" onclick="window.location = '${pageContext.request.contextPath}/meals';">Отмена</button></td>
                </tr>
            </table>
        </form>
    </body>
</html>