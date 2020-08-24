package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AppUpdateRecipe",value = "/app/recipe/edit")
public class AppUpdateRecipe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
        String preparation = request.getParameter("preparation");
        String ingredients = request.getParameter("ingredients");
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("loggedUser");
        Recipe recipe = new Recipe(name,ingredients,description,preparationTime,preparation,userId);
        recipe.setID(id);
        System.out.println(id);
        RecipeDao recipeDao = new RecipeDao();
        recipeDao.update(recipe);   //dodaje nowy zamiast edytować!!
        response.sendRedirect("/app/recipe/list");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        String id = request.getParameter("id");

        RecipeDao recipeDao = new RecipeDao();
        request.setAttribute("recipe", recipeDao.read(Integer.parseInt(id)));

        getServletContext().getRequestDispatcher("/appEditRecipe.jsp").forward(request,response);

    }
}