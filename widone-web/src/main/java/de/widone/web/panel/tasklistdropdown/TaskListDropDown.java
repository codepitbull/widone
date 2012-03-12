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
package de.widone.web.panel.tasklistdropdown;

import de.widone.entities.TaskList;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

public class TaskListDropDown extends DropDownChoice<TaskList>{

    public TaskListDropDown(String id, IModel<TaskList> choice, IModel<List<TaskList>> choices) {
        super(id, choice, choices, new TaskListRenderer());
	    setOutputMarkupId(true);
    }

    public TaskListDropDown(String id, IModel<List<TaskList>> choices) {
        super(id, choices, new TaskListRenderer());
	    setOutputMarkupId(true);
    }

    @Override
    protected boolean wantOnSelectionChangedNotifications() {
        return true;
    }

    private static class TaskListRenderer implements IChoiceRenderer<TaskList> {
        @Override
        public Object getDisplayValue(TaskList object) {
            return object.getDescription();
        }

        @Override
        public String getIdValue(TaskList object, int index) {
            return object.getId().toString();
        }
    }


    @Override
    public void onEvent(IEvent<?> event) {
        super.onEvent(event);
        if(event.getPayload() instanceof AjaxRequestTarget) {
            ((AjaxRequestTarget)event.getPayload()).add(this);
        }
    }
}
