package org.vaadin.alump.gofullscreen;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.NativeButton;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSButtonServerRpc;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSNativeButtonState;

import java.util.LinkedList;
import java.util.List;

/**
 * NativeButton that can be used to toggle client side fullscreen mode on browser
 * supporting requestFullScreen calls. Full screen state change requests has to
 * be done from user actions. For this reason there is no API for server side to
 * ask client side to move to full screen mode.
 */
@SuppressWarnings("serial")
public class FullScreenNativeButton extends NativeButton {

    private boolean targetIsFullscreen = false;
    private final List<FullScreenListener> fsListeners = new LinkedList<FullScreenListener>();

    /**
     * Create new full screen button.
     */
    public FullScreenNativeButton() {
        registerRpc(serverRpc);
    }

    /**
     * Create full screen button with a caption.
     *
     * @param caption Caption of button.
     */
    public FullScreenNativeButton(String caption) {
        super(caption);
        registerRpc(serverRpc);
    }

    /**
     * Create full screen button with caption and clicklistener.
     *
     * @param caption  Caption of button.
     * @param listener Click listener.
     */
    public FullScreenNativeButton(String caption, ClickListener listener) {
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
    protected FSNativeButtonState getState() {
        return (FSNativeButtonState) super.getState();
    }

    /**
     * Define component shown in fullscreen. Use null if you want full client
     * side view to be fullscreened.
     *
     * @param target Component shown in fullscreen or null (full client side view).
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
    public void addFullScreenListener(FullScreenListener listener) {
        fsListeners.add(listener);
    }

    /**
     * Remove fullscreen change listener.
     *
     * @param listener
     */
    public void removeFullScreenListener(FullScreenListener listener) {
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
