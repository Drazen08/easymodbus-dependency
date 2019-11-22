package com.github.sunjx.modbus.protocol;

public class ModbusFunctionCode {
  public static final short READ_COILS = 1;
  
  public static final short READ_DISCRETE_INPUTS = 2;
  
  public static final short READ_HOLDING_REGISTERS = 3;
  
  public static final short READ_INPUT_REGISTERS = 4;
  
  public static final short WRITE_SINGLE_COIL = 5;
  
  public static final short WRITE_SINGLE_REGISTER = 6;
  
  public static final short READ_EXCEPTION_STATUS = 7;
  
  public static final short DIAGNOSTICS = 8;
  
  public static final short GET_COMM_EVENT_COUNTER = 11;
  
  public static final short GET_COMM_EVENT_LOG = 12;
  
  public static final short WRITE_MULTIPLE_COILS = 15;
  
  public static final short WRITE_MULTIPLE_REGISTERS = 16;
  
  public static final short REPORT_SLAVEID = 17;
  
  public static final short READ_FILE_RECORD = 20;
  
  public static final short WRITE_FILE_RECORD = 21;
  
  public static final short MASK_WRITE_REGISTER = 22;
  
  public static final short READ_WRITE_MULTIPLE_REGISTERS = 23;
  
  public static final short READ_FIFO_QUEUE = 24;
  
  public static final short ENCAPSULATED_INTERFACE_TRANSPORT = 43;
}


/* Location:              D:\logs\easymodbus4j-core-0.0.5.jar!\com\github\zengfr\easymodbus4j\protocol\ModbusFunctionCode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */