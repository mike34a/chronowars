package com.miksinouf.chronowars.domain.player;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You already have placed all your tokens.")
public class TooManyTokensException extends RuntimeException {
}
