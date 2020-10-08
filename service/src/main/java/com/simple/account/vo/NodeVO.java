package com.simple.account.vo;

/**
 * 树控件节点封装类
 * @author hejinguo
 * @version $Id: NodeVO.java, v 0.1 2020年7月25日 下午5:42:08
 */
public class NodeVO {
    /**
     * 树节点ID
     */
    private Long   id;
    /**
     * 树节点父ID
     */
    private Long   parentId;
    /**
     * 树节点ID，为菜单树点击
     */
    private String url;
    /**
     * 树节点显示名称
     */
    private String name;
    /**
     * 加载方式   功能节点属性
     */
    private String loadType;

    NodeVO() {
    }

    public NodeVO(Long id, Long parentId, String name, String url) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.url = url;
    }

    public NodeVO(Long id, Long parentId, String name, String url, String loadType) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.url = url;
        this.loadType = loadType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLoadType() {
        return loadType;
    }

    public void setLoadType(String loadType) {
        this.loadType = loadType;
    }

}