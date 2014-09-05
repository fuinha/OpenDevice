/*
 * ******************************************************************************
 *  Copyright (c) 2013-2014 CriativaSoft (www.criativasoft.com.br)
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Ricardo JL Rufino - Initial API and Implementation
 * *****************************************************************************
 */

package br.com.criativasoft.opendevice.core.command;

import br.com.criativasoft.opendevice.core.model.Device;

public class DeviceCommand extends Command {

    public DeviceCommand(){
        super();
    }
	
	public DeviceCommand(CommandType type, int deviceID, long value) {
		super(type);
		this.deviceID = deviceID;
		this.value = value;
	}

	private static final long serialVersionUID = 3448172706632529764L;
	
	private int deviceID;
	private long value;
	
	public int getDeviceID() {
		return deviceID;
	}
	
	public long getValue() {
		return value;
	}

    /**@see br.com.criativasoft.opendevice.core.command.CommandType#isDeviceCommand(CommandType) */
	public static final boolean isCompatible(CommandType type){
		return CommandType.isDeviceCommand(type);
	}

    public static final boolean isCompatible(int type){
        return isCompatible(CommandType.getByCode(type));
    }

    public static DeviceCommand GPIO( int pin, long value){
        return new DeviceCommand(CommandType.GPIO_DIGITAL, pin, value);
    }

    /**
     * Create {@link br.com.criativasoft.opendevice.core.command.DeviceCommand} of type {@link CommandType#ON_OFF} with value HIGH
     * @param deviceID
     * @return
     */
    public static DeviceCommand ON(int deviceID){
        return new DeviceCommand(CommandType.ON_OFF, deviceID, Device.VALUE_HIGH);
    }

    /**
     * Create {@link br.com.criativasoft.opendevice.core.command.DeviceCommand} of type {@link CommandType#ON_OFF} with value LOW
     * @param deviceID
     * @return
     */
    public static DeviceCommand OFF(int deviceID){
        return new DeviceCommand(CommandType.ON_OFF, deviceID, Device.VALUE_LOW);
    }

}
