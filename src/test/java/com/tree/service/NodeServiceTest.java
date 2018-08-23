package com.tree.service;

import com.tree.model.Node;
import com.tree.repository.NodeRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class NodeServiceTest {
    @Autowired
    NodeService nodeService;
    @MockBean
    NodeRepository nodeRepository;

    @Before
    public void setUp() {
        Node node = new Node();

        Mockito.when(nodeRepository.getOne(node.getId()))
                .thenReturn(node);
    }
}
