<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Add meal</title>
    </head>

    <body>
        <h1>Add meal</h1>
        <form method="POST" action="${pageContext.request.contextPath}/meals">
            <input type="hidden" name="action" value="save-new">
            <table align="center">
                <tr>
                    <td><label for="datetime">Дата/Время</label></td>
                    <td><input type="datetime-local" id="datetime" name="datetime"></td>
                </tr>
                <tr>
                    <td><label for="description">Описание</label></td>
                    <td><input type="text" id="description" name="description"></td>
                </tr>
                <tr>
                    <td><label for="calories">Калории</label></td>
                    <td><input type="number" id="calories" name="calories"></td>
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