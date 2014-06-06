package org.vaadin.alump.gofullscreen.gwt.client.connect;

import com.google.gwt.dom.client.Document;
import org.vaadin.alump.gofullscreen.gwt.client.GoFullScreenButton;
import org.vaadin.alump.gofullscreen.gwt.client.shared.GoFullScreenState;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.button.ButtonConnector;
import com.vaadin.shared.ui.Connect;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Connector for FullScreen Button
 */
@SuppressWarnings("serial")
@Connect(org.vaadin.alump.gofullscreen.FullScreenButton.class)
public class GoFullScreenButtonConnector extends ButtonConnector {

    private JavaScriptObject fullscreenTarget;
    private boolean isInFullScreen = false;
    private boolean supported = false;

    // Use static message passing, to keep native event handling simple and to avoid js memory leaks
    private static Set<GoFullScreenButtonConnector> currentInstances = null;

    protected static void addInstance(GoFullScreenButtonConnector instance) {
        initializeSharedResources();
        currentInstances.add(instance);
    }

    protected static void removeInstance(GoFullScreenButtonConnector instance) {
        currentInstances.remove(instance);
    }

    protected static void notifyInstances() {
        for (GoFullScreenButtonConnector instance : currentInstances) {
            instance.onFullScreenChange();
        }
    }

    public static void initializeSharedResources() {
        if (currentInstances == null) {
            currentInstances = new HashSet<GoFullScreenButtonConnector>();
            attachFullScreenChangeListener(getChangeEventName());
        }
    }

    protected native final static void attachFullScreenChangeListener(String eventName)
    /*-{
        $doc.addEventListener(eventName,
            function () {
                @org.vaadin.alump.gofullscreen.gwt.client.connect.GoFullScreenButtonConnector::notifyInstances()();
            }, false);
    }-*/;

    private static String getChangeEventName() {
        if (BrowserInfo.get().isWebkit()) {
            return "webkitfullscreenchange";
        } else if (BrowserInfo.get().isGecko()) {
            return "mozfullscreenchange";
        } else if (BrowserInfo.get().isIE()) {
            return "MSFullscreenChange";
        } else {
            return "fullscreenchange";
        }
    }


    public GoFullScreenButtonConnector() {
        getWidget().addClickHandler(clickToFullScreenHandler);
    }

    @Override
    public GoFullScreenButton getWidget() {
        return (GoFullScreenButton) super.getWidget();
    }

    @Override
    public GoFullScreenState getState() {
        return (GoFullScreenState) super.getState();
    }

    @Override
    public void init() {
        super.init();

        supported = isFullscreenSupported(Document.get().getBody());
        if (supported) {
            addInstance(this);
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

        if (!isFullscreenSupported(Document.get().getBody())) {
            if (getState().hideIfNotSupported) {
                getWidget().setVisible(false);
            }
        }
    }

    private final ClickHandler clickToFullScreenHandler = new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
            if (!GoFullScreenButtonConnector.this.isEnabled()) {
                getLogger().fine("Ignoring click when not enabled.");
                return;
            }
            JavaScriptObject target = getTargetElement();
            if (isInFullScreenMode(target)) {
                // VConsole.log("FullScreen: toogle off");
                cancelFullScreen();
                notifyStateChange();
            } else {
                // VConsole.log("FullScreen: toogle on");
                requestFullScreen(target);
                notifyStateChange();
            }
        }

    };

    protected void notifyStateChange() {
        if (isInFullScreenMode(getTargetElement())) {
            if (!isInFullScreen) {
                isInFullScreen = true;
                getRpcProxy(GoFullScreenServerRpc.class).enteredFullscreen();
            }
        } else {
            if (isInFullScreen) {
                isInFullScreen = false;
                getRpcProxy(GoFullScreenServerRpc.class).leftFullscreen();
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
    protected void onFullScreenChange() {
        notifyStateChange();
    }

    protected native final static boolean isInFullScreenMode()
	/*-{
        return !(!$doc.fullscreenElement && !$doc.mozFullScreenElement && !$doc.webkitFullscreenElement && !$doc.msFullscreenElement);
    }-*/;

    protected native final static boolean isInFullScreenMode(
            JavaScriptObject element)
	/*-{
        return !(element != $doc.fullscreenElement && element != $doc.mozFullScreenElement && element != $doc.webkitFullscreenElement && element != $doc.msFullscreenElement);
    }-*/;

    protected native final static boolean requestFullScreen(JavaScriptObject element)
	/*-{
        if (element.requestFullscreen) {
            element.requestFullscreen();
        } else if (element.webkitRequestFullScreen) {
            element.webkitRequestFullScreen();
        } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
        } else if (element.msRequestFullscreen) {
            element.msRequestFullscreen();
        } else {
            console.error('entering fullscreen not supported!');
        }
    }-*/;

    protected native static boolean isFullscreenSupported(JavaScriptObject element)
    /*-{
        if (element.requestFullscreen) {
            return true;
        } else if (element.webkitRequestFullScreen) {
            return true;
        } else if (element.mozRequestFullScreen) {
            return true;
        } else if (element.msRequestFullscreen) {
            return true;
        } else {
            return false;
        }
    }-*/;

    protected native final static void cancelFullScreen()
	/*-{
        if ($doc.cancelFullScreen) {
            $doc.cancelFullScreen();
        } else if ($doc.webkitCancelFullScreen) {
            $doc.webkitCancelFullScreen();
        } else if ($doc.mozCancelFullScreen) {
            $doc.mozCancelFullScreen();
        } else if ($doc.msExitFullscreen) {
            $doc.msExitFullscreen();
        } else {
            console.error('leaving fullscreen not supported!');
        }
    }-*/;

    private static Logger getLogger() {
        return Logger.getLogger(GoFullScreenButtonConnector.class.getName());
    }

    public void onUnregister() {
        removeInstance(this);
        super.onUnregister();
    }

}
