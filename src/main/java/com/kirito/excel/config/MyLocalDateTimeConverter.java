package com.kirito.excel.config;

import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.LocalDateTime;

/**
 * easyexcel 写入LocalDateTime转换String
 */
public class MyLocalDateTimeConverter implements Converter<LocalDateTime> {

	//private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Override
	public Class<LocalDateTime> supportJavaTypeKey() {
		return LocalDateTime.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	/**
	 * DatePattern.NORM_DATETIME_FORMATTER yyyy-MM-dd HH:mm:ss
	 */
	@Override
	public LocalDateTime convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty,
										   GlobalConfiguration globalConfiguration) {
		return LocalDateTime.parse(cellData.getStringValue(), DatePattern.NORM_DATETIME_FORMATTER);
	}

	@Override
	public WriteCellData<?> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty,
											   GlobalConfiguration globalConfiguration) {
		return new WriteCellData<>(value.format(DatePattern.NORM_DATETIME_FORMATTER));
	}

}