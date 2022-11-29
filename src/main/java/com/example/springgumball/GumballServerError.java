package com.example.springgumball;

import org.springframework.http.HttpStatus ;
import org.springframework.web.bind.annotation.ResponseStatus ;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Invalid Request")  // 500
public class GumballServerError extends RuntimeException { }