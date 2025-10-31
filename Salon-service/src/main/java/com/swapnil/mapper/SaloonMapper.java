package com.swapnil.mapper;

import com.swapnil.modal.Saloon;
import com.swapnil.payloaddto.SaloonDTO;

public class SaloonMapper {

    public static SaloonDTO mapTODTO(Saloon saloon) {
        SaloonDTO saloonDTO = new SaloonDTO();
        saloonDTO.setId(saloon.getId());
        saloonDTO.setName(saloon.getName());
        saloonDTO.setPhone(saloon.getPhone());
        saloonDTO.setImages(saloon.getImages());
        saloonDTO.setAddress(saloon.getAddress());
        saloonDTO.setCity(saloon.getCity());
        saloonDTO.setEmail(saloon.getEmail());
        saloonDTO.setOwnerId(saloon.getOwnerId());
        saloonDTO.setOpenTime(saloon.getOpenTime());
        saloonDTO.setCloseTime(saloon.getCloseTime());
        return saloonDTO;
    }
}
