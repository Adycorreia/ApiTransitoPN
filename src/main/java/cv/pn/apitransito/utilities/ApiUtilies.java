package cv.pn.apitransito.utilities;



import java.util.Optional;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import cv.pn.apitransito.exceptions.RecordNotFoundException;

public class ApiUtilies {

    public static <T> Optional<T> checkResource(Optional<T> resource, String message) {
        if (!resource.isPresent()) {
            throw new RecordNotFoundException(message);
        }
        return resource;
    }
    public static int getRandomNumber(int min, int max) {
        Random random = new Random();

        return  random.nextInt(max - min) + min;
    }

    public static String getRandomAlphabetic(int limit) {
        return RandomStringUtils.randomAlphabetic(limit).toUpperCase();
    }

}
