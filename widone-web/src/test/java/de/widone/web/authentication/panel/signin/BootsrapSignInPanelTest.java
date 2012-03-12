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
package de.widone.web.authentication.panel.signin;

import de.widone.web.WiDoneApplication;
import de.widone.web.authentication.WiDoneSession;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BootsrapSignInPanelTest {

    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WiDoneApplication() {
            @Override
            protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
                return MockSession.class;
            }
        });
    }

    @After
    public void tearDown() {
        tester.destroy();
    }

    @Test
    public void renderSignInPage() {
        BootstrapSignInPanel panel = new BootstrapSignInPanel("signInPanel") {
	        @Override
	        protected void onSignInSucceeded() {
		        //do nothing on success, otherwise we need to Mock the HomePage
	        }
        };
        tester.startComponentInPage(panel);
        tester.assertContains("signInPanel.signInForm");
        FormTester formTester = tester.newFormTester("signInPanel:signInForm");
        formTester.setValue("username", "bla");
        formTester.setValue("password", "bla");
        formTester.submit();
        tester.assertNoErrorMessage();
        tester.assertNoInfoMessage();
    }

    public static class MockSession extends WiDoneSession {
        public MockSession(Request request) {
            super(request);
        }

        @Override
        public Roles getRoles() {
            return new Roles("USER");
        }

        @Override
        public boolean authenticate(String username, String password) {
            return true;
        }
    }
}
