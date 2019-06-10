<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>${isEdit ? 'Edit meal' : 'Add meal'}</title>
    </head>

    <body>
        <h1>${isEdit ? 'Edit meal' : 'Add meal'}</h1>
        <form method="POST" action="${pageContext.request.contextPath}/meals">
            <c:if test="${isEdit}">
                <input type="hidden" name="id" value="${mealTo.id}">
            </c:if>
            <c:if test="${not isEdit}">
                <input type="hidden" name="id" value="">
            </c:if>

            <input type="hidden" name="action" value="${isEdit ? 'update' : 'save-new'}">

            <table align="center">
                <tr>
                    <td><label>Дата/Время</label></td>
                    <c:if test="${isEdit}">
                        <td><input type="datetime-local" name="datetime" value="${mealTo.dateTime}"></td>
                    </c:if>
                    <c:if test="${not isEdit}">
                        <td><input type="datetime-local" name="datetime"></td>
                    </c:if>
                </tr>
                <tr>
                    <td><label>Описание</label></td>
                    <c:if test="${isEdit}">
                        <td><input type="text" name="description" value="${mealTo.description}"></td>
                    </c:if>
                    <c:if test="${not isEdit}">
                        <td><input type="text" name="description"></td>
                    </c:if>
                </tr>
                <tr>
                    <td><label>Калории</label></td>
                    <c:if test="${isEdit}">
                        <td><input type="number" name="calories" value="${mealTo.calories}"></td>
                    </c:if>
                    <c:if test="${not isEdit}">
                        <td><input type="number" name="calories"></td>
                    </c:if>
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