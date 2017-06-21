package org.vaadin.alump.gofullscreen.gwt.client;

import com.vaadin.client.ui.VMenuBar;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class VFSMenuBar extends VMenuBar {

    private Set<Integer> fullscreenItemIds;
    private MenuItemFullScreenHandler fullScreenHandler;

    public interface MenuItemFullScreenHandler {
        boolean handleFullscreenClick(int clickedItemId);
    }

    public void setMenuItemFullScreenHandler(MenuItemFullScreenHandler handler) {
        this.fullScreenHandler = handler;
    }

    @Override
    public void onMenuClick(int clickedItemId) {
        if(fullscreenItemIds != null && fullscreenItemIds.contains(clickedItemId) && fullScreenHandler != null) {
            if(fullScreenHandler.handleFullscreenClick(clickedItemId)) {
                return;
            }
        }

        super.onMenuClick(clickedItemId);
    }

    public void setFullscreenItemIds(Collection<Integer> itemIds) {
        fullscreenItemIds = new HashSet<>(itemIds);
    }
}
