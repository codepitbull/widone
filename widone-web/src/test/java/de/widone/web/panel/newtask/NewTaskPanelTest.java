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
package de.widone.web.panel.newtask;

import de.widone.entities.Task;
import de.widone.entities.TaskList;
import de.widone.web.WiDoneApplication;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NewTaskPanelTest {

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
    public void createTaskSuccess() throws Exception {
        final List<TaskList> taskLists = createTaskList(3);

        NewTaskPanel newTaskPanel = new NewTaskPanel("newTaskPanel", Model.<TaskList>of(taskLists.get(0)));

        tester.startComponentInPage(newTaskPanel);
        FormTester formTester = tester.newFormTester("newTaskPanel:newTaskForm");
        formTester.setValue("description", "bla");
        formTester.submit("submit");
        tester.assertNoErrorMessage();
        tester.assertNoInfoMessage();
    }

    @Test
    public void createTaskFail() throws Exception {
        final List<TaskList> taskLists = createTaskList(3);

        NewTaskPanel newTaskPanel = new NewTaskPanel("newTaskPanel",  Model.<TaskList>of((TaskList)null));

        tester.startComponentInPage(newTaskPanel);
        FormTester formTester = tester.newFormTester("newTaskPanel:newTaskForm");
        formTester.submit("submit");
    }


    private List<TaskList> createTaskList(int size) {
        List<TaskList> taskLists = new ArrayList<TaskList>();
        for(int count=0; count<size; count++ ){
            TaskList taskList = new TaskList();
            taskList.setDescription("taskList"+count);
            taskList.setTasks(new ArrayList<Task>());
            taskList.setId(count);
            taskLists.add(taskList);
        }
        return taskLists;
    }
}
