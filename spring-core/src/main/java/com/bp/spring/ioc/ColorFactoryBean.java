package com.bp.spring.ioc;

import com.bp.spring.ioc.entities.Color;
import org.springframework.beans.factory.FactoryBean;

/**
 * @Auther: daiyu
 * @Date: 25/4/20 09:45
 * @Description:
 */
public class ColorFactoryBean implements FactoryBean<Color> {
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
