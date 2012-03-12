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
package de.widone.web.authentication;

import de.widone.entities.User;
import de.widone.services.UserService;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;
import org.springframework.beans.factory.annotation.Configurable;

import javax.inject.Inject;

@Configurable
public class WiDoneSession extends AuthenticatedWebSession{

    @Inject
    private UserService userService;

    private User user;

    public WiDoneSession(Request request) {
        super(request);
    }

    @Override
    public boolean authenticate(String username, String password) {
        user = userService.authenticateAndGet(username, password);
        return user != null;
    }

    @Override
    public Roles getRoles() {
        Roles roles = new Roles();
        if(user != null)
            roles.add(user.getGroup().toString());
        return roles;
    }

    public User getUser() {
        return user;
    }
}
