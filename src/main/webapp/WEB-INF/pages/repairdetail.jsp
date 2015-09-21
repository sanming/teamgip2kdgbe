<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: wouter
  Date: 8/20/15
  Time: 2:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Repair created</title>
</head>
<body>
<p>
    Repair creation summary
</p>

<p>Item Details</p>
<table>
    <tr>
        <td>${repairResource.repairId}</td>
        <td>${repairResource.itemResource.productName}</td>
        <td>${repairResource.itemResource.brand}</td>
    </tr>
</table>

<p>Repair Details</p>
<table>
    <tr>
        <td>Submit Date</td>
        <td>${repairResource.repairDetailsResource.submitDate}</td>
    </tr>
    <tr>
        <td><s:message code="defect"/></td>
        <td>${repairResource.repairDetailsResource.defect}</td>
    </tr>
    <tr>
        <td><s:message code="pricemodel"/></td>
        <td>${repairResource.repairDetailsResource.priceModel}</td>
    </tr>
    <tr>
        <td><s:message code="status"/></td>
        <td>${repairResource.repairDetailsResource.status}</td>
    </tr>
    <td><s:message code="assigned"/></td>
    <td>${repairResource.repairDetailsResource.assigned}</td>
    </tr>
</table>

</body>
</html>
