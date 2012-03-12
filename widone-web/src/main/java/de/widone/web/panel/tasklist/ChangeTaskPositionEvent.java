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

public class ChangeTaskPositionEvent {
    private TaskList taskList;
    private Task task;
    private Integer newPosition;

    public ChangeTaskPositionEvent(TaskList taskList, Task task, Integer newPosition) {
        this.taskList = taskList;
        this.task = task;
        this.newPosition = newPosition;
    }

    public Task getTask() {
        return task;
    }

    public Integer getNewPosition() {
        return newPosition;
    }

    public TaskList getTaskList() {
        return taskList;
    }
}
