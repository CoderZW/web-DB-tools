package com.homethy.exception;

public class MsgException extends RuntimeException {

  public static final String DEFAULT_TYPE_ERROR = "error";

  private String type = DEFAULT_TYPE_ERROR;

  private static final long serialVersionUID = -6919872844753340832L;

  private boolean toLogin;

  public MsgException(Throwable cause, String msg, Object... args) {
    super(format(msg, args), cause);
  }


  public MsgException(String msg, Object... args) {
    super(format(msg, args));
  }

  private static String format(String msg, Object[] args) {
    return args.length == 0 ? msg : String.format(msg, args);
  }

  public String getType() {
    return type;
  }

  public MsgException info() {
    this.type = "info";
    return this;
  }

  public MsgException warning() {
    this.type = "warning";
    return this;
  }
  public MsgException toLogin(){
    this.toLogin=true;
    return this;
  }
  public boolean isToLogin(){
    return this.toLogin;
  }

}
