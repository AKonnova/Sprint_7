package constants;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomData {

    public static final String RANDOM_LOGIN = RandomStringUtils.randomAlphabetic(10);
    public static final String RANDOM_PASSWORD = RandomStringUtils.randomNumeric(12);
    public static final String RANDOM_NAME = RandomStringUtils.randomAlphabetic(8);
}