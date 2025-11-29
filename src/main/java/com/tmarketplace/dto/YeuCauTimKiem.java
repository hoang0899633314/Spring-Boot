package com.tmarketplace.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class YeuCauTimKiem {
    private String maMatBang;          // Tìm chính xác
    private String tenMatBang;         // Tìm LIKE
    private String diaChi;             // Tìm LIKE
    private BigDecimal dienTichMin;    // Khoảng diện tích
    private BigDecimal dienTichMax;
    private Integer loaiMatBangId;     // Tìm theo ID
    private String khoangGiaThue;      // "all", "duoi2m", "2m-5m", "5m-10m", "tren10m"
    private LocalDate ngayBatDauTu;    // Khoảng thời gian
    private LocalDate ngayBatDauDen;
}