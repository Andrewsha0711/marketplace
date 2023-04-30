package com.andrewsha.warehouse.warehouse.resource;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.RepresentationModel;
import com.andrewsha.warehouse.utils.DTOBuilder;
import com.andrewsha.warehouse.warehouse.Warehouse;

public class WarehouseDTOBuilder implements DTOBuilder<Warehouse>{

    @Override
    public RepresentationModel<?> build(Warehouse source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RepresentationModel<?> build(Page<Warehouse> page) {
        // TODO Auto-generated method stub
        return null;
    }
}
