package com.arena.frontline.teststore.utils;

import java.util.ArrayList;
import java.util.List;

public interface Mapper extends org.dozer.Mapper {

  <T, U> ArrayList<U> map(final List<T> source,
                          final Class<U> destType);
}
