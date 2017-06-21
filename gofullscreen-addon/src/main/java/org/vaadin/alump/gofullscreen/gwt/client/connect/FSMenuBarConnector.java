package org.vaadin.alump.gofullscreen.gwt.client.connect;

import com.google.gwt.dom.client.Document;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.menubar.MenuBarConnector;
import com.vaadin.shared.ui.Connect;
import org.vaadin.alump.gofullscreen.gwt.client.VFSMenuBar;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSMenuBarState;

@Connect(org.vaadin.alump.gofullscreen.FullScreenMenuBar.class)
public class FSMenuBarConnector extends MenuBarConnector {

    @Override
    public FSMenuBarState getState() {
        return (FSMenuBarState)super.getState();
    }

    public void init() {
        super.init();

        getWidget().setMenuItemFullScreenHandler(this::handleFullscreenClick);
    }

    private boolean handleFullscreenClick(int itemId) {
        if(!getState().fullScreenMap.containsKey(itemId)) {
            return false;
        }
        ComponentConnector cc = (ComponentConnector)getState().fullScreenMap.get(itemId);
        if(cc != null) {
            FSButtonUtil.requestFullScreen(cc.getWidget().getElement());
        } else {
            FSButtonUtil.requestFullScreen(Document.get().getBody());
        }
        return true;
    }

    @Override
    public void onStateChanged(StateChangeEvent event) {
        super.onStateChanged(event);

        getWidget().setFullscreenItemIds(getState().fullScreenMap.keySet());
    }

    public VFSMenuBar getWidget() {
        return (VFSMenuBar)super.getWidget();
    }
}
