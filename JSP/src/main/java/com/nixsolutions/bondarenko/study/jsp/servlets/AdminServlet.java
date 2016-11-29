package com.nixsolutions.bondarenko.study.jsp.servlets;

import com.nixsolutions.bondarenko.study.jsp.user.library.*;
import com.nixsolutions.bondarenko.study.jsp.user.library.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminServlet extends HttpServlet {
    private static final String DELETE = "delete";
    private static final String CREATE = "create_user";
    private static final String EDIT = "edit_user";
    private JdbcUserDao jdbcUserDao;
    private JdbcRoleDao jdbcRoleDao;

    @Override
    public void init() throws ServletException {
        super.init();
        jdbcUserDao = new JdbcUserDao();
        jdbcRoleDao = new JdbcRoleDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case EDIT:
                    editUser(request, response);
                    break;
                default:
                    createUser(request, response);
                    break;
            }
        }
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case DELETE:
                    deleteUser(request, response);
                    break;
                case CREATE:
                    List<Role> roleList = findAllRoles();
                    request.setAttribute("roleList", roleList);
                    request.getRequestDispatcher("create_user.jsp").forward(request, response);
                    break;
            }
        } else {
            findAllUsers(request, response);
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

    private List<Role> findAllRoles() {
        List<Role> roleList = new ArrayList<>();
        try {
            Role roleAdmin = jdbcRoleDao.findByName(UserLibraryRole.ADMIN.getName());
            Role roleUser = jdbcRoleDao.findByName(UserLibraryRole.USER.getName());
            if (roleAdmin != null) roleList.add(roleAdmin);
            if (roleUser != null) roleList.add(roleUser);
        } catch (SQLException e) {
            //TODO log exception;
        }
        return roleList;
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        if (login != null) {
            try {
                User user = jdbcUserDao.findByLogin(login);
                jdbcUserDao.remove(user);
                response.sendRedirect("/admin");
            } catch (SQLException e) {

                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) {
        //TODO update user
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) {

        //TODO create user
    }
}