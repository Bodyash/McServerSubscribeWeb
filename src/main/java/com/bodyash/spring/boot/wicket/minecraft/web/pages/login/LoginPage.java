package com.bodyash.spring.boot.wicket.minecraft.web.pages.login;


import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.wicketstuff.annotation.mount.MountPath;

import com.bodyash.spring.boot.wicket.minecraft.web.html.panel.FeedbackPanel;
import com.bodyash.spring.boot.wicket.minecraft.web.pages.BasePage;
import com.bodyash.spring.boot.wicket.minecraft.web.pages.home.HomePage;
import com.giffing.wicket.spring.boot.context.scan.WicketSignInPage;

/**
 * Default login page.
 * 
 * @author Marc Giffing
 *
 */
@MountPath("/login")
@WicketSignInPage
public class LoginPage extends BasePage {
	
	FeedbackPanel feedbackPanel;
	RequiredTextField<String> userNameField;
	PasswordTextField passwordTextField;
	Button submitBtn;

	public LoginPage(PageParameters parameters) {
		super(parameters);

		if (((AbstractAuthenticatedWebSession) getSession()).isSignedIn()) {
			continueToOriginalDestination();
		}
		add(getLoginForm("loginForm"));
	}
	
	private Form getLoginForm(String id) {
		Form form = new Form(id);
		
		feedbackPanel = new FeedbackPanel("feedback");
		userNameField = new RequiredTextField<String>("username");
		passwordTextField = new PasswordTextField("password");
		submitBtn = new Button("submitBtn") {
			@Override
			public void onSubmit() {
				
				AuthenticatedWebSession session = AuthenticatedWebSession.get();
				if (session.signIn(userNameField.getModelObject(), passwordTextField.getModelObject())) {
					setResponsePage(HomePage.class);
				} else {
					error("Login failed");
				}
			}
		};
		form.add(feedbackPanel, userNameField, passwordTextField, submitBtn);
		return form;
		
	}
	
	@Override
		public void renderHead(IHeaderResponse response) {
			response.render(CssHeaderItem.forReference(new CssResourceReference(BasePage.class, "../css/authorize.css")));
			super.renderHead(response);
		}
}
