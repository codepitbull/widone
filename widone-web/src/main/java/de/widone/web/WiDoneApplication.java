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
package de.widone.web;

import de.widone.web.authentication.WiDoneSession;
import de.widone.web.authentication.page.signin.SignInPage;
import de.widone.web.page.home.HomePage;
import de.widone.web.resource.Css;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.SharedResourceReference;

import javax.inject.Named;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 * @see de.widone.web.Start#main(String[])
 */
@Named("wicketApplication")
public class WiDoneApplication extends AuthenticatedWebApplication {

    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return WiDoneSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return SignInPage.class;
    }

    @Override
    public void init() {
        super.init();
        mountResource("/resource/bootstrap.resource", new SharedResourceReference(Css.class, Css.BOOTSTRAP));
    }
}
