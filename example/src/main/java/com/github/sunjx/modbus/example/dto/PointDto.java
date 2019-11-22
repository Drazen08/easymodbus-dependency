package com.github.sunjx.modbus.example.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangpeng
 *         2019/11/11.
 */
@Data
public class PointDto implements Serializable{
    private static final long serialVersionUID = -8924733128704823973L;
    /**
     * dtu
     */
    private String dtu;

    /**
     * 点位+dtu
     */
    private String pointName;
    /**
     * 点位值
     */
    private String value;

    /**
     * 时间
     */
    private String time;

    /**
     * 点位描述
     */
    private String pointDesc;

    /**
     * 点位状态
     */
    private Boolean pointStatus=true;

}
