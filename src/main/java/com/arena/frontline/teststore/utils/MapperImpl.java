package com.arena.frontline.teststore.utils;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

public class MapperImpl extends DozerBeanMapper implements Mapper {

  public <T, U> ArrayList<U> map(final List<T> source,
      final Class<U> destType) {

    final ArrayList<U> dest = new ArrayList<U>();

    for (T element : source) {
      if (element == null) {
        continue;
      }
      dest.add(map(element, destType));
    }

    List s1 = new ArrayList();
    s1.add(null);
    dest.removeAll(s1);

    return dest;
  }

}
