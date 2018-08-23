package com.tree.service;

import com.tree.model.Node;
import com.tree.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class NodeService {
    @Autowired
    private NodeRepository nodeRepository;
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method creates a new node and assigns a parent to it.
     * It also assigns value that will be shown if the node is a leaf.
     * @param node  new node which is created and saved to database.
     * @param parent id of parent that will be assigned to new created node
     */
    @Transactional
    public void createNode(Node node, Integer parent){
        Node myParent = getNodeById(parent);
        myParent.getChildren().add(node);
        node.setParent(myParent);
        node.setLeafValue(myParent.getLeafValue());
        entityManager.merge(node);
    }

    /**
     * This method gets all the nodes
     * @return list of all the nodes
     */
    public List<Node> getAllNodes(){
        return nodeRepository.findAll();
    }

    /**
     * This method gets all the nodes that are the roots
     * @return new list of all roots
     */
    public List<Node> getAllRoots(){
        List<Node> allNodes = getAllNodes();
        List<Node> roots = new ArrayList<>();
        for(Node o : allNodes) {
            if(o.isRoot()) {
                roots.add(o); } }
        return roots;
    }

    /**
     * This method gets the node by sending its id.
     * @param id id of searched node.
     * @return node get by sended id.
     */
    public Node getNodeById(Integer id){
        return nodeRepository.getOne(id);
    }

    /**
     * This method creates new tree by assign root value for it.
     * It also assign a leaf value which is its data.
     * @param node new node which is a root.
     */
    @Transactional
    public void createTree(Node node) {
        node.setLeafValue(node.getData());
        entityManager.merge(node);
    }

    /**
     * This method deletes node by sending its id.
     * @param id id of deleted node.
     */
    @Transactional
    public void deleteNode(Integer id) {
        nodeRepository.deleteById(id);
    }

    /**
     * This method update node by assign new value to it.
     * @param node node that will be updated.
     */
    @Transactional
    public void updateNode(Node node){
        if (node.isRoot()){
            node.setLeafValue(node.getData());
            entityManager.merge(node);
        }
        else {
        node.setLeafValue(node.getParent().getLeafValue());
        entityManager.merge(node);}
    }
}
