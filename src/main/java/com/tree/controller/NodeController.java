package com.tree.controller;

import com.tree.model.Node;
import com.tree.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class NodeController {
    @Autowired
    private NodeService nodeService;

    /**
     * This method returns home template with root form and list of roots.
     * @param model attributes added to the template
     * @return name of template.
     */
    @RequestMapping(value = "/home")
    public String home(Model model){
        model.addAttribute("node", new Node());
        model.addAttribute("nodes", nodeService.getAllRoots());
        return "index";
    }

    /**
     * This method creates a new tree.
     * @param node new root that will be saved.
     * @param bindingResult error handling
     * @return redirects to home template
     */
    @RequestMapping(value = "/saveRoot", method = RequestMethod.POST)
    public String createTree(@Valid Node node, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error ->{
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return "redirect:/home";
        }
        else {
        nodeService.createTree(node);
        return "redirect:/home";}
    }

    /**
     * This method creates a new node.
     * @param node new node that will be saved.
     * @param bindingResult error handling
     * @param parent id od parent which will be assigned to new node
     * @return if there are valid errors, method returns node form template. If not, method redirects to home template
     */
    @RequestMapping(value = "/saveNode", method = RequestMethod.POST)
    public String createNode(@Valid Node node, BindingResult bindingResult, @RequestParam(name = "parent") Integer parent){
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error ->{
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return "nodeForm";
        }
        else {
        nodeService.createNode(node, parent);
        return "redirect:/home";}
    }

    /**
     * This method updates the node
     * @param node node that will be updated
     * @param bindingResult error handling
     * @return if there are valid errors, method returns update form template. If not, method redirects to home template
     */
    @RequestMapping(value = "/updateNode", method = RequestMethod.POST)
    public String updateNode(@Valid Node node, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error ->{
                System.out.println(error.getObjectName() + " " + error.getDefaultMessage());
            });
            return "updateForm";
        }
        else {
        nodeService.updateNode(node);
        return "redirect:/home";}
    }

    /**
     * This method sends new node and parent id to "createNode" method.
     * @param id parent id that will be send to "createNode" method
     * @param model attributes added to the template
     * @return form to assing value to new node
     */
    @RequestMapping(value = "/createNode/{id}")
    public String createNodeToParentById(@PathVariable Integer id, Model model) {
        model.addAttribute("node", new Node());
        model.addAttribute("parent", id);
        return "nodeForm";
    }

    /**
     * This method deletes the node by getting its id.
     * @param id id of deleted node
     * @return redirects to to home template
     */
    @RequestMapping(value = "/deleteNode/{id}")
    public String deleteNodeById(@PathVariable Integer id) {
        nodeService.deleteNode(id);
        return "redirect:/home";
    }

    /**
     * This method sends updated node to "updateNode" method.
     * @param id id of the node that will be updated
     * @param model attributes added to template
     * @return name of update form template
     */
    @RequestMapping(value = "/updateNode/{id}")
    public String getNodeByIdToUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("node", nodeService.getNodeById(id));
        return "updateForm";
    }

    /**
     * This method gets node by getting its id.
     * @param id id of the node that will be get
     * @param model attributes added to template
     * @return name of read node template
     */
    @RequestMapping(value = "/node/{id}")
    public String getNodeById(@PathVariable Integer id, Model model) {
        model.addAttribute("node", nodeService.getNodeById(id));
        return "node";
    }
}
