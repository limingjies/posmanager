package com.allinfinance.exception;

/**
 * <p>Title: Hermes -- Post MIS/DSS System</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Shanghai allinfinance Co., Ltd.</p>
 * @author Charles Zhang
 * @version 3.0
 */

public class AllinfinanceException extends Exception {

  static final long serialVersionUID = -2398001218377041500L;

public AllinfinanceException() {
  }

  public AllinfinanceException(String message) {
    super(message);
  }

  public AllinfinanceException(String message, Throwable cause) {
    super(message, cause);
  }

  public AllinfinanceException(Throwable cause) {
    super(cause);
  }
}