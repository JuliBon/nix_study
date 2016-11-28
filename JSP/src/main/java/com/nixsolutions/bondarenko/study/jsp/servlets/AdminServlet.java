package com.nixsolutions.bondarenko.study.jsp.servlets;

import com.nixsolutions.bondarenko.study.jsp.user.library.JdbcUserDao;
import com.nixsolutions.bondarenko.study.jsp.user.library.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public class AdminServlet extends HttpServlet {
    private static final String HOME = "/admin";
    private static final String DELETE = "/admin/delete";


    private JdbcUserDao jdbcUserDao = new JdbcUserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case HOME:
                findAllUsers(request, response);
                break;
            case DELETE:
                break;
            default:
                break;
        }
    }

    private void findAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> userList = jdbcUserDao.findAll();
            request.setAttribute("userList", userList);
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}