package com.nixsolutions.bondarenko.study.jsp.servlets;

import com.nixsolutions.bondarenko.study.jsp.user.library.*;
import com.nixsolutions.bondarenko.study.jsp.user.library.Role;
import com.nixsolutions.bondarenko.study.jsp.user.library.dao.JdbcRoleDao;
import com.nixsolutions.bondarenko.study.jsp.user.library.dao.JdbcUserDao;

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

    private UserDao userDao;
    private RoleDao roleDao;

    private boolean checkCurrentUserIsAdmin(HttpSession session) throws ServletException, IOException {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser != null) {
            User userByLogin;
            try {
                userByLogin = userDao.findByLogin(currentUser.getLogin());
            } catch (Exception e) {
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
        userDao = new JdbcUserDao();
        roleDao = new JdbcRoleDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!checkCurrentUserIsAdmin(request.getSession())) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            request.getSession().setAttribute("currentUser", request.getSession().getAttribute("currentUser"));

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
        } catch (Exception e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void showEditUserPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", ACTION_EDIT_USER);

        Long id = new Long(request.getParameter("id"));
        try {

            User user = userDao.findById(id);
            request.setAttribute("user", user);

            List<Role> roleList = findAllRoles();
            request.setAttribute("roleList", roleList);
            request.getRequestDispatcher("user_form.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!checkCurrentUserIsAdmin(request.getSession())) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            request.getSession().setAttribute("currentUser", request.getSession().getAttribute("currentUser"));

            String action = request.getParameter("action");
            if (action != null) {
                request.setAttribute("action", action);
                try {
                    User user = new User();
                    user.setLogin(request.getParameter("login"));
                    user.setEmail(request.getParameter("email"));
                    user.setPassword(request.getParameter("password"));
                    user.setFirstName(request.getParameter("firstName"));
                    user.setLastName(request.getParameter("lastName"));
                    user.setBirthday(Date.valueOf(request.getParameter("birthday")));
                    Role role = roleDao.findByName(request.getParameter("roleName"));
                    user.setRole(role);

                    if (processUser(user, action, request, response)) {
                        List<User> userList = userDao.findAll();
                        request.setAttribute("userList", userList);
                        request.getRequestDispatcher("admin.jsp").forward(request, response);

                    } else {
                        request.setAttribute("user", user);

                        List<Role> roleList = findAllRoles();
                        request.setAttribute("roleList", roleList);
                        request.getRequestDispatcher("user_form.jsp").forward(request, response);
                    }
                } catch (Exception e) {
                    request.setAttribute("error", e);
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            }
        }
    }


    private void findAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> userList = userDao.findAll();
            request.setAttribute("userList", userList);
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private List<Role> findAllRoles() throws Exception {
        List<Role> roleList = new ArrayList<>();

        Role roleAdmin = roleDao.findByName(UserLibraryRole.ADMIN.getName());
        Role roleUser = roleDao.findByName(UserLibraryRole.USER.getName());
        if (roleAdmin != null) roleList.add(roleAdmin);
        if (roleUser != null) roleList.add(roleUser);

        return roleList;
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = new Long(request.getParameter("id"));
        if (id != null) {
            try {
                userDao.remove(userDao.findById(id));
                response.sendRedirect("/admin");
            } catch (Exception e) {
                request.setAttribute("error", e);
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
    }

    private boolean processUser(User user, String action, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User userByEmail = userDao.findByEmail(user.getEmail());
            if (action.equals(ACTION_CREATE_USER)) {
                //если логин не уникален
                if (userDao.findByLogin(user.getLogin()) != null) {
                    request.setAttribute("error_message", ERROR_NOT_UNIQUE_LOGIN);
                    return false;
                }
                // если нашелся юзер с таким емейлом
                if (userByEmail != null) {
                    request.setAttribute("error_message", ERROR_NOT_UNIQUE_EMAIL);
                    return false;
                }

                userDao.create(user);
                request.setAttribute("message", MESSAGE_USER_CREATED);
            } else if (action.equals(ACTION_EDIT_USER)) {
                //если нашелся юзер с таким емейлом и это не текущий юзер
                if (userByEmail != null) {
                    if (!userByEmail.getLogin().equals(user.getLogin())) {
                        request.setAttribute("error_message", ERROR_NOT_UNIQUE_EMAIL);
                        return false;
                    }
                }
                userDao.update(user);
                request.setAttribute("message", MESSAGE_USER_UPDATED);
            }
        } catch (Exception e) {
            request.setAttribute("error", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return false;
        }
        return true;
    }
}