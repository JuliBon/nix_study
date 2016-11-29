package com.nixsolutions.bondarenko.study.jsp;

import com.nixsolutions.bondarenko.study.jsp.user.library.Role;
import com.nixsolutions.bondarenko.study.jsp.user.library.UserLibraryRole;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class RoleSelectTag extends TagSupport {
    private List<Role> roleList;
    private Role selectedRole;
    private String styleClass;

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    @Override
    public int doStartTag() throws JspTagException {
        Iterator<Role> roleIterator = roleList.iterator();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<select ");
            if (styleClass != null) {
                stringBuilder.append("class=\"" + styleClass + "\"");
            }
            stringBuilder.append(" name=\"role\">");

            if (selectedRole == null) {
                selectedRole = new Role(UserLibraryRole.USER.getName());
            }

            while (roleIterator.hasNext()) {
                Role role = roleIterator.next();
                stringBuilder.append("<option");

                if (role.getName().equals(selectedRole.getName())) {
                    stringBuilder.append(" selected=\"selected\"");
                }
                stringBuilder.append(" value=");
                stringBuilder.append(role.getName());
                stringBuilder.append(">");
                stringBuilder.append(role.getName());
                stringBuilder.append("</option>");
            }
            stringBuilder.append("</select>");
            pageContext.getOut().write(stringBuilder.toString());
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
