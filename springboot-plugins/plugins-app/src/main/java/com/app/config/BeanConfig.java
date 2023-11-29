package com.app.config;

import com.app.loader.HotClassLoader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig {

    /**
     * 配置成原型（多例），主要是为了更新jar时，使用新的类加载器实例去加载
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public HotClassLoader hotClassLoader() {
        return new HotClassLoader(this.getClass().getClassLoader());
    }
}
