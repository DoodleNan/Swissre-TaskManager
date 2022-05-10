package com.swissre.taskmanager;

import com.swissre.taskmanager.controller.TaskManagerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static junit.framework.TestCase.assertNotNull;

@SpringBootTest
class TaskManagerApplicationTests {

	@Autowired
	private TaskManagerController taskManagerController;

	@Test
	void contextLoads() {
		assertNotNull(taskManagerController);
	}

}
