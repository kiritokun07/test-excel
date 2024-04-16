package com.kirito.excel.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kirito.excel.base.R;
import com.kirito.excel.domain.BigData;
import com.kirito.excel.dto.BigDataDto;
import com.kirito.excel.mapstruct.BigDataConverter;
import com.kirito.excel.service.IBigDataService;
import com.kirito.excel.utils.ExportUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * 1 excel
 */
@RestController
@Slf4j
@RequestMapping("/excel")
@Tag(name = "1 excel")
@RequiredArgsConstructor
public class ExcelController {

    private final IBigDataService iBigDataService;
    private final BigDataConverter bigDataConverter;

    @Operation(summary = "test0")
    @GetMapping("/test0")
    public R<?> test0() {
        log.info("test0");
        return R.success();
    }

    @Operation(summary = "test1")
    @GetMapping("/test1")
    public R<List<BigData>> test1() {
        log.info("test1");
        return R.success(iBigDataService.list());
    }

    @Operation(summary = "page")
    @GetMapping("/page")
    public R<IPage<BigData>> page(@RequestParam("page") Integer page,
                                  @RequestParam("size") Integer size) {
        log.info("page");
        return R.success(iBigDataService.page(new Page<>(page, size)));
    }

    @Operation(summary = "generateData")
    @GetMapping("/generateData")
    public R<?> generateData() {
        List<BigData> saveList = new ArrayList<>();
        BigData saveModel;
        for (int i = 20_0000; i <= 30_0000; i++) {
            saveModel = new BigData();
            saveModel.setName("A" + i);
            saveModel.setAge(i);
            saveModel.setDetail("hhhhhhhhhhh" + i);
            saveList.add(saveModel);
        }
        iBigDataService.saveBatch(saveList);
        return R.success();
    }

    @Operation(summary = "export1")
    @GetMapping("/export1")
    public R<?> export1() {
        log.info("查询开始");
        List<BigData> bigDataList = iBigDataService.lambdaQuery()
                .last("limit 10000")
                .list();
        log.info("查询结束");
        List<BigDataDto> bigDataDtoList = bigDataConverter.bigData2DtoList(bigDataList);
        log.info("转换结束");
        Instant start = Instant.now();
        String destPath = geneDestPath();
//        EasyExcel.write(destPath, BigDataDto.class)
//                .sheet()
//                .registerConverter(new MyLocalDateTimeConverter())
//                // 设置字段宽度为自动调整，不太精确
//                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
//                .doWrite(bigDataDtoList);
        ExportUtil.export(destPath, BigDataDto.class, bigDataDtoList);
        log.info("导出结束");
        Instant end = Instant.now();
        Duration executionDuration = Duration.between(start, end);
        System.out.println("执行用时: " + executionDuration.toMillis() + " 毫秒");
        return R.success();
    }

    private static String geneDestPath() {
        return "C:\\Users\\Administrator\\Desktop\\bigData-" + System.currentTimeMillis() / 1000 + ".xlsx";
    }

    @Operation(summary = "export2")
    @GetMapping("/export2")
    public R<?> export2() {
        long total = iBigDataService.count();
        String destPath = geneDestPath();
        log.info("导出开始");
        Instant start = Instant.now();
        Runnable startFunc = () -> System.out.println("开始导出");
        BiFunction<Integer, Integer, Collection<BigDataDto>> exportFunc = (cursor, size) -> {
            log.info("page limit {},{}", cursor, size);
            return bigDataConverter.bigData2DtoList(iBigDataService.lambdaQuery()
                    .last(StrUtil.format("limit {},{}", cursor, size))
                    .list()
            );
        };
        BiConsumer<Integer, Integer> noticeFunc = (cursor, myTotal) -> System.out.println("导出百分比= " + cursor * 1.0 / myTotal);
        Runnable endFunc = () -> System.out.println("结束导出");
        ExportUtil.export2(destPath, BigDataDto.class, (int) total, 3000, startFunc,
                exportFunc, noticeFunc, endFunc);
        log.info("导出结束");
        Instant end = Instant.now();
        Duration executionDuration = Duration.between(start, end);
        System.out.println("执行用时: " + executionDuration.toMillis() + " 毫秒");
        return R.success();
    }

}
