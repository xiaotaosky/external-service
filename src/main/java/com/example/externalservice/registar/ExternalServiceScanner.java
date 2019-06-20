package com.example.externalservice.registar;

import com.example.externalservice.anotation.ExternalService;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class ExternalServiceScanner extends ClassPathBeanDefinitionScanner {
    public ExternalServiceScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    protected void registerFilters() {
        this.addIncludeFilter(new AnnotationTypeFilter(ExternalService.class));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        if (beanDefinition.getMetadata().hasAnnotation(ExternalService.class.getName())
                && beanDefinition.getMetadata().isInterface()
                && beanDefinition.getMetadata().isIndependent()) {
            return true;
        }
        return false;
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        for (BeanDefinitionHolder holder : beanDefinitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            MutablePropertyValues properties = definition.getPropertyValues();
            properties.add("className", definition.getBeanClassName());
            definition.setBeanClass(ExternalServiceAnnotationFactoryBean.class);
            // this.getRegistry().registerBeanDefinition(holder.getBeanName(), definition);
        }
        return beanDefinitions;
    }
}
