package top.sillyfan.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import top.sillyfan.api.response.code.ApiErrorCodeDef;

/**
 * API 返回信息
 *
 * @param <T>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {


    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public static <T> ApiResponse success() {
        return new ApiResponse<>(ApiErrorCodeDef.SUCCESS, "成功", null);
    }

    public static <T> ApiResponse success(T data) {
        return new ApiResponse<>(ApiErrorCodeDef.SUCCESS, "成功", data);
    }

    public static <T> ApiResponse error(ApiErrorCodeDef code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public static <T> ApiResponse error(ApiErrorCodeDef code) {
        return new ApiResponse<>(code, code.getMessage(), null);
    }

    private ApiResponse(ApiErrorCodeDef code, String message, T data) {
        this.code = Integer.valueOf(code.getCode());
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
