package com.simple.core.data.pageBean;

import java.util.List;

/**
 * 返回单个集合实体
 * @author hejinguo
 * @version $Id: SinglePageBean.java, v 0.1 2019年11月17日 下午7:29:39
 */
public class SinglePageBean<T> extends AbsPagingBean {
    /**  */

    public SinglePageBean() {
        super();
    }

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
