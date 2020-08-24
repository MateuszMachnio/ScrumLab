package pl.coderslab.web;

import pl.coderslab.dao.RecipeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AppDeleteRecipe", value ="/app/recipe/safeDelete")
public class AppDeleteRecipe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        RecipeDao recipeDao = new RecipeDao();
        recipeDao.delete(recipeId);
        response.sendRedirect("/app/recipe/list");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("recipeId", request.getParameter("id"));
        getServletContext().getRequestDispatcher("/recipeSafeDelete.jsp").forward(request,response);
    }
}
