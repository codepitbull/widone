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
package de.widone.web.authentication.page.signin;

import de.widone.services.UserService;
import de.widone.web.authentication.panel.newuser.CancelEvent;
import de.widone.web.authentication.panel.newuser.NewUserPanel;
import de.widone.web.authentication.panel.newuser.UserCreatedEvent;
import de.widone.web.authentication.panel.signin.BootstrapSignInPanel;
import de.widone.web.authentication.panel.signin.NewUserEvent;
import de.widone.web.resource.Css;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.springframework.beans.factory.annotation.Configurable;

import javax.inject.Inject;

@Configurable
public class SignInPage extends WebPage {

    @Inject
    private transient UserService userService;

    private static final String SIGNINPANEL = "signInPanel";
    public SignInPage() {
        add(new BootstrapSignInPanel(SIGNINPANEL).setOutputMarkupId(true));
    }

    @Override
    public void onEvent(IEvent<?> event) {
        if(event.getPayload() instanceof NewUserEvent) {
            get(SIGNINPANEL).replaceWith(new NewUserPanel(SIGNINPANEL));
            ((NewUserEvent)event.getPayload()).getTarget().add(get(SIGNINPANEL));
        }
        else if(event.getPayload() instanceof CancelEvent) {
            get(SIGNINPANEL).replaceWith(new BootstrapSignInPanel(SIGNINPANEL));
            ((CancelEvent)event.getPayload()).getTarget().add(get(SIGNINPANEL));
        }
        else if(event.getPayload() instanceof UserCreatedEvent) {
            userService.create(((UserCreatedEvent)event.getPayload()).getUser());
            get(SIGNINPANEL).replaceWith(new BootstrapSignInPanel(SIGNINPANEL));
            ((UserCreatedEvent)event.getPayload()).getTarget().add(get(SIGNINPANEL));
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.renderCSSReference(new SharedResourceReference(Css.class, Css.BOOTSTRAP));
        response.renderCSS("body {padding-top: 60px;}", null);
    }
}
