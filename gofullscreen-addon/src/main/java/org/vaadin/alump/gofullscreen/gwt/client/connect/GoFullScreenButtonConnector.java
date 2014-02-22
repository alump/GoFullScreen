package org.vaadin.alump.gofullscreen.gwt.client.connect;

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

/**
 * Connector for FullScreen Button
 */
@SuppressWarnings("serial")
@Connect(org.vaadin.alump.gofullscreen.FullScreenButton.class)
public class GoFullScreenButtonConnector extends ButtonConnector {

	private JavaScriptObject fullscreenTarget;
	private boolean isInFullScreen = false;
	private boolean changeListenerAttached = false;

	private final GoFullScreenServerRpc serverRpc = RpcProxy.create(
			GoFullScreenServerRpc.class, this);

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

		if (!isBrowserSupported()) {
            if(getState().hideIfNotSupported) {
			    getWidget().setVisible(false);
            }
		} else if (!changeListenerAttached) {
			if (isSupportedWebKit()) {
				attachFullScreenChangeListener("webkitfullscreenchange");
			} else if (BrowserInfo.get().isGecko()) {
				attachFullScreenChangeListener("mozfullscreenchange");
            } else if (isIE11()) {
                attachFullScreenChangeListener("MSFullscreenChange");
			} else {
				attachFullScreenChangeListener("fullscreenchange");
			}
			changeListenerAttached = true;
		}
	}

    /**
     * Check if browser is one of supported WebKit browsers.
     * @return
     */
    private static boolean isSupportedWebKit() {
        BrowserInfo info = BrowserInfo.get();

        if(!info.isWebkit()) {
            return false;
        } else if(info.isChrome()) {
            return true;
        } else if(info.isSafari() && !info.isIOS()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if browser is IE11
     * @return
     */
    private static boolean isIE11() {
        if(BrowserInfo.get().isIE()) {
            return BrowserInfo.getBrowserString().toLowerCase().contains("rv:11");
        } else {
            return false;
        }
    }

	protected boolean isBrowserSupported() {
		return isSupportedWebKit() || BrowserInfo.get().isGecko()
				|| BrowserInfo.get().isOpera() || isIE11();
	}

	private final ClickHandler clickToFullScreenHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
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
				serverRpc.enteredFullscreen();
			}
		} else {
			if (isInFullScreen) {
				isInFullScreen = false;
				serverRpc.leftFullscreen();
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
		// VConsole.log("onFullScreenChange");
		notifyStateChange();
	}

	protected native final void attachFullScreenChangeListener(String eventName)
	/*-{
	    var that = this;
		$doc.addEventListener(eventName, function() {
			that.@org.vaadin.alump.gofullscreen.gwt.client.connect.GoFullScreenButtonConnector::onFullScreenChange()();
		}, false); 
	}-*/;

	protected native final static boolean isInFullScreenMode()
	/*-{
		return !(!$doc.fullscreenElement && !$doc.mozFullScreenElement && !$doc.webkitFullscreenElement && !$doc.msFullscreenElement);
	}-*/;

	protected native final static boolean isInFullScreenMode(
			JavaScriptObject element)
	/*-{
		return !(element != $doc.fullscreenElement &&  element != $doc.mozFullScreenElement &&  element != $doc.webkitFullscreenElement && element != $doc.msFullscreenElement);
	}-*/;

	protected native final static boolean requestFullScreen(
			JavaScriptObject element)
	/*-{
	    if(element.requestFullscreen) {
	        element.requestFullscreen();
	    } else if(element.webkitRequestFullScreen) {
	        element.webkitRequestFullScreen();
	    } else if(element.mozRequestFullScreen) {
	        element.mozRequestFullScreen();
	    } else if(element.msRequestFullscreen) {
	        element.msRequestFullscreen();
	    } else {
	        console.log('entering fullscreen not supported!');
	    }
	}-*/;

	protected native final static void cancelFullScreen()
	/*-{
		if($doc.cancelFullScreen) {
	        $doc.cancelFullScreen();
	    } else if($doc.webkitCancelFullScreen) {
	        $doc.webkitCancelFullScreen();
	    } else if($doc.mozCancelFullScreen) {
	        $doc.mozCancelFullScreen();
	    } else if($doc.msExitFullscreen) {
            $doc.msExitFullscreen();
	    } else {
	        console.log('leaving fullscreen not supported!');
	    }
	}-*/;

}
