package com.unitechApi.store.indent.Service;

import com.unitechApi.store.indent.Model.IndentCreateHistory;
import com.unitechApi.store.indent.Repository.IndentEventRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndentEventHistory {
    private final IndentEventRepository indentEventRepository;

    public IndentEventHistory(IndentEventRepository indentEventRepository) {
        this.indentEventRepository = indentEventRepository;
    }

    /*
    *  get All Indent By IndentId
    * */
    public List<?> getAllIndentId(Long indentId)
    {
        return indentEventRepository.findByIndentId(indentId)
                .stream()
                .sorted(Comparator.comparing(IndentCreateHistory::getIndentId).reversed())
                .collect(Collectors.toList());
    }
    public List<?> getAll()
    {
        return indentEventRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(IndentCreateHistory::getIndent_history_id))
                .collect(Collectors.toList());
    }
    public List<?> getByIndentNumber(String indentNumber)
    {
        return indentEventRepository.findByIndentNumber(indentNumber);
    }

}

