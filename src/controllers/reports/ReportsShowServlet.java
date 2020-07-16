package controllers.reports;

import java.io.IOException;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r =em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        request.setAttribute("likedFlag", false);
        for(Iterator<Employee> it = r.getLikedByList().iterator(); it.hasNext();) {
            if(it.next().getId() == ((Employee)request.getSession().getAttribute("login_employee")).getId()) {
                request.setAttribute("likedFlag", true);
            }
        }

        request.setAttribute("report", r);
        request.setAttribute("_token", request.getSession().getId());

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }
        if(request.getSession().getAttribute("hasError") != null) {
            request.setAttribute("hasError", request.getSession().getAttribute("hasError"));
            request.getSession().removeAttribute("hasError");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
