package com.nixsolutions.bondarenko.study;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.filter.IColumnFilter;

class MyPrimaryKeyFilter implements IColumnFilter {

    public boolean accept(String tableName, Column column) {
        return column.getColumnName().equalsIgnoreCase("id");
    }
}