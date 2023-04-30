package com.andrewsha.warehouse.product.resource;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.andrewsha.warehouse.product.Product;
import com.andrewsha.warehouse.utils.DTOBuilder;
import com.toedter.spring.hateoas.jsonapi.JsonApiModelBuilder;

public class ProductDTOBuilder implements DTOBuilder<Product> {
    @Value(value = "${api.endpoint.product}")
    private String endpoint;

    @Override
    public RepresentationModel<?> build(Product source) {
        return JsonApiModelBuilder.jsonApiModel().model(new ProductDTO(source))
                .link(Link.of(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .replacePath(endpoint + "/" + source.getId().toString()).toUriString()))
                .build();
    }

    @Override
    public RepresentationModel<?> build(Page<Product> page) {
        Collection<RepresentationModel<?>> contentCollection =
                page.getContent().stream().map(e -> this.build(e)).collect(Collectors.toList());
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(),
                page.getNumber(), page.getTotalElements(), page.getTotalPages());
        Link selfLink = Link.of(ServletUriComponentsBuilder.fromCurrentContextPath()
                .replacePath(endpoint).toUriString()).withSelfRel();
        PagedModel<RepresentationModel<?>> pagedModel =
                PagedModel.of(contentCollection, metadata, selfLink);
        return JsonApiModelBuilder.jsonApiModel().model(pagedModel).pageMeta()
                .pageLinks(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .replacePath(endpoint).toUriString())
                .build();
    }
}
