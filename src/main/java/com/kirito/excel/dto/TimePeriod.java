package com.kirito.excel.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author kirito
 * @date 2023-08-30 11:02:31
 * @desc ...
 */
@Data
public class TimePeriod implements Serializable {

    @DateTimeFormat
    private LocalDate startTime;

    @DateTimeFormat
    private LocalDate endTime;

}
