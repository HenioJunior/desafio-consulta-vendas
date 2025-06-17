package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

import java.time.LocalDate;

public class SaleMinDTOWithSellerName {

	private String sellerName;
	private Double amount;

	public SaleMinDTOWithSellerName(String sellerName, Double amount) {
		this.sellerName = sellerName;
		this.amount = amount;
	}

	public SaleMinDTOWithSellerName(Sale entity) {
		sellerName = entity.getSeller().getName();
		amount = entity.getAmount();
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getAmount() {
		return amount;
	}

}
