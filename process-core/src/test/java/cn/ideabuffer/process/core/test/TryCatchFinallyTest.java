package cn.ideabuffer.process.core.test;

import cn.ideabuffer.process.core.exception.IllegalCatchGrammarException;
import cn.ideabuffer.process.core.nodes.TryCatchFinallyNode;
import cn.ideabuffer.process.core.processors.impl.TryCatchFinallyProcessorImpl;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author sangjian.sj
 * @date 2020/06/09
 */
public class TryCatchFinallyTest {

    @Test(expected = IllegalCatchGrammarException.class)
    public void grammarTest() throws Throwable {
        List<TryCatchFinallyNode.CatchMapper> catchMapperList = new LinkedList<>();

        catchMapperList.add(new TryCatchFinallyNode.CatchMapper(Exception.class, null));
        catchMapperList.add(new TryCatchFinallyNode.CatchMapper(NullPointerException.class, null));

        TryCatchFinallyProcessorImpl processor = new TryCatchFinallyProcessorImpl(null, null, null);
        Method method = TryCatchFinallyProcessorImpl.class.getDeclaredMethod("checkCatchGrammar", List.class);
        method.setAccessible(true);
        try {
            method.invoke(processor, catchMapperList);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test(expected = IllegalCatchGrammarException.class)
    public void grammarTest2() throws Throwable {
        List<TryCatchFinallyNode.CatchMapper> catchMapperList = new LinkedList<>();

        catchMapperList.add(new TryCatchFinallyNode.CatchMapper(NullPointerException.class, null));
        catchMapperList.add(new TryCatchFinallyNode.CatchMapper(Exception.class, null));
        catchMapperList.add(new TryCatchFinallyNode.CatchMapper(IllegalStateException.class, null));

        TryCatchFinallyProcessorImpl processor = new TryCatchFinallyProcessorImpl(null, null, null);
        Method method = TryCatchFinallyProcessorImpl.class.getDeclaredMethod("checkCatchGrammar", List.class);
        method.setAccessible(true);
        try {
            method.invoke(processor, catchMapperList);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test(expected = IllegalCatchGrammarException.class)
    public void grammarTest3() throws Throwable {
        List<TryCatchFinallyNode.CatchMapper> catchMapperList = new LinkedList<>();

        catchMapperList.add(new TryCatchFinallyNode.CatchMapper(NullPointerException.class, null));
        catchMapperList.add(new TryCatchFinallyNode.CatchMapper(NullPointerException.class, null));

        TryCatchFinallyProcessorImpl processor = new TryCatchFinallyProcessorImpl(null, null, null);
        Method method = TryCatchFinallyProcessorImpl.class.getDeclaredMethod("checkCatchGrammar", List.class);
        method.setAccessible(true);
        try {
            method.invoke(processor, catchMapperList);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    @Test
    public void grammarTest4() throws Throwable {
        List<TryCatchFinallyNode.CatchMapper> catchMapperList = new LinkedList<>();

        catchMapperList.add(new TryCatchFinallyNode.CatchMapper(NullPointerException.class, null));
        catchMapperList.add(new TryCatchFinallyNode.CatchMapper(Exception.class, null));

        TryCatchFinallyProcessorImpl processor = new TryCatchFinallyProcessorImpl(null, null, null);
        Method method = TryCatchFinallyProcessorImpl.class.getDeclaredMethod("checkCatchGrammar", List.class);
        method.setAccessible(true);
        try {
            method.invoke(processor, catchMapperList);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

}
