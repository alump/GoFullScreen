package org.vaadin.alump.gofullscreen.gwt.client.connect;

import com.google.gwt.core.client.JavaScriptObject;
import com.vaadin.client.BrowserInfo;

import java.util.HashSet;
import java.util.Set;

/**
 * Helper class for FSButtonConnector and FSNativeButtonConnector
 */
public class FSButtonUtil {

    // Use static message passing, to keep native event handling simple and to avoid js memory leaks
    private static Set<FSButtonCIF> currentInstances = null;


    public native final static boolean isInFullScreenMode()
	/*-{
        return !(!$doc.fullscreenElement && !$doc.mozFullScreenElement && !$doc.webkitFullscreenElement && !$doc.msFullscreenElement);
    }-*/;

    public native final static boolean isInFullScreenMode(
            JavaScriptObject element)
	/*-{
        return !(element != $doc.fullscreenElement && element != $doc.mozFullScreenElement && element != $doc.webkitFullscreenElement && element != $doc.msFullscreenElement);
    }-*/;

    public native final static boolean requestFullScreen(JavaScriptObject element)
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

    public native static boolean isFullscreenSupported(JavaScriptObject element)
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

    public native final static void cancelFullScreen()
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

    protected static void addInstance(FSButtonCIF instance) {
        initializeSharedResources();
        currentInstances.add(instance);
    }

    protected static void removeInstance(FSButtonCIF instance) {
        currentInstances.remove(instance);
    }

    protected static void notifyInstances() {
        for (FSButtonCIF instance : currentInstances) {
            instance.onFullScreenChange();
        }
    }

    public static void initializeSharedResources() {
        if (currentInstances == null) {
            currentInstances = new HashSet<FSButtonCIF>();
            attachFullScreenChangeListener(getChangeEventName());
        }
    }

    protected native final static void attachFullScreenChangeListener(String eventName)
    /*-{
        $doc.addEventListener(eventName,
            function () {
                @org.vaadin.alump.gofullscreen.gwt.client.connect.FSButtonUtil::notifyInstances()();
            }, false);
    }-*/;

    protected static String getChangeEventName() {
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



}
