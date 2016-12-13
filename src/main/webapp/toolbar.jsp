<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<h:commandButton value="Create"
                 styleClass="green-button"
                 action="#{restaurantManager.addNew}"
                 rendered="#{restaurantManager.currentEditable == null}"/>
<h:commandButton value="Delete"
                 styleClass="red-button"
                 action="#{restaurantManager.deleteChecked}"
                 rendered="#{restaurantManager.currentEditable == null}"/>
<h:commandButton value="Save"
                 styleClass="green-button"
                 action="#{restaurantManager.saveChanges}"
                 rendered="#{restaurantManager.currentEditable != null}"/>
<h:commandButton value="Cancel"
                 styleClass="red-button"
                 action="#{restaurantManager.cancelChanges}"
                 rendered="#{restaurantManager.currentEditable != null}"
                 immediate="true"/>
<h:inputText value="#{restaurantManager.searchField}"
             styleClass="search-input"
             valueChangeListener="#{restaurantManager.searchFieldUpdated}"
             immediate="true"/>
<h:commandButton value="Search"
                 styleClass="blue-button"
                 action="#{restaurantManager.searchFood}"
                 immediate="true"/>