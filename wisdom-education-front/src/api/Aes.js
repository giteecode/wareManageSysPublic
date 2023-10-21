import CryptoJS from 'crypto-js'

const DEFAULT_KEY = "1964d18695d7f8ad";
//默认自定义的约定密钥(与后端密钥保持一致)
const KEY = CryptoJS.enc.Utf8.parse(DEFAULT_KEY);// 密钥16位长度字符   内容可自定义
const IV = CryptoJS.enc.Utf8.parse(DEFAULT_KEY); //  密钥偏移量    16位长度字符

/**
 * AES对称加密 （CBC模式，需要偏移量）
 * @param {Object} params 明文参数
 */
export function encrypt(str) {
  let key = KEY
  let iv = IV
  //明文
  let srcs = CryptoJS.enc.Utf8.parse(str);
  //加密
  let encrypt = CryptoJS.AES.encrypt(srcs, key, {
    iv: iv,
    mode: CryptoJS.mode.CBC, //AES加密模式
    padding: CryptoJS.pad.Pkcs7 //填充方式
  });
  return CryptoJS.enc.Base64.stringify(encrypt.ciphertext); //返回base64格式密文
}

/**
 * AES对称解密
 * @param {Object} params 加密参数
 */
export function decrypt(str) {
  let key = KEY
  let iv = IV
  //base64格式密文转换
  let base64 = CryptoJS.enc.Base64.parse(str);
  let src = CryptoJS.enc.Base64.stringify(base64);
  //解密
  let decrypt = CryptoJS.AES.decrypt(src, key, {
    iv: iv,
    mode: CryptoJS.mode.CBC, //AES加密模式
    padding: CryptoJS.pad.Pkcs7 //填充方式
  });
  return CryptoJS.enc.Utf8.stringify(decrypt).toString(); //返回明文
}
