package com.bp.spring.registry;

import com.bp.spring.registry.condition.MyImportSelector;
import com.bp.spring.registry.domain.Color;
import com.bp.spring.registry.domain.Red;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//配置类==配置文件
@Configuration  //告诉Spring这是一个配置类
@Import({MyImportSelector.class})
public class ImportSelectorConfig {

}
