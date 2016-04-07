package org.vaadin.alump.gofullscreen.gwt.client.connect;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.menubar.MenuBarConnector;
import com.vaadin.server.ClientConnector;
import com.vaadin.shared.Connector;
import org.vaadin.alump.gofullscreen.gwt.client.VFSMenuBar;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSButtonServerRpc;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSMenuBarState;

import java.util.logging.Logger;

/**
 * Connector for FullScreenMenuBar
 */
public class FSMenuBarConnector extends MenuBarConnector implements VFSMenuBar.VFSMenuBarClickHandler, FSButtonCIF {

    private static final Logger LOGGER = Logger.getLogger(FSMenuBarConnector.class.getName());

    private boolean supported = false;

    private FSClientUtil clientUtil;

    @Override
    public FSMenuBarState getState() {
        return (FSMenuBarState)super.getState();
    }

    @Override
    public VFSMenuBar getWidget() {
        return (VFSMenuBar) super.getWidget();
    }

    public void init() {
        super.init();

        clientUtil = new FSClientUtil(getRpcProxy(FSButtonServerRpc.class));

        supported = FSButtonUtil.isFullscreenSupported(Document.get().getBody());
        if (supported) {
            FSButtonUtil.addInstance(this);
        }

        getWidget().setVFSMenuBarClickHandler(this);
    }

    @Override
    public void onStateChanged(StateChangeEvent event) {
        super.onStateChanged(event);


    }

    @Override
    public boolean handleMenuClicked(int clickedItemId) {
        Connector target = getState().fullscreenTargets.get(clickedItemId);
        if(target != null) {
            clientUtil.handleClick(getTargetElement(clickedItemId));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onFullScreenChange() {
        notifyStateChange();

    }

    protected Connector getTargetElement(int itemId) {
        return getState().fullscreenTargets.get(itemId);
    }

    protected void notifyStateChange(int itemId) {
        Connector connector = getTargetElement(itemId);
        if(connector != null) {
            JavaScriptObject targetObject = clientUtil.getTargetJavaScriptObject(connector);
            clientUtil.notifyStateChange(targetObject);
        }
    }

    protected void notifyStateChange() {
        for(Connector connector : getState().fullscreenTargets.values()) {
            JavaScriptObject targetObject = clientUtil.getTargetJavaScriptObject(connector);
            clientUtil.notifyStateChange(targetObject);
        }
    }
}
