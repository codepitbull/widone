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
package de.widone.web.panel.newtasklist;

import de.widone.entities.TaskList;
import de.widone.entities.User;
import de.widone.web.WiDoneApplication;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NewTaskListPanelTest {

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
    public void createTaskListSuccess() throws Exception {
        IModel<User> user = Model.of(new User());
	    IModel<List<TaskList>> choices = new Model(new ArrayList<TaskList>());
	    IModel<TaskList> choice = Model.of(new TaskList());
        NewTaskListPanel newTaskListPanel = new NewTaskListPanel("newTaskListPanel", choice, choices);

        tester.startComponentInPage(newTaskListPanel);
        FormTester formTester = tester.newFormTester("newTaskListPanel:newTaskListForm");
        formTester.setValue("description", "bla");
        formTester.submit("submit");
        tester.assertNoErrorMessage();
        tester.assertNoInfoMessage();
    }

}
