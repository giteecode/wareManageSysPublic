"use strict";
const merge = require("webpack-merge");
const prodEnv = require("./prod.env");

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  VUE_APP_BASEURL: '"http://127.0.0.1"',
  FILE_HOST: '"https://edu-1306212169.cos.ap-nanjing.myqcloud.com"',
  WEBSOCKET_HOST: '"127.0.0.1"'
});
