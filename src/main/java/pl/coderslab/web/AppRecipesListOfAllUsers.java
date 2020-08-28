package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "AppRecipesListOfAllUsers", value = "app/recipe/list/allusers")
public class AppRecipesListOfAllUsers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> allRecipes = recipeDao.findAll();
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            if (!recipes.contains(recipe)) {
                recipes.add(recipe);
            }
        }
        recipes.sort(Comparator.comparing(Recipe::getCreated).reversed());
        request.setAttribute("recipes", recipes);
        getServletContext().getRequestDispatcher("/appLoggedRecipesList.jsp").forward(request, response);
    }
}
