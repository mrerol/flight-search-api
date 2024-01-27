package com.fsa.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelUtils {

  private static final Logger logger = LoggerFactory.getLogger(ModelUtils.class);
  private static final ModelMapper modelMapper = new ModelMapper();

  static {
    modelMapper.getConfiguration().setSkipNullEnabled(true);
  }

  public static <T> T convert(Object entity, Class<T> theClass) {
    return modelMapper.map(entity, theClass);
  }
}
