package com.bharatkhoj.metasearch.domain;

public class GoogleSearchException extends Exception
{
  public GoogleSearchException()
  {
    super();
  }

  public GoogleSearchException(Exception e)
  {
    super(e);
  }

  public GoogleSearchException(String message)
  {
    super(message);
  }
}