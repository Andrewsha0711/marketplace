package com.andrewsha.warehouse.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.andrewsha.warehouse.utils.DTOBuilder;
import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;
import com.toedter.spring.hateoas.jsonapi.MediaTypes;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(path = "api/v1/warehouse")
@Validated
public class WarehouseController {

    @Autowired
    private WarehouseService service;

    @Autowired
    private DTOBuilder<Warehouse> builder;

    @GetMapping(produces = MediaTypes.JSON_API_VALUE)
    public ResponseEntity<?> getPage(
            @RequestParam(value = "page[number]", required = true) @Min(0) int pageNumber,
            @RequestParam(value = "page[size]", required = true) @Min(1) int size) {
        Page<Warehouse> page = this.service.getPage(pageNumber, size);
        return ResponseEntity.ok(this.builder.build(page));
    }

    @GetMapping(path = "/{id}", produces = MediaTypes.JSON_API_VALUE)
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.builder.build(this.service.findById(id)));
    }

    @PostMapping(produces = MediaTypes.JSON_API_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody /* TODO form */ Warehouse form) {
        return ResponseEntity.ok(this.builder.build(this.service.create(form)));
    }

    @PatchMapping(path = "{id}", produces = MediaTypes.JSON_API_VALUE)
    public ResponseEntity<?> update(@PathVariable("id") String id,
            @Valid @RequestBody /* TODO form */ Warehouse form) {
        return ResponseEntity.ok(this.builder.build(this.service.patch(id, form)));
    }

    @PutMapping(path = "{id}", produces = MediaTypes.JSON_API_VALUE)
    public ResponseEntity<?> put(@PathVariable("id") String id,
            @Valid @RequestBody /* TODO form */ Warehouse form) {
        return ResponseEntity.ok(this.builder.build(this.service.put(id, form)));
    }

    @DeleteMapping(path = "{id}", produces = MediaTypes.JSON_API_VALUE)
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        this.service.delete(id);
        return ResponseEntity.ok(JsonApiModelBuilder.jsonApiModel().meta("deleted", id).build());
    }
}
