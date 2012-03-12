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
import de.widone.entities.User;
import de.widone.services.UserService;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.springframework.beans.factory.annotation.Configurable;

import javax.inject.Inject;
import java.util.List;

@Configurable
public class ListOfTaskListsModel extends AbstractReadOnlyModel<List<TaskList>> {
    @Inject
    private transient UserService userService;

    private IModel<User> userModel;

    public ListOfTaskListsModel(IModel<User> userModel) {
        this.userModel = userModel;
    }

    @Override
    public List<TaskList> getObject() {
        return userService.initTaskLists(userModel.getObject()).getTaskLists();
    }

    @Override
    public void detach() {
        super.detach();
        userModel.detach();
    }
}
