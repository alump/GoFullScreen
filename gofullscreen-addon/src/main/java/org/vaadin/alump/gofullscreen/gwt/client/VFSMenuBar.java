package org.vaadin.alump.gofullscreen.gwt.client;

import com.vaadin.client.ui.VMenuBar;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alump on 07/04/16.
 */
public class VFSMenuBar extends VMenuBar {

    private VFSMenuBarClickHandler clickHandler;

    public interface VFSMenuBarClickHandler {
        /**
         *
         * @param clickedItemId
         * @return true if click is handled, false if default behavior should be done
         */
        boolean handleMenuClicked(int clickedItemId);
    }

    public void setVFSMenuBarClickHandler(VFSMenuBarClickHandler handler) {
        clickHandler = handler;
    }

    @Override
    public void onMenuClick(int clickedItemId) {
        if (clickHandler != null && clickHandler.handleMenuClicked(clickedItemId)) {
            return;
        }
        super.onMenuClick(clickedItemId);
    }
}
