<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<%@include file="WEB-INF/head.jspf" %>


<body>
<%@include file="WEB-INF/dashboardHeader.jspf" %>

<section class="dashboard-section">
    <form action="post">
        <%@include file="WEB-INF/sideBar.jspf" %>

        <a href="<c:url value="/app/recipe/list"/>"  class="btn btn-warning rounded-0 text-light m-1">Anuluj</a>
        <a href="<c:url value="/app/recipe/delete"/>" class="btn btn-danger rounded-0 text-light m-1">Usuń</a>
    </form>
</section>
<%@include file="WEB-INF/footer.jspf" %>
</body>
</html>