/**
 * Copyright (C) 2011-2012 by Jochen Mader (pflanzenmoerder@gmail.com)
 * ==============================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.widone.web.authentication.panel.newuser;

import de.widone.entities.User;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponentLabel;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;

public class NewUserPanel extends Panel {
    private static final String feedback = "feedback";

    public NewUserPanel(String id) {
        super(id);
        add(new FeedbackPanel(feedback).setOutputMarkupId(true));

        TextField<String> usernameTextField = (TextField<String>)new TextField("username").add(new StringValidator.LengthBetweenValidator(3,12));
        PasswordTextField passwordTextField = new PasswordTextField("password");
        PasswordTextField passwordAgainTextField = new PasswordTextField("passwordAgain", Model.of(""));

        add(new Form<User>("newUserForm", new CompoundPropertyModel<User>(new User()))
                .add(usernameTextField)
                .add(passwordTextField)
                .add(passwordAgainTextField)
                .add(new FormComponentLabel("usernameLabel", usernameTextField))
                .add(new FormComponentLabel("passwordLabel", passwordTextField))
                .add(new FormComponentLabel("passwordAgainLabel", passwordAgainTextField))
                .add(new AjaxFallbackLink<Void>("cancel") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        send(getPage(), Broadcast.EXACT, new CancelEvent(target));
                    }
                })
                .add(new AjaxFallbackButton("submit", (Form)get("newUserForm")) {
                    @Override
                    protected void onError(AjaxRequestTarget target, Form<?> form) {
                        target.add(NewUserPanel.this.get(feedback));
                    }

                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        send(getPage(), Broadcast.EXACT, new UserCreatedEvent(target, (User) form.getModelObject()));
                    }
                })
                .add(new EqualPasswordInputValidator(passwordTextField, passwordAgainTextField))
        );
    }
}
