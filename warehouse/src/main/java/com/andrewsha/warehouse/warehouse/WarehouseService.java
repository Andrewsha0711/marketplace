package com.andrewsha.warehouse.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.andrewsha.warehouse.exception.WarehouseServiceException;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseRepository repository;

    public Page<Warehouse> getPage(int page, int size) {
        // TODO check get
        return this.repository.findAll(PageRequest.of(page, size));
    }

    public Warehouse findById(String id) {
        return this.repository.findById(id).orElseThrow(() -> new WarehouseServiceException(
                "warehouse with id " + id + " does not exists"));
    }

    @Transactional
    public Warehouse create(/* TODO form */ Warehouse form) {
        return this.repository.save(form);
    }

    @Transactional
    public Warehouse patch(String id, /* TODO form */ Warehouse form) {
        Warehouse warehouse =
                this.repository.findById(id).orElseThrow(() -> new WarehouseServiceException(
                        "warehouse with id " + id + " does not exists"));
        if (form.getProducts() != null) {
            warehouse.setProducts(form.getProducts());
        }
        return this.repository.save(warehouse);
    }

    @Transactional
    public Warehouse put(String id, /* TODO form */ Warehouse form) {
        Warehouse warehouse =
                this.repository.findById(id).orElseThrow(() -> new WarehouseServiceException(
                        "warehouse with id " + id + " does not exists"));

        warehouse.setProducts(form.getProducts());
        return this.repository.save(warehouse);
    }

    public void delete(String id) {
        this.repository.deleteById(id);
    }
}
