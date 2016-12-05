package com.nixsolutions.bondarenko.study.jsp.servlets;

import com.nixsolutions.bondarenko.study.jsp.UserFieldPattern;
import com.nixsolutions.bondarenko.study.jsp.dao.hibernate.HibernateRoleDao;
import com.nixsolutions.bondarenko.study.jsp.dao.hibernate.HibernateUserDao;
import com.nixsolutions.bondarenko.study.jsp.dto.DtoConvert;
import com.nixsolutions.bondarenko.study.jsp.dto.UserDto;
import com.nixsolutions.bondarenko.study.jsp.user.library.*;
import com.nixsolutions.bondarenko.study.jsp.user.library.Role;
import com.nixsolutions.bondarenko.study.jsp.dao.RoleDao;
import com.nixsolutions.bondarenko.study.jsp.dao.UserDao;
import com.nixsolutions.bondarenko.study.jsp.validate.UserCreateValidator;
import com.nixsolutions.bondarenko.study.jsp.validate.UserUpdateValidator;
import com.nixsolutions.bondarenko.study.jsp.validate.UserValidator;

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
    private static final String MESSAGE_USER_CREATED = "New user has been created";
    private static final String MESSAGE_USER_UPDATED = "User has been updated";

    private static final String ACTION_DELETE_USER = "delete";
    private static final String ACTION_CREATE_USER = "create_user";
    private static final String ACTION_EDIT_USER = "edit_user";

    private UserDao userDao;
    private RoleDao roleDao;

    @Override
    public void init() throws ServletException {
        super.init();
        userDao = new HibernateUserDao();
        roleDao = new HibernateRoleDao();
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
            UserDto userDto = new UserDto(user.getId().toString(),
                    user.getLogin(), "", "",
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getBirthday().toString(),
                    user.getRole().getName());

            request.setAttribute("userDto", userDto);

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

                UserDto userDto = new UserDto(
                        request.getParameter("id"),
                        request.getParameter("login"),
                        request.getParameter("password"),
                        request.getParameter("passwordConfirm"),
                        request.getParameter("email"),
                        request.getParameter("firstName"),
                        request.getParameter("lastName"),
                        request.getParameter("birthday"),
                        request.getParameter("roleName")
                );

                try {
                    UserValidator userValidator;
                    Map<String, String> errorMap = new HashMap<>();
                    if (action.equals(ACTION_CREATE_USER)) {
                        userValidator = new UserCreateValidator(userDao);
                        errorMap = userValidator.validate(userDto);
                    } else if (action.equals(ACTION_EDIT_USER)) {
                        userValidator = new UserUpdateValidator(userDao);
                        errorMap = userValidator.validate(userDto);
                    }

                    if (errorMap.isEmpty()) {
                        processUser(userDto, action, request, response);

                        List<User> userList = userDao.findAll();
                        request.setAttribute("userList", userList);
                        request.getRequestDispatcher("admin.jsp").forward(request, response);

                    } else {
                        request.setAttribute("userDto", userDto);
                        request.setAttribute("errorMap", errorMap);
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

    private void processUser(UserDto userDto, String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = DtoConvert.convertToUser(userDto, roleDao);

        if (action.equals(ACTION_CREATE_USER)) {
            userDao.create(user);
            request.setAttribute("message", MESSAGE_USER_CREATED);
        } else if (action.equals(ACTION_EDIT_USER)) {
            userDao.update(user);
            request.setAttribute("message", MESSAGE_USER_UPDATED);
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
}