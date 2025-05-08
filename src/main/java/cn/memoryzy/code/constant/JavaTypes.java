package cn.memoryzy.code.constant;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author Memory
 * @since 2025/4/30
 */
public interface JavaTypes {

    String[] COLLECTION_FQN = {
            Iterable.class.getName(),
            Collection.class.getName(),
            List.class.getName(),
            Set.class.getName(),
            ArrayList.class.getName(),
            LinkedList.class.getName()
    };

    String[] BIG_DECIMAL_FQN = {BigDecimal.class.getName()};

    String[] DATE_TIME_FQN = {Date.class.getName(), LocalDateTime.class.getName()};

    String[] DATE_FQN = {LocalDate.class.getName()};

    String[] TIME_FQN = {LocalTime.class.getName(), Time.class.getName()};

}
