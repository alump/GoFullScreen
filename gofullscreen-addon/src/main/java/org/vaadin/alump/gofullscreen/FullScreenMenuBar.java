package org.vaadin.alump.gofullscreen;

import com.vaadin.shared.ui.menubar.MenuBarState;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import org.vaadin.alump.gofullscreen.gwt.client.shared.FSMenuBarState;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Extending MenuBar to allow adding fullscreen options
 */
public class FullScreenMenuBar extends MenuBar {

    @Override
    protected FSMenuBarState getState() {
        return (FSMenuBarState) super.getState();
    }

    @Override
    protected FSMenuBarState getState(boolean markAsDirty) {
        return (FSMenuBarState) super.getState(markAsDirty);
    }

    @Override
    public void beforeClientResponse(boolean initialResponse) {
        super.beforeClientResponse(initialResponse);

        //Clean up fullscreen mapping from items not present anymore
        Set<Integer> allIds = getAllItemIds();
        Set<Integer> removeMappingIds = getState(false).fullScreenMap.keySet().stream()
                .filter(id -> !allIds.contains(id)).collect(Collectors.toSet());
        removeMappingIds.forEach(id -> getState().fullScreenMap.remove(id));
    }


    protected Set<Integer> getAllItemIds() {
        final HashSet<Integer> ids = new HashSet<>();
        super.getItems().stream().forEach(item -> {
            addAllItemIds(ids, item);
        });
        return ids;
    }


    protected void addAllItemIds(final Set<Integer> currentList, MenuBar.MenuItem item) {
        currentList.add(item.getId());
        Optional.ofNullable(item.getChildren()).ifPresent(c -> c.stream().forEach(i -> addAllItemIds(currentList, i)));
    }

    /**
     * Add fullscreen mapping to given menuitem. When item is clicked given component will be shown in fullscreen.
     * @param menuItem Menu item that will lead to fullscreen mode
     * @param component Component fullscreened, or null if everything should be fullscreened
     */
    public void addFullScreenMapping(MenuItem menuItem, Component component) {
        if(Objects.requireNonNull(menuItem).getCommand() == null) {
            //Make sure item has a command, even if empty
            menuItem.setCommand(selectedItem -> {
            });
        }
        getState().fullScreenMap.put(menuItem.getId(), component);
    }

    /**
     * Remove fullscreen mapping from menu item
     * @param menuItem Menu that will not have fullscreen action after this call
     */
    public void removeFullScreenMapping(MenuItem menuItem) {
        getState().fullScreenMap.remove(menuItem.getId());
    }
}
