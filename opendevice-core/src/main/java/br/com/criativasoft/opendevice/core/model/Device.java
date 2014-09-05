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

package br.com.criativasoft.opendevice.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe que representa um disposivo inteligente que pode ser controlado via REDE/USB/Etc...
 * @author Ricardo JL Rufino
 * @date 04/09/2011 12:40:01
 */
public class Device implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final int VALUE_HIGH = 1;
	public static final int VALUE_LOW = 0;

    public static final int ON = 1;
    public static final int OFF = 0;
	
	private long id; // Database ID
	private int uid; // Logic level user ID.
	private String name;
	private DeviceType type;
	private DeviceCategory category;
	private Date lastUpdate;
	private Date dateCreated;
	
	private long value = VALUE_LOW;

    private volatile Set<DeviceListener> listeners = new HashSet<DeviceListener>();

    public Device(int uid,DeviceType type) {
        super();
        this.uid = uid;
        this.type = type;
        this.category = DeviceCategory.GENERIC;
    }

	public Device(int uid, String name, DeviceType type, DeviceCategory category) {
		super();
		this.uid = uid;
		this.name = name;
		this.type = type;
		this.category = category;
	}
	
	
	
	public Device(int uid, String name, DeviceType type, DeviceCategory category, long value) {
		super();
		this.uid = uid;
		this.name = name;
		this.type = type;
		this.category = category;
		this.value = value;
	}

	public Device(int uid) {
		this.uid = uid;
		this.type = DeviceType.DIGITAL;
		this.category = DeviceCategory.GENERIC;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public int getUid() {
		return uid;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DeviceType getType() {
		return type;
	}
	public void setType(DeviceType type) {
		this.type = type;
	}
	
	public void setValue(long value) {

        if(value != this.value){
            this.value = value;
            notifyListeners();
        }

	}

    /**
     * Set value 1(HIGH).
     * shorthand call to setValue(HIGH)
     */
    public void on(){
       this.setValue(Device.VALUE_HIGH);
    }

    /**
     * Set value 0 (LOW).
     * shorthand call to setValue(LOW)
     */
    public void off(){
        this.setValue(Device.VALUE_LOW);
    }

    public long getValue() {
		return value;
	}
	
	public void setCategory(DeviceCategory category) {
		this.category = category;
	}
	
	public DeviceCategory getCategory() {
		return category;
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}

    public boolean addListener(DeviceListener e) {
        return listeners.add(e);
    }

    /**
     * Notify All Listeners about received command.
     */
    public void notifyListeners() {

        if (listeners.isEmpty()) return;

        for (final DeviceListener listener : listeners) {
            listener.onDeviceChanged(this);
        }

    }

    @Override
	public String toString() {
		return "Device[UID:"+uid+", Name:"+getName()+", Value:"+getValue()+", Type:" + getType()+"]";
	}


}