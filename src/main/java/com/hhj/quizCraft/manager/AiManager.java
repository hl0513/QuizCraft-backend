package com.hhj.quizCraft.manager;

import com.hhj.quizCraft.common.ErrorCode;
import com.hhj.quizCraft.exception.BusinessException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用 AI 调用能力
 */
@Component
public class AiManager {

    @Resource
    private ClientV4 clientV4;

    // 稳定随机数
    public static final  float STABLE_TEMPERATURE = 0.05f;

    // 不稳定随机数
    public static final  float UNSTABLE_TEMPERATURE = 0.9f;

    /**
     * 通用同步请求 (随机出题版）
     * @param systemMessage
     * @param userMessage
     * @return
     */
    public String doSyncUnstableRequest(String systemMessage,String userMessage){
        return doSyncRequest(systemMessage,userMessage,UNSTABLE_TEMPERATURE);
    }

    /**
     * 通用同步请求 (稳定结果版）
     * @param systemMessage
     * @param userMessage
     * @return
     */
    public String doSyncStableRequest(String systemMessage,String userMessage){
        return doSyncRequest(systemMessage,userMessage,STABLE_TEMPERATURE);
    }

    /**
     * 通用同步请求
     * @param systemMessage
     * @param userMessage
     * @param temperature
     * @return
     */
    public String doSyncRequest(String systemMessage,String userMessage, Float temperature){
        List<ChatMessage> chatMessageList = new ArrayList<>();
        ChatMessage systemChartMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage);
        chatMessageList.add(systemChartMessage);
        // 创建一个ChatMessage对象，表示用户发送的消息，并添加到消息列表中
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        chatMessageList.add(userChatMessage);
        return doRequest(chatMessageList,Boolean.FALSE,temperature);
    }

    /**
     * 通用请求 (简化消息传递)
     * @param systemMessage
     * @param userMessage
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(String systemMessage,String userMessage, Boolean stream, Float temperature){
        List<ChatMessage> chatMessageList = new ArrayList<>();
        ChatMessage systemChartMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage);
        chatMessageList.add(systemChartMessage);
        // 创建一个ChatMessage对象，表示用户发送的消息，并添加到消息列表中
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        chatMessageList.add(userChatMessage);
        return doRequest(chatMessageList,stream,temperature);
    }

    /**
     * 通用请求
     * @param messages
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(List<ChatMessage> messages, Boolean stream, Float temperature){
        // 构建ChatCompletionRequest对象，设置模型名称、是否流式传输、调用方法、消息列表和请求ID
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(stream)
                .temperature(temperature)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .build();
        try{
            // 使用ClientV4客户端调用模型API，并获取响应
            ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
            return invokeModelApiResp.getData().getChoices().get(0).toString();
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,e.getMessage());
        }
    }
}
