package com.mks.prometheus;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.function.ToDoubleFunction;

public class DemoGauge {

    public DemoGauge(MeterRegistry registry) {

        Oscillator oscillator = new Oscillator();
        Gauge
                .builder("demoservice_gauge", oscillator,oscillator)
                .description("a simple gauge")
                .register(registry);
    }


    private static class Oscillator implements ToDoubleFunction<Object>{

        private static int AMPLITUDE = 10;

        private boolean increase = true;
        private int currentValue = 0;

        @Override
        public double applyAsDouble(Object value) {
            currentValue = currentValue + (increase ? 1 :-1);
            if (Math.abs(currentValue) >= AMPLITUDE){
                increase =! increase;
            }
            int result = Math.abs(currentValue);
            return result;
        }
    }
}
