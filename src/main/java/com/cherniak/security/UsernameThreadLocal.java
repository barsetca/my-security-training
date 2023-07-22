package com.cherniak.security;

import java.util.function.Supplier;

public class UsernameThreadLocal {

  private static final ThreadLocal<String> stringThreadLocal = ThreadLocal.withInitial(() -> "defaultUser");

  public static String getUsername(){
    return stringThreadLocal.get();
  }

  public static void setUsername(String username){
    stringThreadLocal.set(username);
  }

  public static void clear(){
    stringThreadLocal.remove();
  }

}
