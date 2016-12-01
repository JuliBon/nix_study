package com.nixsolutions.bondarenko.study.jsp.servlets;

import com.nixsolutions.bondarenko.study.jsp.user.library.*;
import com.nixsolutions.bondarenko.study.jsp.user.library.Role;
import com.nixsolutions.bondarenko.study.jsp.user.library.jdbc.JdbcRoleDao;
import com.nixsolutions.bondarenko.study.jsp.user.library.jdbc.JdbcUserDao;

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
    private static final String ERROR_NOT_UNIQUE_LOGIN = "Error! User with this login already exists!";
    private static final String ERROR_NOT_UNIQUE_EMAIL = "Error! This email is already attached to another user";
    private static final String MESSAGE_USER_CREATED = "New user has been created";
    private static final String MESSAGE_USER_UPDATED = "User has been updated";

    public static final String ACTION_DELETE_USER = "delete";
    public static final String ACTION_CREATE_USER = "create_user";
    public static final String ACTION_EDIT_USER = "edit_user";

    private JdbcUserDao jdbcUserDao;
    private JdbcRoleDao jdbcRoleDao;

    private boolean checkCurrentUserIsAdmin(HttpSession session) throws ServletException, IOException {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            User userByLogin;
            try {
                userByLogin = jdbcUserDao.findByLogin(currentUser.getLogin());
            } catch (SQLException e) {
                return false;
            }
            if (userByLogin != null) {
                if (userByLogin.getPassword().equals(currentUser.getPassword())) {
                    if (userByLogin.getRole().getName().equals(UserLibraryRole.ADMIN.getName())) {
                        return true;
                    }
                }
            }
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
            String action = request.getParameter("action");
            if (action != null) {
                switch (action) {
                    case ACTION_DELETE_USER:
                        deleteUser(request, response);
                        break;
                    case ACTION_CREATE_USER:
                        showCreateUserPage(request, response);
                        break;
                    case ACTION_EDIT_USER:
                        showEditUserPage(request, response);
                        break;

                }
            } else {
                findAllUsers(request, response);
            }
        }
    }

    private void showCreateUserPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", ACTION_CREATE_USER);
        try {
            List<Role> roleList = findAllRoles();
            request.setAttribute("roleList", roleList);
            request.getRequestDispatcher("user_form.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void showEditUserPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", ACTION_EDIT_USER);

        String login = request.getParameter("login");
        try {
            User user = jdbcUserDao.findByLogin(login);
            request.setAttribute("user", user);

            List<Role> roleList = findAllRoles();
            request.setAttribute("roleList", roleList);
            request.getRequestDispatcher("user_form.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!checkCurrentUserIsAdmin(request.getSession())) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {

            String action = request.getParameter("action");
            if (action != null) {
                request.setAttribute("action", action);
                try {
                    User user = new User();
                    user.setLogin(request.getParameter("login"));
                    user.setEmail(request.getParameter("email"));
                    user.setPassword(request.getParameter("password"));
                    user.setFirstName(request.getParameter("first_name"));
                    user.setLastName(request.getParameter("last_name"));
                    user.setBirthday(Date.valueOf(request.getParameter("birthday")));
                    Role role = jdbcRoleDao.findByName(request.getParameter("roleName"));
                    user.setRole(role);

                    if (processUser(user, action, request, response)) {
                        List<User> userList = jdbcUserDao.findAll();
                        request.setAttribute("userList", userList);
                        request.getRequestDispatcher("admin.jsp").forward(request, response);

                    } else {
                        request.setAttribute("user", user);

                        List<Role> roleList = findAllRoles();
                        request.setAttribute("roleList", roleList);
                        request.getRequestDispatcher("user_form.jsp").forward(request, response);
                    }
                } catch (SQLException e) {
                    request.setAttribute("error", e);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }
        }
    }


    private void findAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> userList = jdbcUserDao.findAll();
            request.setAttribute("userList", userList);
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private List<Role> findAllRoles() throws SQLException {
        List<Role> roleList = new ArrayList<>();

        Role roleAdmin = jdbcRoleDao.findByName(UserLibraryRole.ADMIN.getName());
        Role roleUser = jdbcRoleDao.findByName(UserLibraryRole.USER.getName());
        if (roleAdmin != null) roleList.add(roleAdmin);
        if (roleUser != null) roleList.add(roleUser);

        return roleList;
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        if (login != null) {
            User user;
            try {
                user = jdbcUserDao.findByLogin(login);
                jdbcUserDao.remove(user);
                response.sendRedirect("/admin");
            } catch (SQLException e) {
                request.setAttribute("error", e);
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }


    private boolean processUser(User user, String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User userByEmail = jdbcUserDao.findByEmail(user.getEmail());
            if (action.equals(ACTION_CREATE_USER)) {
                //если логин не уникален
                if (jdbcUserDao.findByLogin(user.getLogin()) != null) {
                    request.setAttribute("error_message", ERROR_NOT_UNIQUE_LOGIN);
                    return false;
                }
                // если нашелся юзер с таким емейлом
                if (userByEmail != null) {
                    request.setAttribute("error_message", ERROR_NOT_UNIQUE_EMAIL);
                    return false;
                }

                jdbcUserDao.create(user);
                request.setAttribute("message", MESSAGE_USER_CREATED);
            } else if (action.equals(ACTION_EDIT_USER)) {
                //если нашелся юзер с таким емейлом и это не текущий юзер
                if (userByEmail != null) {
                    if (!userByEmail.getLogin().equals(user.getLogin())) {
                        request.setAttribute("error_message", ERROR_NOT_UNIQUE_EMAIL);
                        return false;
                    }
                }
                jdbcUserDao.update(user);
                request.setAttribute("message", MESSAGE_USER_UPDATED);
            }
        } catch (SQLException e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return false;
        }
        return true;
    }
}