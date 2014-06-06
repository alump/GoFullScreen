package org.vaadin.alump.gofullscreen.gwt.client.connect;

import com.vaadin.shared.communication.ServerRpc;

public interface GoFullScreenServerRpc extends ServerRpc {
    public void enteredFullscreen();

    public void leftFullscreen();
}
