package org.vaadin.alump.gofullscreen.gwt.client.connect;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.ComponentConnector;
import com.vaadin.shared.Connector;
import org.vaadin.alump.gofullscreen.gwt.client.VFSButton;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSButtonServerRpc;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSButtonState;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.button.ButtonConnector;
import com.vaadin.shared.ui.Connect;

import java.util.logging.Logger;

/**
 * Connector for FullScreen Button
 */
@SuppressWarnings("serial")
@Connect(org.vaadin.alump.gofullscreen.FullScreenButton.class)
public class FSButtonConnector extends ButtonConnector implements FSButtonCIF {

    private Connector fullscreenTarget;
    private boolean supported = false;

    private FSClientUtil clientUtil;

    private static final Logger LOGGER = Logger.getLogger(FSButtonConnector.class.getName());

    public FSButtonConnector() {
        getWidget().addClickHandler(clickToFullScreenHandler);
    }

    @Override
    public VFSButton getWidget() {
        return (VFSButton) super.getWidget();
    }

    @Override
    public FSButtonState getState() {
        return (FSButtonState) super.getState();
    }

    @Override
    public void init() {
        super.init();

        clientUtil = new FSClientUtil(getRpcProxy(FSButtonServerRpc.class));

        supported = FSButtonUtil.isFullscreenSupported(Document.get().getBody());
        if (supported) {
            FSButtonUtil.addInstance(this);
        }
    }

    @Override
    public void onStateChanged(StateChangeEvent event) {
        super.onStateChanged(event);

        if(event.isInitialStateChange() || event.hasPropertyChanged("fullscreenTarget")) {
            if(getState().fullscreenTarget != null) {
                notifyStateChange();
            }
        }

        if (!FSButtonUtil.isFullscreenSupported(Document.get().getBody())) {
            if (getState().hideIfNotSupported) {
                getWidget().setVisible(false);
            }
        }
    }

    private final ClickHandler clickToFullScreenHandler = new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            if (!FSButtonConnector.this.isEnabled()) {
                LOGGER.fine("Ignoring click when not enabled.");
                return;
            }
            clientUtil.handleClick(getTargetElement());
        }

    };

    protected JavaScriptObject getTargetElement() {
        if (getState().fullscreenTarget == null) {
            return RootPanel.getBodyElement();
        } else {
            return clientUtil.getTargetJavaScriptObject(getState().fullscreenTarget);
        }
    }

    /**
     * Handler for fullscreen events.
     */
    @Override
    public void onFullScreenChange() {
        notifyStateChange();
    }

    public void onUnregister() {
        FSButtonUtil.removeInstance(this);
        super.onUnregister();
    }

    protected void notifyStateChange() {
        clientUtil.notifyStateChange(getTargetElement());
    }

}
