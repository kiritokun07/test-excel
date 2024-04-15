package com.kirito.excel.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CodeGenerator {

    public static void main(String[] args) {
        DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig.Builder(
                "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai", "root", "root"
        );
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                .globalConfig(builder -> {
                    builder.author("kirito") // 设置作者
                            .disableOpenDir()
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(projectPath + "/src/main/java")
                            .commentDate("yyyy-MM-dd HH:mm:ss")
                            .dateType(DateType.TIME_PACK); // 指定输出目录
                })
                //.dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                //    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                //    if (typeCode == Types.SMALLINT) {
                //        // 自定义类型转换
                //        return DbColumnType.INTEGER;
                //    }
                //    return typeRegistry.getColumnType(metaInfo);
                //
                //}))
                .packageConfig(builder -> {
                    builder.parent("com.kirito.excel") // 设置父包名
                            //.moduleName("domain") // 设置父包模块名
                            .entity("domain")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig((scanner, builder) -> {
                    builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all"))) // 设置需要生成的表名
                            .controllerBuilder().enableRestStyle().enableHyphenStyle()
                            .entityBuilder().enableLombok().addTableFills(
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE)
                            ).enableFileOverride().build();
                })
                //.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split("[,，]"));
    }

}
