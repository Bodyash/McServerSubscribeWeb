package com.bodyash.spring.boot.wicket.minecraft.web.pages;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;

import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;

public abstract class BasePage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MarkupContainer defaultModal;
	private Label title;
	
	public Label getTitle() {
		return title;
	}


	public BasePage(PageParameters params){
		super(params);
		initPage();
	}
	
	public BasePage(){
		initPage();
	}
	
	private void initPage(){
		defaultModal = new EmptyPanel("defaultModal");
		defaultModal.setOutputMarkupId(true);
		add(defaultModal);
		title = new Label("title", "Minecraft Subscribe Service");
		add(title);
	}
	
	public void replaceDefaultModal(ModalWindow newModal){
		defaultModal.replaceWith(newModal);
		defaultModal = newModal;
		defaultModal.setOutputMarkupId(true);
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		
		response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getJQueryReference()));
		response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getWicketEventReference()));
		response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getWicketAjaxReference()));
		
		String bootstrapPrefixPath = "bootstrap/current";
		response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference(bootstrapPrefixPath + "/js/bootstrap.js")));
		response.render(CssHeaderItem.forReference(new WebjarsJavaScriptResourceReference(bootstrapPrefixPath + "/css/bootstrap.css")));
	
		response.render(CssHeaderItem.forReference(new CssResourceReference(BasePage.class, "../css/style.css")));
	}

}
