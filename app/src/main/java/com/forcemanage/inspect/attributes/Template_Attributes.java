package com.forcemanage.inspect.attributes;

public class Template_Attributes {
    private String clientName;
    private String templateName;
    private int catId;
    private int level;
    private int parent;
    private int label;
    private int child;
    private int aId;

    public Template_Attributes(String clientName, String templateName, int catId, int level, int parent, int label, int child, int aId) {
        this.clientName = clientName;
        this.templateName = templateName;
        this.catId = catId;
        this.level = level;
        this.parent = parent;
        this.label = label;
        this.child = child;
        this.aId = aId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getChild() {
        return child;
    }

    public void setChild(int child) {
        this.child = child;
    }

    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }
}
