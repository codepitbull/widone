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
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class NewTaskPanel extends Panel {
    private static final String DESCRIPTION = "description";
    private static final String FEEDBACK = "feedback";
    private static final String NEW_TASK_FORM = "newTaskForm";

    public NewTaskPanel(String id, final IModel<TaskList> selectedTaskList) {
        super(id, selectedTaskList);
        add(new FeedbackPanel(FEEDBACK).setOutputMarkupId(true));

        add(new Form<Task>(NEW_TASK_FORM, new CompoundPropertyModel<Task>(new Task()))
                .add(new TextField(DESCRIPTION).add(StringValidator.minimumLength(3)).setRequired(true))
                .add(new AjaxFallbackButton("submit", (Form) get(NEW_TASK_FORM)) {
                    @Override
                    protected void onError(AjaxRequestTarget target, Form<?> form) {
                        target.add(NewTaskPanel.this.get(FEEDBACK));
                    }

                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        send(getPage(), Broadcast.EXACT, new NewTaskEvent(target, (Task)form.getModelObject()));
                    }
                })
        );
    }

}
