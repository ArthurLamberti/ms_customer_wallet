package com.arthurlamberti.customerwallet;

import com.arthurlamberti.customerwallet.infrastructure.config.ObjectMapperConfig;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test-integration")
@WebMvcTest
@Import(ObjectMapperConfig.class)
@Tags(value = {@Tag("integrationTest"), @Tag("controllerTest")})
public @interface ControllerTest {

    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
    Class<?>[] controllers() default {};
}
