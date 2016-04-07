package org.vaadin.alump.gofullscreen.gwt.client.shared;

import com.vaadin.shared.communication.ServerRpc;

public interface FSButtonServerRpc extends ServerRpc {
    void enteredFullscreen();

    void leftFullscreen();
}
