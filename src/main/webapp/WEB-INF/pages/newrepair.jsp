<%--
  Created by IntelliJ IDEA.
  User: wouter
  Date: 8/18/15
  Time: 3:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>New Repair</title>
    <link rel="stylesheet" type="text/css" href="resources/style/view.css" media="all">
    <script type="text/javascript" src="resources/js/view.js"></script>
    <script type="text/javascript" src="resources/js/calendar.js"></script>
</head>
<body>
<img id="top" src="resources/images/top.png" alt="">

<div id="form_container">

    <sf:form id="form_1043456" cssClass="appnitro"
             method="post" action="saverepair.do" modelAttribute="repairResource">
        <div class="form_description">
            <h2>Repair details</h2>
        </div>
        <ul>
            <li id="li_3">
                <label class="description" for="element_3"><s:message code="productName"/></label>

                <div>
                    <sf:input id="element_3" path="itemResource.productName" class="element text medium" type="text"
                              maxlength="255" value=""/>
                </div>
            </li>
            <li id="li_1">
                <label class="description" for="element_1"><s:message code="category"/></label>

                <div>
                    <sf:input id="element_1" path="itemResource.category" class="element text medium" type="text"
                              maxlength="255"
                              value=""/>
                </div>
            </li>
            <li id="li_2">
                <label class="description" for="element_2"><s:message code="brand"/></label>

                <div>
                    <sf:input id="element_2" path="itemResource.brand" class="element text medium" type="text"
                              maxlength="255"
                              value=""/>
                </div>
            </li>
            <li id="li_6">
                <label class="description"><s:message code="defect"/></label>
                <span>
                        <sf:radiobutton id="element_6_1" path="repairDetailsResource.defect" class="element radio"
                                        value="Electrical"/>
                        <label class="choice" for="element_6_1"><s:message code="electrical"/></label>
                        <sf:radiobutton id="element_6_2" path="repairDetailsResource.defect" class="elemgent radio"
                                        value="Mechanical"/>
                        <label class="choice" for="element_6_2"><s:message code="mechanical"/> </label>
                        <sf:radiobutton id="element_6_3" path="repairDetailsResource.defect" class="element radio"
                                        value="Other"/>
                        <label class="choice" for="element_6_3"><s:message code="other"/></label>
                </span>
            </li>
            <li id="li_4">
                <label class="description" for="element_4"><s:message code="description"/></label>

                <div>
                    <sf:textarea id="element_4" path="repairDetailsResource.description"
                                 class="element textarea medium"></sf:textarea>
                </div>
            </li>
            <li id="li_7">
                <label class="description"><s:message code="pricemodel"/> </label>
		<span>
			<sf:radiobutton id="element_7_1" path="repairDetailsResource.priceModel" class="element radio"
                            value="FIXED"/>
            <label class="choice" for="element_7_1">Fixed price</label>
            <sf:radiobutton id="element_7_2" path="repairDetailsResource.priceModel" class="element radio"
                            value="PER_HOUR"/>
            <label class="choice" for="element_7_2">Hourly</label>

		</span>
            </li>
            <li id="li_5">
                <label class="description"><s:message code="duedate"/> </label>
		<span>
			<sf:input id="element_5_1" path="repairDetailsResource.dueDate" class="element text" type="datetime"/>
			<label for="element_5_1">Date</label>
		</span>
                    <%--<span>
                        <input id="element_5_2" name="element_5_2" class="element text" size="2" maxlength="2" value="" type="text"> /
                        <label for="element_5_2">MM</label>
                    </span>
                    <span>
                         <input id="element_5_3" name="element_5_3" class="element text" size="4" maxlength="4" value="" type="text">
                        <label for="element_5_3">YYYY</label>
                    </span>

                    <span id="calendar_5">
                        <img id="cal_img_5" class="datepicker" src="resources/images/calendar.gif" alt="Pick a date.">
                    </span>
                            <script type="text/javascript">
                                Calendar.setup({
                                    inputField: "element_5_3",
                                    baseField: "element_5",
                                    displayArea: "calendar_5",
                                    button: "cal_img_5",
                                    ifFormat: "%B %e, %Y",
                                    onSelect: selectEuropeDate
                                }); </script>

                        </li>
            --%>
            <li class="buttons">
                <input id="saveForm" class="button_text" type="submit" name="submit" value=
                        <s:message code="send"/>/>
            </li>
        </ul>
    </sf:form>
</div>
<img id="bottom" src="resources/images/bottom.png" alt="">

</body>
</html>
