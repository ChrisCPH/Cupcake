<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
        View orders
    </jsp:attribute>

    <jsp:attribute name="footer">
        View orders
    </jsp:attribute>

    <jsp:body>

        <h3>Here you can see all orders</h3>

        <table class="table table-striped">
            <thead>
            <tr>
                <th>Order id</th>
                <th>User</th>
                <th>Topping</th>
                <th>Bottom</th>
                <th>Status</th>
                <th>Date</th>
                <th>Price</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${requestScope.orderList}">
                <tr>
                    <td>${order.orderId}</td>
                    <td>${order.user}</td>
                    <td>${order.topping}</td>
                    <td>${order.bottom}</td>
                    <td>${order.status}</td>
                    <td>${order.date}</td>
                    <td>${order.price}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </jsp:body>
</t:pagetemplate>