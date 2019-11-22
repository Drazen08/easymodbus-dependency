package com.github.sunjx.modbus.example.example.schedule;

public class ModbusDataPointConfig {

    public ModbusDataPointConfig() {
        this.unitId = 1;
        this.scale = 1;
        this.offset = 0;
    }

    public String dataPointName;
    public byte unitId;
    public short functionCode;
    public short regAddr;
    public short regCount;
    public String dataType;
    public float scale;
    public float offset;

}
