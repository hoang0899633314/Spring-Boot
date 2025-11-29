package com.tmarketplace.repository;

import com.tmarketplace.dto.YeuCauTimKiem;
import com.tmarketplace.model.MatBang;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class KhoMatBangImpl implements KhoMatBang {

    private final JdbcTemplate jdbcTemplate;

    public KhoMatBangImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper để map ResultSet sang MatBang
    private final RowMapper<MatBang> rowMapper = (rs, rowNum) -> {
        MatBang mb = new MatBang();
        mb.setMaMatBang(rs.getString("ma_mat_bang"));
        mb.setTenMatBang(rs.getString("ten_mat_bang"));
        mb.setDiaChi(rs.getString("dia_chi"));
        mb.setDienTich(rs.getBigDecimal("dien_tich"));
        mb.setLoaiMatBangId(rs.getInt("loai_mat_bang_id"));
        mb.setTenLoaiMatBang(rs.getString("ten_loai"));
        mb.setGiaThue(rs.getBigDecimal("gia_thue"));
        mb.setNgayBatDau(rs.getDate("ngay_bat_dau") != null ?
                rs.getDate("ngay_bat_dau").toLocalDate() : null);
        return mb;
    };

    @Override
    public List<MatBang> timKiem(YeuCauTimKiem yeuCau) {
        StringBuilder sql = new StringBuilder(
                "SELECT mb.*, lmb.ten_loai " +
                        "FROM mat_bang mb " +
                        "LEFT JOIN loai_mat_bang lmb ON mb.loai_mat_bang_id = lmb.id " +
                        "WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        // Tìm theo mã (chính xác)
        if (yeuCau.getMaMatBang() != null && !yeuCau.getMaMatBang().isEmpty()) {
            sql.append(" AND mb.ma_mat_bang = ?");
            params.add(yeuCau.getMaMatBang());
        }

        // Tìm theo tên (LIKE)
        if (yeuCau.getTenMatBang() != null && !yeuCau.getTenMatBang().isEmpty()) {
            sql.append(" AND mb.ten_mat_bang LIKE ?");
            params.add("%" + yeuCau.getTenMatBang() + "%");
        }

        // Tìm theo địa chỉ (LIKE)
        if (yeuCau.getDiaChi() != null && !yeuCau.getDiaChi().isEmpty()) {
            sql.append(" AND mb.dia_chi LIKE ?");
            params.add("%" + yeuCau.getDiaChi() + "%");
        }

        // Tìm theo khoảng diện tích
        if (yeuCau.getDienTichMin() != null) {
            sql.append(" AND mb.dien_tich >= ?");
            params.add(yeuCau.getDienTichMin());
        }
        if (yeuCau.getDienTichMax() != null) {
            sql.append(" AND mb.dien_tich <= ?");
            params.add(yeuCau.getDienTichMax());
        }

        // Tìm theo loại
        if (yeuCau.getLoaiMatBangId() != null) {
            sql.append(" AND mb.loai_mat_bang_id = ?");
            params.add(yeuCau.getLoaiMatBangId());
        }

        // Tìm theo khoảng giá thuê
        if (yeuCau.getKhoangGiaThue() != null) {
            switch (yeuCau.getKhoangGiaThue()) {
                case "duoi2m":
                    sql.append(" AND mb.gia_thue < 2000000");
                    break;
                case "2m-5m":
                    sql.append(" AND mb.gia_thue >= 2000000 AND mb.gia_thue < 5000000");
                    break;
                case "5m-10m":
                    sql.append(" AND mb.gia_thue >= 5000000 AND mb.gia_thue < 10000000");
                    break;
                case "tren10m":
                    sql.append(" AND mb.gia_thue >= 10000000");
                    break;
            }
        }

        // Tìm theo khoảng thời gian
        if (yeuCau.getNgayBatDauTu() != null) {
            sql.append(" AND mb.ngay_bat_dau >= ?");
            params.add(yeuCau.getNgayBatDauTu());
        }
        if (yeuCau.getNgayBatDauDen() != null) {
            sql.append(" AND mb.ngay_bat_dau <= ?");
            params.add(yeuCau.getNgayBatDauDen());
        }

        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }

    @Override
    public Optional<MatBang> timTheoMa(String maMatBang) {
        String sql = "SELECT mb.*, lmb.ten_loai " +
                "FROM mat_bang mb " +
                "LEFT JOIN loai_mat_bang lmb ON mb.loai_mat_bang_id = lmb.id " +
                "WHERE mb.ma_mat_bang = ?";

        List<MatBang> result = jdbcTemplate.query(sql, rowMapper, maMatBang);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public int luu(MatBang matBang) {
        String sql = "INSERT INTO mat_bang (ma_mat_bang, ten_mat_bang, dia_chi, " +
                "dien_tich, loai_mat_bang_id, gia_thue, ngay_bat_dau) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                matBang.getMaMatBang(),
                matBang.getTenMatBang(),
                matBang.getDiaChi(),
                matBang.getDienTich(),
                matBang.getLoaiMatBangId(),
                matBang.getGiaThue(),
                matBang.getNgayBatDau()
        );
    }

    @Override
    public int xoa(String maMatBang) {
        String sql = "DELETE FROM mat_bang WHERE ma_mat_bang = ?";
        return jdbcTemplate.update(sql, maMatBang);
    }
}