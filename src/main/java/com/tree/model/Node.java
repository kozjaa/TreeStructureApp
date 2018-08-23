package com.tree.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** Value that will be shown if the node is a leaf.*/
    private Integer leafValue;

    /** Parameter that tells us if the node is a leaf. */
    private boolean leaf;

    /** Value of the node which is not a leaf. */
    @NotNull
    private Integer data;

    /** Parent assigned to the node. */
    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "parent_id")
    private Node parent;

    /** Children assigned to the node. */
    @OneToMany(mappedBy = "parent")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Node> children;

    public Node(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    /**
     * This method counts how many children does the node have.
     * @return number of children.
     */
    public int getDegree() {
        return children.size();
    }

    /**
     * This method checks if the node is the root.
     * @return Whether node has parent.
     */
    public boolean isRoot(){
        return this.parent == null;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public String toString() {
        return data.toString();
    }

    public Integer getLeafValue() {
        if (isRoot()){
            return data;
        }
        return data + getParent().getLeafValue();
    }

    public void setLeafValue(Integer leafValue) {
        this.leafValue = leafValue;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }
}
