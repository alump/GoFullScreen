package org.vaadin.alump.gofullscreen.gwt.client.shared;

import com.google.gwt.dev.CompileOnePerm;
import com.google.gwt.dev.util.collect.HashMap;
import com.vaadin.shared.Connector;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.shared.ui.menubar.MenuBarState;

import java.util.Map;

/**
 * Created by alump on 07/04/16.
 */
public class FSMenuBarState extends MenuBarState {

    public Map<Integer,Connector> fullscreenTargets = new HashMap<Integer,Connector>();

}
