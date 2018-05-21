package com.example.currencyExchange.api.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Empty Response")
public class NoContentException extends Exception {
}
