package com.example.JasonShaw.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DictionaryController {

    // Map lưu danh sách hãng gậy bida và mô tả
    private static final Map<String, String> cueBrands = new HashMap<>();

    static {
        cueBrands.put("predator", "Predator — thương hiệu gậy bida nổi tiếng với công nghệ low deflection.");
        cueBrands.put("mcdermott", "McDermott — hãng gậy Mỹ cao cấp, nổi bật với độ bền và thiết kế đẹp.");
        cueBrands.put("mezz", "Mezz — thương hiệu Nhật Bản với độ chính xác cao.");
        cueBrands.put("lucasi", "Lucasi — hãng gậy chất lượng tốt, giá tầm trung, phù hợp người chơi phổ thông.");
        cueBrands.put("viking", "Viking — thương hiệu gậy Mỹ với thiết kế mạnh mẽ và độ bền cao.");
        cueBrands.put("poison", "Poison — thương hiệu con của Predator, nổi bật với thiết kế trẻ trung.");
        cueBrands.put("balabushka", "Balabushka — thương hiệu gậy huyền thoại trong giới bida.");
    }

    @GetMapping("/dictionary")
    public ResponseEntity<String> translate(@RequestParam(required = false) String word) {

        if (word == null || word.trim().isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Vui lòng nhập tên hãng gậy bida.");
        }

        String key = word.trim().toLowerCase();

        if (cueBrands.containsKey(key)) {
            return ResponseEntity.ok(cueBrands.get(key));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy hãng gậy bida này.");
        }
    }
}
