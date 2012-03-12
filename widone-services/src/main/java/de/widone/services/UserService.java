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

import de.widone.entities.GroupsEnum;
import de.widone.entities.TaskList;
import de.widone.entities.User;
import de.widone.services.daos.UserDAO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.inject.Inject;
import javax.inject.Named;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Named
public class UserService {

    private BASE64Decoder decoder;
    private BASE64Encoder encoder;

    private Integer hashIterations;

    @Inject
    private UserDAO userDAO;

    public UserService(Integer hashIterations) {
        this.hashIterations = hashIterations;
        decoder = new BASE64Decoder();
        encoder = new BASE64Encoder();
    }

    public UserService() {
        this(10000);
    }

    public User update(User user) {
        return userDAO.update(user);
    }

    public void create(User user) {
        try {
            byte[] salt = getSalt();
            user.setSalt(encoder.encode(salt));
            user.setPassword(encryptStringWithSalt(user.getPassword(), user.getSalt()));
            user.setGroup(GroupsEnum.USER);
            List<TaskList> taskLists = new ArrayList<TaskList>();
            TaskList taskList = new TaskList();
            taskList.setInbox(Boolean.TRUE);
            taskList.setDescription("The default inbox");
            taskLists.add(taskList);
            user.setTaskLists(taskLists);
            userDAO.create(user);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User initTaskLists(User user) {
        User ret = userDAO.findById(user.getId());
        ret.getTaskLists().size();
        return ret;
    }

    public User authenticateAndGet(String username, String password) {
        try {
            User user = userDAO.findUserByUsername(username);
            if (user != null && (user.getPassword().equals(encryptStringWithSalt(password, user.getSalt()))))
                return user;
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encryptStringWithSalt(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(decoder.decodeBuffer(salt));
            byte[] input = digest.digest(password.getBytes("UTF-8"));
            for (int count = 0; count < hashIterations; count++) {
                digest.reset();
                input = digest.digest(input);
            }
            return encoder.encode(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getSalt() {
        try {
            SecureRandom rnd = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[8];
            rnd.nextBytes(salt);
            return salt;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
