import QuestionType from "./QuestionTypeEnum";

/**
 * socket 消息通讯枚举类
 * @type {{HEART: {code: number, value: string},
 * REJECT_SESSION: {code: number, value: string},
 * EXAM_CORRECT: {code: number, value: string},
 * CONNECTION_SUCCESS: {code: number, value: string}}}
 */
const SocketMessageType = {
  HEART: {
    'code': 10001,
    'value': '心跳包'
  },

  CONNECTION_SUCCESS: {
    'code': 10011,
    'value': 'socket连接成功'
  },

  REJECT_SESSION: {
    'code': 10012,
    'value': '账号下线通知'
  },

  EXAM_CORRECT: {
    'code': 10013,
    'value': '试卷批改通知'
  }
}

export default SocketMessageType
