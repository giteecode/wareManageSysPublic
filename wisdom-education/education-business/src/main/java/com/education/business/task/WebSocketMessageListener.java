package com.education.business.task;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.service.education.MessageInfoService;
import com.education.business.service.education.TestPaperInfoService;
import com.education.business.task.param.WebSocketMessageParam;
import com.education.business.webSocket.SystemWebSocketHandler;
import com.education.common.annotation.EventQueue;
import com.education.common.constants.LocalQueueConstants;
import com.education.common.enums.PlatformTypeEnum;
import com.education.common.enums.SocketMessageTypeEnum;
import com.education.common.utils.Ip2regionUtil;
import com.education.model.dto.SocketMessageCommand;
import com.education.model.entity.MessageInfo;
import com.education.model.entity.TestPaperInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;


/**
 * websocket 异步发送消息通知
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/24 21:28
 */
@Component
@EventQueue(name = LocalQueueConstants.SYSTEM_SOCKET_MESSAGE)
public class WebSocketMessageListener implements TaskListener<WebSocketMessageParam> {

    private static final Logger logger = LoggerFactory.getLogger(MailMessageListener.class);

    @Resource
    private SystemWebSocketHandler systemWebSocketHandler;
    @Resource
    private TestPaperInfoService testPaperInfoService;
    @Resource
    private MessageInfoService messageInfoService;


    @Override
    public void onMessage(WebSocketMessageParam webSocketMessageParam) {
        try {
            Integer messageType = webSocketMessageParam.getSocketMessageTypeEnum().getCode();
            SocketMessageCommand socketMessageCommand = new SocketMessageCommand();
            socketMessageCommand.setMessageType(messageType);
            if (SocketMessageTypeEnum.REJECT_SESSION.getCode().equals(messageType)) {
                String ip = webSocketMessageParam.getIp();
                String address = Ip2regionUtil.getIpProvinceAndCity(ip);
                String content = "您的账号已在" + address + "登录，" +
                        "5秒后将自动下线，如非本人操作请重新登录并及时修改密码";
                socketMessageCommand.setMsgContent(content);
                systemWebSocketHandler.sendMessageByHashToken(webSocketMessageParam.getHashToken(), JSONUtil.toJsonStr(socketMessageCommand));
            } else if (SocketMessageTypeEnum.EXAM_CORRECT.getCode().equals(messageType)) {
                Integer studentId = webSocketMessageParam.getStudentId();
                this.saveExamMessage(webSocketMessageParam);
                systemWebSocketHandler.sendMessageByUserId(studentId, PlatformTypeEnum.WEB_FRONT.getCode(), JSONUtil.toJsonStr(socketMessageCommand));
            }
        } catch (Exception e) {
            logger.error("websocket消息发送异常", e);
        }
    }

    private void saveExamMessage(WebSocketMessageParam webSocketMessageParam) {
        TestPaperInfo testPaperInfo = testPaperInfoService.getOne(Wrappers.lambdaQuery(TestPaperInfo.class)
                .select(TestPaperInfo::getName)
                .eq(TestPaperInfo::getId, webSocketMessageParam.getTestPaperId()));

        MessageInfo messageInfo = new MessageInfo();
        String content = "您参加的考试【" + testPaperInfo.getName() + "】已被管理员批改,赶紧去查看吧!";
        messageInfo.setContent(content);
        messageInfo.setStudentId(webSocketMessageParam.getStudentId());
        messageInfo.setMessageType(SocketMessageTypeEnum.EXAM_CORRECT.getCode());
        messageInfo.setCreateDate(new Date());
        messageInfoService.save(messageInfo);
    }
}
