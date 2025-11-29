package com.tmarketplace.repository;

import com.tmarketplace.dto.YeuCauTimKiem;
import com.tmarketplace.model.MatBang;
import java.util.List;
import java.util.Optional;

public interface KhoMatBang {
    List<MatBang> timKiem(YeuCauTimKiem yeuCau);
    Optional<MatBang> timTheoMa(String maMatBang);
    int luu(MatBang matBang);
    int xoa(String maMatBang);
}