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
import org.apache.wicket.ajax.AjaxRequestTarget;

public class NewTaskEvent {
    private AjaxRequestTarget ajaxRequestTarget;
    private Task newTask;

    public NewTaskEvent(AjaxRequestTarget ajaxRequestTarget, Task newTask) {
        this.ajaxRequestTarget = ajaxRequestTarget;
        this.newTask = newTask;
    }

    public AjaxRequestTarget getAjaxRequestTarget() {
        return ajaxRequestTarget;
    }

    public Task getNewTask() {
        return newTask;
    }
}