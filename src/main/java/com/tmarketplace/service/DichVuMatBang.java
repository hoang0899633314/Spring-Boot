package com.tmarketplace.service;

import com.tmarketplace.dto.YeuCauTimKiem;
import com.tmarketplace.model.MatBang;
import java.util.List;

public interface DichVuMatBang {
    List<MatBang> timKiemMatBang(YeuCauTimKiem yeuCau);
    MatBang xemChiTiet(String maMatBang);
    void themMoi(MatBang matBang);
    void xoa(String maMatBang);
}