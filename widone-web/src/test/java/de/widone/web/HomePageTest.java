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
package de.widone.web;

import de.widone.entities.GroupsEnum;
import de.widone.entities.TaskList;
import de.widone.entities.User;
import de.widone.services.UserService;
import de.widone.web.authentication.WiDoneSession;
import de.widone.web.page.home.HomePage;
import de.widone.web.testhelper.MockRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.RoleAuthorizationStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ReflectionUtils;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Simple test using the WicketTester
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/aspectj-context.xml"})
public class HomePageTest {

	private WicketTester tester;

	@Inject
	private UserService userService;

	@Before
	public void setUp() throws Exception {
		User user = new User();
		user.setUsername("username");
		user.setGroup(GroupsEnum.USER);
		List<TaskList> taskLists = new ArrayList<TaskList>();
		TaskList taskList = new TaskList();
		taskList.setInbox(Boolean.TRUE);
		taskList.setDescription("The default inbox");
		taskLists.add(taskList);
		user.setTaskLists(taskLists);
		tester = new WicketTester(new WiDoneApplication());
		when(userService.initTaskLists(Matchers.<User>any())).thenReturn(user);
		Field userField = ReflectionUtils.findField(WiDoneSession.class, "user");
		ReflectionUtils.makeAccessible(userField);
		userField.set(((WiDoneSession) tester.getSession()), user);
	}

	@After
	public void tearDown() {
		tester.destroy();
	}

	@Test
	public void homepageRendersSuccessfully() {
		tester.getApplication().getSecuritySettings().setAuthorizationStrategy(new RoleAuthorizationStrategy(new MockRoleCheckingStrategy(new Roles("USER"))));
		HomePage h=new HomePage();
		tester.startPage(HomePage.class);
		tester.assertRenderedPage(HomePage.class);
	}

}
