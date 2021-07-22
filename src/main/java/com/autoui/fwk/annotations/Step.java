package com.autoui.fwk.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.autoui.fwk.core.WebTestSteps;
import com.autoui.fwk.core.UnitTestSteps;
import com.autoui.fwk.core.WebServiceTestSteps;

/**
 * This annotation marks a method as Step method. Step methods are methods
 * defined in Step classes (classes which inherit {@link WebTestSteps},
 * {@link UnitTestSteps} or {@link WebServiceTestSteps})
 * 
 * @author rama.bisht
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Step {

}
