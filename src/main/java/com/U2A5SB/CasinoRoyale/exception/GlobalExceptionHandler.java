package com.U2A5SB.CasinoRoyale.exception;

import com.U2A5SB.CasinoRoyale.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404: recursos que no existen
    @ExceptionHandler({JugadorNoEncontradoException.class, PartidaNoEncontradaException.class})
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(RuntimeException ex) {
        return construir(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    // 409: reglas de negocio que no se cumplen
    @ExceptionHandler({JugadorInactivoException.class,
            SaldoInsuficienteException.class,
            PartidaNoEnJuegoException.class})
    public ResponseEntity<ErrorResponse> manejarReglaDeNegocio(RuntimeException ex) {
        return construir(HttpStatus.CONFLICT, ex.getMessage(), null);
    }

    // 400: fallan las validaciones de @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> detalles = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> detalles.put(error.getField(), error.getDefaultMessage()));
        return construir(HttpStatus.BAD_REQUEST, "Error de validación en los datos enviados", detalles);
    }

    // 400: JSON mal formado o tipos incorrectos en el body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return construir(HttpStatus.BAD_REQUEST, "El cuerpo de la petición es inválido o está mal formado", null);
    }

    // 400: id con formato incorrecto en la URL (por ejemplo /jugadores/abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> manejarTipoIncorrecto(MethodArgumentTypeMismatchException ex) {
        return construir(HttpStatus.BAD_REQUEST, "El parámetro '" + ex.getName() + "' tiene un formato inválido", null);
    }

    // 500: cualquier error inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGenerico(Exception ex) {
        return construir(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", null);
    }

    private ResponseEntity<ErrorResponse> construir(HttpStatus status, String mensaje, Map<String, String> detalles) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                mensaje,
                detalles
        );
        return ResponseEntity.status(status).body(body);
    }
}