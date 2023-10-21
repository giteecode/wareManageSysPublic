/**
 * 平台类型枚举
 * @type {{STUDENT: {code: number, value: string},
 * ADMIN: {code: number, value: string},
 * MOBILE: {code: number, value: string}}}
 */
const PlatformEnum = {
  ADMIN: {
    'code': 1,
    'value': '管理后台'
  },

  STUDENT: {
    'code': 2,
    'value': 'web学生端'
  },

  MOBILE: {
    'code': 3,
    'value': '移动端'
  }
}
export default PlatformEnum
