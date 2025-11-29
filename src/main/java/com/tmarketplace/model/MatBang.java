package com.tmarketplace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatBang {
    private String maMatBang;
    private String tenMatBang;
    private String diaChi;
    private BigDecimal dienTich;
    private Integer loaiMatBangId;
    private String tenLoaiMatBang; // Để hiển thị JOIN
    private BigDecimal giaThue;
    private LocalDate ngayBatDau;
}