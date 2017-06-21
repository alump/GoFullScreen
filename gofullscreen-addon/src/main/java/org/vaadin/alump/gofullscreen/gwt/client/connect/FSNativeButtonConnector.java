package org.vaadin.alump.gofullscreen.gwt.client.connect;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.nativebutton.NativeButtonConnector;
import com.vaadin.shared.ui.Connect;
import org.vaadin.alump.gofullscreen.gwt.client.VFSNativeButton;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSButtonServerRpc;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSNativeButtonState;

import java.util.logging.Logger;

/**
 * Created by alump on 08/08/14.
 */
@SuppressWarnings("serial")
@Connect(org.vaadin.alump.gofullscreen.FullScreenNativeButton.class)
public class FSNativeButtonConnector extends NativeButtonConnector implements FSButtonCIF {

    private JavaScriptObject fullscreenTarget;
    private boolean isInFullScreen = false;
    private boolean supported = false;

    private static final Logger LOGGER = Logger.getLogger(FSNativeButtonConnector.class.getName());

    public FSNativeButtonConnector() {
        getWidget().addClickHandler(clickToFullScreenHandler);
    }

    @Override
    public VFSNativeButton getWidget() {
        return (VFSNativeButton) super.getWidget();
    }

    @Override
    public FSNativeButtonState getState() {
        return (FSNativeButtonState) super.getState();
    }

    @Override
    public void init() {
        super.init();

        supported = FSButtonUtil.isFullscreenSupported(Document.get().getBody());
        if (supported) {
            FSButtonUtil.addInstance(this);
        }
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        if (fullscreenTarget != getState().fullscreenTarget) {
            if (getState().fullscreenTarget == null) {
                fullscreenTarget = null;
            } else {
                fullscreenTarget = ((AbstractComponentConnector) (getState().fullscreenTarget))
                        .getWidget().getElement();
            }
            notifyStateChange();
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
            if (!FSNativeButtonConnector.this.isEnabled()) {
                LOGGER.fine("Ignoring click when not enabled.");
                return;
            }
            JavaScriptObject target = getTargetElement();
            if (FSButtonUtil.isInFullScreenMode(target)) {
                // LOGGER.fine("FullScreen: toogle off");
                FSButtonUtil.cancelFullScreen();
                notifyStateChange();
            } else {
                // LOGGER.fine("FullScreen: toogle on");
                FSButtonUtil.requestFullScreen(target);
                notifyStateChange();
            }
        }

    };

    protected void notifyStateChange() {
        if (FSButtonUtil.isInFullScreenMode(getTargetElement())) {
            if (!isInFullScreen) {
                isInFullScreen = true;
                getRpcProxy(FSButtonServerRpc.class).enteredFullscreen();
            }
        } else {
            if (isInFullScreen) {
                isInFullScreen = false;
                getRpcProxy(FSButtonServerRpc.class).leftFullscreen();
            }
        }
    }

    protected JavaScriptObject getTargetElement() {
        if (fullscreenTarget == null) {
            return RootPanel.getBodyElement();
        } else {
            return fullscreenTarget;
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

}
