package com.tass.productservice.model.request;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class ProductRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String barcode;
    private String name;
    private String image;
    private String description;
    private String content;
}
