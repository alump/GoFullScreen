package org.vaadin.alump.gofullscreen;

import com.vaadin.server.AbstractExtension;
import com.vaadin.server.Resource;
import com.vaadin.shared.Connector;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSButtonServerRpc;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSMenuBarState;

/**
 * Extends MenuBar to allow fullscreen actions in MenuItems
 */
public class FullScreenMenuBar extends MenuBar {

    public FullScreenMenuBar() {
        super();
    }

    @Override
    protected FSMenuBarState getState() {
        return (FSMenuBarState)super.getState();
    }

    @Override
    protected FSMenuBarState getState(boolean markDirty) {
        return (FSMenuBarState)super.getState(markDirty);
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        super.beforeClientResponse(initial);
    }

    /**
     * Call this to method with item to attach fullscreen functionality to it
     * @param item
     * @param fullscreenTarget
     */
    public void attachFullScreenToItem(MenuBar.MenuItem item, Component fullscreenTarget) {
        getState().fullscreenTargets.put(item.getId(), fullscreenTarget);
    }

    @Override
    public void removeItem(MenuBar.MenuItem item) {
        getState().fullscreenTargets.remove(item.getId());
        super.removeItem(item);
    }

    @Override
    public void removeItems() {
        getState().fullscreenTargets.clear();
        super.removeItems();
    }

    private final FSButtonServerRpc serverRpc = new FSButtonServerRpc() {
        @Override
        public void enteredFullscreen(Connector connector) {
            setFullScreenState(connector, true);
        }

        @Override
        public void leftFullscreen(Connector connector) {
            setFullScreenState(connector, false);
        }
    };

    protected void setFullScreenState(Connector connector, boolean fullscreen) {
        if (targetIsFullscreen != fullscreen) {
            targetIsFullscreen = fullscreen;
            final FullScreenEvent event = new FullScreenEvent(this, getFullScreenTarget(), targetIsFullscreen);

            for (FullScreenListener listener : fsListeners) {
                listener.onFullScreenEvent(event);
            }
        }
    }
}
