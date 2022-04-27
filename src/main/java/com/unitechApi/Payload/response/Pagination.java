package com.unitechApi.Payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Pagination {
    int page;
    int pageSize;
    long rowcount;
    long pageCount;
    Sort sort;
    @JsonIgnore
    public Pageable getpageble()
    {
        return PageRequest.of(page, pageSize);
    }
    public Pagination(int page, int pageSize) {
        super();
        this.page = page;
        this.pageSize = pageSize;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pageSize;
    }

    public void setPagesize(int pagesize) {
        this.pageSize = pagesize;
    }

    public long getRowcount() {
        return rowcount;
    }

    public void setRowcount(long rowcount) {
        this.rowcount = rowcount;
    }

    public long getPagecount() {
        return pageCount;
    }

    public void setPagecount(long pagecount) {
        this.pageCount = pagecount;
    }

    public Sort getSort() {
        return Sort.by(Sort.Direction.ASC,"id");
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
