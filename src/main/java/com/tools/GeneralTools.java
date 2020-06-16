package com.tools;

import com.service.search.SearchCriteria;
import com.service.search.SearchCriteriaList;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

public class GeneralTools {
    public static String createRandom(String type, int targetStringLength) {
        int leftLimit = 0;
        int rightLimit = 0;
        switch (type) {
            case "number":
                leftLimit = 49;// letter '1'
                rightLimit = 57;// letter '9'
                break;
            case "lowercase":
                leftLimit = 97;
                rightLimit = 122;
                break;
            case "uppercase":
                leftLimit = 65;
                rightLimit = 90;
                break;
            case "password":
                return createRandom("lowercase", 3) +
                        createRandom("number", 1) +
                        createRandom("uppercase", 3);
            default:
                break;
        }

        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

    public static <R extends SearchCriteriaList> SearchCriteriaList convertToCriteriaList(R  test, String... except) {
        List<String> notInclude = new ArrayList<>() ;
        notInclude.addAll(Arrays.asList(except));
        notInclude.add("serialVersionUID");
        //------------
        SearchCriteriaList search = new SearchCriteriaList(test.getPage(), test.getSize(), test.getSort());
        HashSet<SearchCriteria> filter = new HashSet<>();
        //------------
        for (Field f : test.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.get(test) instanceof String || f.get(test) instanceof Long || f.get(test) instanceof Integer || f.get(test) instanceof Boolean) {
                    String value = f.get(test).toString();
                    String name = f.getName();
                    //------------
                    if (!notInclude.contains(name) && !StringUtils.isEmpty(name) && !StringUtils.isEmpty(value)) {
                        if (f.get(test) instanceof String) {
                            if (name.startsWith("start") && value.contains("/") && value.length() == 10) {
                                //filter.add(new SearchCriteria(name.substring(5, 6).toLowerCase().concat(name.substring(6)), ">", GeneralUtilityService.shamsiTomailadiByTime(value, "00:00:01")));
                            } else if (name.startsWith("end") && value.contains("/") && value.length() == 10) {
                           //     filter.add(new SearchCriteria(name.substring(3, 4).toLowerCase().concat(name.substring(4)), "<", GeneralUtilityService.shamsiTomailadiByTime(value, "23:59:59")));
                            } else {
                                filter.add(new SearchCriteria(name, ":", value));
                            }
                        } else {
                            filter.add(new SearchCriteria(name, ":", value));
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        search.setSearch(filter);
        return search;
    }
}
