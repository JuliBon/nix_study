package com.nixsolutions.bondarenko.study.jsp;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class UserLibraryTag extends BodyTagSupport implements javax.servlet.jsp.tagext.Tag {

    private int count;

    public void setCount(String count) {
        this.count = new Integer(count);
    }

    public int doStartTag() throws JspTagException {
        try {
            pageContext.getOut().write(
                    "<TABLE BORDER=\"3\" WIDTH=\"100%\">");
            pageContext.getOut().write("<TR><TD>");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doAfterBody()
            throws JspTagException {
        if (count-- > 1) {
            try {
                pageContext.getOut().write("</TD></TR><TR><TD>");
            } catch (IOException e) {
                throw new JspTagException(e.getMessage());
            }
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }

    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("</TD></TR>");
            pageContext.getOut().write("</TABLE>");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
