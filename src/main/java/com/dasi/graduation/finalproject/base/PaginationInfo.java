package com.dasi.graduation.finalproject.base;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;

/**
 * @author zhangyutao
 * @Date 2018/7/2 0002 14:52
 * @Description
 */
@Data
public class PaginationInfo {

    /**
     * The offset for pagination i.e the index at which the current page started
     */
    @Getter
    private final int offset;

    /**
     * The count of records in the current page
     */
    @Getter
    private final int countInPage;

    /**
     * The max number of records available. In the request, if offset=max, then
     * 0 records will be returned. -1 represents unknown max
     */
    @Getter
    private final long totalCount;

    public PaginationInfo() {
        this(0, 0);
    }

    public PaginationInfo(int offset, int countInPage) {
        super();
        this.offset = offset;
        this.countInPage = countInPage;
        this.totalCount = -1L;
    }

    public PaginationInfo(int offset, int countInPage, long totalCount) {
        super();
        this.offset = offset;
        this.countInPage = countInPage;
        this.totalCount = totalCount;
    }

    public PaginationInfo(Page<?> page) {
        super();
        this.offset = page.getNumber() * page.getSize();
        this.countInPage = page.getNumberOfElements();
        this.totalCount = page.getTotalElements();
    }

}