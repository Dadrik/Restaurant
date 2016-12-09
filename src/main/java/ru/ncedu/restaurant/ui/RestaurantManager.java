package ru.ncedu.restaurant.ui;

import ru.ncedu.restaurant.model.dao.CategoryDao;
import ru.ncedu.restaurant.model.dao.FoodDao;
import ru.ncedu.restaurant.model.dao.PersistException;
import ru.ncedu.restaurant.model.entity.Category;
import ru.ncedu.restaurant.model.entity.Food;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import java.util.*;

public class RestaurantManager {
    public RestaurantManager() {
    }

    // Managed properties
    private FoodDao foodService;
    private CategoryDao categoryService;

    public void setFoodService(FoodDao foodService) {
        this.foodService = foodService;
    }

    public void setCategoryService(CategoryDao categoryService) {
        this.categoryService = categoryService;
    }

    // Frontend properties
    private String searchField = "";
    private Food currentEditable = null;
    private Map<Food, Boolean> foodChecked = new HashMap<>();

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public Food getCurrentEditable() {
        return currentEditable;
    }

    public void setCurrentEditable(Food currentEditable) {
        this.currentEditable = currentEditable;
    }

    public Map<Food, Boolean> getFoodChecked() {
        return foodChecked;
    }

    public void setFoodChecked(Map<Food, Boolean> foodChecked) {
        this.foodChecked = foodChecked;
    }

    // Additional fields
    private List<Food> foodList = null; // Current list of Food
    private List<SelectItem> categorySelectItems = null; // List of category selection menu

    public List<Food> getFood() {
        try {
            if (this.foodList == null) {
                if (searchField != null && !searchField.isEmpty())
                    foodList = foodService.findByName(searchField);
                else
                    foodList = foodService.getAll();
            }
        } catch (PersistException e) {
            handleException(e);
        }
        return this.foodList;
    }

    public List<SelectItem> getCategorySelectItems() {
        if (categorySelectItems == null) {
            try {
                List<Category> categories = categoryService.getAll();
                categories.add(0, new Category(0L, "No category"));
                categorySelectItems = new LinkedList<>();
                for (Category category : categories) {
                    categorySelectItems.add(new SelectItem(category, category.getName()));
                }
            } catch (PersistException e) {
                handleException(e);
            }
        }
        return categorySelectItems;
    }

    // Action Listeners
    public void searchFieldUpdated(ValueChangeEvent valueChangeEvent) {
        this.searchField = valueChangeEvent.getNewValue().toString();
    }

    // Actions
    public void searchFood() {
        foodList = null;
        foodChecked.clear();
        currentEditable = null;
    }

    public void addNew() {
        currentEditable = new Food();
        foodList.add(0, currentEditable);
        searchField = null;
    }

    public void deleteChecked() {
        for (Food food : foodChecked.keySet()) {
            if (food != null && foodChecked.get(food)) {
                try {
                    foodService.delete(food);
                } catch (PersistException ex) {
                    handleException(ex);
                }
            }
        }
        currentEditable = null;
        foodChecked.clear();
        foodList = null;
    }

    public void editFood() {
    }

    public void cancelChanges() {
        currentEditable = null;
        foodList = null;
    }

    public void saveChanges() {
        try {
            if (currentEditable.getId() == null)
                foodService.add(currentEditable);
            else
                foodService.update(currentEditable);
        } catch (PersistException ex) {
            handleException(ex);
        }
        currentEditable = null;
        foodList = null;
    }

    // Error handler
    private void handleException(RuntimeException ex) {
        StringBuilder details = new StringBuilder();
        Throwable causes = ex;
        while (causes.getCause() != null) {
            details.append(ex.getMessage());
            //details.append("    Caused by:");
            //details.append(causes.getCause().getMessage());
            causes = causes.getCause();
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), details.toString());
        FacesContext.getCurrentInstance().addMessage("errorTag", message);
    }
}
