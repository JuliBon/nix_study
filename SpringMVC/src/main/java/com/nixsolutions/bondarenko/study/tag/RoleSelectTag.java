package com.nixsolutions.bondarenko.study.tag;

import com.nixsolutions.bondarenko.study.entity.UserLibraryRole;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class RoleSelectTag extends TagSupport {
    private List<String> roleNameList;
    private String selectedRoleName;
    private String styleClass;

    public void setRoleNameList(List<String> roleNameList) {
        this.roleNameList = roleNameList;
    }

    public void setSelectedRoleName(String selectedRoleName) {
        this.selectedRoleName = selectedRoleName;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    @Override
    public int doStartTag() throws JspTagException {
        Iterator<String> roleIterator = roleNameList.iterator();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<select id=\"roleName\" required ");
            if (styleClass != null) {
                stringBuilder.append("class=\"" + styleClass + "\"");
            }
            stringBuilder.append(" name=\"roleName\">");

            if (selectedRoleName == null || selectedRoleName.isEmpty()) {
                selectedRoleName = UserLibraryRole.USER.getName();
            }

            while (roleIterator.hasNext()) {
                String roleName = roleIterator.next();
                stringBuilder.append("<option");

                if (roleName.equals(selectedRoleName)) {
                    stringBuilder.append(" selected=\"selected\"");
                }
                stringBuilder.append(" value=");
                stringBuilder.append(roleName);
                stringBuilder.append(">");
                stringBuilder.append(roleName);
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
