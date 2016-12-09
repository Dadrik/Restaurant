package ru.ncedu.restaurant.ui;

import ru.ncedu.restaurant.model.dao.CategoryDao;
import ru.ncedu.restaurant.model.dao.PersistException;
import ru.ncedu.restaurant.model.entity.Category;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class CategoryConverter implements Converter {
    private CategoryDao categoryService;

    public void setCategoryService(CategoryDao categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            if (Long.valueOf(value) == 0L)
                return null;
            return categoryService.getById(Long.valueOf(value));
        } catch (PersistException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        return ((Category) value).getId().toString();
    }
}