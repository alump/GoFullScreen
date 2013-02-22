package org.vaadin.alump.gofullscreen.demo;

import org.vaadin.alump.gofullscreen.FullScreenButton;
import org.vaadin.alump.gofullscreen.FullScreenButton.FullScreenChangeListener;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("demo")
@Title("GoFullScreen Demo")
public class GoFullScreenDemoUI extends UI {
	
	protected Label notice;

	@Override
	protected void init(VaadinRequest request) {
		
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setWidth("100%");
		setContent(layout);
		
		notice = new Label("Notice: Full screen buttons are hidden for nonsupported browsers (IE, Safari...)");
		layout.addComponent(notice);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setCaption("Full screen actions:");
		layout.addComponent(buttonLayout);
		
		final FullScreenButton button = new FullScreenButton("All (on)");
		button.addFullScreenChangeListener(new FullScreenChangeListener() {

			@Override
			public void onFullScreenChangeListener(AbstractComponent component,
					boolean fullscreen) {
				if (fullscreen) {
					System.out.println("All is now fullscreen");
					button.setCaption("All (off)");
				} else {
					System.out.println("All isn't anymore fullscreen");
					button.setCaption("All (on)");
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
		
		Image image = new Image();
		image.addStyleName("demo-image");
		image.setSource(new ExternalResource("http://farm9.staticflickr.com/8106/8476237039_277fd10caf_b.jpg"));
		layout.addComponent(image);
		button2.setFullScreenTarget(image);
		
		Label label = new Label("Hello World!");
		label.addStyleName("demo-label");
		layout.addComponent(label);
		button3.setFullScreenTarget(label);
		
	}

}
