package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(
			@RequestParam(name = "minDate", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minDate,
			@RequestParam(name = "maxDate", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maxDate,
			@RequestParam(name = "name", defaultValue = "") String name,
			Pageable pageable) {
		if(maxDate == null) {
			maxDate = LocalDate.now(ZoneId.systemDefault());
		}
		if(minDate == null) {
			minDate = maxDate.minusYears(1L);
		}
		Page<SaleMinDTO> sales = service.searchSalesBetweenDateForName(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(sales);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<Page<SaleMinDTO>> getSummary(
			@RequestParam(name = "minDate", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate minDate,
			@RequestParam(name = "maxDate", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate maxDate,
			Pageable pageable
	) {
		if(maxDate == null) {
			maxDate = LocalDate.now(ZoneId.systemDefault());
		}
		if(minDate == null) {
			minDate = maxDate.minusYears(1L);
		}
		Page<SaleMinDTO> sales = service.searchSalesBetweenDate(minDate, maxDate, pageable);
		return ResponseEntity.ok(sales);
	}
}
