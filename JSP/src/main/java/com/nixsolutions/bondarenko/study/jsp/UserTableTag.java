package com.nixsolutions.bondarenko.study.jsp;

import com.nixsolutions.bondarenko.study.jsp.user.library.User;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Iterator;
import java.util.List;

public class UserTableTag extends TagSupport {
    private List<User> userList;

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int doStartTag() throws JspTagException {
        Iterator<User> userIterator = userList.iterator();
        try {
            pageContext.getOut().write(
                    "<TABLE WIDTH=\"100%\">");
            pageContext.getOut().write("");
            pageContext.getOut().write("<TR><TD>Login</TD>");
            pageContext.getOut().write("<TD>First name</TD>");
            pageContext.getOut().write("<TD>LastName</TD>");
            pageContext.getOut().write("<TD>Age</TD>");
            pageContext.getOut().write("<TD>Role</TD>");
            pageContext.getOut().write("<TD>Actions</TD></TR>");

            while (userIterator.hasNext()) {
                User user = userIterator.next();
                LocalDate birthDate = user.getBirthday().toLocalDate();
                LocalDate currentDate = LocalDate.now();
                int age = Period.between(birthDate, currentDate).getYears();
                String deleteLink = "<a href=/admin?action=delete&login=" + user.getLogin() + ">Delete</a>";
                String editLink = "<a href=/admin?action=edit_use&?login=" + user.getLogin() + ">Edit</a>";

                pageContext.getOut().write("<TR>");
                pageContext.getOut().write("<TD>" + user.getLogin() + "</TD>");
                pageContext.getOut().write("<TD>" + user.getFirstName() + "</TD>");
                pageContext.getOut().write("<TD>" + user.getLastName() + "</TD>");
                pageContext.getOut().write("<TD>" + age + "</TD>");
                pageContext.getOut().write("<TD>" + user.getRole().getName() + "</TD>");
                pageContext.getOut().write("<TD>" + deleteLink + " " + editLink + "</TD>");
                pageContext.getOut().write("</TR>");
            }
            pageContext.getOut().write("</TABLE>");

        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
