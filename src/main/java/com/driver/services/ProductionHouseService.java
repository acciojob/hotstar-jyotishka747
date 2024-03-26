package com.driver.services;


import com.driver.EntryDto.ProductionHouseEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductionHouseService {

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addProductionHouseToDb(ProductionHouseEntryDto productionHouseEntryDto){
        ProductionHouse productionhouse= new ProductionHouse();
        productionhouse.setName(productionHouseEntryDto.getName());
        productionhouse.setRatings(0);
        List<WebSeries> webSeriesList = new ArrayList<>();
        productionhouse.setWebSeriesList(webSeriesList);

        return productionHouseRepository.save(productionhouse).getId();
    }



}
