package com.example.dscommerce.services;

import com.example.dscommerce.dto.CategoryDTO;
import com.example.dscommerce.dto.ProductDTO;
import com.example.dscommerce.dto.ProductMinDTO;
import com.example.dscommerce.entities.Product;
import com.example.dscommerce.repositories.CategoryRepository;
import com.example.dscommerce.repositories.ProductRepository;
import com.example.dscommerce.services.exceptions.DatabaseException;
import com.example.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository) {
        this.productRepository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product entity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource with ID " + id + " not found."));
        return new ProductDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(String name, Pageable pageable) {
        Page<Product> result = productRepository.searchByName(name, pageable);
        return result.map(ProductMinDTO::new);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = productRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource with ID " + id + " not found.");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource with ID " + id + " not found.");
        }
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Cannot delete resource with ID " + id + " because it is referenced by other records.");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.getCategories().clear();
        for (CategoryDTO categoryDTO : dto.getCategories()) {
            entity.getCategories().add(categoryRepository.getReferenceById(categoryDTO.getId()));
        }
    }

}
