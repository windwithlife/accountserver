package com.simple.account.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 菜单树VO
 * @author hejinguo
 * @version $Id: MenuTreeVO.java, v 0.1 2020年7月25日 下午5:42:20
 */
@SuppressWarnings("rawtypes")
public class MenuTreeVO {
    public MenuTreeVO() {
    }

    public StringBuffer returnStr = new StringBuffer();

    /**
     * 所需json数据
     * @param list 根节点以及根节点以下集合
     * @param node 根节点
     * @param checked  树是否多选
     */
    public void getTree(List list, NodeVO node, Boolean checked) {
        if (hasChild(list, node)) {
            returnStr = addNode(node, checked);
            returnStr.append(",children:[");
            List childList = getChildList(list, node);
            Iterator it = childList.iterator();
            while (it.hasNext()) {
                NodeVO n = (NodeVO) it.next();
                getTree(list, n, checked);
            }
            returnStr.append("]},");
        } else {
            returnStr = addNode(node, checked);
            returnStr.append(",leaf:true},");
        }
    }

    public StringBuffer addNode(NodeVO node, Boolean checked) {
        returnStr.append("{id:");
        returnStr.append("'" + node.getId() + "'");
        returnStr.append(",parentId:");
        returnStr.append("'" + node.getParentId() + "'");
        returnStr.append(",text:");
        returnStr.append("'" + node.getName() + "'");
        returnStr.append(",url:");//此属性为菜单树的点击事件所加
        returnStr.append("'" + node.getUrl() + "'");
        returnStr.append(",loadType:");//此属性为菜单树的点击事件所加
        returnStr.append("'" + node.getLoadType() + "'");
        if (checked == true) {
            returnStr.append(",checked:");
            returnStr.append(false);
        }
        return returnStr;
    }

    /**
     * 判断是否有子节点
     * @param list 
     * @param node
     * @return
     */
    public boolean hasChild(List list, NodeVO node) { // 判断是否有子节点
        return getChildList(list, node).size() > 0 ? true : false;
    }

    /**
     * 得到子节点列表
     * 
     * @param list
     * @param node
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getChildList(List list, NodeVO node) { // 得到子节点列表
        List li = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NodeVO n = (NodeVO) it.next();
            if (node.getId().equals(n.getParentId())) {
                li.add(n);
            }
        }
        return li;
    }

    public String modifyStr(String returnStr) {// 修饰一下才能满足Extjs的Json格式
        return ("[" + returnStr + "]").replaceAll(",]", "]");
    }
}