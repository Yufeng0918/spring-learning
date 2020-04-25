package com.bp.spring.ioc.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 09:16
 * @Description:
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

//        importingClassMetadata.get
        return new String[]{"com.bp.spring.ioc.s01.bean.entities.Blue", "com.bp.spring.ioc.s01.bean.entities.Yellow", "com.bp.spring.ioc.s01.bean.entities.Red"};
    }
}
