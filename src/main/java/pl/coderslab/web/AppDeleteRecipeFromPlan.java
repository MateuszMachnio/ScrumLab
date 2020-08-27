package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.dao.RecipePlanDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;
import pl.coderslab.utils.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "AppDeleteRecipeFromPlan", value = "/app/plan/delete/recipe")
public class AppDeleteRecipeFromPlan extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int planId = Integer.parseInt(request.getParameter("planId"));
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        RecipePlanDao rpd=new RecipePlanDao();
        rpd.deleteInAllPlan(recipeId, planId);
        response.sendRedirect("/app/plan/details?planId=" + (planId));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        int planId =Integer.parseInt(request.getParameter("planId"));
        request.setAttribute("recipeId", recipeId);
        request.setAttribute("planId", planId);
        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(planId);
        request.setAttribute("plan", plan);
        RecipeDao recipeDao = new RecipeDao();
        Recipe recipe = recipeDao.read(recipeId);
        request.setAttribute("recipe", recipe);

        getServletContext().getRequestDispatcher("/appDeleteRecipeFromPlan.jsp").forward(request, response);
    }
}
