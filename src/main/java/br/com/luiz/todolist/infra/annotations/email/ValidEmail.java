package br.com.luiz.todolist.infra.annotations.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Email.class)
public @interface ValidEmail {
    String message() default "email inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}