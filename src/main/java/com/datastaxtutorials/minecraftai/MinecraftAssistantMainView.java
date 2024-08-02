package com.datastaxtutorials.minecraftai;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@Route("")
public class MinecraftAssistantMainView extends VerticalLayout {

	private static final long serialVersionUID = 6300314719664762371L;
    private String USER_AVATAR = "https://api.dicebear.com/6.x/big-ears-neutral/svg?seed=Molly";
    private String AI_AVATAR = "https://api.dicebear.com/6.x/bottts/svg?seed=Sheba";

	private MinecraftAssistantController controller;
	private MessageList chat;
	private MessageInput query;
	private List<MessageListItem> messages;
	
//	private Parser parser;
//	private HtmlRenderer renderer;
	
	public MinecraftAssistantMainView() {
		controller = new MinecraftAssistantController();
		messages = new ArrayList<>();
//		parser = Parser.builder().build();
//		renderer = HtmlRenderer.builder().build();

		//getStyle().set("background-color", "black");
		
		add(showImage());
		
		chat = new MessageList();
		query = new MessageInput();

		add(chat, query);
		
		query.addSubmitListener(this::getResponse);
		
		chat.setSizeFull();
		chat.setMaxWidth("800px");
		
		query.setWidthFull();
		query.setMaxWidth("800px");
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
	
	private void getResponse(MessageInput.SubmitEvent submitEvent) {
		
		String req = submitEvent.getValue();
		Instant requestTime = LocalDateTime.now().toInstant(ZoneOffset.UTC);
		messages.add(new MessageListItem(req, requestTime, "User", USER_AVATAR));
		chat.setItems(messages);

		// prep for markdown parsing		
		String resp = controller.askQuestion(req);
		//Node document = parser.parse(resp);
		//renderer.render(document);
		
		Instant responseTime = LocalDateTime.now().toInstant(ZoneOffset.UTC);
		messages.add(new MessageListItem(resp, responseTime, "MC Assistant", AI_AVATAR));
		//messages.add(new MessageListItem(renderer.render(document), responseTime, "MC Assistant", AI_AVATAR));
		chat.setItems(messages);
	}
}
