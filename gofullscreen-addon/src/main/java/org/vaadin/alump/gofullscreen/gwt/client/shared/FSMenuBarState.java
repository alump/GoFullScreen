package org.vaadin.alump.gofullscreen.gwt.client.shared;

import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.menubar.MenuBarState;

import java.util.HashMap;
import java.util.Map;

public class FSMenuBarState extends MenuBarState {

    public Map<Integer, Connector> fullScreenMap = new HashMap<>();

}
