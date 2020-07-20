package controllers.employees;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.FollowList;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeeFollowServlet
 */
@WebServlet("/employees/follow")
public class EmployeeFollowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeFollowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        FollowList f = new FollowList();
        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        f.setE_follow((Employee)request.getSession().getAttribute("login_employee"));
        f.setE_followed(e);

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        request.getSession().setAttribute("flush", "フォローしました。");
        em.close();

        response.sendRedirect(request.getContextPath() + "/employees/show?id=" + e.getId());

    }

}
