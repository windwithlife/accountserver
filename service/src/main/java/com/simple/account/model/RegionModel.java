package com.simple.account.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 地区实体
 * @author hejinguo
 * @version $Id: RegionModel.java, v 0.1 2020年8月7日 上午4:47:51
 */
public class RegionModel implements Serializable {
    /**  */
    private static final long serialVersionUID = -82791186447912003L;
    /**地区ID*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer           id;
    /**地区名称*/
    private String            name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
