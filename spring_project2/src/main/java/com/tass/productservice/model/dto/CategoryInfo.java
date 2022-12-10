package com.tass.productservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.tass.productservice.database.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfo {
    private Long id;
    private String name;
    private String icon;
    private String description;

    @JsonProperty("is_root")
    private Integer isRoot;
    @JsonProperty("parents")
    private List<CategoryInfo> parentId;

    @JsonProperty("childs")
    private List<CategoryInfo> children;

    public CategoryInfo(Long id, String name, String icon, String description, Integer isRoot) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.isRoot = isRoot;
    }

    @Lob
    private Set<Category> child;
    @Lob
    private Set<Category> parent;

    public void setChild(String child) {
        Gson gson = new Gson();
        Set<Category> childSet = gson.fromJson(child, Set.class);
        this.child = childSet;
    }

    public void setParent(String parent) {
        Gson gson = new Gson();
        Set<Category> parentSet = gson.fromJson(parent, Set.class);
        this.parent = parentSet;
    }
    
}
