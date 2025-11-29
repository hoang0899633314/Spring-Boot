package com.tmarketplace.controller;

import com.tmarketplace.dto.KetQuaAPI;
import com.tmarketplace.dto.YeuCauTimKiem;
import com.tmarketplace.model.MatBang;
import com.tmarketplace.service.DichVuMatBang;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mat-bang")
@CrossOrigin(origins = "*")
public class MatBangController {

    private final DichVuMatBang dichVuMatBang;

    public MatBangController(DichVuMatBang dichVuMatBang) {
        this.dichVuMatBang = dichVuMatBang;
    }

    // 1. API Tìm kiếm (3 điểm)
    @GetMapping("/tim-kiem")
    public ResponseEntity<KetQuaAPI<List<MatBang>>> timKiem(
            @RequestParam(required = false) String maMatBang,
            @RequestParam(required = false) String tenMatBang,
            @RequestParam(required = false) String diaChi,
            @RequestParam(required = false) java.math.BigDecimal dienTichMin,
            @RequestParam(required = false) java.math.BigDecimal dienTichMax,
            @RequestParam(required = false) Integer loaiMatBangId,
            @RequestParam(required = false) String khoangGiaThue,
            @RequestParam(required = false) String ngayBatDauTu,
            @RequestParam(required = false) String ngayBatDauDen
    ) {
        try {
            YeuCauTimKiem yeuCau = new YeuCauTimKiem();
            yeuCau.setMaMatBang(maMatBang);
            yeuCau.setTenMatBang(tenMatBang);
            yeuCau.setDiaChi(diaChi);
            yeuCau.setDienTichMin(dienTichMin);
            yeuCau.setDienTichMax(dienTichMax);
            yeuCau.setLoaiMatBangId(loaiMatBangId);
            yeuCau.setKhoangGiaThue(khoangGiaThue);

            if (ngayBatDauTu != null) {
                yeuCau.setNgayBatDauTu(java.time.LocalDate.parse(ngayBatDauTu));
            }
            if (ngayBatDauDen != null) {
                yeuCau.setNgayBatDauDen(java.time.LocalDate.parse(ngayBatDauDen));
            }

            List<MatBang> danhSach = dichVuMatBang.timKiemMatBang(yeuCau);
            return ResponseEntity.ok(KetQuaAPI.thanhCong(danhSach));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(KetQuaAPI.loi(500, "Lỗi tìm kiếm: " + e.getMessage()));
        }
    }

    // 2. API Thêm mới (2 điểm)
    @PostMapping
    public ResponseEntity<KetQuaAPI<MatBang>> themMoi(@RequestBody MatBang matBang) {
        try {
            dichVuMatBang.themMoi(matBang);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(KetQuaAPI.thanhCong("Thêm mặt bằng thành công", matBang));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(KetQuaAPI.loi(400, e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(KetQuaAPI.loi(500, "Lỗi thêm mặt bằng: " + e.getMessage()));
        }
    }

    // 3. API Xem chi tiết (2 điểm)
    @GetMapping("/{maMatBang}")
    public ResponseEntity<KetQuaAPI<MatBang>> xemChiTiet(@PathVariable String maMatBang) {
        try {
            MatBang matBang = dichVuMatBang.xemChiTiet(maMatBang);
            return ResponseEntity.ok(KetQuaAPI.thanhCong(matBang));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(KetQuaAPI.loi(404, e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(KetQuaAPI.loi(500, "Lỗi xem chi tiết: " + e.getMessage()));
        }
    }

    // 4. API Xóa (2 điểm)
    @DeleteMapping("/{maMatBang}")
    public ResponseEntity<KetQuaAPI<Void>> xoa(@PathVariable String maMatBang) {
        try {
            dichVuMatBang.xoa(maMatBang);
            return ResponseEntity.ok(KetQuaAPI.thanhCong("Xóa mặt bằng thành công", null));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(KetQuaAPI.loi(404, e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(KetQuaAPI.loi(500, "Lỗi xóa mặt bằng: " + e.getMessage()));
        }
    }
}