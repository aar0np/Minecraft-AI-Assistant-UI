package com.datastaxtutorials.minecraftai;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route("")
public class MinecraftAssistantMainView extends VerticalLayout {

	private static final long serialVersionUID = 6300314719664762371L;

	private MinecraftAssistantController controller;
	private Paragraph response;
	private TextField queryField;
	private Button queryButton;
	
	public MinecraftAssistantMainView() {
		controller = new MinecraftAssistantController();
		
		getStyle().set("background-color", "black");
		
		add(showImage());
		add(showResponse());
		add(showQueryBar());
	}
	
	public Component showImage() {
		HorizontalLayout layout = new HorizontalLayout();
		
		Image mcImage = new Image();
		StreamResource imageResource = new StreamResource("minecraft_ai_assistant.png",
				() -> getClass().getResourceAsStream("/images/minecraft_ai_assistant.png"));
		
		mcImage.setSrc(imageResource);
		mcImage.setWidth("500px");
		
		layout.add(mcImage);
		
		return layout;
	}
	
	public Component showResponse() {
		HorizontalLayout layout = new HorizontalLayout();
		
		response = new Paragraph();
		response.setWidth("500px");
		response.setHeight("100px");
		response.getStyle().set("background-color", "aliceblue");
		response.getStyle().set("white-space", "pre-line");
		// enable scroll bars?
		
		layout.add(response);
		return layout;
	}

	public Component showQueryBar() {
		HorizontalLayout layout = new HorizontalLayout();
		
		queryField = new TextField();
		queryField.setWidth("400px");
		queryField.getStyle().set("background-color", "gainsboro");
		queryButton = new Button("Query");
		queryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		queryButton.addClickListener(click -> {
			getResponse();
		});
		
		layout.add(queryField);
		layout.add(queryButton);
		
		return layout;
	}
	
	private void getResponse() {
		
		String message = controller.askQuestion(queryField.getValue());
		response.setText(message);
		
		queryField.setValue("");
	}
}
