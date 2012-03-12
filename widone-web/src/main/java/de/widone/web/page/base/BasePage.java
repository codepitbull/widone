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
package de.widone.web.page.base;

import de.widone.entities.User;
import de.widone.services.UserService;
import de.widone.web.authentication.WiDoneSession;
import de.widone.web.authentication.page.signin.SignInPage;
import de.widone.web.authentication.panel.newuser.CancelEvent;
import de.widone.web.model.CurrentUserModel;
import de.widone.web.panel.userdetails.UserDetailsPanel;
import de.widone.web.panel.userdetails.UserUpdatedEvent;
import de.widone.web.resource.Css;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.springframework.beans.factory.annotation.Configurable;

import javax.inject.Inject;

@SuppressWarnings("serial")
@Configurable
public class BasePage extends WebPage {

    @Inject
    private transient UserService userService;

    public BasePage() {
        super();
        final ModalWindow modalWindow = new ModalWindow("modalWindow");
        modalWindow.setContent(new UserDetailsPanel(modalWindow.getContentId(), new CurrentUserModel()));
        modalWindow.add(new Behavior() {
            @Override
            public void onEvent(Component component, IEvent<?> event) {
                if(event.getPayload() instanceof CancelEvent) {
                    modalWindow.close(((CancelEvent)event.getPayload()).getTarget());
                }
            }
        });
        add(new Label("loginName", new StringResourceModel("loginName", this, new CompoundPropertyModel<User>(((WiDoneSession) WebSession.get()).getUser()))));
        add(modalWindow);
        add(new AjaxFallbackLink<Void>("userDetails") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                target.appendJavaScript("Wicket.Window.unloadConfirmation = false;");
                modalWindow.show(target);
            }
        });
        add(new AjaxFallbackLink<Void>("logout") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                WebSession.get().invalidate();
                setResponsePage(SignInPage.class);
            }
        });
    }

    @Override
    public void onEvent(IEvent<?> event) {
        super.onEvent(event);
        if(event instanceof UserUpdatedEvent) {
            UserUpdatedEvent userUpdatedEvent = (UserUpdatedEvent)event;
            User user = userUpdatedEvent.getUser();
            if(StringUtils.isNotEmpty(userUpdatedEvent.getNewPassword())) {
                user.setPassword(userService.encryptStringWithSalt(userUpdatedEvent.getNewPassword(), user.getSalt()));
            }
            userService.update(user);
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderCSSReference(new SharedResourceReference(Css.class, Css.BOOTSTRAP));
    }

    @Override
    public WiDoneSession getSession() {
        return (WiDoneSession)super.getSession();
    }
}
