package org.vaadin.alump.gofullscreen.demo;

import org.vaadin.alump.gofullscreen.FullScreenButton;
import org.vaadin.alump.gofullscreen.FullScreenButton.FullScreenChangeListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Video;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

@SuppressWarnings("serial")
@Theme("demo")
@Title("GoFullScreen Demo")
public class GoFullScreenDemoUI extends UI {

    protected Label notice;
    protected Button openWindowButton;
    protected HorizontalLayout buttonLayout;
    protected FullScreenButton windowFullScreenButton;
    protected Window extraWindow;

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setWidth("100%");
        setContent(layout);

        notice = new Label(
                "Notice: Full screen buttons are hidden for nonsupported browsers (IE, Safari...)");
        layout.addComponent(notice);

        buttonLayout = new HorizontalLayout();
        buttonLayout.setCaption("Full screen actions:");
        layout.addComponent(buttonLayout);

        final FullScreenButton button = new FullScreenButton("All (on)");
        button.addFullScreenChangeListener(new FullScreenChangeListener() {

            @Override
            public void onFullScreenChangeListener(AbstractComponent component,
                    boolean fullscreen) {
                if (fullscreen) {
                    System.out.println("View is now fullscreen");
                    button.setCaption("View (off)");
                } else {
                    System.out.println("View isn't anymore fullscreen");
                    button.setCaption("View (on)");
                }
            }

        });
        buttonLayout.addComponent(button);

        FullScreenButton button2 = new FullScreenButton("Picture");
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

        FullScreenButton button4 = new FullScreenButton("Video");
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
        
        openWindowButton = new Button("Window (Open)");
        openWindowButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				createExtraWindow();
				windowFullScreenButton.setFullScreenTarget(
						(AbstractComponent)extraWindow.getContent());
				windowFullScreenButton.setVisible(true);
				openWindowButton.setVisible(false);
			}
        	
        });
        buttonLayout.addComponent(openWindowButton);
        
        windowFullScreenButton = new FullScreenButton("Window (FS)");
        windowFullScreenButton.addFullScreenChangeListener(new FullScreenChangeListener() {

            @Override
            public void onFullScreenChangeListener(AbstractComponent component,
                    boolean fullscreen) {
                if (fullscreen) {
                    System.out.println("Window is now fullscreen");
                } else {
                    System.out.println("Window isn't anymore fullscreen");
                }
            }

        });
        buttonLayout.addComponent(windowFullScreenButton);
        windowFullScreenButton.setVisible(false);

        Image image = new Image();
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
