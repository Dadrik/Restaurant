package ru.ncedu.restaurant.ui;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class FoodNameValidator implements Validator {
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 64;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String name = o.toString();
        FacesMessage message;
        if (name.length() < MIN_LENGTH) {
            message = new FacesMessage("Name validation failed.", "Name is too short.");
        } else if (name.length() > MAX_LENGTH) {
            message = new FacesMessage("Name validation failed.", "Name is too long.");
        } else {
            return;
        }
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }
}
