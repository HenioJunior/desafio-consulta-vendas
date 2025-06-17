package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT obj FROM Sale obj " +
            "WHERE (obj.date BETWEEN :minDate AND :maxDate) " +
            "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Sale> searchSalesBetweenDateForName(
            @Param("minDate") LocalDate minDate,
            @Param("maxDate") LocalDate maxDate,
            @Param("name") String name,
            Pageable pageable
    );

    @Query("SELECT obj FROM Sale obj WHERE obj.date BETWEEN :minDate AND :maxDate")
    Page<Sale> searchSalesBetweenDate(LocalDate minDate, LocalDate maxDate, Pageable pageable);

}
