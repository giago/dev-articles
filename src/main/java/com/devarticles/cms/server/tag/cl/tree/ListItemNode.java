package com.devarticles.cms.server.tag.cl.tree;

public class ListItemNode extends ItemNode {

    private ItemNode titleItem;
    
    private ItemNode contentItem;
    
    public ListItemNode(String token) {
        super(token);
    }
    
    public ItemNode getTitleItem() {
        return titleItem;
    }
    
    public ItemNode getContentItem() {
        return contentItem;
    }

    public void setTitleItem(ItemNode titleItem) {
        this.titleItem = titleItem;
    }

    public void setContentItem(ItemNode contentItem) {
        this.contentItem = contentItem;
    }

}
