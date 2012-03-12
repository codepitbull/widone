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
import org.apache.wicket.ajax.AjaxRequestTarget;

public class NewTaskListEvent {
    private AjaxRequestTarget ajaxRequestTarget;
    private TaskList newTaskList;

    public NewTaskListEvent(AjaxRequestTarget ajaxRequestTarget, TaskList newTaskList) {
        this.ajaxRequestTarget = ajaxRequestTarget;
        this.newTaskList = newTaskList;
    }

    public AjaxRequestTarget getAjaxRequestTarget() {
        return ajaxRequestTarget;
    }

    public TaskList getNewTaskList() {
        return newTaskList;
    }
}
