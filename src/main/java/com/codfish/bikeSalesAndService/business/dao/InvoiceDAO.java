package com.codfish.bikeSalesAndService.business.dao;

import com.codfish.bikeSalesAndService.api.dto.InvoiceDTO;

import java.util.List;

public interface InvoiceDAO {

    List<InvoiceDTO> findAll();
}
