package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleReportDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleReportDTO(entity);
	}

	public Page<SaleSummaryDTO> searchSalesBetweenDate(String minDate, String maxDate, Pageable pageable) {
		LocalDate today;
		try {
			today = (maxDate != null && !maxDate.isBlank())
					? LocalDate.parse(maxDate)
					: LocalDate.now(ZoneId.systemDefault());
		} catch (DateTimeParseException e) {
			today = LocalDate.now(ZoneId.systemDefault());
		}
		LocalDate initDate;
		try {
			initDate = (minDate != null && !minDate.isBlank())
					? LocalDate.parse(minDate)
					: today.minusYears(1L);
		} catch (DateTimeParseException e) {
			initDate = today.minusYears(1L);
		}
		Page<Sale> sales = repository.searchSalesBetweenDate(initDate, today, pageable);
	return sales.map(SaleSummaryDTO::new);
	}

	public Page<SaleReportDTO> searchSalesBetweenDateForName(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate today;
		try {
			today = (maxDate != null && !maxDate.isBlank())
					? LocalDate.parse(maxDate)
					: LocalDate.now(ZoneId.systemDefault());
		} catch (DateTimeParseException e) {
			today = LocalDate.now(ZoneId.systemDefault());
		}
		LocalDate initDate;
		try {
			initDate = (minDate != null && !minDate.isBlank())
					? LocalDate.parse(minDate)
					: today.minusYears(1L);
		} catch (DateTimeParseException e) {
			initDate = today.minusYears(1L);
		}

		Page<Sale> sales = repository.searchSalesBetweenDateForName(initDate, today, name, pageable);
		return sales.map(SaleReportDTO::new);
	}
}
