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
import de.widone.web.panel.tasklistdropdown.TaskListDropDown;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.StringValidator;

import java.util.List;

public class NewTaskListPanel extends Panel {
    private static final String DESCRIPTION = "description";
    private static final String FEEDBACK = "feedback";
    private static final String NEW_TASK_LIST_FORM = "newTaskListForm";
	private static final String TASK_LIST = "taskList";

    public NewTaskListPanel(String id, IModel<TaskList> choice, IModel<List<TaskList>> choices) {
        super(id);
        add(new FeedbackPanel(FEEDBACK).setOutputMarkupId(true));
	    add(new TaskListDropDown(TASK_LIST, choice, choices));

        add(new Form<TaskList>(NEW_TASK_LIST_FORM, new CompoundPropertyModel<TaskList>(new TaskList()))
                .add(new TextField(DESCRIPTION).add(StringValidator.minimumLength(3)).setRequired(true))
                .add(new AjaxFallbackButton("submit", (Form) get(NEW_TASK_LIST_FORM)) {
	                @Override
	                protected void onError(AjaxRequestTarget target, Form<?> form) {
		                target.add(NewTaskListPanel.this.get(FEEDBACK));
	                }

	                @Override
	                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		                send(getPage(), Broadcast.EXACT, new NewTaskListEvent(target, (TaskList) form.getModelObject()));
	                }
                })
        );

    }
}
