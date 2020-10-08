package com.simple.core.data.pageBean;

import com.simple.core.data.message.ResponseMessage;

/**
 * 分页参数实体
 * @author hejinguo
 * @version $Id: AbsPagingBean.java, v 0.1 2019年11月17日 下午7:29:31
 */
public abstract class AbsPagingBean extends ResponseMessage {
    public static final String INVALID_PARAMETER = "传入的参数不合法";
    /**当前页*/
    private Integer            currentPage;
    /**总的页数*/
    private Integer            totalPage;
    /**总记录数*/
    private Integer            totalItems;
    /**每页显示的记录数*/
    private Integer            pageSize;

    public AbsPagingBean() {
        super();
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 设置分页信息
     */
    public void countAllPage() {
        if (this.getCurrentPage() == null || this.getCurrentPage().intValue() <= 0) {
            this.currentPage = 1;
        }
        if (this.getPageSize() == null || this.getPageSize().intValue() <= 0) {
            this.pageSize = 10;
        }
        //计算总页数
        if (this.getTotalItems() % this.getPageSize() == 0) {
            this.totalPage = this.getTotalItems() / this.getPageSize();
        } else {
            this.totalPage = this.getTotalItems() / this.getPageSize() + 1;
        }
    }

    /**
     * 获取从第几条数据开始查询
     * 
     * @param currentPage 当前页
     * @param pageSize  每页显示条数
     * @return
     */
    public int getBeginIndex(int currentPage, int pageSize) {
        return pageSize * (currentPage - 1);
    }

    /**
     * 获取分页查询起始页
     * @author hejinguo
     * @return
     */
    public int gainStartIndex() {
        return (this.currentPage - 1) * this.getPageSize();
    }

    /**
     * 获取总页数
     * @author songtao
     * @param totalItems 总条数
     * @param pageSize 每页显示条数
     * @return
     */
    public int getTotalPage(int totalItems, int pageSize) {
        //计算总页数
        if (totalItems % pageSize == 0) {
            return totalItems / pageSize;
        } else {
            return totalItems / pageSize + 1;
        }
    }
}
