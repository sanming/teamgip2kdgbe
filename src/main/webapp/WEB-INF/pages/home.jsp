<jsp:useBean id="userResource" scope="request"
             type="be.kdg.repaircafe.frontend.controllers.resources.users.UserResource"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welkom in het Repair Cafe</title>
</head>
<body>
<h1>Welkom beste gebruiker ...</h1>
<ul>
    <li>${userResource.username}</li>
    <li>${userResource.personResource.firstname}</li>
    <li>${userResource.personResource.lastname}</li>
    <li>${userResource.personResource.addressResource.street}</li>
    <li>${userResource.personResource.addressResource.nr}</li>
    <li>${userResource.personResource.addressResource.zip}</li>
    <li>${userResource.personResource.addressResource.city}</li>
</ul>

<a href="getrepairs.do">Mijn repararaties</a>
</div>
</body>
</html>
