package com.service.inventory.mapper;

import com.service.inventory.model.dto.InventoryDto;
import com.service.inventory.model.entity.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    InventoryDto toInventoryDto(Inventory inventory);
}
