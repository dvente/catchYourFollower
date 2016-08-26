package vangDeVolger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum DirectionEnum {
    //This allows us, for example, to refer to "going right" by using "DirectionEnum.RIGHT".
    //We also have some code that allow us to "random" steps (as in: the direction is random).

    NONE, LEFT, RIGHT, UP, DOWN;

    private static final List<DirectionEnum> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static DirectionEnum randomDirection() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
