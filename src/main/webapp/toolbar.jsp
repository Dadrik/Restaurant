<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<h:commandButton value="Create"
                 action="#{restaurantManager.addNew}"
                 rendered="#{restaurantManager.currentEditable == null}"/>
<h:commandButton value="Delete"
                 action="#{restaurantManager.deleteChecked}"
                 rendered="#{restaurantManager.currentEditable == null}"/>
<h:commandButton value="Save"
                 action="#{restaurantManager.saveChanges}"
                 rendered="#{restaurantManager.currentEditable != null}"/>
<h:commandButton value="Cancel"
                 action="#{restaurantManager.cancelChanges}"
                 rendered="#{restaurantManager.currentEditable != null}"
                 immediate="true"/>
<h:inputText value="#{restaurantManager.searchField}"
             valueChangeListener="#{restaurantManager.searchFieldUpdated}"
             immediate="true"/>
<h:commandButton value="search"
                 action="#{restaurantManager.searchFood}"/>