/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.sunjx.modbus.example.example.processor;


import com.github.sunjx.modbus.common.util.BitSetUtil;
import com.github.sunjx.modbus.common.util.RegistersUtil;
import com.github.sunjx.modbus.func.request.*;
import com.github.sunjx.modbus.func.response.*;
import com.github.sunjx.modbus.processor.AbstractModbusProcessor;
import com.github.sunjx.modbus.processor.ModbusSlaveRequestProcessor;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.BitSet;
import java.util.Random;

/**
 * @author zengfr QQ:362505707/1163551688 Email:zengfr3000@qq.com
 *         https://github.com/zengfr/easymodbus4j
 */
public class ExampleModbusSlaveRequestProcessor extends AbstractModbusProcessor implements ModbusSlaveRequestProcessor {
	private static final InternalLogger logger = InternalLoggerFactory.getInstance(ExampleModbusSlaveRequestProcessor.class);
	protected static Random random = new Random();

	public ExampleModbusSlaveRequestProcessor(short transactionIdentifierOffset) {
		super(transactionIdentifierOffset, true);
	}

	@Override
	public WriteSingleCoilResponse writeSingleCoil(short unitIdentifier, WriteSingleCoilRequest request) {

		return new WriteSingleCoilResponse(request.getOutputAddress(), request.isState());
	}

	@Override
	public WriteSingleRegisterResponse writeSingleRegister(short unitIdentifier, WriteSingleRegisterRequest request) {
		return new WriteSingleRegisterResponse(request.getRegisterAddress(), request.getRegisterValue());
	}

	@Override
	public ReadCoilsResponse readCoils(short unitIdentifier, ReadCoilsRequest request) {
		BitSet coils = new BitSet(request.getQuantityOfCoils());
		coils = BitSetUtil.getRandomBits(request.getQuantityOfCoils(), random);
		if (coils.size() > 0 && random.nextInt(99) < 66)
			coils.set(0, false);
		return new ReadCoilsResponse(coils);
	}

	@Override
	public ReadDiscreteInputsResponse readDiscreteInputs(short unitIdentifier, ReadDiscreteInputsRequest request) {
		BitSet coils = new BitSet(request.getQuantityOfCoils());
		coils = BitSetUtil.getRandomBits(request.getQuantityOfCoils(), random);

		return new ReadDiscreteInputsResponse(coils);
	}

	@Override
	public ReadInputRegistersResponse readInputRegisters(short unitIdentifier, ReadInputRegistersRequest request) {
		int[] registers = new int[request.getQuantityOfInputRegisters()];
		registers = RegistersUtil.getRandomRegisters(registers.length, 1, 1024, random);

		return new ReadInputRegistersResponse(registers);
	}

	@Override
	public ReadHoldingRegistersResponse readHoldingRegisters(short unitIdentifier, ReadHoldingRegistersRequest request) {
		int[] registers = new int[request.getQuantityOfInputRegisters()];
		registers = RegistersUtil.getRandomRegisters(registers.length, 1, 1024, random);
		// RegistersFactory.getInstance().getOrInit(unitIdentifier).getHoldingRegister(request.getStartingAddress());
		return new ReadHoldingRegistersResponse(registers);

	}

	@Override
	public WriteMultipleCoilsResponse writeMultipleCoils(short unitIdentifier, WriteMultipleCoilsRequest request) {
		return new WriteMultipleCoilsResponse(request.getStartingAddress(), request.getQuantityOfOutputs());
	}

	@Override
	public WriteMultipleRegistersResponse writeMultipleRegisters(short unitIdentifier, WriteMultipleRegistersRequest request) {
		// RegistersFactory.getInstance().getOrInit(unitIdentifier).setHoldingRegister(request.getStartingAddress(),
		// request.getRegisters());
		return new WriteMultipleRegistersResponse(request.getStartingAddress(), request.getQuantityOfRegisters());
	}

}
