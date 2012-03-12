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
package de.widone.web.testhelper;

import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MockRoleCheckingStrategy implements IRoleCheckingStrategy, Serializable {
    private Roles roles;

    public MockRoleCheckingStrategy(Roles roles) {
        this.roles = roles;
    }

    @Override
    public boolean hasAnyRole(Roles roles) {
        return this.roles.hasAnyRole(roles);
    }
}
