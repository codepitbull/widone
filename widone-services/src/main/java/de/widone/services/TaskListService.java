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
package de.widone.services;

import de.widone.entities.Task;
import de.widone.entities.TaskList;
import de.widone.services.daos.TaskListDAO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Named
public class TaskListService {
    @Inject
    private TaskListDAO taskListDAO;

    public TaskList update(TaskList taskList) {
        return taskListDAO.update(taskList);
    }

    public TaskList addTaskToTaskList(Task task, TaskList taskList) {
        TaskList ret = taskListDAO.findById(taskList.getId());
        ret.getTasks().add(task);
        return taskListDAO.update(ret);
    }

    public TaskList updateTaskList(TaskList taskList) {
        return taskListDAO.update(taskList);
    }

    public TaskList loadListOfTasks(TaskList taskList) {
        TaskList ret = taskListDAO.findById(taskList.getId());
        ret.getTasks().size();
        return ret;
    }
}
