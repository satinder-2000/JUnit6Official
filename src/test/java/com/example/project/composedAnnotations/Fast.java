package com.example.project.composedAnnotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import org.junit.jupiter.api.Tag;

//@Target({ElementType.TYPE, ElementType.METHOD})
//@Retention(RetentionPolicy.RUNTIME)
@Tag("fast")
public interface Fast {

}
