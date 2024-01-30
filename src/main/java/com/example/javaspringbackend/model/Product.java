//Product.java
package com.example.javaspringbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String detail;
    private Double price;

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name='" + name + '\'' + ", detail='" + detail + '\'' + ", price=" + price + '}';
    }
}
