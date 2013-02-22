package org.vaadin.alump.gofullscreen.gwt.client.connect;

import org.vaadin.alump.gofullscreen.gwt.client.GoFullScreenButton;
import org.vaadin.alump.gofullscreen.gwt.client.shared.GoFullScreenState;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.VConsole;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.button.ButtonConnector;
import com.vaadin.shared.ui.Connect;

@SuppressWarnings("serial")
@Connect(org.vaadin.alump.gofullscreen.FullScreenButton.class)
public class GoFullScreenButtonConnector extends ButtonConnector {
	
	private JavaScriptObject fullscreenTarget;
	private boolean isInFullScreen = false;
	
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
    	
    	if (getState().fullscreenTarget == null) {
    		fullscreenTarget = null;
    	} else {
    		fullscreenTarget = ((AbstractComponentConnector) (getState().fullscreenTarget)).getWidget().getElement();
    	}
    	
    	if (!isBrowserSupported()) {
    		getWidget().setVisible(false);
    	}
    }
    
    protected boolean isBrowserSupported() {
    	return BrowserInfo.get().isChrome() || BrowserInfo.get().isGecko() || BrowserInfo.get().isOpera();
    }
    
    private final ClickHandler clickToFullScreenHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			JavaScriptObject target = getTargetElement();
			if (isInFullScreenMode(target)) {
				VConsole.log("FullScreen: toogle off");
				cancelFullScreen();
				notifyStateChange();
			} else {
				VConsole.log("FullScreen: toogle on");
				requestFullScreen(target);
				notifyStateChange();
			}
		}
    	
    };
    
    protected void notifyStateChange() {
    	if (isInFullScreenMode(fullscreenTarget)) {
    		if (!isInFullScreen) {
    			isInFullScreen = true;
    			serverRpc.enteredFullscreen();
    		}
    	} else {
    		if (isInFullScreen) {
    			isInFullScreen = false;
    			serverRpc.enteredFullscreen();
    		}
    	}
    }
	
	protected JavaScriptObject getTargetElement() {
		if (fullscreenTarget == null) {
			return getConnection().getUIConnector().getWidget().getElement();
		} else {
			return fullscreenTarget;
		}
	}
	
	protected native final static boolean isInFullScreenMode()
	/*-{
		return !(!$doc.fullscreenElement && !$doc.mozFullScreenElement && !$doc.webkitFullscreenElement);
	}-*/;
	
	protected native final static boolean isInFullScreenMode(JavaScriptObject element)
	/*-{
		return !(element != $doc.fullscreenElement &&  element != $doc.mozFullScreenElement &&  element != $doc.webkitFullscreenElement);
	}-*/;
	
    protected native final static boolean requestFullScreen(JavaScriptObject element)
    /*-{
        if(element.requestFullscreen) {
            element.requestFullscreen();
        } else if(element.webkitRequestFullScreen) {
            element.webkitRequestFullScreen();
        } else if(element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
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
        } else {
            console.log('leaving fullscreen not supported!');
        }
    }-*/;

}
