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

import de.widone.web.WiDoneApplication;
import de.widone.web.page.home.HomePage;
import de.widone.web.testhelper.MockRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.RoleAuthorizationStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SignInPageTest {

    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WiDoneApplication());
    }

    @After
    public void tearDown() {
        tester.destroy();
    }

    @Test
    public void renderSignInPage() {
        tester.getApplication().getSecuritySettings().setAuthorizationStrategy(new RoleAuthorizationStrategy(new MockRoleCheckingStrategy(new Roles())));
        tester.startPage(HomePage.class);
        tester.assertRenderedPage(SignInPage.class);
        tester.assertContains("signInPanel");
    }

}
