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
package de.widone.services.daos;

import de.widone.entities.User;
import de.widone.services.api.AbstractGenericJpaDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDAO extends AbstractGenericJpaDAO<User, Long>{

    public User findUserByUsername(String username) {
        CriteriaQuery<User> query = createCriteria();
        Root<User> userRoot = query.from(User.class);
        entityManager.getCriteriaBuilder().equal(userRoot.get("username"), username);
        List<User> users = entityManager.createQuery(query).getResultList();
        if (users.size() == 1)
            return users.get(0);
        return null;
    }
}
