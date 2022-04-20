<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<c:set var="servletPath" value="${pageContext.request.servletPath}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="da">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><jsp:invoke fragment="header"/></title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <!-- Bootstrap CSS -->
    <link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container">
            <a class="navbar-brand" href="/index.jsp">
                <img src="${pageContext.request.contextPath}/images/cupcake.png" width="400px;" class="img-fluid"/>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                    aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-end" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/index.jsp">Home</a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/order">Order</a>
                    <c:if test="${sessionScope.user.role.equals('admin')}">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/viewaccounts">Accounts</a>
                    </c:if>
                    <c:if test="${sessionScope.user.role.equals('admin')}">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/vieworders">Orders</a>
                    </c:if>
                    <c:if test="${sessionScope.user == null }">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/login.jsp">Login</a>
                    </c:if>
                    <c:if test="${sessionScope.user != null }">
                        <a class="nav-item nav-link" href="${pageContext.request.contextPath}/logout">Log out</a>
                    </c:if>
                    <c:if test="${sessionScope.user != null}">
                        <a class="nav-item nav-link">Balance: ${sessionScope.user.balance} kr.</a>
                    </c:if>
                    <c:if test="${sessionScope.user != null}">
                        <a class="nav-item nav-link">Email: ${sessionScope.user.email}</a>
                    </c:if>
                </div>
            </div>
        </div>
    </nav>
</header>

<div id="body" class="container mt-4" style="min-height: 400px;">
    <h1><jsp:invoke fragment="header"/></h1>
    <jsp:doBody/>
</div>

<!-- Footer -->
<div class="container mt-3">
    <hr/>
    <div class="row mt-4">
        <div class="col">
            Nørgaardsvej 30<br/>
            2800 Lyngby
        </div>
        <div class="col">
            <jsp:invoke fragment="footer"/><br/>
            <p>&copy; 2022 Olsker Cupcakes</p>
        </div>
        <div class="col">
            Datamatikeruddannelsen<br/>
            2. semester forår 2022
        </div>
    </div>

</div>

</div>

<!-- Bootstrap Bundle with Popper -->
<script src="${contextPath}/js/bootstrap.bundle.min.js"></script>

</body>
</html>