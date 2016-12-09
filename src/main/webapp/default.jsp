<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:view>
    <head>
        <title>Restaurant X</title>
        <link rel="stylesheet" type="text/css" href="css/table-style.css"/>
    </head>
    <body>
    <h1>Restaurant</h1>
    <h:form>
    <c:import url="toolbar.jsp"/>
    <c:import url="food.jsp"/>
    </h:form>
    <h:message id="errorTag" for="errorTag" showDetail="true" style="color:blue"/>
    </body>
</f:view>
</html>
