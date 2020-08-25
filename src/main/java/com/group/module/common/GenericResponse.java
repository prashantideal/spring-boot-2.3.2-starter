package com.group.module.common;

import lombok.experimental.UtilityClass;

import java.text.MessageFormat;

@UtilityClass
public class GenericResponse {

    public static <T> ApiResponse<T> createSuccessResponse(T apiResponse, String code, String status, String comment, Object... placeHolderValues) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setResponse(apiResponse);
        response.setSuccess(true);
        return response;
    }
    
    public static <T> ApiResponse<T> createSuccessResponse(T apiResponse) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setResponse(apiResponse);
        response.setSuccess(true);
        return response;
    }

    public static <T> ApiResponse<T> createErrorResponse(String errorCode, Object... placeHolderValues) {
        ApiResponse<T> response = new ApiResponse<>();
        Error error = new Error();
        error.setCode(Integer.parseInt(errorCode));
        error.setMessage(replacePlaceHolders(ExceptionMessageHolder.getMessage(errorCode), placeHolderValues));
        error.setStatusCode(ExceptionStatusCodeHolder.getCode(errorCode));
        response.setError(error);
        response.setSuccess(false);
        return response;
    }

    private static String replacePlaceHolders(String message, Object... values) {
        return MessageFormat.format(message, values);
    }
}