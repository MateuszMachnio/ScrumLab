package pl.coderslab.web;

import pl.coderslab.dao.PlanDao;
import pl.coderslab.dao.RecipeDao;
import pl.coderslab.model.Plan;
import pl.coderslab.model.PlanDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AppPlanDetails", value = "/app/plan/details")
public class AppPlanDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        session.setAttribute("planId", id);
        PlanDao planDao = new PlanDao();
        Plan plan = planDao.read(id);
        request.setAttribute("plan", plan);
        Map<String, List<PlanDetails>> stringListMap = planDao.detailsOfPlan(id);
        request.setAttribute("planDetails", stringListMap);
        getServletContext().getRequestDispatcher("/appPlanDetails.jsp").forward(request, response);
    }
}
