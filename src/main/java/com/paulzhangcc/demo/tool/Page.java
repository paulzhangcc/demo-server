package com.paulzhangcc.demo.tool;

import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.List;

/**
 * @param <SELECT> 数据查询类
 * @param <RESULT> 返回数据类 和数据保持一致
 */
public class Page<SELECT, RESULT> implements Serializable {
    private static final long serialVersionUID = -4861421206255705029L;

    public static <SELECT, RESULT> Page<SELECT, RESULT> create(int currentPage) {
        Page<SELECT, RESULT> page = new Page<SELECT, RESULT>();
        page.setCurrentPage(currentPage < 1 ? 1 : currentPage);

        return page;
    }

    public static <SELECT, RESULT> Page<SELECT, RESULT> create() {
        Page<SELECT, RESULT> page = new Page<SELECT, RESULT>();
        page.setCurrentPage(1);

        return page;
    }

    private int DEFAULT_PAGE_SIZE = 10; // 默认分页记录数
    private int recordCount; // 总记录数
    private int pageSize; // 一页页面记录条数
    private int pageCount; // 总页数
    public int currentPage; // 当前页码

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    private int offset;//数据库使用

    public SELECT getSelect() {
        return select;
    }

    public void setSelect(SELECT select) {
        this.select = select;
    }

    public List<RESULT> getResults() {
        return results;
    }

    public void setResults(List<RESULT> results) {
        this.results = results;
    }

    private SELECT select;
    private List<RESULT> results; // 数据记录List

    public Page() {
        recordCount = -1;
        pageCount = -1;

        pageSize = DEFAULT_PAGE_SIZE;
        currentPage = -1;
    }


    /**
     * @return 当前页码
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 设置所需的页码
     *
     * @param currentPage 页码。第一页就是1，第二页就是2
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize < 1 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
        rePageCount();
    }

    public void rePageCount() {
        if (pageSize * (currentPage-1) > recordCount)
            setCurrentPage((int) Math.ceil((double) recordCount / (double) pageSize));
        if (pageSize == 0)
            setPageSize(DEFAULT_PAGE_SIZE);
        setPageCount((int) Math.ceil((double) recordCount / (double) pageSize));
        setOffset((currentPage-1)*pageSize);
    }

    public  RowBounds getRowBounds(){
        return new RowBounds(offset,pageSize);
    }

    public static void main(String[] args) {
        Page<UserDO, UserDO> userDOPage = new Page<>();
        userDOPage.currentPage=2;
        userDOPage.setRecordCount(30);

        userDOPage.getOffset();
        userDOPage.getPageSize();



    }
}
