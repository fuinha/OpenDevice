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

package br.com.criativasoft.opendevice.core;

import br.com.criativasoft.opendevice.connection.DeviceConnection;
import br.com.criativasoft.opendevice.core.command.Command;
import br.com.criativasoft.opendevice.core.connection.Connections;
import br.com.criativasoft.opendevice.core.connection.InputContections;
import br.com.criativasoft.opendevice.core.connection.OutputConnections;
import br.com.criativasoft.opendevice.core.dao.DeviceDao;
import br.com.criativasoft.opendevice.core.dao.memory.DeviceMemoryDao;
import br.com.criativasoft.opendevice.core.model.Device;
import br.com.criativasoft.opendevice.core.model.OpenDeviceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * TODO: PENDING DOC
 *
 * @author Ricardo JL Rufino
 * @date 24/08/14.
 */
public class LocalDeviceManager extends BaseDeviceManager {

    protected static final Logger log = LoggerFactory.getLogger(LocalDeviceManager.class);

    private String applicationID = OpenDeviceConfig.LOCAL_APP_ID;

    // ToDo: use only if dao is not memory
    private Set<Device> runtimeDevices = new LinkedHashSet<Device>();

    // Aliases to factory connections
    protected OutputConnections out = Connections.out;
    protected InputContections in = Connections.in;

    public void setApplicationID(String applicationID) {
        TenantProvider.setCurrentID(applicationID);
        this.applicationID = applicationID;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public LocalDeviceManager(){
        super();
        setDeviceDao(new DeviceMemoryDao());
    }

    @Override
    public void addInput(DeviceConnection connection) {
        if(connection.getApplicationID() == null) {
            connection.setApplicationID(this.applicationID);
        }
        super.addInput(connection);
    }

    @Override
    public void addOutput(DeviceConnection connection) {
        if(connection.getApplicationID() == null) {
            connection.setApplicationID(this.applicationID);
        }
        super.addOutput(connection);
    }

    @Override
    public void send(Command command) throws IOException {
        if(command.getApplicationID() == null){
            command.setApplicationID(getApplicationID());
        }
        super.send(command);
    }

    @Override
    public Collection<Device> getDevices() {

        Set<Device> devices = new LinkedHashSet<Device>();
        Collection<Device> dblist = super.getDevices();

        devices.addAll(runtimeDevices);
        devices.addAll(dblist); // TODO: this must be enabled (comentado devido a duplicidade nos testes)


        return devices;
    }


    @Override
    public void addDevice(Device device) {
        super.addDevice(device);

        if(findDeviceByUID(device.getUid()) == null) {
            runtimeDevices.add(device);
        }
    }

    /**
     * Alias to {@link #findDeviceByUID(int)}
     * @param deviceUID
     * @return Device
     */
    public Device findDevice(int deviceUID) {
        return findDeviceByUID(deviceUID);
    }

    @Override
    public Device findDeviceByUID(int deviceUID) {

        if(!runtimeDevices.isEmpty()){
            for (Device runtimeDevice : runtimeDevices) {
                if(runtimeDevice.getUid() == deviceUID){
                    return runtimeDevice;
                }
            }
        }

        return super.findDeviceByUID(deviceUID);
    }

    @Override
    public DeviceDao getValidDeviceDao() {

        if(TenantProvider.getCurrentID() == null){
            TenantProvider.setCurrentID(getApplicationID());
        }

        // Check if tenant is valid.
        if(TenantProvider.getCurrentID() != null && getConfig().isSupportTenants() && OpenDeviceConfig.LOCAL_APP_ID.equals(TenantProvider.getCurrentID())){
            throw new IllegalStateException("In Multi-Tenant support don't allow '*' in applicationID !");
        }

        return super.getValidDeviceDao();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public static void launch(String... args) {
        // Figure out the right class to call
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        boolean foundThisMethod = false;
        String callingClassName = null;
        for (StackTraceElement se : stack) {
            // Skip entries until we get to the entry for this class
            String className = se.getClassName();
            String methodName = se.getMethodName();
            // System.out.println("-className" + className + ", methodName: " + methodName);
            if (foundThisMethod) {
                callingClassName = className;
                break;
            } else if ("launch".equals(methodName)) {
                foundThisMethod = true;
            }
        }

        if (callingClassName == null) {
            throw new RuntimeException("Error: unable to determine Application class");
        }

        try {
            Class theClass = Class.forName(callingClassName, true,
                    Thread.currentThread().getContextClassLoader());
            if (LocalDeviceManager.class.isAssignableFrom(theClass)) {
                Class<? extends LocalDeviceManager> appClass = theClass;
                launchApplication(appClass, args);
            } else {
                throw new RuntimeException("Error: " + theClass + " is not a subclass of " + LocalDeviceManager.class.getName());
            }
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void launchApplication(Class<? extends LocalDeviceManager> appClass, String... args) {
        try {
            final LocalDeviceManager main = appClass.newInstance();
            main.start();

            // Automatic shutdown
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    main.stop();
                }
            });

            // Manual shutdown
            log.info("========================================================");
            log.info("Application - started ");
            log.info("Type [CTRL+C] to stop the server");
            log.info("========================================================");

            while(true){
                Thread.sleep(60000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(-1);
    }


    public void start() throws IOException {
        log.info("Method start not implemented");
    }

}
