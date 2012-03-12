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

import de.widone.web.WiDoneApplication;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NewUserPanelTest {

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
    public void createUserSuccess() throws Exception {
        NewUserPanel newUserPanel = new NewUserPanel("newUserPanel");

        tester.startComponentInPage(newUserPanel);
        FormTester formTester = tester.newFormTester("newUserPanel:newUserForm");
        formTester.setValue("username", "bla");
        formTester.setValue("password", "bla");
        formTester.setValue("passwordAgain", "bla");
        formTester.submit("submit");
        tester.assertNoErrorMessage();
        tester.assertNoInfoMessage();
    }

    @Test
    public void createUserFail() throws Exception {
        NewUserPanel newUserPanel = new NewUserPanel("newUserPanel");

        tester.startComponentInPage(newUserPanel);
        FormTester formTester = tester.newFormTester("newUserPanel:newUserForm");
        formTester.setValue("username", "bla");
        formTester.setValue("password", "bla");
        formTester.setValue("passwordAgain", "blaub");
        formTester.submit("submit");
    }
}
