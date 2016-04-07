package org.vaadin.alump.gofullscreen;

/**
 * Interface to be implemented to listen fullscreen events
 */
public interface FullScreenListener {

    /**
     * Called when fullscreen state has changed
     * @param event Event defining the changes
     */
    void onFullScreenEvent(FullScreenEvent event);
}
