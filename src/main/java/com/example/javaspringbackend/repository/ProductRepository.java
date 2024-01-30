//ProductRepository.java
package com.example.javaspringbackend.repository;
import com.example.javaspringbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
