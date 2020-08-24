package pl.coderslab.web;

import pl.coderslab.dao.AdminsDao;
import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admins;
import pl.coderslab.model.DayName;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "AppAddRecipeToPlan", value = "/app/recipe/plan/add")
public class AppAddRecipeToPlan extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;UTF-8");
        String ADD_RECIPE_TO_PLAN_QUERY = "INSERT INTO recipe_plan (recipe_id, meal_name, display_order, day_name_id, plan_id) VALUES (?,?,?,?,?);";
        int planId = Integer.parseInt(request.getParameter("choosePlan"));
        String name = request.getParameter("name");
        int displayOrder = Integer.parseInt(request.getParameter("displayOrder"));
        int recipeID = Integer.parseInt(request.getParameter("recipe"));
        int dayId = Integer.parseInt(request.getParameter("day"));
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement addRecipeToPlan = connection.prepareStatement(ADD_RECIPE_TO_PLAN_QUERY)) {
            addRecipeToPlan.setInt(1, recipeID);
            addRecipeToPlan.setString(2, name);
            addRecipeToPlan.setInt(3, displayOrder);
            addRecipeToPlan.setInt(4, dayId);
            addRecipeToPlan.setInt(5, planId);
            addRecipeToPlan.executeUpdate();
            System.out.println("Recipe was added to plan");
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/appAddNextRecipeToPlan.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        AdminsDao adminsDao = new AdminsDao();
        DayNameDao nameDao = new DayNameDao();
        RecipeDao recipeDao = new RecipeDao();
        PlanDao planDao = new PlanDao();
        Admins user = adminsDao.read((Integer) session.getAttribute("loggedUser"));
        //wysyłamy liste dni
        List<DayName> dayNameList = nameDao.findAll();
        request.setAttribute("dayList", dayNameList);
        //wysyłamy posortowana liste przepisów użytkownika
        List<Recipe> recipes = recipeDao.findAll();
        List<Recipe> recipeList = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (user.getId() == recipe.getAdminId()) {
                recipeList.add(recipe);
            }
        }
        recipeList.sort(Comparator.comparing(Recipe::getCreated).reversed());
        request.setAttribute("recipeList", recipeList);


        //wysyłamy posortowana liste planów użytkownika
        List<Plan> plans = planDao.findAll();
        List<Plan> planList = new ArrayList<>();
        for (Plan plan : plans) {
            if (user.getId() == plan.getAdminId()) {
                planList.add(plan);
            }
            planList.sort(Comparator.comparing(Plan::getCreated).reversed());
            request.setAttribute("planList", planList);

        }
        getServletContext().getRequestDispatcher("/appAddRecipeToPlan.jsp").forward(request, response);
    }
}
