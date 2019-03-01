package fi.vm.kapa.identification.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DummyDataDeviator {

    public static final String errorMsg = "dummydata.random.error";

    public double[] delays;
    public int errorSleep;


    private static final Logger logger = LoggerFactory.getLogger(DummyDataDeviator.class);

    public DummyDataDeviator(long mean, double deviation, int errorPercent , int errorSleep) {
        Random random = new Random();
        delays = new double[100];
        for (int i = 0; i < delays.length; i++) {
            double delay = -1; // populate array up to errorPercent with error value
            if ( i >= errorPercent ) {
                delay = random.nextGaussian() * deviation + mean;
                if ( delay < 0 ) {
                    delay = 0;
                }
            }
            delays[i] = delay;
        }
        this.errorSleep = errorSleep;
    }

    public long getDelay() throws DummyDataDeviatorException {
        int i = ThreadLocalRandom.current().nextInt(0, 100);
        double delay = delays[i];
        logger.debug("DummyDataDeviator returning delay: " + delay + "ms");
        if ( delay < 0 ) {
            logger.debug("DummyDataDeviator returns error!");
            throw new DummyDataDeviatorException(errorMsg);
        }
        return Math.round(delay);
    }
    
    public long getErrorSleep() {
        return errorSleep;
    }
}
