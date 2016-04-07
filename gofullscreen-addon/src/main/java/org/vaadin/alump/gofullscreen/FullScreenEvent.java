package org.vaadin.alump.gofullscreen;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

import java.io.Serializable;

/**
 * Interface for fullscreen state change listeners of target component.
 */
public class FullScreenEvent implements Serializable {
    private final Component component;
    private final boolean fullscreen;
    private final Button source;

    public FullScreenEvent(Button source, Component component, boolean fullscreen) {
        this.source = source;
        this.component = component;
        this.fullscreen = fullscreen;
    }

    /**
     * Get source button
     * @return Fullscreen button sending events
     */
    public Button getSource() {
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
     * Get target component's fullscreen state
     * @return true if fullscreen, false if not
     */
    public boolean isFullscreen() {
        return fullscreen;
    }
}
