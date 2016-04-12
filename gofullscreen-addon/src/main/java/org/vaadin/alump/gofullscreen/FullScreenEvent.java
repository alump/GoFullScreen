package org.vaadin.alump.gofullscreen;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import java.io.Serializable;

/**
 * Interface for fullscreen state change listeners of target component.
 */
public class FullScreenEvent implements Serializable {
    private final Component component;
    private final boolean fullScreen;
    private final Component source;

    public FullScreenEvent(Component source, Component component, boolean fullScreen) {
        this.source = source;
        this.component = component;
        this.fullScreen = fullScreen;
    }

    /**
     * Get source component (in 0.5.1: FullScreenButton or FullScreenNativeButton)
     * @return Fullscreen component sending events
     */
    public Component getSource() {
        return source;
    }

    /**
     * Get target component with changed fullscreen state.
     * @return target component
     */
    public Component getComponent() {
        return component;
    }

    /**
     * Get target component's full screen state
     * @return true if full screen, false if not
     */
    public boolean isFullScreen() {
        return fullScreen;
    }
}
