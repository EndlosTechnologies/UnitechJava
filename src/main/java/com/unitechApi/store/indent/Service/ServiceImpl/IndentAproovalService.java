package com.unitechApi.store.indent.Service.ServiceImpl;

import com.unitechApi.store.indent.Model.Indent;
import com.unitechApi.store.indent.Model.IndentStatus;
import com.unitechApi.store.indent.Repository.IndentRepository;

public class IndentAproovalService implements IndentServiceInterface{
    private final IndentRepository indentRepository;

    public IndentAproovalService(IndentRepository indentRepository) {
        this.indentRepository = indentRepository;
    }

    /**
     * @param indentStatus 
     * @return
     */
    @Override
    public boolean AdminApprove(Indent indentStatus) {
        //indentStatus.
        return false;
    }

    /**
     * @param indentStatus
     * @return
     */
    
}
