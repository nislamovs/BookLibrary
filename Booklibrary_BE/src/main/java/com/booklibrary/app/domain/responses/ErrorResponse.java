package com.booklibrary.app.domain.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import static java.lang.String.format;

@Data
@AllArgsConstructor
@ApiModel
public class ErrorResponse {

    @ApiModelProperty(required = true, value = "Error text", example = "Validation exception - name is too short.")
    public String errorText;

    public ErrorResponse(String format, Object... args) {
        this(format(format, args));
    }
}


