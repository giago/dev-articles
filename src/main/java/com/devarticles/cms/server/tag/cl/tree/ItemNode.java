package com.devarticles.cms.server.tag.cl.tree;

import java.util.List;


public class ItemNode {
    
    private String token;
    
    public List<ItemNode> nodes;
    
    public ItemNode(String token) {
        this.token = token;
    }

    public String dump() {
        return ItemNode.class.getSimpleName() + " for token : " + token;
    }
    
    public String getToken() {
        return token;
    }

    public String getTransformedToken() {
        return token;
    }

    public void add(ItemNode node) {
        // TODO Auto-generated method stub
        
    }

    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    public List<ItemNode> children() {
        // TODO Auto-generated method stub
        return null;
    }

}
