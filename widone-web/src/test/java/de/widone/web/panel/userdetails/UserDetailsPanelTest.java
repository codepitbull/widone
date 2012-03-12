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
import de.widone.web.WiDoneApplication;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserDetailsPanelTest {

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
    public void updateUserSuccess() throws Exception {
        User user = new User();
        user.setId(1l);
        user.setUsername("test");
        UserDetailsPanel userDetailsPanel = new UserDetailsPanel("userDetailsPanel", Model.of(user));

        tester.startComponentInPage(userDetailsPanel);
        FormTester formTester = tester.newFormTester("userDetailsPanel:userDetailsForm");
        formTester.setValue("firstname", "bla");
        formTester.setValue("lastname", "bla");
        formTester.setValue("birthday", "03.12.1976");
        formTester.setValue("email", "test@test.com");
        formTester.submit("submit");
        tester.assertNoErrorMessage();
        tester.assertNoInfoMessage();
    }
}
