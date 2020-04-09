package com.jkys.consult.infrastructure.rpc.chat;

import com.jkys.consult.common.constants.Constants;
import com.jkys.consult.shine.service.AppConfig;
import com.jkys.consult.shine.utils.DateUtil1;
import com.jkys.im.client.api.ChatMessageQueryApiService;
import com.jkys.im.client.api.ChatMessageSendApiService;
import com.jkys.im.client.bean.BodyRequest;
import com.jkys.im.client.bean.ChatApiResponse;
import com.jkys.im.client.bean.ChatMessageRequest;
import com.jkys.im.client.bean.UserSendAction;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author yangZh
 * @since 2018/7/17
 **/
@Service
@Slf4j
public class ChatMessageService {

    // TODO ---- 干掉 ------> todoByliming
    @Resource
    private AppConfig appConfig;
    @Resource
    private ChatMessageSendApiService chatMessageSendApiService;
    @Resource
    private ChatMessageQueryApiService chatMessageQueryApiService;

    private final ExecutorService service = new ThreadPoolExecutor(10, 20, 10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100), r -> new Thread(r, "chatMessage-"));
    // TODO ---- url待定 ------> todoByliming
    //  用订单表物理id
    private static final String RICH_ADDRESS = "/wechat-official-account/build/IMDetail/index.html?id=";
    // TODO ---- 图片url待定 ------> todoByliming
    private static final String RICH_IMG =
            "https://static.91jkys" + "" + ".com/uploadfile/45622229_1537496347492_fileUploads_1537496347343.jpg";


    /**
     * 咨询结束的im消息
     */
    public void sendFinishAdvisoryMessage(Long fromUid, Long toId, String text, String type) {
        sendFinishAdvisoryMessage(fromUid, toId, text, type, null, null);
    }

    public void sendFinishAdvisoryMessage(Long fromUid, Long toId, String text, String type, Long imGroupId) {
        sendFinishAdvisoryMessage(fromUid, toId, text, type, imGroupId, null);
    }

    public void sendFinishAdvisoryMessage(Long fromUid, Long toId, String text, String type, Long imGroupId,
                                          UserSendAction action) {
        service.submit(() -> {
            try {
                Thread.sleep(100L);//延迟发送，防止消息已达业务未跑完
                ChatMessageRequest request = new ChatMessageRequest();
                BodyRequest bodyRequest = new BodyRequest();
                bodyRequest.setText(text);
                request.setFrom(fromUid.toString());
                request.setOwnerId(fromUid.toString());
                request.setTo(toId.toString());
                request.setBody(bodyRequest);
                request.setModified(System.currentTimeMillis());
                request.setType(type);
                request.setGroupId(imGroupId);
                if (action != null) {
                    request.setAction(action);
                }
                chatMessageSendApiService.sendMessageFromAndTo(request);
            } catch (Exception e) {
                log.error("sendFinishAdvisoryMessage send error", e);
            }
        });
    }

    public Long queryLastMessageTimeStamp(Long doctorId, Long patientId) {
        try {
            ChatApiResponse response = chatMessageQueryApiService.queryLastMessageDate(doctorId, patientId);
            if (response != null && response.isSuccess() && StringUtils.isNotEmpty(response.getData())) {
                Date time = DateUtil1.parse(response.getData(), DateUtil1.PATTERN_NORMAL_YEAR2SECOND);
                return time != null ? time.getTime() : 0L;
            }
        } catch (Exception e) {
            log.error("queryLastMessageDate  error", e);
        }
        return 0L;
    }

    /**
     * 查询指定时间后的第一次聊天
     *
     * @param doctorId  医生
     * @param patientId 病人
     * @param dateTime  开始时间
     *
     * @return 聊天时间戳
     */
    public Long queryFirstMessageDateTime(Long doctorId, Long patientId, Date dateTime) {
        try {
            ChatApiResponse response = chatMessageQueryApiService.queryFirstMessageDateTime(doctorId, patientId, dateTime, null);
            if (response != null && response.isSuccess() && StringUtils.isNotEmpty(response.getData())) {
                Date time = DateUtil1.parse(response.getData(), DateUtil1.PATTERN_NORMAL_YEAR2SECOND);
                return time != null ? time.getTime() : 0L;
            }
        } catch (Exception e) {
            log.error("queryFirstMessageDateTime  error", e);
        }
        return 0L;
    }

    public void sendOrderMessage(Long fromUid, Long toId, Long infoId) {
        service.submit(() -> {
            sendOrderRichMessage(fromUid, toId, infoId);
            sendOrderBeginMessage(fromUid, toId);
        });
    }

    /**
     * im图文消息
     */
    private void sendOrderRichMessage(Long fromUid, Long toId, Long infoId) {
        ChatMessageRequest request = new ChatMessageRequest();
        try {
            BodyRequest bodyRequest = new BodyRequest();
            bodyRequest.setLinkTitle("图文咨询");
            bodyRequest.setLinkDetail("点击查看疾病信息和病情资料");
            bodyRequest.setLinkImg(RICH_IMG);
            bodyRequest.setLinkAddress(appConfig.getH5Domain() + RICH_ADDRESS + infoId);
            request.setFrom(fromUid.toString());
            request.setOwnerId(fromUid.toString());
            request.setTo(toId.toString());
            request.setBody(bodyRequest);
            request.setModified(System.currentTimeMillis());
            request.setType(Constants.IM_RICHLINK);
            chatMessageSendApiService.sendMessageFromAndTo(request);
        } catch (Exception e) {
            log.error("sendFinishAdvisoryMessage send error", e);
        }
    }

    /**
     * im 文本消息
     *
     * @param fromUid 发送人
     * @param toId    接收人
     *
     * @return Boolean
     */
    private Boolean sendOrderBeginMessage(Long fromUid, Long toId) {
        ChatMessageRequest request = new ChatMessageRequest();
        try {
            BodyRequest bodyRequest = new BodyRequest();
            bodyRequest.setText(Constants.BEGIN_TEXT);
            request.setFrom(fromUid.toString());
            request.setOwnerId(fromUid.toString());
            request.setTo(toId.toString());
            request.setBody(bodyRequest);
            request.setModified(System.currentTimeMillis());
            request.setType(Constants.IM_SYSTEM);
            ChatApiResponse response = chatMessageSendApiService.sendMessageFromAndTo(request);
            return response.isSuccess();
        } catch (Exception e) {
            log.error("sendFinishAdvisoryMessage send error", e);
        }
        return false;
    }
}
