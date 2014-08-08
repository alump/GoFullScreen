package org.vaadin.alump.gofullscreen.gwt.client;

import com.vaadin.client.ui.VButton;

/**
 * Simple extension to VNativeButton to allow GWT replace magic
 */
public class VFSButton extends VButton {

    public static final String CLASS_NAME = "gofullscreen-button";

    public VFSButton() {
        addStyleName(CLASS_NAME);
    }
}
