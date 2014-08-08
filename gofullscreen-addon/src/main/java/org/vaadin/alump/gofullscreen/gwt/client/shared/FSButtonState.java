package org.vaadin.alump.gofullscreen.gwt.client.shared;

import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.button.ButtonState;

@SuppressWarnings("serial")
public class FSButtonState extends ButtonState {
	public Connector fullscreenTarget;
    public boolean hideIfNotSupported = true;
}
