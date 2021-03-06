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
package de.widone.web.panel.userdetails;

import de.widone.entities.User;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class UserUpdatedEvent {
    private AjaxRequestTarget target;
    private User user;
    private String newPassword;

    public UserUpdatedEvent(AjaxRequestTarget target, User user, String newPassword) {
        this.target = target;
        this.user = user;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public User getUser() {
        return user;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }
}
