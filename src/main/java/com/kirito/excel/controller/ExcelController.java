package com.kirito.excel.controller;

import com.kirito.excel.base.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/test")
@Tag(name = "test")
public class ExcelController {

    @Operation(summary = "test0")
    @GetMapping("/test0")
    public R<?> test0() {
        log.info("test0");
        return R.success();
    }

}
