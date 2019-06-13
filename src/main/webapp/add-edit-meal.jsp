<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>${meal.id eq 0 ? 'Add meal' : 'Edit meal'}</title>
    </head>

    <body>
        <h1>${meal.id eq 0 ? 'Add meal' : 'Edit meal'}</h1>
        <form method="POST" action="${pageContext.request.contextPath}/meals">
            <input type="hidden" name="id" value="${meal.id}">
            <input type="hidden" name="action" value="${meal.id eq 0 ? 'save-new' : 'update'}">

            <table align="center">
                <tr>
                    <td><label for="datetime">Дата/Время</label></td>
                    <td><input type="datetime-local" name="datetime" id="datetime" value="${meal.dateTime}"></td>
                </tr>
                <tr>
                    <td><label for="description">Описание</label></td>
                    <td><input type="text" name="description" id="description" value="${meal.description}"></td>
                </tr>
                <tr>
                    <td><label for="calories">Калории</label></td>
                    <td><input type="number" name="calories" id="calories" value="${meal.calories}"></td>
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