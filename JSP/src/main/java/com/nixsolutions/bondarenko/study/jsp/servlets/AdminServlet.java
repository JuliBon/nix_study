package com.nixsolutions.bondarenko.study.jsp.servlets;

import com.nixsolutions.bondarenko.study.jsp.UserFieldPattern;
import com.nixsolutions.bondarenko.study.jsp.user.library.*;
import com.nixsolutions.bondarenko.study.jsp.user.library.Role;
import com.nixsolutions.bondarenko.study.jsp.dao.RoleDao;
import com.nixsolutions.bondarenko.study.jsp.dao.UserDao;
import com.nixsolutions.bondarenko.study.jsp.dao.jdbc.JdbcRoleDao;
import com.nixsolutions.bondarenko.study.jsp.dao.jdbc.JdbcUserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<String, UserFieldPattern> getUserFieldPatternMap() {
        Map<String, UserFieldPattern> userFieldPatternMap = new HashMap<>();
        userFieldPatternMap.put("login", UserFieldPattern.LOGIN_PATTERN);
        userFieldPatternMap.put("password", UserFieldPattern.PASSWORD_PATTERN);
        userFieldPatternMap.put("email", UserFieldPattern.EMAIL_PATTERN);
        userFieldPatternMap.put("firstName", UserFieldPattern.FIRST_NAME_PATTERN);
        userFieldPatternMap.put("lastName", UserFieldPattern.LAST_NAME_PATTERN);
        userFieldPatternMap.put("birthday", UserFieldPattern.BIRTHDAY_PATTERN);
        return userFieldPatternMap;
    }

    private void showCreateUserPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("action", ACTION_CREATE_USER);
        request.setAttribute("userFieldPatternMap", getUserFieldPatternMap());
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
        request.setAttribute("userFieldPatternMap", getUserFieldPatternMap());

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
                    String idStr = request.getParameter("id");
                    if (!idStr.isEmpty()) {
                        user.setId(new Long(idStr));
                    }

                    user.setLogin(request.getParameter("login"));
                    user.setEmail(request.getParameter("email"));
                    user.setPassword(request.getParameter("password"));
                    user.setFirstName(request.getParameter("firstName"));
                    user.setLastName(request.getParameter("lastName"));
                    String birthday = request.getParameter("birthday");
                    String passwordConfirm = request.getParameter("passwordConfirm");

                    request.setAttribute("birthday", birthday); //set now because of further reset if form is not correct

                    boolean backToForm = true;
                    if (validateUser(user, passwordConfirm, birthday, action, request)) {
                        user.setBirthday(Date.valueOf(birthday));
                        Role role = roleDao.findByName(request.getParameter("roleName"));
                        user.setRole(role);
                        if (processUser(user, action, request, response)) {
                            backToForm = false;

                            List<User> userList = userDao.findAll();
                            request.setAttribute("userList", userList);
                            request.getRequestDispatcher("admin.jsp").forward(request, response);
                        } else {
                            backToForm = true;
                        }
                    }
                    if (backToForm) {
                        request.setAttribute("user", user);
                        List<Role> roleList = findAllRoles();
                        request.setAttribute("roleList", roleList);
                        request.setAttribute("userFieldPatternMap", getUserFieldPatternMap());
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
        String idStr = request.getParameter("id");
        if (idStr != null) {
            Long id = new Long(idStr);
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
            if (action.equals(ACTION_CREATE_USER)) {
                userDao.create(user);
                request.setAttribute("message", MESSAGE_USER_CREATED);
            } else if (action.equals(ACTION_EDIT_USER)) {
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

    private boolean validateUser(User user, String passwordConfirm, String birthday, String action, HttpServletRequest request) throws Exception {
        User userByEmail = userDao.findByEmail(user.getEmail());

        if (action.equals(ACTION_CREATE_USER)) {
            //validate login format
            if (!user.getLogin().matches(UserFieldPattern.LOGIN_PATTERN.getPattern())) {
                request.setAttribute("error_message", "Login does not matches request format: "
                        + UserFieldPattern.LOGIN_PATTERN.getValidateTitle());
                return false;
            }
            //whether login is not unique
            if (userDao.findByLogin(user.getLogin()) != null) {
                request.setAttribute("error_message", ERROR_NOT_UNIQUE_LOGIN);
                return false;
            }
            //whether email is not unique
            if (userByEmail != null) {  //if there is already a user with this email
                request.setAttribute("error_message", ERROR_NOT_UNIQUE_EMAIL);
                return false;
            }
        } else if (action.equals(ACTION_EDIT_USER)) {
            //if user with this email was found and he is not this user
            if (userByEmail != null) {  //
                if (!userByEmail.getLogin().equals(user.getLogin())) {
                    request.setAttribute("error_message", ERROR_NOT_UNIQUE_EMAIL);
                    return false;
                }
            }
        }
        //validate email format
        if (!user.getEmail().matches(UserFieldPattern.EMAIL_PATTERN.getPattern())) {
            request.setAttribute("error_message", "Email does not matches request format: "
                    + UserFieldPattern.EMAIL_PATTERN.getValidateTitle());
            return false;
        }
        //validate passwords
        if (!user.getPassword().matches(UserFieldPattern.PASSWORD_PATTERN.getPattern())) {
            request.setAttribute("error_message", "Password does not matches request format: "
                    + UserFieldPattern.PASSWORD_PATTERN.getValidateTitle());
            return false;
        }
        if (!user.getPassword().equals(passwordConfirm)) {
            request.setAttribute("error_message", "Passwords do not match!");
            return false;
        }
        //validate first name format
        if (!user.getFirstName().matches(UserFieldPattern.FIRST_NAME_PATTERN.getPattern())) {
            request.setAttribute("error_message", "First name does not matches request format: "
                    + UserFieldPattern.FIRST_NAME_PATTERN.getValidateTitle());
            return false;
        }
        //validate last name format
        if (!user.getLastName().matches(UserFieldPattern.LAST_NAME_PATTERN.getPattern())) {
            request.setAttribute("error_message", "Last name does not matches request format: "
                    + UserFieldPattern.LAST_NAME_PATTERN.getValidateTitle());
            return false;
        }
        //validate birthday format
        if (!birthday.matches(UserFieldPattern.BIRTHDAY_PATTERN.getPattern())) {
            request.setAttribute("error_message", "Birthday does not matches request format: "
                    + UserFieldPattern.BIRTHDAY_PATTERN.getValidateTitle());
            return false;
        }
        return true;
    }
}