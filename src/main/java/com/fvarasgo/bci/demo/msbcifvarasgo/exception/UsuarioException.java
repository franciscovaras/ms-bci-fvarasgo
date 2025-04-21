package com.fvarasgo.bci.demo.msbcifvarasgo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

public class UsuarioException extends RuntimeException {

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class BusquedaItem extends RuntimeException {
        private final transient Map<String, Object> map;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class CrearSolicitud extends RuntimeException {
        public CrearSolicitud(Throwable cause) {
            super(cause);
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ErrorSolicitud extends RuntimeException {
        private final transient Map<String, String> map;

        public ErrorSolicitud(String key, String message) {
            super(message);
            this.map = Map.of(key, message);
        }

        public ErrorSolicitud(Map<String, String> map) {
            super(map.values().stream().findFirst().orElse("Error en la solicitud"));
            this.map = map;
        }
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class ErrorMail extends RuntimeException {
        private final transient Map<String, String> map;
    }
}
