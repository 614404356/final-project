package com.dasi.graduation.finalproject.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yulong
 */
@JsonInclude(Include.NON_NULL)
public class ResponseEnvelope<T> {

    @Getter
    private T data;

    @Getter
    private long total;

    @Getter
    private int status;

    @Getter
    private String message;

    @Getter
    private PaginationInfo pagination;
    @Getter
    private RestApiError result;

    public ResponseEnvelope() {
        this(null, null);
    }

    public ResponseEnvelope(T data) {
        this(data, null);
    }

    public ResponseEnvelope(T data, PaginationInfo pagination) {
        this.data = data;
        this.pagination = pagination;
        if(pagination != null){
            total=pagination.getTotalCount();
        }

        result = new RestApiError();
        result.setStatus(HttpStatus.OK.value());
        result.setCode(RestApiError.CODE_OK);
        result.setMessage(RestApiError.MESSAGE_SUCCEED);

        message= RestApiError.MESSAGE_SUCCEED;
        status= HttpStatus.OK.value();

        Map<String, Object> defaultMap = new HashMap<>();
        defaultMap.put(RestApiError.DEFAULT_ERROR_PARAMS, RestApiError.DEFAULT_ERROR_PARAMS);
        result.setErrorParams(defaultMap);
    }

    public ResponseEnvelope(RestApiError error) {
        this.result = error;
        message=error.getMessage();
        status=error.getStatus();
    }

}
