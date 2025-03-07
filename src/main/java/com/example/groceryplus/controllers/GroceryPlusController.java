package com.example.groceryplus.controllers;

import com.example.groceryplus.model.Grocery;
import com.example.groceryplus.model.RecipeDTO;
import com.example.groceryplus.services.GroceryPlusException;
import com.example.groceryplus.services.GroceryPlusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("groceryplus")
public class GroceryPlusController {
    private GroceryPlusService groceryPlusService;

    public GroceryPlusController(GroceryPlusService groceryPlusService) {
        this.groceryPlusService = groceryPlusService;
    }

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("all_recipes")
    public String allRecipes(Model model) {
        List<RecipeDTO> list = groceryPlusService.getAllRecipes();

        model.addAttribute("list", list);
        return "all_recipes";
    }

    @GetMapping("all_recipes/{recipeName}")
    public String getSingleRecipe(@PathVariable String recipeName, Model model){
        RecipeDTO recipe = groceryPlusService.getSingleRecipe(recipeName);
        model.addAttribute("recipe", recipe);

        return "single_Recipe";
    }


    @GetMapping("all_groceries")
    public String allGroceries(Model model) throws GroceryPlusException {
        Grocery grocery = new Grocery();
        model.addAttribute("grocery", grocery);

        List<Grocery> list = groceryPlusService.getAllGroceries();
        System.out.println(list);
        model.addAttribute("list", list);
        return "all_groceries";
    }

    @PostMapping("create_new_grocery")
    public String submitForm(@ModelAttribute("grocery") Grocery grocery) {
        System.out.println(grocery);
        groceryPlusService.addGrocery(grocery);
        return "redirect:/groceryplus/all_groceries";
    }

    @PostMapping("add_to_shoppinglist")
    public String addToShoppingList(@ModelAttribute("grocery") Grocery grocery) throws GroceryPlusException {
        System.out.println(grocery);
        groceryPlusService.addToShoppinglist(grocery);
        return "redirect:/groceryplus/all_groceries";
    }

    @GetMapping("shopping_list")
    public String shoppingList(Model model) throws GroceryPlusException {
        List<Grocery> list = groceryPlusService.getShoppinglist();
        System.out.println(list);
        model.addAttribute("list", list);
        return "shopping_list";
    }

    @PostMapping("create_new_recipe")
    public String addRecipe() {
        return "create_new_recipe";
    }

}
