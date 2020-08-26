<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<%@include file="WEB-INF/head.jspf" %>
<body id="page-top">
<%@include file="WEB-INF/header.jspf" %>
<section class="padding-medium story bg-light" id="about">
    <div class="container d-flex justify-content-center align-items-center">
        <div class="row">
            <div class="col-4 mr-4">
                <div class="div-img">
                </div>
            </div>
            <div class="col-7 ml-4">
                <h1 class="pb-1">Kontakt z nami</h1>
                <p>"${dataInfo.companyName}"
                </p>
                <p>"${dataInfo.companyAdress}"
                </p>
                <p>"${dataInfo.companyEmail}"
                </p>
                <p>"${dataInfo.companyPhone}"
                </p>
            </div>
        </div>
    </div>
</section>
<%@include file="WEB-INF/footer.jspf" %>
</body>
</html>
