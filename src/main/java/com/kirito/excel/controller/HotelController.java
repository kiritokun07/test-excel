package com.kirito.excel.controller;

import com.kirito.excel.base.R;
import com.kirito.excel.logic.HotelLogic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 2 hotel
 * @author kirito
 * @since 2024-06-10 21:26:21
 */
@RestController
@Slf4j
@RequestMapping("/hotel")
@Tag(name = "2 hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelLogic hotelLogic;

    @Operation(summary = "test0")
    @GetMapping("/test0")
    public R<?> test0() {
        log.info("test0");
        return R.success();
    }

}
