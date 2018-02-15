package com.bodyash.spring.boot.wicket.minecraft.web.pages.home;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import com.bodyash.spring.boot.wicket.minecraft.web.pages.BasePage;
import com.bodyash.spring.boot.wicket.minecraft.web.pages.login.LoginPage;
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
	private Link<String> loginLink;
	private Link<String> cabinetLink;
	private Link<String> logoutLink;

	@SpringBean(name = "serverConfig")
	private Properties serverConfigProp;

	public HomePage() {
		createPage();
	}

	private void createPage() {
		getTitle().setDefaultModel(Model.of("Home Page"));
		add(new Label("serverLabel", new ResourceModel("serverLabel")));
		add(new Label("serverName",
				Model.of(" " + serverConfigProp.getProperty("serverName", "serverName key not Set"))));
		createLocalesDCC();
		createHeaderLinks();

	}

	private void createHeaderLinks() {
		loginLink = new Link<String>("loginLink", new ResourceModel("loginLink")) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		};

		logoutLink = new Link<String>("logoutLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		};

		cabinetLink = new Link<String>("cabinetLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		};
		loginLink.add(new Label("label", new ResourceModel("loginLink")));
		logoutLink.add(new Label("label", new ResourceModel("logoutLink"))).setVisible(false);
		cabinetLink.add(new Label("label", new ResourceModel("cabinetLink"))).setVisible(false);

		add(loginLink, logoutLink, cabinetLink);

	}

	private void createLocalesDCC() {
		List<Locale> locales = Arrays.asList(Locale.ENGLISH, new Locale("ru"));

		final DropDownChoice<Locale> localeDDCSelection = new DropDownChoice<Locale>("changeLocale",
				new Model<Locale>(), locales, getLocalesChoiseRenderer());
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

	private IChoiceRenderer<Locale> getLocalesChoiseRenderer() {
		return new IChoiceRenderer<Locale>() {

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
				for (int index = 0; index < _choices.size(); index++) {
					// Get next choice
					final Locale choice = _choices.get(index);
					if (getIdValue(choice, index).equals(id)) {
						return choice;
					}
				}
				return null;
			}
		};

	}
}
