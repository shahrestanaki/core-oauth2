package com.tools;

import java.util.Collection;
import java.util.List;

public interface ICustomMapper {
    <T> T onlyMap(Object var1, Class<T> var2);

    <T> Collection<T> mapCollection(Collection<?> var1, Class<T> var2);

    <T> List<T> mapList(Collection<?> var1, Class<T> var2);
}
