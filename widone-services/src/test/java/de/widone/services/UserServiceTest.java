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

import de.widone.entities.User;
import de.widone.services.daos.UserDAO;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.*;
import static org.springframework.util.ReflectionUtils.*;

public class UserServiceTest {
    @Test
    public void testCreateUser() throws Exception{
        User user = new User();
        user.setPassword("1234");
        user.setUsername("testUser");
        UserDAO userDAO = mock(UserDAO.class);
        UserService userService = new UserService();
        setUserDAOOnUserService(userDAO, userService);
        userService.create(user);
        verify(userDAO).create(user);
        //password should be encrypted
        assertNotSame(user.getPassword(), "1234");
    }

    @Test
    public void testAuthenticateAndGetUser() throws Exception{
        //needed cause we are testing the password encryption
        User user = new User();
        user.setPassword("1234");
        user.setUsername("testUser");
        UserDAO userDAO = mock(UserDAO.class);
        UserService userService = new UserService();
        setUserDAOOnUserService(userDAO, userService);
        userService.create(user);
        verify(userDAO).create(user);

        //actual signin test
        when(userDAO.findUserByUsername("testUser")).thenReturn(user);
        assertNotNull(userService.authenticateAndGet("testUser", "1234"));
    }

    private void setUserDAOOnUserService (UserDAO userDAO, UserService userService) {
        Field userDAOField = findField(UserService.class, "userDAO");
        makeAccessible(userDAOField);
        setField(userDAOField, userService, userDAO);
    }
}