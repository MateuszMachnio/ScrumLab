package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RecipeDetails", value = "/app/recipe/details")
public class AppRecipeDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String from = request.getParameter("from");
        if (from.equals("dash")) {
            request.setAttribute("dash", "something");
        }
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.read(id);
        String ingredients = recipe.getIngredients();
        String[] splitIngredients = ingredients.split(", ");
        request.setAttribute("recipe", recipe);
        request.setAttribute("splitIngredients", splitIngredients);
        getServletContext().getRequestDispatcher("/appRecipeDetails.jsp").forward(request,response);
    }
}
