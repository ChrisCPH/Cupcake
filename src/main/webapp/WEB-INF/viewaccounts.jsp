<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page errorPage="../error.jsp" isErrorPage="false" %>

<t:pagetemplate>
    <jsp:attribute name="header">
        View accounts
    </jsp:attribute>

    <jsp:attribute name="footer">
        View accounts
    </jsp:attribute>

    <jsp:body>

        <h3>Here you can see all users</h3>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Email</th>
                    <th>Password</th>
                    <th>Role</th>
                    <th>Balance</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${requestScope.userList}">
                    <tr>
                        <td>${user.email}</td>
                        <td>${user.password}</td>
                        <td>${user.role}</td>
                        <td>${user.balance}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </jsp:body>
</t:pagetemplate>