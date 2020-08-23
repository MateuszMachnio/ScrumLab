package pl.coderslab.web;

import pl.coderslab.dao.DayNameDao;
import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.DayName;
import pl.coderslab.model.Plan;
import pl.coderslab.model.Recipe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AppAddRecipeToPlan", value = "/app/recipe/plan/add")
public class AppAddRecipeToPlan extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Po wejściu metodą POST na adres  /app/recipe/plan/add , aplikacja powinna:
//        - pobrać dane, a nastepnie zapisać je przy pomocy klasy Dao do bazy,
//        - przekierować użytkownika na adres  /app/recipe/plan/add, umożliwiając tym samym dodanie kolejnego przepisu do planu.
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DayNameDao nameDao = new DayNameDao();
        RecipeDao recipeDao=new RecipeDao();
        PlanDao planDao=new PlanDao();
        List<DayName> dayNameList = nameDao.findAll();
        List<Recipe> recipeList = recipeDao.findAll();
        List<Plan> planList = planDao.findAll();
        request.setAttribute("dayList", dayNameList);
        request.setAttribute("recipeList", recipeList);
        request.setAttribute("planList", planList);

        getServletContext().getRequestDispatcher("/appAddRecipeToPlan.jsp").forward(request, response);
    }
}
