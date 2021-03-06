package com.autoui.fwk.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.autoui.fwk.core.PageObject;

/**
 * This annotation marks a method as Action method. Action methods are methods
 * defined in Page/View classes (classes which inherit {@link PageObject })
 * 
 * @author rama.bisht
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

}
