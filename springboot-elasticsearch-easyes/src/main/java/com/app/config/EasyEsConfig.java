package com.app.config;

import cn.easyes.starter.register.EsMapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * es config
 */
@Configuration
@EsMapperScan("com.app.mapper")
public class EasyEsConfig {
}
