package org.vaadin.alump.gofullscreen.gwt.client.connect;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.Connector;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSButtonServerRpc;

import java.util.logging.Logger;

/**
 * Created by alump on 07/04/16.
 */
public class FSClientUtil {

    private static final Logger LOGGER = Logger.getLogger(FSClientUtil.class.getName());

    private boolean isInFullScreen = false;
    private FSButtonServerRpc serverRpc;

    public FSClientUtil(FSButtonServerRpc serverRpc) {
        this.serverRpc = serverRpc;
    }

    public void handleClick(Connector connector) {
        JavaScriptObject target = getTargetJavaScriptObject(connector);

        if (FSButtonUtil.isInFullScreenMode(target)) {
            // LOGGER.fine("FullScreen: toogle off");
            FSButtonUtil.cancelFullScreen();
            notifyStateChange(connector);
        } else {
            // LOGGER.fine("FullScreen: toogle on");
            FSButtonUtil.requestFullScreen(target, BrowserInfo.get().isSafari());
            notifyStateChange(connector);
        }
    }

    public void notifyStateChange(Connector connector) {
        JavaScriptObject target = getTargetJavaScriptObject(connector);

        if (FSButtonUtil.isInFullScreenMode(target)) {
            if (!isInFullScreen) {
                isInFullScreen = true;
                serverRpc.enteredFullscreen(connector);
            }
        } else {
            if (isInFullScreen) {
                isInFullScreen = false;
                serverRpc.leftFullscreen(connector);
            }
        }
    }

    public JavaScriptObject getTargetJavaScriptObject(Connector targetConnector) {
        Widget widget = null;

        if(targetConnector instanceof AbstractComponentConnector) {
            widget = ((AbstractComponentConnector)targetConnector).getWidget();
        } else if(targetConnector instanceof ComponentConnector) {
            widget = ((ComponentConnector)targetConnector).getWidget();
        }
        if(widget != null) {
            return widget.getElement();
        } else {
            LOGGER.severe("Failed to locate widget's element");
            return null;
        }
    }
}
