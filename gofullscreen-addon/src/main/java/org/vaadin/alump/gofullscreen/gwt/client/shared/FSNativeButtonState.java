package org.vaadin.alump.gofullscreen.gwt.client.shared;

import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.button.NativeButtonState;

@SuppressWarnings("serial")
public class FSNativeButtonState extends NativeButtonState {
    public Connector fullscreenTarget;
    public boolean hideIfNotSupported = true;
}
