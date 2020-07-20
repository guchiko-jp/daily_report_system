package controllers.follows;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsNewReportServlet
 */
@WebServlet("/follows/new_report")
public class FollowsNewReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsNewReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        if(em.createNamedQuery("getMyReportsCount", Long.class).setParameter("employee", e).getSingleResult() != 0) {
            Report new_report = em.createNamedQuery("getMyAllReports", Report.class)
                                  .setParameter("employee", e)
                                  .setMaxResults(1)
                                  .getSingleResult();

            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + new_report.getId());
        } else {
            request.getSession().setAttribute("hasError", true);
            response.sendRedirect(request.getContextPath() + "/follows/index");
        }
        em.close();
    }
}
