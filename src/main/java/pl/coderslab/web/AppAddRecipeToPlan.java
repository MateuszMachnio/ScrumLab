package pl.coderslab.web;

import pl.coderslab.dao.AdminsDao;
import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Admins;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
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

@WebServlet(name = "AppAddRecipeToPlan", value = "/app/recipe/plan/add")
public class AppAddRecipeToPlan extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Po wejściu metodą POST na adres  /app/recipe/plan/add , aplikacja powinna:
//        - pobrać dane, a nastepnie zapisać je przy pomocy klasy Dao do bazy,
//        - przekierować użytkownika na adres  /app/recipe/plan/add, umożliwiając tym samym dodanie kolejnego przepisu do planu.
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
