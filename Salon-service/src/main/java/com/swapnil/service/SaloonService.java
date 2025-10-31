package com.swapnil.service;

import com.swapnil.modal.Saloon;
import com.swapnil.payloaddto.SaloonDTO;
import com.swapnil.payloaddto.UserDTO;

import java.util.List;

public interface SaloonService {

    Saloon createSaloon(SaloonDTO saloon, UserDTO user);

    Saloon updateSaloon(SaloonDTO saloon, UserDTO user, Long saloonId) throws Exception;

    List<Saloon> getAllSaloon();

    Saloon getSaloonById(Long id) throws Exception;

    Saloon getByOwnerId(Long id) throws Exception;

    List<Saloon> searchSaloonByCity(String city);


}
