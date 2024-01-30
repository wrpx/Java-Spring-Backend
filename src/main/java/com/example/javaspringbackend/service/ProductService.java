// ProductService.java
package com.example.javaspringbackend.service;

import com.example.javaspringbackend.exception.ErrorType;
import com.example.javaspringbackend.exception.CustomException;
import com.example.javaspringbackend.model.Product;
import com.example.javaspringbackend.repository.ProductRepository;
import com.example.javaspringbackend.util.StringValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException("Product not found with id: " + id, ErrorType.PRODUCT_NOT_FOUND.getStatus()));
    }

    public Product createProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        validateProduct(productDetails);
        Product product = getProductById(id);

        if (productDetails.getName() != null && !productDetails.getName().isEmpty()) {
            product.setName(productDetails.getName());
        }
        if (productDetails.getDetail() != null && !productDetails.getDetail().isEmpty()) {
            product.setDetail(productDetails.getDetail());
        }
        if (productDetails.getPrice() != null) {
            product.setPrice(productDetails.getPrice());
        }
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new CustomException("Product not found with id: " + id, ErrorType.PRODUCT_NOT_FOUND.getStatus());
        }
        productRepository.deleteById(id);
    }

    private void validateProduct(Product product) {
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new CustomException("Price must be a positive number", HttpStatus.BAD_REQUEST);
        }
        if (StringValidationUtil.isInvalidString(product.getName()) || StringValidationUtil.isInvalidString(product.getDetail())) {
            throw new CustomException("Name and detail must contain only English letters and numbers", ErrorType.INVALID_STRING.getStatus());
        }
    }
}

