package controllers.reports;

import java.io.IOException;
import java.util.Iterator;

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
 * Servlet implementation class ReportsLikeServlet
 */
@WebServlet("/reports/like")
public class ReportsLikeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsLikeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        Boolean uniqueFlag = true;
        for(Iterator<Employee> it = r.getLikedByList().iterator(); it.hasNext();) {
            if(it.next().getId() == login_employee.getId()) {
                uniqueFlag = false;
            }
        }

        if(uniqueFlag && r != null && login_employee.getId() != r.getEmployee().getId() && !r.getLikedByList().contains(login_employee)) {
            r.getLikedByList().add(login_employee);

            em.getTransaction().begin();
            em.getTransaction().commit();
            request.getSession().setAttribute("flush", "いいねしました。");
            em.close();
        } else {
            request.getSession().setAttribute("hasError", true);
        }

        response.sendRedirect(request.getContextPath() + "/reports/show?id=" + r.getId());
    }

}
