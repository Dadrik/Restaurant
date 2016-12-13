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
    static private final String SORT_ASC_CLASS = "sort_asc";
    static private final String SORT_DESC_CLASS = "sort_desc";
    static private final String SORT_INACT_CLASS = "sort_both";

    private String searchField = "";
    private Food currentEditable = null;
    private Map<Food, Boolean> foodChecked = new HashMap<>();
    private Map<String, String> sortClasses;

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

    public Map<String, String> getSortClasses() {
        if (sortClasses == null) {
            sortClasses = new HashMap<>();
            sortClasses.put("name", SORT_ASC_CLASS);
            sortClasses.put("cost", SORT_INACT_CLASS);
            sortClasses.put("category", SORT_INACT_CLASS);
        }
        return sortClasses;
    }

    // Additional fields
    private List<Food> foodList = null; // Current list of Food
    private List<SelectItem> categorySelectItems = null; // List of category selection menu

    public List<Food> getFood() {
        try {
            if (this.foodList == null) {
                if (searchField != null && !searchField.isEmpty())
                    foodList = foodService.findByNameOrCategory(searchField);
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
        cancelChanges();
        foodChecked.clear();
        foodList = null;
    }

    public void addNew() {
        currentEditable = new Food();
        foodList.add(0, currentEditable);
    }

    public void deleteChecked() {
        for (Food food : foodChecked.keySet()) {
            if (food != null && foodChecked.get(food)) {
                try {
                    foodService.delete(food);
                    foodList.remove(food);
                } catch (PersistException ex) {
                    handleException(ex);
                }
            }
        }
        foodChecked.clear();
    }

    public void editFood() {
    }

    public void cancelChanges() {
        if (currentEditable == null)
            return;
        if (currentEditable.getId() == null) {
            foodList.remove(currentEditable);
        } else {
            try {
                foodList.set(foodList.indexOf(currentEditable), foodService.getById(currentEditable.getId()));
            } catch (PersistException ex) {
                handleException(ex);
            }
        }
        currentEditable = null;
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
    }

    public void sortByName() {
        if (sortClasses.get("name").compareTo(SORT_ASC_CLASS) == 0) {
            sortClasses.replace("name", SORT_DESC_CLASS);
            foodList.sort((o1, o2) -> o2.getName().compareToIgnoreCase(o1.getName()));
        } else {
            sortClasses.replace("name", SORT_ASC_CLASS);
            sortClasses.replace("cost", SORT_INACT_CLASS);
            sortClasses.replace("category", SORT_INACT_CLASS);
            foodList.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        }
    }

    public void sortByCost() {
        if (sortClasses.get("cost").compareTo(SORT_ASC_CLASS) == 0) {
            sortClasses.replace("cost", SORT_DESC_CLASS);
            foodList.sort((o1, o2) -> o2.getCost().compareTo(o1.getCost()));
        } else {
            sortClasses.replace("cost", SORT_ASC_CLASS);
            sortClasses.replace("name", SORT_INACT_CLASS);
            sortClasses.replace("category", SORT_INACT_CLASS);
            foodList.sort(Comparator.comparing(Food::getCost));
        }
    }

    public void sortByCategory() {
        Comparator<Food> categoryComparator = (o1, o2) -> {
            if (o1.getCategory() == null) {
                if (o2.getCategory() == null) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                if (o2.getCategory() == null) {
                    return 1;
                } else {
                    return o1.getCategory().getName().compareToIgnoreCase(o2.getCategory().getName());
                }
            }
        };
        if (sortClasses.get("category").compareTo(SORT_ASC_CLASS) == 0) {
            sortClasses.replace("category", SORT_DESC_CLASS);
            foodList.sort(categoryComparator.reversed());
        } else {
            sortClasses.replace("category", SORT_ASC_CLASS);
            sortClasses.replace("name", SORT_INACT_CLASS);
            sortClasses.replace("cost", SORT_INACT_CLASS);
            foodList.sort(categoryComparator);
        }
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
