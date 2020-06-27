package com.kakaopay.moneysplash.support;

public class Result<T> {
    private final boolean success;
    private final T payload;

    private Result(boolean success, T payload) {
        this.success = success;
        this.payload = payload;
    }

    /**
     * 성공인 결과 객체 생성. payload(반환값)은 없다.
     */
    public static <T> Result<T> success() {
        return new Result<T>(true, null);
    }

    /**
     * 성공인 결과 객체 생성. payload(반환값)을 지정한다.
     */
    public static <T> Result<T> success(T payload) {
        return new Result<T>(true, payload);
    }

    /**
     * 실패인 결과 객체 생성. payload(반환값)을 지정한다. 에러 메시지 등을 반환값으로 사용할 수 있다.
     */
    public static <T> Result<T> error(T payload) {
        return new Result<T>(false, payload);
    }

    /**
     * 실패인 결과 객체 생성. payload(반환값)을 지정하지 않는다.
     */
    public static <T> Result<T> error() {
        return new Result<T>(false, null);
    }

    /**
     * 성공 여부. {@link #isError()}와 반대 결과
     */
    public boolean isSuccess() {
        return this.success;
    }

    /**
     * 실패 여부. {@link #isSuccess()}와 반대 결과
     */
    public boolean isError() {
        return !this.success;
    }

    /**
     * 반환값을 얻는 메서드
     */
    public T payload() {
        return this.payload;
    }


}
