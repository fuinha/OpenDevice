/*
 *
 *  * ******************************************************************************
 *  *  Copyright (c) 2013-2014 CriativaSoft (www.criativasoft.com.br)
 *  *  All rights reserved. This program and the accompanying materials
 *  *  are made available under the terms of the Eclipse Public License v1.0
 *  *  which accompanies this distribution, and is available at
 *  *  http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  *  Contributors:
 *  *  Ricardo JL Rufino - Initial API and Implementation
 *  * *****************************************************************************
 *
 */

var od = od || {};

// Like OpenDevice JAVA-API
od.DeviceType = {
    DIGITAL:1,
    ANALOG:2,
    NUMERIC:3
};

// Like OpenDevice JAVA-API
od.DeviceCategory = {
    LAMP:1,
    FAN:2,
    GENERIC:3,
    POWER_SOURCE : 4,
    GENERIC_SENSOR: 50,
    IR_SENSOR: 51
};



od.CommandType = {
    DIGITAL:1,
    ANALOG:2,
    NUMERIC:3,
    GPIO_DIGITAL:4,
    GPIO_ANALOG:5,
    INFRA_RED:6,

    /** Response to commands like: DIGITAL, POWER_LEVEL, INFRA RED  */
    DEVICE_COMMAND_RESPONSE : 10, // Responsta para comandos como: DIGITAL, POWER_LEVEL, INFRA_RED
    COMMAND_RESPONSE : 11,
    SET_PROPERTY:12,
    ACTION:13,

    PING_REQUEST            :20,
    PING_RESPONSE           :21,
    DISCOVERY_REQUEST       :22,
    DISCOVERY_RESPONSE      :23,
    MEMORY_REPORT           :24,
    CPU_TEMPERATURE_REPORT  :25,
    CPU_USAGE_REPORT        :26,


    GET_DEVICES             :30,
    GET_DEVICES_RESPONSE    :31,
    DEVICE_ADD              :32,
    DEVICE_ADD_RESPONSE	    :33,
    DEVICE_DEL              :34,
    CLEAR_DEVICES           :35,
    GET_CONNECTIONS         :36,
    GET_CONNECTIONS_RESPONSE:37,
    CONNECTION_ADD          :38,
    CONNECTION_ADD_RESPONSE :39,
    CONNECTION_DEL          :40,
    CLEAR_CONNECTIONS       :41,

    USER_COMMAND            :99,

    isDeviceCommand : function(type){
        switch (type) {
            case this.DIGITAL:
                return true;
            case this.ANALOG:
                return true;
            case this.NUMERIC:
                return true;
            default:
                break;
        }
        return false;
    }
};


od.Event = {
    DEVICE_LIST_UPDATE : "DEVICE_LIST_UPDATE",
    DEVICE_CHANGED : "DEVICE_CHANGED",
    CONNECTION_CHANGE : "CONNECTION_CHANGE",
    CONNECTED : "CONNECTION_CHANGE_CONNECTED",
    DISCONNECTED : "CONNECTION_CHANGE_DISCONNECTED"
};