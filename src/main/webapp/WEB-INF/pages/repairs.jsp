<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<table>
    <c:forEach var="repairResource" items="${repairResources}">
        <tr>
            <td>${repairResource.repairId}</td>
            <td>${repairResource.itemResource.productName}</td>
            <td>${repairResource.itemResource.brand}</td>
        </tr>
    </c:forEach>
</table>
<a href="newrepair.do"><s:message code="create_repair"/> </a>
</body>
</html>
