package com.nixsolutions.bondarenko.study.servlets;

import com.nixsolutions.bondarenko.study.HibernateUtil;
import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.dao.hibernate.HibernateUserDao;
import com.nixsolutions.bondarenko.study.user.library.User;
import com.nixsolutions.bondarenko.study.user.library.UserLibraryRole;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SigninServlet extends HttpServlet {
    private UserDao userDao = new HibernateUserDao(HibernateUtil.getSessionFactory());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("signin.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            boolean incorrectLoginOrPassword = false;
            User user = userDao.findByLogin(login);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    request.getSession().setAttribute("currentUser", user);

                    if (user.getRole().getName().equals(UserLibraryRole.USER.getName())) {
                        request.getRequestDispatcher("home.jsp").forward(request, response);
                    } else if (user.getRole().getName().equals(UserLibraryRole.ADMIN.getName())) {
                        response.sendRedirect("/admin");
                    }
                } else {
                    incorrectLoginOrPassword = true;
                }
            } else {
                incorrectLoginOrPassword = true;
            }
            if (incorrectLoginOrPassword) {
                request.setAttribute("login", login);
                request.setAttribute("errorMessage", "Incorrect login or password!");
                request.getRequestDispatcher("signin.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
