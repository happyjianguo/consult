package com.jkys.consult.shine.utils;

import com.jkys.consult.common.constants.Constants;
import com.jkys.consult.shine.response.SimpleResponse;

/**
 * 消息响应服务类
 *
 * @author xiecw
 * @date 2018/9/4
 */
public class ResponseServiceUtils {
    public static SimpleResponse success() {
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setSuccess(true);
        simpleResponse.setMsg(Constants.SUCCESS_MSG);
        return simpleResponse;
    }

    public static SimpleResponse failure() {
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setSuccess(false);
        simpleResponse.setMsg(Constants.FAILURE_MSG);
        return simpleResponse;
    }

    public static SimpleResponse invalidParams() {
        SimpleResponse simpleResponse = new SimpleResponse();
        simpleResponse.setSuccess(false);
        simpleResponse.setMsg(Constants.PARAM_INVALID_MSG);
        return simpleResponse;
    }
}
