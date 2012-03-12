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
package de.widone.web.page.home;

import de.widone.entities.Task;
import de.widone.entities.TaskList;
import de.widone.entities.User;
import de.widone.services.TaskListService;
import de.widone.services.UserService;
import de.widone.web.model.CurrentUserModel;
import de.widone.web.model.ListOfTaskListsModel;
import de.widone.web.model.SelectedTaskListModel;
import de.widone.web.model.TaskListWIthTasksInitializedModel;
import de.widone.web.page.base.BasePage;
import de.widone.web.panel.newtask.NewTaskEvent;
import de.widone.web.panel.newtasklist.NewTaskListEvent;
import de.widone.web.panel.newtasklist.NewTaskListPanel;
import de.widone.web.panel.tasklist.ChangeTaskPositionEvent;
import de.widone.web.panel.tasklist.DeleteTaskEvent;
import de.widone.web.panel.tasklist.DoneTaskEvent;
import de.widone.web.panel.tasklist.TaskListPanel;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.model.IModel;
import org.springframework.beans.factory.annotation.Configurable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Configurable
@AuthorizeInstantiation("USER")
public class HomePage extends BasePage {

	@Inject
	private transient TaskListService taskListService;

	@Inject
	private transient UserService userService;

	public HomePage() {
		super();
		IModel<User> userModel = new CurrentUserModel();
		IModel<List<TaskList>> listOfTaskListsModel = new ListOfTaskListsModel(userModel);
		IModel<TaskList> selectedTaskListModel = new SelectedTaskListModel(listOfTaskListsModel);
		IModel<TaskList> taskListIModel = new TaskListWIthTasksInitializedModel(selectedTaskListModel);
		add(new NewTaskListPanel("newTaskListPanel", selectedTaskListModel, listOfTaskListsModel));
		add(new TaskListPanel("taskListPanel", taskListIModel));
		setDefaultModel(selectedTaskListModel);
	}

	@Override
	public void onEvent(IEvent<?> event) {
		super.onEvent(event);
		if (event.getPayload() instanceof ChangeTaskPositionEvent) {
			ChangeTaskPositionEvent changePosEvent = (ChangeTaskPositionEvent) event.getPayload();
			Task task = changePosEvent.getTask();
			List<Task> tasks = changePosEvent.getTaskList().getTasks();
			Integer newPos = changePosEvent.getNewPosition();
			Integer oldPos = tasks.indexOf(task);
			if (newPos < 0 || newPos >= tasks.size()) {
				return;
			}
			tasks.remove(task);
			List<Task> sublist = new ArrayList<Task>(tasks.subList(newPos, tasks.size()));
			tasks.removeAll(sublist);
			tasks.add(task);
			tasks.addAll(sublist);
			((IModel<TaskList>) getDefaultModel()).setObject(taskListService.update(changePosEvent.getTaskList()));
		} else if (event.getPayload() instanceof DoneTaskEvent) {
			DoneTaskEvent deleteTaskEvent = (DoneTaskEvent) event.getPayload();
			TaskList taskList = deleteTaskEvent.getTaskList();
			taskList.getTasks().remove(deleteTaskEvent.getTask());
			((IModel<TaskList>) getDefaultModel()).setObject(taskListService.update(taskList));
		} else if (event.getPayload() instanceof DeleteTaskEvent) {
			DeleteTaskEvent deleteTaskEvent = (DeleteTaskEvent) event.getPayload();
			TaskList taskList = deleteTaskEvent.getTaskList();
			taskList.getTasks().remove(deleteTaskEvent.getTask());
			((IModel<TaskList>) getDefaultModel()).setObject(taskListService.update(taskList));
		} else if (event.getPayload() instanceof NewTaskEvent) {
			NewTaskEvent newTaskEvent = (NewTaskEvent) event.getPayload();
			((IModel<TaskList>) getDefaultModel()).setObject(taskListService.addTaskToTaskList(newTaskEvent.getNewTask(), ((IModel<TaskList>) getDefaultModel()).getObject()));
		} else if (event.getPayload() instanceof NewTaskListEvent) {
			NewTaskListEvent newTaskListEvent = (NewTaskListEvent) event.getPayload();
			User user = userService.initTaskLists(new CurrentUserModel().getObject());
			user.getTaskLists().add(newTaskListEvent.getNewTaskList());
			userService.update(user);
		}
	}
}
