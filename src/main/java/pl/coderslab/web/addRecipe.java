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

@WebServlet(name = "addRecipe",value =  "/app/recipe/add")
public class addRecipe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        System.out.println(name);
        String description = request.getParameter("description");
        System.out.println(description);
        int preparationTime = Integer.parseInt(request.getParameter("preparationTime"));
        System.out.println(preparationTime);
        String preparation = request.getParameter("preparation");
        System.out.println(preparation);
        String ingredients = request.getParameter("ingredients");
        System.out.println(ingredients);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("loggedUser");
        Recipe recipe = new Recipe(name,ingredients,description,preparationTime,preparation,userId);
        RecipeDao recipeDao = new RecipeDao();
        recipeDao.create(recipe);
        response.sendRedirect("/app/recipe/list/"); // przekierować na odpowiedni servlet

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.sendRedirect("/addRecipe.jsp");
    }
}
