package com.autoui.fwk.execution;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import com.autoui.fwk.annotations.Action;
import com.autoui.fwk.annotations.Step;
import com.autoui.fwk.core.BaseTest;
import com.autoui.fwk.core.PageObject;
import com.autoui.fwk.enums.MethodType;
import com.autoui.fwk.enums.ScreenshotMode;
import com.autoui.fwk.enums.TestStatus;
import com.autoui.fwk.exceptions.ActionExecutionException;
import com.autoui.fwk.exceptions.StepExecutionException;
import com.autoui.fwk.launcher.LauncherHeartBeat;
import com.autoui.fwk.reporting.ExecutionLogManager;
import com.autoui.fwk.reporting.Logger;
import com.autoui.fwk.reporting.ReportingTools;
import com.autoui.fwk.utils.AspectJUtils;
import com.autoui.fwk.utils.ThreadUtils;

/**
 * This class contains aspects for manipulating {@link Action} and {@link Step}
 * methods
 *
 * @author rama.bisht
 */
@Aspect
public class StepAndActionCompiler {

    /**
     * Aspect for {@link Action} methods. Pre and post method execution logic is
     * written here
     *
     * @param point {@link ProceedingJoinPoint} to get access to {@link Action} method
     * @return Result Result of execution
     */
    @Around("execution(* *(..)) && @annotation(com.autoui.fwk.annotations.Action)")
    public Object aroundActionCompilerAspect(ProceedingJoinPoint point){
        long startTime = new Date().getTime();
        ExecutionLogManager.logMethodStart(point, MethodType.ACTION);
        String methodNameWithArguments = AspectJUtils.getMethodNameWithArguments(point);

        if (BaseTest.getTestObject().getScreenshotModes().contains(ScreenshotMode.BEFORE_ACTION))
            ReportingTools.takeScreenshot(ScreenshotMode.BEFORE_ACTION.name());

        Object result = null;
        TestStatus testStatus = TestStatus.PASS;
        Throwable t = null;


        try {
            ThreadUtils.sleepFor((int) BaseTest.getTestObject().getSlowdownDurationInSeconds());
            result = point.proceed();
        } catch (Throwable e) {
            testStatus = TestStatus.FAIL;
            boolean retry = ExceptionManager.shouldPerformActionRetry(e);
            if (ExceptionManager.isStaleElementReferenceException(e) && point.getThis() instanceof PageObject)
                retry = ((PageObject) point.getThis()).reInitializePageObject();
            if (retry) {
                try {
                    Logger.getLogger(MethodSignature.class.cast(point.getSignature()).getDeclaringType()).info("Retrying action");
                    result = point.proceed();
                    testStatus = TestStatus.PASS;
                } catch (Throwable ee) {
                    t = ee;
                    throw new ActionExecutionException(methodNameWithArguments, ee);
                }
            } else {
                t = e;
                throw new ActionExecutionException(methodNameWithArguments, e);
            }
        } finally {
            ExecutionLogManager.logMethodEnd(point, MethodType.ACTION, testStatus, new Date().getTime() - startTime,
                    result, t);
        }

        if (BaseTest.getTestObject().getScreenshotModes().contains(ScreenshotMode.AFTER_ACTION))
            ReportingTools.takeScreenshot(ScreenshotMode.AFTER_ACTION.name());

        IterationManager.getManager().checkIfTestDurationExceeded();
        LauncherHeartBeat.getManager().ping();

        return result;

    }

    /**
     * Aspect for {@link Step} methods. Pre and post method execution logic is
     * written here
     *
     * @param point {@link ProceedingJoinPoint} to get access to {@link Step} method
     * @return Result Result of execution
     */
    @Around("execution(* *(..)) && @annotation(com.autoui.fwk.annotations.Step)")
    public Object aroundStepCompilerAspect(ProceedingJoinPoint point) {
        LauncherHeartBeat.getManager().ping();
        long startTime = new Date().getTime();
        ExecutionLogManager.logMethodStart(point, MethodType.STEP);
        String methodNameWithArguments = AspectJUtils.getMethodNameWithArguments(point);

        if (BaseTest.getTestObject().getScreenshotModes().contains(ScreenshotMode.BEFORE_STEP))
            ReportingTools.takeScreenshot(ScreenshotMode.BEFORE_STEP.name());

        Object result = null;
        TestStatus testStatus = TestStatus.PASS;
        Throwable t = null;
        try {
            result = point.proceed();
        } catch (Throwable e) {
            testStatus = TestStatus.FAIL;
            t = e;
            throw new StepExecutionException(methodNameWithArguments, e);
        } finally {
            ExecutionLogManager.logMethodEnd(point, MethodType.STEP, testStatus, new Date().getTime() - startTime,
                    result, t);
        }

        if (BaseTest.getTestObject().getScreenshotModes().contains(ScreenshotMode.AFTER_STEP))
            ReportingTools.takeScreenshot(ScreenshotMode.AFTER_STEP.name());

        return result;
    }
}