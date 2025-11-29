package com.tmarketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KetQuaAPI<T> {
    private int trangThai;
    private String thongBao;
    private T duLieu;

    public static <T> KetQuaAPI<T> thanhCong(T duLieu) {
        return new KetQuaAPI<>(200, "Thành công", duLieu);
    }

    public static <T> KetQuaAPI<T> thanhCong(String thongBao, T duLieu) {
        return new KetQuaAPI<>(200, thongBao, duLieu);
    }

    public static <T> KetQuaAPI<T> loi(int trangThai, String thongBao) {
        return new KetQuaAPI<>(trangThai, thongBao, null);
    }
}