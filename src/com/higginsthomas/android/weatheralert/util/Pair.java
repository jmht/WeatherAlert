package com.higginsthomas.android.weatheralert.util;


public class Pair<T1, T2> {
  public T1 x;
  public T2 y;
  public Pair(T1 t1, T2 t2) {
    this.x = t1;
    this.y = t2;
  }
  @Override
  public String toString() {
    return "<" + x.toString() + ", " + y.toString() + ">";
  }
}
