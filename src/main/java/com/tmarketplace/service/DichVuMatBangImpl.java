package com.tmarketplace.service;

import com.tmarketplace.dto.YeuCauTimKiem;
import com.tmarketplace.model.MatBang;
import com.tmarketplace.repository.KhoMatBang;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DichVuMatBangImpl implements DichVuMatBang {

    private final KhoMatBang khoMatBang;

    public DichVuMatBangImpl(KhoMatBang khoMatBang) {
        this.khoMatBang = khoMatBang;
    }

    @Override
    public List<MatBang> timKiemMatBang(YeuCauTimKiem yeuCau) {
        return khoMatBang.timKiem(yeuCau);
    }

    @Override
    public MatBang xemChiTiet(String maMatBang) {
        return khoMatBang.timTheoMa(maMatBang)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mặt bằng: " + maMatBang));
    }

    @Override
    public void themMoi(MatBang matBang) {
        // Kiểm tra trùng mã
        if (khoMatBang.timTheoMa(matBang.getMaMatBang()).isPresent()) {
            throw new RuntimeException("Mã mặt bằng đã tồn tại");
        }

        int result = khoMatBang.luu(matBang);
        if (result == 0) {
            throw new RuntimeException("Thêm mặt bằng thất bại");
        }
    }

    @Override
    public void xoa(String maMatBang) {
        // Kiểm tra tồn tại
        if (!khoMatBang.timTheoMa(maMatBang).isPresent()) {
            throw new RuntimeException("Không tìm thấy mặt bằng: " + maMatBang);
        }

        int result = khoMatBang.xoa(maMatBang);
        if (result == 0) {
            throw new RuntimeException("Xóa mặt bằng thất bại");
        }
    }
}