package org.vaadin.alump.gofullscreen.gwt.client.shared;

import com.vaadin.shared.Connector;
import com.vaadin.shared.communication.ServerRpc;

public interface FSButtonServerRpc extends ServerRpc {
    void enteredFullscreen(Connector target);

    void leftFullscreen(Connector target);
}
