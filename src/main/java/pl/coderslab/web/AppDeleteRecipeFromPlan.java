package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
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
        String DELETE_RECIPE_FROM_PLAN = "DELETE FROM recipe_plan WHERE plan_id=? AND recipe_id=?;";

        int planId = Integer.parseInt(request.getParameter("planId"));
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));

        try (Connection connection = DbUtil.getConnection();
             PreparedStatement deleteRecipeFromPlan = connection.prepareStatement(DELETE_RECIPE_FROM_PLAN)) {
            deleteRecipeFromPlan.setInt(1, planId);
            deleteRecipeFromPlan.setInt(2, recipeId);
            deleteRecipeFromPlan.executeUpdate();
            System.out.println("Recipe was deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
