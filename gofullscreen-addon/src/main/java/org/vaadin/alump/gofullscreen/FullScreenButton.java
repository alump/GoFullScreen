package org.vaadin.alump.gofullscreen;

import org.vaadin.alump.gofullscreen.gwt.client.connect.GoFullScreenServerRpc;
import org.vaadin.alump.gofullscreen.gwt.client.shared.GoFullScreenState;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

/**
 * Button that can be used to toggle client side fullscreen mode on browser
 * supporting requedtFullScreen calls.
 */
@SuppressWarnings("serial")
public class FullScreenButton extends Button {
	
	public FullScreenButton() {
		registerRpc(serverRpc);
	}
	
	public FullScreenButton(String caption) {
		super(caption);
		registerRpc(serverRpc);
	}
	
	public FullScreenButton(String caption, ClickListener listener) {
		super(caption, listener);
		registerRpc(serverRpc);
	}
	
	private final GoFullScreenServerRpc serverRpc = new GoFullScreenServerRpc() {
		@Override
		public void enteredFullscreen() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void leftFullscreen() {
			// TODO Auto-generated method stub
			
		}
	};
	
	@Override
	public GoFullScreenState getState() {
		return (GoFullScreenState) super.getState();
	}
	
	public void setFullScreenTarget(AbstractComponent target) {
		getState().fullscreenTarget = target;
	}
	
	public AbstractComponent getFullScreenTarget() {
		if (getState().fullscreenTarget != null) {
			return (AbstractComponent)getState().fullscreenTarget;
		} else {
			return UI.getCurrent();
		}
	}
}
