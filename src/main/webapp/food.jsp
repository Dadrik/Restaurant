<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>

<h:panelGrid>
    <h:dataTable value="#{restaurantManager.food}" var="food"
                 border="1"
                 styleClass="food-table"
                 headerClass="food-table-header"
                 rowClasses="food-table-odd-row,food-table-even-row">
        <h:column>
            <h:selectBooleanCheckbox value="#{restaurantManager.foodChecked[food]}"/>
        </h:column>
        <h:column>
            <f:facet name="header">
                <h:outputText value="Name"/>
            </f:facet>
            <h:panelGrid>
                <h:inputText id="name" value="#{food.name}"
                             rendered="#{restaurantManager.currentEditable == food}">
                    <f:validateLength minimum="3" maximum="32"/>
                </h:inputText>
                <h:outputText value="#{food.name}"
                              rendered="#{restaurantManager.currentEditable != food}"/>

                <h:message for="name" style="font:5px;color:red"/>
            </h:panelGrid>
        </h:column>
        <h:column>
            <f:facet name="header">
                <h:outputText value="Cost"/>
            </f:facet>
            <h:panelGrid>
                <h:inputText id="cost" value="#{food.cost}"
                             rendered="#{restaurantManager.currentEditable == food}">
                    <f:validateLongRange minimum="1"/>
                </h:inputText>
                <h:outputText value="#{food.cost}"
                              rendered="#{restaurantManager.currentEditable != food}"/>

                <h:message for="cost" style="font:5px;color:red"/>
            </h:panelGrid>
        </h:column>
        <h:column>
            <f:facet name="header">
                <h:outputText value="Category"/>
            </f:facet>
            <h:selectOneMenu value="#{food.category}"
                             rendered="#{restaurantManager.currentEditable == food}"
                             converter="#{categoryConverter}">
                <f:selectItems value="#{restaurantManager.categorySelectItems}"/>
            </h:selectOneMenu>
            <h:outputText value="#{food.category == null ? 'No category' : food.category.name}"
                          rendered="#{restaurantManager.currentEditable != food}"/>
        </h:column>
        <h:column>
            <f:facet name="header">
                <h:outputText value="Action"/>
            </f:facet>
            <h:commandLink value="Edit"
                           action="#{restaurantManager.editFood}"
                           rendered="#{restaurantManager.currentEditable == null}">
                <t:updateActionListener property="#{restaurantManager.currentEditable}"
                                        value="#{food}"/>
            </h:commandLink>
        </h:column>
    </h:dataTable>
</h:panelGrid>

