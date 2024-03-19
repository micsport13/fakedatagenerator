package com.fakedatagenerator.generators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import lombok.extern.log4j.Log4j2;
import net.datafaker.Faker;

@Log4j2
public class FakerMethodFactory {
  private static final Map<String, Method> PROVIDER_MAP =
      new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

  private static Method getProviderByName(Faker faker, String methodName) {
    if (PROVIDER_MAP.isEmpty()) {
      for (Method method : faker.getClass().getMethods()) {
        PROVIDER_MAP.put(method.getName(), method);
      }
    }
    if (!PROVIDER_MAP.containsKey(methodName)) {
      log.error("Unable to locate provider: {}", methodName);
      throw new RuntimeException("Unable to locate provider: " + methodName);
    }
    return PROVIDER_MAP.get(methodName);
  }

  private static Object invokeProvider(Faker faker, String providerName) {
    try {
      return getProviderByName(faker, providerName).invoke(faker);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException("Unable to locate provider: " + providerName, e);
    }
  }

  public static Object invokeMethod(
      Faker faker, String providerName, String methodName, Object... args) throws RuntimeException {
    try {
      Object provider = invokeProvider(faker, providerName);
      Class<?>[] argTypes = new Class[args.length];
      for (int i = 0; i < args.length; i++) {
        argTypes[i] = args[i].getClass();
      }
      Method[] methods = provider.getClass().getDeclaredMethods();
      List<Method> eligibleMethods = new ArrayList<>();
      for (Method method : methods) {
        if (method.getName().equalsIgnoreCase(methodName)) {
          eligibleMethods.add(method);
        }
      }
      for (Method method : eligibleMethods) {
        if (method.getParameterCount() == 0 && args.length == 0) {
          return method.invoke(provider);
        }
        if (method.getParameterCount() == args.length
            && checkMethodCompatibility(method, argTypes)) {
          return method.invoke(provider, args);
        }
      }
      throw new NoSuchMethodException(
          "Unable to locate method: "
              + methodName
              + " compatible with args: "
              + Arrays.toString(args));
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private static boolean checkMethodCompatibility(Method method, Class<?>[] argTypes) {
    for (int i = 0; i < argTypes.length; i++) {
      if (!method.getParameterTypes()[i].isAssignableFrom(argTypes[i])
          && !method.getParameterTypes()[i].isPrimitive()) {
        return false;
      }
    }
    return true;
  }

  public static Method getMethod(
      Faker faker, String providerName, String methodName, Object... args) {
    try {
      Object provider = getProviderByName(faker, providerName);
      return provider.getClass().getMethod(methodName, args.getClass());
    } catch (NoSuchMethodException e) {
      log.error("Unable to find method {} with arguments {}", methodName, args);
      throw new RuntimeException(e);
    }
  }
}
