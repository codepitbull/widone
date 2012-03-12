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
package de.widone.web.panel.userdetails;

import de.widone.entities.User;
import de.widone.web.authentication.panel.newuser.CancelEvent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Date;

@Configurable
public class UserDetailsPanel extends Panel {

    private boolean passwordChanged = false;
    private String passwordUpdate = "----------";

    public UserDetailsPanel(String id, IModel<User> model) {
        super(id, model);
        add(new FeedbackPanel("feedback").setOutputMarkupId(true));

        TextField<String> firstnameTextField = new TextField("firstname");
        TextField<String> lastnameTextField = new TextField("lastname");
        TextField<String> emailTextField = new EmailTextField("email");
        TextField<Date> birthdayTextField = (TextField<Date>)new DateTextField("birthday")
                .add(new DatePicker());
        PasswordTextField passwordTextField = (PasswordTextField) new PasswordTextField("passwordUpdate", Model.of("aaaaaa"))
                .setResetPassword(false)
                .setRequired(false);
        PasswordTextField passwordAgainTextField = (PasswordTextField) new PasswordTextField("passwordAgain", Model.of(""))
                .setRequired(false);


        add(new Form<User>("userDetailsForm", new CompoundPropertyModel<User>(model))
                .add(firstnameTextField)
                .add(lastnameTextField)
                .add(emailTextField)
                .add(birthdayTextField)
                .add(passwordTextField)
                .add(passwordAgainTextField)
                .add(new FormComponentLabel("firstnameLabel", firstnameTextField))
                .add(new FormComponentLabel("lastnameLabel", lastnameTextField))
                .add(new FormComponentLabel("emailLabel", emailTextField))
                .add(new FormComponentLabel("birthdayLabel", birthdayTextField))
                .add(new FormComponentLabel("passwordLabel", passwordTextField))
                .add(new FormComponentLabel("passwordAgainLabel", passwordAgainTextField))
                .add(new AjaxFallbackButton("submit", (Form) get("userDetailsForm")) {
                    @Override
                    protected void onError(AjaxRequestTarget target, Form<?> form) {
                        target.add(UserDetailsPanel.this.get("feedback"));
                    }

                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        send(getPage(), Broadcast.EXACT, new UserUpdatedEvent(target, (User) form.getModelObject(), passwordChanged ? passwordUpdate : null));
                    }
                })
                .add(new AjaxFallbackLink<Void>("cancel") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        send(getPage(), Broadcast.BREADTH, new CancelEvent(target));
                    }
                })
        );
    }

    public String getPasswordUpdate() {
        return passwordUpdate;
    }

    public void setPasswordUpdate(String passwordUpdate) {
        passwordChanged = true;
        this.passwordUpdate = passwordUpdate;
    }
}
