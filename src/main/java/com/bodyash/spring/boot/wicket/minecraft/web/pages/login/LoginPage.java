package com.bodyash.spring.boot.wicket.minecraft.web.pages.login;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
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

	public LoginPage(PageParameters parameters) {
		super(parameters);

		if (((AbstractAuthenticatedWebSession) getSession()).isSignedIn()) {
			continueToOriginalDestination();
		}
		add(new LoginForm("loginForm"));
	}

	private class LoginForm extends StatelessForm<LoginForm> {

		private String username;
		
		private String password;

		public LoginForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel<>(this));
			add(new FeedbackPanel("feedback"));
			add(new RequiredTextField<String>("username"));
			add(new PasswordTextField("password"));
		}

		@Override
		protected void onSubmit() {
			AuthenticatedWebSession session = AuthenticatedWebSession.get();
			if (session.signIn(username, password)) {
				setResponsePage(HomePage.class);
			} else {
				error("Login failed");
			}
		}
	}
}
