<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<t:pagetemplate>
    <jsp:attribute name="header">
        Order
    </jsp:attribute>

    <jsp:attribute name="footer">
        Order
    </jsp:attribute>

    <jsp:body>

        <c:if test="${sessionScope.user == null}">
            <p>Please login or create a user before ordering</p>
        </c:if>

        <h3>You can order your cupcake here</h3>

        <form action="order" method="post">
            <label for="bot">Bottom</label>
            <select name="bottom" id="bot">
                <c:forEach var="bot" items="${requestScope.bottomList}">
                <option value="${bot.bottomName}">${bot.bottomName}</option>
                </c:forEach>
            </select>
        </form>
        <br>

        <form action="order" method="post">
            <label for="top">Topping</label>
            <select name="topping" id="top">
                <c:forEach var="top" items="${requestScope.toppingList}">
                <option value="${top.toppingName}">${top.toppingName}</option>
                </c:forEach>
            </select>
            <br>
            <br>
            <input type="submit" value="Bestil" />
        </form>
    </jsp:body>
</t:pagetemplate>