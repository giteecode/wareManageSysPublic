/**
 * 邮箱
 * @param {*} s
 */
export function isEmail (s) {
  return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
}

/**
 * 手机号码
 * @param {*} s
 */
export function isMobile (mobile) {
  let isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/
  let isMobile = /^1[0-9]{10}$/
  return isPhone.test(mobile) || isMobile.test(mobile)
}

/**
 * 电话号码
 * @param {*} s
 */
export function isPhone (s) {
  return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
}

/**
 * URL地址
 * @param {*} s
 */
export function isURL (s) {
  return /^http[s]?:\/\/.*/.test(s)
}

/**
 * 整数校验
 * @param {*} s
 */
export function isInt (s) {
  return /^\d+$/.test(s)
}
