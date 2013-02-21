package org.vaadin.alump.gofullscreen.demo;

import org.vaadin.alump.gofullscreen.FullScreenButton;

import com.vaadin.annotations.Theme;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("demo")
public class GoFullScreenDemoUI extends UI {

	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setWidth("100%");
		setContent(layout);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setCaption("Full screen actions:");
		layout.addComponent(buttonLayout);
		
		FullScreenButton button = new FullScreenButton("All");
		buttonLayout.addComponent(button);
		
		FullScreenButton button2 = new FullScreenButton("Picture");
		buttonLayout.addComponent(button2);
		
		Image image = new Image();
		image.addStyleName("demo-image");
		image.setSource(new ExternalResource("http://farm9.staticflickr.com/8106/8476237039_277fd10caf_z.jpg"));
		layout.addComponent(image);
		button2.setFullScreenTarget(image);
		
	}

}
