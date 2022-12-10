package com.tass.productservice.database.entities;

import com.tass.productservice.utils.Constant;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String icon;
    private int isRoot;

    public boolean checkIsRoot(){
        return isRoot == Constant.ONOFF.ON;
    }


}
