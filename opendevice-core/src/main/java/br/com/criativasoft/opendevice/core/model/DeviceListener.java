/*
 * *****************************************************************************
 * Copyright (c) 2013-2014 CriativaSoft (www.criativasoft.com.br)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * - Ricardo JL Rufino - Initial API and Implementation
 * *****************************************************************************
 */

package br.com.criativasoft.opendevice.core.model;

/**
 * The listener interface for receiving Device change events. <br/>
 * When the event occurs, the @{link #onDeviceChanged } method is invoked.
 *
 * @autor Ricardo JL Rufino
 * @date 24/08/14.
 */
public interface DeviceListener {

    /**
     * Invoked when any changes occur in the value of the device
     * @param device
     */
    void onDeviceChanged(Device device);

}
