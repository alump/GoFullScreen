package org.vaadin.alump.gofullscreen.gwt.client.connect;

import org.vaadin.alump.gofullscreen.gwt.client.GoFullScreenButton;
import org.vaadin.alump.gofullscreen.gwt.client.shared.GoFullScreenState;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.vaadin.client.VConsole;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.button.ButtonConnector;
import com.vaadin.shared.ui.Connect;

@SuppressWarnings("serial")
@Connect(org.vaadin.alump.gofullscreen.FullScreenButton.class)
public class GoFullScreenConnector extends ButtonConnector {
	
	private JavaScriptObject fullscreenTarget;
	
	public GoFullScreenConnector() {
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
    }
    
    private final ClickHandler clickToFullScreenHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			VConsole.log("FULLSCREEN!");
			requestFullScreen(getTargetElement());
		}
    	
    };
	
	
	protected JavaScriptObject getTargetElement() {
		if (fullscreenTarget == null) {
			return getConnection().getUIConnector().getWidget().getElement();
		} else {
			return fullscreenTarget;
		}
	}
	
    protected native final static void requestFullScreen(JavaScriptObject element)
    /*-{
        if(element.requestFullScreen) {
            element.requestFullScreen();
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
