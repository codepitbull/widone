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
import de.widone.web.panel.newtask.NewTaskPanel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy;
import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.util.Iterator;
import java.util.List;

public class TaskListPanel extends Panel {
	private static final String TASK_LIST_CONTAINER = "taskListContainer";
	private static final String TASK_LIST = "taskList";
	private boolean useBest = true;

	public TaskListPanel(String id, final IModel<TaskList> taskList) {
		super(id);
		add(new NewTaskPanel("newTask", taskList));
		add(new WebMarkupContainer(TASK_LIST_CONTAINER)
				.add(createListView(taskList))
				.setOutputMarkupId(true));
		add(new AjaxFallbackLink<Void>("switchContainer") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				Component taskListComp = TaskListPanel.this.get(TASK_LIST_CONTAINER+":"+TASK_LIST);
				if(taskListComp instanceof ListView) {
					((ListView)taskListComp).replaceWith(createRefreshingView(taskList));
				}
				else {
					((RefreshingView)taskListComp).replaceWith(createListView(taskList));
				}
				target.add(TaskListPanel.this.get(TASK_LIST_CONTAINER));
			}
		});

	}

	private Component createRefreshingView(final IModel<TaskList> taskList) {
		return new RefreshingView<Task>(TASK_LIST, new PropertyModel<List<Task>>(taskList, "tasks")) {
					@Override
					protected Iterator<IModel<Task>> getItemModels() {
						return new ModelIteratorAdapter<Task>(((IModel<List<Task>>)getDefaultModel()).getObject().iterator()) {
							@Override
							protected IModel<Task> model(Task object) {
								return Model.of(object);
							}
						};
					}

					@Override
					protected void populateItem(final Item<Task> item) {
						TaskListPanel.this.populateItem(item, taskList);
					}
				}.setItemReuseStrategy(new ReuseIfModelsEqualStrategy());
	}

	private Component createListView(final IModel<TaskList> taskList) {
		return new ListView<Task>(TASK_LIST, new PropertyModel<List<Task>>(taskList, "tasks")) {
					public void populateItem(final ListItem<Task> item) {
						TaskListPanel.this.populateItem(item, taskList);
					}
				};
	}


	public void populateItem(final ListItem<Task> item, final IModel<TaskList> taskList) {
		final Task task = item.getModelObject();
		item.add(new Label("description", task.getDescription()));

		item.add(new AjaxFallbackLink("moveDown") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						Task task = item.getModelObject();
						send(TaskListPanel.this.getPage(), Broadcast.EXACT, new ChangeTaskPositionEvent(taskList.getObject(), task, taskList.getObject().getTasks().indexOf(task) - 1));
						target.add(TaskListPanel.this.get(TASK_LIST_CONTAINER));
					}
				}
				.add(new Image("downImg", new PackageResourceReference(TaskListPanel.class, "up.png"))));

		item.add(new AjaxFallbackLink("moveUp") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						Task task = item.getModelObject();
						send(TaskListPanel.this.getPage(), Broadcast.EXACT, new ChangeTaskPositionEvent(taskList.getObject(), task, taskList.getObject().getTasks().indexOf(task) + 1));
						target.add(TaskListPanel.this.get(TASK_LIST_CONTAINER));
					}
				}
				.add(new Image("upImg", new PackageResourceReference(TaskListPanel.class, "down.png"))));

		item.add(new AjaxFallbackLink("done") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						Task task = item.getModelObject();
						send(TaskListPanel.this.getPage(), Broadcast.EXACT, new DoneTaskEvent(taskList.getObject(), task));
						target.add(TaskListPanel.this.get(TASK_LIST_CONTAINER));
					}
				}
				.add(new Image("doneImg", new PackageResourceReference(TaskListPanel.class, "ok.png"))));

		item.add(new AjaxFallbackLink("delete") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						Task task = item.getModelObject();
						send(TaskListPanel.this.getPage(), Broadcast.EXACT, new DeleteTaskEvent(taskList.getObject(), task));
						target.add(TaskListPanel.this.get(TASK_LIST_CONTAINER));
					}
				}
				.add(new Image("deleteImg", new PackageResourceReference(TaskListPanel.class, "delete.png"))));
	}

	@Override
	public void onEvent(IEvent<?> event) {
		super.onEvent(event);
		if (event.getPayload() instanceof AjaxRequestTarget) {
			((AjaxRequestTarget)event.getPayload()).add(get(TASK_LIST_CONTAINER));
		}
	}
}
