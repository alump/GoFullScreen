package org.vaadin.alump.gofullscreen;

import org.vaadin.alump.gofullscreen.gwt.client.connect.GoFullScreenServerRpc;
import org.vaadin.alump.gofullscreen.gwt.client.shared.GoFullScreenState;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

/**
 * Button that can be used to toggle client side fullscreen mode on browser
 * supporting requedtFullScreen calls. Full screen state change requests has to
 * be done from user actions. For this reason there is no API for server side
 * to ask client side to move to full screen mode.
 */
@SuppressWarnings("serial")
public class FullScreenButton extends Button {
	
	private Boolean lastFullScreenRequest = null;
	
	/**
	 * Create new full screen button.
	 */
	public FullScreenButton() {
		registerRpc(serverRpc);
	}
	
	/**
	 * Create full screen button with a caption.
	 * @param caption Caption of button.
	 */
	public FullScreenButton(String caption) {
		super(caption);
		registerRpc(serverRpc);
	}
	
	/**
	 * Create full screen button with caption and clicklistener.
	 * @param caption Caption of button.
	 * @param listener Click listener.
	 */
	public FullScreenButton(String caption, ClickListener listener) {
		super(caption, listener);
		registerRpc(serverRpc);
	}
	
	private final GoFullScreenServerRpc serverRpc = new GoFullScreenServerRpc() {
		@Override
		public void enteredFullscreen() {
			lastFullScreenRequest = true;
		}

		@Override
		public void leftFullscreen() {
			lastFullScreenRequest = false;
		}
	};
	
	@Override
	public GoFullScreenState getState() {
		return (GoFullScreenState) super.getState();
	}
	
	/**
	 * Define component shown in fullscreen, if null the current UI will be
	 * used.
	 * @param target Component shown in fullscreen.
	 */
	public void setFullScreenTarget(AbstractComponent target) {
		getState().fullscreenTarget = target;
	}
	
	/**
	 * Get current full screen target component.
	 * @return Component shown in fullscreen.
	 */
	public AbstractComponent getFullScreenTarget() {
		if (getState().fullscreenTarget != null) {
			return (AbstractComponent)getState().fullscreenTarget;
		} else {
			return UI.getCurrent();
		}
	}
	
	/**
	 * Get last request done by button. This can be out-of-sync if user has
	 * entered to or escaped from fullscreen mode with another method.
	 * @return null if nothing requested, true if full screen requested, false
	 * if full screen cancelled.
	 */
	public Boolean getLastRequest() {
		return lastFullScreenRequest;
	}
}
