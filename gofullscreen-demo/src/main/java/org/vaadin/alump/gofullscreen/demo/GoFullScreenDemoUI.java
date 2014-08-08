package org.vaadin.alump.gofullscreen.demo;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.vaadin.alump.gofullscreen.FullScreenButton;
import org.vaadin.alump.gofullscreen.FullScreenButton.FullScreenChangeListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import org.vaadin.alump.gofullscreen.FullScreenNativeButton;

import javax.servlet.annotation.WebServlet;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
@Theme("demo")
@Title("GoFullScreen Demo")
public class GoFullScreenDemoUI extends UI {

    protected Label notice;
    protected Button openWindowButton;
    protected HorizontalLayout buttonLayout;
    protected FullScreenButton windowFullScreenButton;
    protected Window extraWindow;
    protected final Set<Component> buttons = new HashSet<Component>();
    protected Image image;

    @WebServlet(value = "/*")
    @VaadinServletConfiguration(productionMode = false, ui = GoFullScreenDemoUI.class, widgetset = "org.vaadin.alump.gofullscreen.demo.gwt.GoFullScreenDemoWidgetSet")
    public static class FancyLayoutsUIServlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setWidth("100%");
        setContent(layout);

        notice = new Label(
                "Notice: Full screen buttons are hidden for unsupported browsers (IE <11, iOS Safari...)");
        layout.addComponent(notice);

        buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        buttonLayout.setCaption("Full screen actions:");
        layout.addComponent(buttonLayout);

        final FullScreenButton button = new FullScreenButton("All (turn on)");
        button.addFullScreenChangeListener(new FullScreenChangeListener() {

            @Override
            public void onFullScreenChangeListener(AbstractComponent component,
                                                   boolean fullscreen) {
                if (fullscreen) {
                    System.out.println("View is now fullscreen");
                    button.setCaption("All (turn off)");
                } else {
                    System.out.println("View isn't anymore fullscreen");
                    button.setCaption("All (turn on)");
                }
            }

        });
        buttonLayout.addComponent(button);
        buttons.add(button);

        final FullScreenButton uiButton = new FullScreenButton("UI");
        uiButton.addFullScreenChangeListener(new FullScreenChangeListener() {

            @Override
            public void onFullScreenChangeListener(AbstractComponent component,
                                                   boolean fullscreen) {
                if (fullscreen) {
                    System.out.println("UI is now fullscreen");
                    uiButton.setCaption("UI (turn off)");
                } else {
                    System.out.println("UI isn't anymore fullscreen");
                    uiButton.setCaption("UI (turn on)");
                }
            }

        });
        uiButton.setFullScreenTarget(UI.getCurrent());
        buttonLayout.addComponent(uiButton);
        buttons.add(uiButton);

        FullScreenNativeButton button2 = new FullScreenNativeButton("Picture");
        button2.addFullScreenChangeListener(new FullScreenChangeListener() {

            @Override
            public void onFullScreenChangeListener(AbstractComponent component,
                                                   boolean fullscreen) {
                if (fullscreen) {
                    System.out.println("Picture is now fullscreen");
                } else {
                    System.out.println("Picture isn't anymore fullscreen");
                }
            }

        });
        buttonLayout.addComponent(button2);
        buttons.add(button2);

        FullScreenButton button3 = new FullScreenButton("Label");
        button3.addFullScreenChangeListener(new FullScreenChangeListener() {

            @Override
            public void onFullScreenChangeListener(AbstractComponent component,
                                                   boolean fullscreen) {
                if (fullscreen) {
                    System.out.println("Label is now fullscreen");
                } else {
                    System.out.println("Label isn't anymore fullscreen");
                }
            }

        });
        buttonLayout.addComponent(button3);
        buttons.add(button3);

        FullScreenNativeButton button4 = new FullScreenNativeButton("Video");
        button4.addFullScreenChangeListener(new FullScreenChangeListener() {

            @Override
            public void onFullScreenChangeListener(AbstractComponent component,
                                                   boolean fullscreen) {
                if (fullscreen) {
                    System.out.println("Video is now fullscreen");
                } else {
                    System.out.println("Video isn't anymore fullscreen");
                }
            }

        });
        buttonLayout.addComponent(button4);
        buttons.add(button4);

        openWindowButton = new Button("Window (Open)");
        openWindowButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                createExtraWindow();
                windowFullScreenButton
                        .setFullScreenTarget((AbstractComponent) extraWindow
                                .getContent());
                windowFullScreenButton.setVisible(true);
                openWindowButton.setVisible(false);
            }

        });
        buttonLayout.addComponent(openWindowButton);

        windowFullScreenButton = new FullScreenButton("Window (FS)");
        windowFullScreenButton
                .addFullScreenChangeListener(new FullScreenChangeListener() {

                    @Override
                    public void onFullScreenChangeListener(
                            AbstractComponent component, boolean fullscreen) {
                        if (fullscreen) {
                            System.out.println("Window is now fullscreen");
                        } else {
                            System.out
                                    .println("Window isn't anymore fullscreen");
                        }
                    }

                });
        buttonLayout.addComponent(windowFullScreenButton);
        buttons.add(windowFullScreenButton);
        windowFullScreenButton.setVisible(false);

        image = new Image();
        image.addStyleName("demo-image");
        image.setSource(new ExternalResource(
                "http://farm9.staticflickr.com/8106/8476237039_277fd10caf_b.jpg"));
        layout.addComponent(image);
        button2.setFullScreenTarget(image);

        Label label = new Label("Hello World!");
        label.addStyleName("demo-label");
        layout.addComponent(label);
        button3.setFullScreenTarget(label);

        Video video = new Video();
        video.addStyleName("demo-video");
        video.setSource(new ExternalResource("http://misc.siika.fi/linnut.mp4",
                "video/mp4"));
        video.setWidth("320px");
        video.setHeight("180px");
        video.setShowControls(true);
        video.setAltText("You browser doesn't support HTML5 video, sorry!");
        layout.addComponent(video);
        button4.setFullScreenTarget(video);

        Button popupButton = new Button("Open subwindow",
                new Button.ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        Window window = new Window();
                        window.setContent(new Label("This is subwindow"));
                        window.center();
                        UI.getCurrent().addWindow(window);
                    }
                });
        popupButton
                .setDescription("Use this to understand difference between null and UI target");
        layout.addComponent(popupButton);

        CheckBox buttonsEnabled = new CheckBox("Buttons disabled");
        buttonsEnabled.setImmediate(true);
        buttonsEnabled.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                boolean value = (Boolean) event.getProperty().getValue();
                for (Component button : buttons) {
                    button.setEnabled(!value);
                }
            }
        });
        layout.addComponent(buttonsEnabled);
    }

    private void createExtraWindow() {
        if (extraWindow != null) {
            return;
        }

        extraWindow = new Window();
        extraWindow.setWidth("100px");
        extraWindow.setHeight("100px");
        extraWindow.setCaption("Extra window");
        extraWindow.setClosable(true);

        CssLayout layout = new CssLayout();
        extraWindow.setContent(layout);

        Label label = new Label("Hello world inside a window!");
        layout.addComponent(label);

        getCurrent().addWindow(extraWindow);

        extraWindow.addCloseListener(new CloseListener() {

            @Override
            public void windowClose(CloseEvent e) {
                extraWindow = null;
                windowFullScreenButton.setVisible(false);
                openWindowButton.setVisible(true);
            }

        });
    }

}
