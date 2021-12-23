package study.tdd.simpleboard.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 다국어처리시 관리를 용이하도록 properties 파일의 인코딩을 UTF-8로 지정하는 설정 클래스입니다.
 * [참고사항] 인텔리제이 IDE를 사용하는 경우:
 * File > Settings > Editor > File Encoding > Properties File 인코딩 방식을 UTF-8로 설정하시기 바랍니다.
 *
 * @author Informix
 * @created 2021-12-23
 * @since 2.6.1 spring boot
 * @since 0.0.1 dev
 */
@Configuration
public class MessagePropertiesEncodingConfig implements WebMvcConfigurer {

    /*
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages/message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    */

    @Bean
    public MessageSource validationMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages/validation");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(validationMessageSource());
        return bean;
    }

}