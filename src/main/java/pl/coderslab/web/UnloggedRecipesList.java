package pl.coderslab.web;

import pl.coderslab.dao.AdminsDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admins;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "UnloggedRecipesList", value ="/recipes/list")
public class UnloggedRecipesList extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String searchRecipe = request.getParameter("searchRecipe");
        RecipeDao recipeDao=new RecipeDao();
        List<Recipe> recipes=recipeDao.findAll();
        List<Recipe> foundedRecipes= new ArrayList<>();
        for (Recipe recipe:recipes) {
            if (recipe.getName().toLowerCase().trim().contains(searchRecipe.toLowerCase().trim())) {
                foundedRecipes.add(recipe);
            }
        }
        request.setAttribute("recipes", foundedRecipes);
        getServletContext().getRequestDispatcher("/unloggedRecipesList.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecipeDao recipeDao = new RecipeDao();
        List<Recipe> recipes = recipeDao.findAll();

        recipes.sort(Comparator.comparing(Recipe::getCreated).reversed());
        request.setAttribute("recipes", recipes);
        getServletContext().getRequestDispatcher("/unloggedRecipesList.jsp").forward(request, response);

    }
}
