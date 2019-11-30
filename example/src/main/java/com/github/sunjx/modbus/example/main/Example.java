package com.github.sunjx.modbus.example.main;

import com.github.sunjx.modbus.example.example.ModbusConsoleApp;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 *         https://github.com/zengfr/easymodbus4j
 */
public class Example {
	public static void main(String[] args) throws Exception {
		String [] arg = "6,127.0.0.1,502,1,0,T,0,T,12000,heartbeat,0,54321".split(",");
		if (args == null || args.length <= 0)
			args = new String[] { "" };


		String[] argsArray = args[0].split("[,;|]");
		ModbusConsoleApp.initAndStart(arg);
	}
}
