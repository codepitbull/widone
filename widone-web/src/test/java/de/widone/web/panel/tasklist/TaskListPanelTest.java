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
package de.widone.web.panel.tasklist;

import de.widone.entities.Task;
import de.widone.entities.TaskList;
import de.widone.web.WiDoneApplication;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class TaskListPanelTest {
    private WicketTester tester;

    @Before
    public void setUp() {
        tester = new WicketTester(new WiDoneApplication());
    }

    @After
    public void tearDown() {
        tester.destroy();
    }

    public void testRender() {
        TaskList taskList = createTaskList();
        TaskListPanel taskListPanel = new TaskListPanel("taskListPanel", Model.<TaskList>of(taskList));
        tester.startComponentInPage(taskListPanel);
    }

    private TaskList createTaskList() {
        TaskList taskList = new TaskList();
        taskList.setDescription("descr");
        taskList.setInbox(Boolean.TRUE);
        taskList.setId(0);
        List<Task> tasks = new ArrayList<Task>();
        Task task = new Task();
        task.setDescription("aaaa");
        task.setId(1);
        tasks.add(task);
        task = new Task();
        task.setDescription("bbbb");
        task.setId(2);
        tasks.add(task);
        taskList.setTasks(tasks);
        return taskList;
    }
}
