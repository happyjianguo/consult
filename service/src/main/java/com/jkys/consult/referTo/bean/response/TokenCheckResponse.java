package com.jkys.consult.referTo.bean.response;

/**
 * token校验
 * @author yangzh
 * @since 2018/5/14
 **/

public class TokenCheckResponse extends BaseResponse {

    /**
     * 用户id
     */
    private Long  userId;
    /**
     * 生成的token
     */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
