package org.vaadin.alump.gofullscreen;

import java.util.LinkedList;
import java.util.List;

import org.vaadin.alump.gofullscreen.gwt.client.connect.GoFullScreenServerRpc;
import org.vaadin.alump.gofullscreen.gwt.client.shared.GoFullScreenState;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;

/**
 * Button that can be used to toggle client side fullscreen mode on browser
 * supporting requedtFullScreen calls. Full screen state change requests has to
 * be done from user actions. For this reason there is no API for server side to
 * ask client side to move to full screen mode.
 */
@SuppressWarnings("serial")
public class FullScreenButton extends Button {

	private boolean targetIsFullscreen = false;
	private final List<FullScreenChangeListener> fsListeners = new LinkedList<FullScreenChangeListener>();

	/**
	 * Interface for fullscreen state change listeners of target component.
	 */
	public interface FullScreenChangeListener {
		/**
		 * Called when target component's fullscreen state changes.
		 * 
		 * @param component
		 *            Target component with changed fullscreen state.
		 * @param fullscreen
		 *            Is component fullscreen.
		 */
		void onFullScreenChangeListener(AbstractComponent component,
				boolean fullscreen);
	}

	/**
	 * Create new full screen button.
	 */
	public FullScreenButton() {
		registerRpc(serverRpc);
	}

	/**
	 * Create full screen button with a caption.
	 * 
	 * @param caption
	 *            Caption of button.
	 */
	public FullScreenButton(String caption) {
		super(caption);
		registerRpc(serverRpc);
	}

	/**
	 * Create full screen button with caption and clicklistener.
	 * 
	 * @param caption
	 *            Caption of button.
	 * @param listener
	 *            Click listener.
	 */
	public FullScreenButton(String caption, ClickListener listener) {
		super(caption, listener);
		registerRpc(serverRpc);
	}

	private final GoFullScreenServerRpc serverRpc = new GoFullScreenServerRpc() {
		@Override
		public void enteredFullscreen() {
			setFullScreenState(true);
		}

		@Override
		public void leftFullscreen() {
			setFullScreenState(false);
		}
	};

	protected void setFullScreenState(boolean fullscreen) {
		if (targetIsFullscreen != fullscreen) {
			targetIsFullscreen = fullscreen;
			for (FullScreenChangeListener listener : fsListeners) {
				listener.onFullScreenChangeListener(getFullScreenTarget(),
						targetIsFullscreen);
			}
		}
	}

	@Override
	protected GoFullScreenState getState() {
		return (GoFullScreenState) super.getState();
	}

	/**
	 * Define component shown in fullscreen. Use null if you want full client
	 * side view to be fullscreened.
	 * 
	 * @param target
	 *            Component shown in fullscreen or null (full client side view).
	 */
	public void setFullScreenTarget(AbstractComponent target) {
		getState().fullscreenTarget = target;
	}

	/**
	 * Get current full screen target component.
	 * 
	 * @return Component shown in fullscreen or null (full client side view)
	 */
	public AbstractComponent getFullScreenTarget() {
		if (getState().fullscreenTarget != null) {
			return (AbstractComponent) getState().fullscreenTarget;
		} else {
			return null;
		}
	}

	/**
	 * Is target component currently full screen.
	 */
	public boolean isFullScreen() {
		return targetIsFullscreen;
	}

	/**
	 * Add listener for fullscreen change of target element.
	 * 
	 * @param listener
	 */
	public void addFullScreenChangeListener(FullScreenChangeListener listener) {
		fsListeners.add(listener);
	}

	/**
	 * Remove fullscreen change listener.
	 * 
	 * @param listener
	 */
	public void removeFullScreenChangeListener(FullScreenChangeListener listener) {
		fsListeners.remove(listener);
	}

    /**
     * Hide button automatically on browsers that do not support FullScreen API
     * @param hidden true to auto hide
     */
    public void setHiddenWhenNotSupported(boolean hidden) {
        getState().hideIfNotSupported = hidden;
    }

    /**
     * Check if button is automatically hidden on browsers not supporting FullScreen API
     * @return true if auto hidden
     */
    public boolean isHiddenWhenNotSupported() {
        return getState().hideIfNotSupported;
    }
}
