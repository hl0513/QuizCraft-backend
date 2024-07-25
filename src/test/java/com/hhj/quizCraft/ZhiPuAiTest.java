package com.hhj.quizCraft;

import com.hhj.quizCraft.constant.KeyConstant;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ZhiPuAiTest {
    /**
     * 测试方法，用于调用模型API并打印模型输出。
     */
    @Resource
    private ClientV4 clientV4;
    @Test
    public void test(){
        // 创建ClientV4客户端，使用KeyConstant.KEY进行初始化
        ClientV4 client = new ClientV4.Builder(KeyConstant.KEY).build();

        // 创建一个ChatMessage列表，用于存储聊天消息
        List<ChatMessage> messages = new ArrayList<>();

        // 创建一个ChatMessage对象，表示用户发送的消息，并添加到消息列表中
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "作为一名营销专家，请为智谱开放平台创作一个吸引人的slogan");
        messages.add(chatMessage);

        // 生成唯一的请求ID，使用当前时间戳
//        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());

        // 构建ChatCompletionRequest对象，设置模型名称、是否流式传输、调用方法、消息列表和请求ID
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .build();

        // 使用ClientV4客户端调用模型API，并获取响应
        ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);

        System.out.println("model output:" + invokeModelApiResp.getData().getChoices().get(0));

    }
//    @Test
//    public void test2(){
//         String AI_TEST_SCORING_SYSTEM_MESSAGE = "你是一位严谨的判题专家，我会给你如下信息：\n" +
//                "```\n" +
//                "应用名称，\n" +
//                "【【【应用描述】】】，\n" +
//                "题目和用户回答的列表：格式为 [{\"title\": \"题目\",\"answer\": \"用户回答\"}]\n" +
//                "```\n" +
//                "\n" +
//                "请你根据上述信息，按照以下步骤来对用户进行评价：\n" +
//                "1. 要求：需要给出一个明确的评价结果，包括评价名称（尽量简短）和评价描述（尽量详细，大于 200 字）\n" +
//                "2. 严格按照下面的 json 格式输出评价名称和评价描述\n" +
//                "```\n" +
//                "{\"resultName\": \"评价名称\", \"resultDesc\": \"评价描述\"}\n" +
//                "```\n" +
//                "3. 返回格式必须为 JSON 对象";
//        System.out.println(AI_TEST_SCORING_SYSTEM_MESSAGE);
//    }
}
