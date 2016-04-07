package org.vaadin.alump.gofullscreen;

import java.util.LinkedList;
import java.util.List;

import com.vaadin.ui.Component;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSButtonServerRpc;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSButtonState;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;

/**
 * Button that can be used to toggle client side fullscreen mode on browser
 * supporting requestFullScreen calls. Full screen state change requests has to
 * be done from user actions. For this reason there is no API for server side to
 * ask client side to move to full screen mode.
 */
@SuppressWarnings("serial")
public class FullScreenButton extends Button {

    private boolean targetIsFullscreen = false;
    private final List<FullScreenListener> fsListeners = new LinkedList<FullScreenListener>();
    /**
     * Create new full screen button.
     */
    public FullScreenButton() {
        registerRpc(serverRpc);
    }

    /**
     * Create full screen button with a caption.
     *
     * @param caption Caption of button.
     */
    public FullScreenButton(String caption) {
        super(caption);
        registerRpc(serverRpc);
    }

    /**
     * Create full screen button with caption and clicklistener.
     *
     * @param caption  Caption of button.
     * @param listener Click listener.
     */
    public FullScreenButton(String caption, ClickListener listener) {
        super(caption, listener);
        registerRpc(serverRpc);
    }

    private final FSButtonServerRpc serverRpc = new FSButtonServerRpc() {
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
            final FullScreenEvent event = new FullScreenEvent(this, getFullScreenTarget(), targetIsFullscreen);

            for (FullScreenListener listener : fsListeners) {
                listener.onFullScreenEvent(event);
            }
        }
    }

    @Override
    protected FSButtonState getState() {
        return (FSButtonState) super.getState();
    }

    /**
     * Define component shown in fullscreen. Use null if you want full client
     * side view to be fullscreened.
     *
     * @param target Component shown in fullscreen or null (full client side view).
     */
    public void setFullScreenTarget(Component target) {
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
     * Use addFullScreenListener
     */
    public void addFullScreenListener(FullScreenListener listener) {
        fsListeners.add(listener);
    }

    /**
     * Remove fullscreen change listener.
     *
     * @param listener
     */
    public void removeFullScreenChangeListener(FullScreenListener listener) {
        fsListeners.remove(listener);
    }

    /**
     * Hide button automatically on browsers that do not support FullScreen API
     *
     * @param hidden true to auto hide
     */
    public void setHiddenWhenNotSupported(boolean hidden) {
        getState().hideIfNotSupported = hidden;
    }

    /**
     * Check if button is automatically hidden on browsers not supporting FullScreen API
     *
     * @return true if auto hidden
     */
    public boolean isHiddenWhenNotSupported() {
        return getState().hideIfNotSupported;
    }
}
