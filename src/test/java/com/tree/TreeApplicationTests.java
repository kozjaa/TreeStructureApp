package com.tree;

import com.tree.model.Node;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TreeApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testNode() {
		Node node = new Node();
		boolean isRoot = node.isRoot();
		assertEquals(true, isRoot);
	}

}
