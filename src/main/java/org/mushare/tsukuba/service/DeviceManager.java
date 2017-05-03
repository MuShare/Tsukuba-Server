package org.mushare.tsukuba.service;

public interface DeviceManager {

    public static final String iOSDevice = "iOS";
    public static final String AndroidDevice = "Android";

    /**
     * Register a device.
     *
     * @param identifier
     * @param os
     * @param version
     * @param lan
     * @param deviceToken
     * @param ip
     * @return
     */
    String registerDevice(String identifier, String os, String version, String lan, String deviceToken, String ip, String uid);

    /**
     * Device OS must be iOS or Andriod
     *
     * @param os
     * @return
     */
    boolean isLegalDevice(String os);

}
