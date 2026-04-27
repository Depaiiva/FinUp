package br.com.finup.core;

import org.springframework.http.HttpStatusCode;

public record RestErroMessage(String title, HttpStatusCode status, String detail) {

}
