package io.caojun221.examples.concurrent;

import static junit.framework.TestCase.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class ExceptionHandlingTest {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Test
    public void testCustomException() throws Exception {

        Callable<Void> task = () -> {
            throw new CustomException("");
        };

        Future<Void> future = executorService.submit(task);
        try {
            future.get();
        }catch (ExecutionException e) {
            assertTrue(e.getCause() instanceof CustomException);
        }
    }

    private static class CustomException extends RuntimeException {
        private static final long serialVersionUID = -1086589627017180498L;

        private CustomException(String s) {
            super(s);
        }
    }
}
