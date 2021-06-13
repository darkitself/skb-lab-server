package com.test.skblabserver.verifier;

import lombok.Data;

import java.io.Serializable;

/**
 * Класс, хранящий информацию о результате верификации
 */
@Data
public class VerificationResult implements Serializable {
    private Status status;

    public static VerificationResult withStatus(Status status){
        var result = new VerificationResult();
        result.setStatus(status);
        return result;
    }
}
