package com.nixsolutions.bondarenko.study.jsp.servlets;

import com.nixsolutions.bondarenko.study.jsp.user.library.*;
import com.nixsolutions.bondarenko.study.jsp.user.library.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminServlet extends HttpServlet {
    private static final String DELETE = "delete";
    private static final String CREATE = "create_user";
    private static final String EDIT = "edit_user";

    private static final String MESSAGE_NOT_UNIQUE_LOGIN = "Error! User with this login already exists!";
    private static final String MESSAGE_NOT_UNIQUE_EMAIL = "Error! This email is already attached to another user";
    private static final String MESSAGE_USER_CREATED = "New user has been created";

    private JdbcUserDao jdbcUserDao;
    private JdbcRoleDao jdbcRoleDao;

    private boolean checkCurrentUserIsAdmin(HttpSession session) throws ServletException, IOException {
        try {
            User currentUser = (User) session.getAttribute("currentUser");
            if (currentUser != null) {
                User userByLogin = jdbcUserDao.findByLogin(currentUser.getLogin());
                if (userByLogin != null) {
                    if (userByLogin.getPassword().equals(currentUser.getPassword())) {
                        if (userByLogin.getRole().getName().equals(UserLibraryRole.ADMIN.getName())) {
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            //TODO log
        }
        return false;
    }


    @Override
    public void init() throws ServletException {
        super.init();
        jdbcUserDao = new JdbcUserDao();
        jdbcRoleDao = new JdbcRoleDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!checkCurrentUserIsAdmin(request.getSession())) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!checkCurrentUserIsAdmin(request.getSession())) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {

            String action = request.getParameter("action");
            if (action != null) {
                switch (action) {
                    case EDIT:
                        editUser(request, response);
                        break;
                    case CREATE:
                        createUser(request, response);
                        break;
                }
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
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
            //TODO log
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
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) {

    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String birthday = request.getParameter("birthday");
        String roleName = request.getParameter("roleName");
        Date birthdayDate = Date.valueOf(birthday);

        boolean userParamsBad = false;
        try {
            Role role = jdbcRoleDao.findByName(roleName);
            if (jdbcUserDao.findByLogin(login) != null) {
                request.setAttribute("error_message", MESSAGE_NOT_UNIQUE_LOGIN);
                userParamsBad = true;
                return;
            }
            if (jdbcUserDao.findByEmail(email) != null) {
                request.setAttribute("error_message", MESSAGE_NOT_UNIQUE_EMAIL);
                userParamsBad = true;
                return;
            }
            User user = new User(login, password, email, first_name, last_name, birthdayDate, role);
            jdbcUserDao.create(user);
            request.setAttribute("message", MESSAGE_USER_CREATED);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (userParamsBad) {
                request.setAttribute("login", login);
                request.setAttribute("email", email);
                request.setAttribute("first_name", first_name);
                request.setAttribute("last_name", last_name);
                request.setAttribute("birthday", birthday);
                request.setAttribute("roleName", roleName);
            }
            List<Role> roleList = findAllRoles();
            request.setAttribute("roleList", roleList);
            request.getRequestDispatcher("create_user.jsp").forward(request, response);
        }
    }
}