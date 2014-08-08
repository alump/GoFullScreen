package org.vaadin.alump.gofullscreen.gwt.client;

import com.vaadin.client.ui.VNativeButton;

/**
 * Simple extension to VNativeButton to allow GWT replace magic
 */
public class VFSNativeButton extends VNativeButton {

    public static final String CLASS_NAME = "gofullscreen-nativebutton";

    public VFSNativeButton() {
        addStyleName(CLASS_NAME);
    }

}
