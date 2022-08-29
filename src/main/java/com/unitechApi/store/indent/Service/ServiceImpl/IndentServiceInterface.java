package com.unitechApi.store.indent.Service.ServiceImpl;

import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.IndentStatus;

public interface IndentServiceInterface {
    boolean AdminApprove(Indent indentStatus);
}
