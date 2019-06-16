<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }

        table {
            margin: 0 auto;
            border: solid 1px;
        }

        #addMealDiv {
            text-align: center;
        }

        .spacer {
            width: 150px;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>

    <form method="POST" action="meals">
        <input type="hidden" name="action" value="filter">
        <table>
            <tr>
                <td><label for="fromDate">От даты</label></td>
                <td><label for="toDate">До даты</label></td>
                <td class="spacer"></td>
                <td><label for="fromTime">От времени</label></td>
                <td><label for="toTime">До времени</label></td>
            </tr>
            <tr>
                <td><input type="date" id="fromDate" name="fromDate"></td>
                <td><input type="date" id="toDate" name="toDate"></td>
                <td class="spacer"></td>
                <td><input type="time" id="fromTime" name="fromTime"></td>
                <td><input type="time" id="toTime" name="toTime"></td>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td><button type="button" onclick="window.location='meals';">Отменить</button></td>
                <td><button type="submit">Отфильтровать</button></td>
            </tr>
        </table>
    </form>
    <br>
    <br>

    <div id="addMealDiv"><a href="meals?action=create">Add Meal</a></div>
    <br><br>
    <table id="mealTable" border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>