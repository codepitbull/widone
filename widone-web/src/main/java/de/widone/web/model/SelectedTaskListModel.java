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
package de.widone.web.model;

import de.widone.entities.TaskList;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

public class SelectedTaskListModel extends Model<TaskList>{

    private IModel<List<TaskList>> taskLists;

    public SelectedTaskListModel(IModel<List<TaskList>> taskLists) {
        this.taskLists = taskLists;
    }

    @Override
    public TaskList getObject() {
        if (super.getObject() == null) {
            setObject(taskLists.getObject().get(0));
        }
        return super.getObject();
    }

    @Override
    public void detach() {
        super.detach();
        taskLists.detach();
    }
}
