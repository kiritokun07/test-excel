package com.kirito.excel.controller;

import com.kirito.excel.base.R;
import com.kirito.excel.domain.HotelDoc;
import com.kirito.excel.dto.HotelListReq;
import com.kirito.excel.dto.PageVO;
import com.kirito.excel.logic.HotelLogic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "hotelList")
    @PostMapping("/list")
    public R<PageVO<HotelDoc>> hotelList(@RequestBody @Validated HotelListReq req) {
        log.info("hotelList");
        return hotelLogic.hotelList(req);
    }

}
