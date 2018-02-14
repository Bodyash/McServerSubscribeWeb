package com.bodyash.spring.boot.wicket.minecraft.web.pages.home;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.FileSystemResourceReference;
import org.apache.wicket.resource.Properties;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.wicketstuff.annotation.mount.MountPath;

import com.bodyash.spring.boot.wicket.minecraft.web.pages.BasePage;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

@MountPath("/home")
@WicketHomePage
public class HomePage extends BasePage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected List<String> localeNames;
	protected DropDownChoice<String> localeDDC;
	protected Image logoImage = null;

	public HomePage(){
		createPage();
	}

	private void createPage() {
		add(new Label("serverLabel", new ResourceModel("serverName")));
		createLocalesDCC();
	}
	
	private void createLocalesDCC() {
		List<Locale> locales = Arrays.asList(Locale.ENGLISH, new Locale("ru"));

		final DropDownChoice<Locale> localeDDCSelection = 
		             new DropDownChoice<Locale>("changeLocale", new Model<Locale>(), locales, getLocalesChoiseRenderer());
		localeDDCSelection.add(new OnChangeAjaxBehavior() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				Session.get().setLocale(localeDDCSelection.getModelObject());
				target.add(HomePage.this);
			}
		});
		localeDDCSelection.setModelObject(Session.get().getLocale());
		add(localeDDCSelection);
	}
	
	private IChoiceRenderer<Locale> getLocalesChoiseRenderer(){
		return  new IChoiceRenderer<Locale>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(Locale object) {
				String displayValue = object.getDisplayLanguage(object);
				return displayValue.substring(0, 1).toUpperCase() + displayValue.substring(1);
			}

			@Override
			public String getIdValue(Locale object, int index) {
				return object.getDisplayLanguage();
			}

			@Override
			public Locale getObject(String id, IModel<? extends List<? extends Locale>> choices) {
				List<? extends Locale> _choices = choices.getObject();
				for (int index = 0; index < _choices.size(); index++)
				{
					// Get next choice
					final Locale choice = _choices.get(index);
					if (getIdValue(choice, index).equals(id))
					{
						return choice;
					}
				}
				return null;
			}
		};
		
	}
}
