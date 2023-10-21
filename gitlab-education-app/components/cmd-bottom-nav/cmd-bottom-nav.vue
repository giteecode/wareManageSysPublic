<template>
  <view class="cmd-bottom-nav" :class="[textAuto ? 'cmd-bottom-nav-text-auto':'',fixed ? 'cmd-bottom-nav-fixed':'']"
    :style="setColorStyle">
    <view :class="['cmd-bottom-nav-box',select == index ? 'cmd-bottom-nav-active':'']" :style="select == index ? setActiveFontColorStyle : ''"
      @tap="$_click(index)" v-for="(nav,index) in list" :key="index">
      <view class="cmd-bottom-nav-box-icon">
        <cmd-icon v-if="nav.icon && !nav.src" :type="nav.icon" size="24" :color="select == index ?activeFontColor :  fontColor"></cmd-icon>
      </view>
      <image v-if="nav.src && !nav.icon" class="cmd-bottom-nav-box-img" :src="select == index ? nav.srcSelect:nav.src"
        mode="aspectFit"></image>
      <text class="cmd-bottom-nav-box-text">{{nav.text}}</text>
    </view>
  </view>
</template>

<script>
  import cmdIcon from "../cmd-icon/cmd-icon.vue"

  /**  
   * 底部导航栏组件
   * @description 底部导航栏固定在页面底部，模仿md风格。  
   * @tutorial https://ext.dcloud.net.cn/plugin?id=188  
   * @property {Number} current 底部导航栏选中项 - 默认0，第一项  
   * @property {Array} list 底部导航栏项列表 - 默认：首页和我的  
   * @property {String} font-color 底部导航栏文字颜色 - 默认：#000  
   * @property {String} border-color 底部导航栏上边线颜色 - 默认：#dadada  
   * @property {String} background-color 底部导航栏背景颜色 - 默认：#fff  
   * @property {String} active-font-color 底部导航栏激活文字颜色 - 默认：#000  
   * @property {Boolean} text-auto 底部导航栏只在激活状态附加显示文本，默认显示图标  
   * @property {Boolean} fixed 底部导航栏固定到页面底部 - 默认true  
   * @event {Function} click 底部导航栏项 点击事件  
   * @example <cmd-nav-bar title="基本"></cmd-nav-bar>  
   */
  export default {
    name: 'cmd-bottom-nav',

    components: {
      cmdIcon
    },

    props: {
      /**
       * 底部导航栏选中项
       */
      current: {
        type: Number,
        default: 0
      },
      /**
       * 底部导航栏项列表
       */
      list: {
        type: Array,
        default: () => {
          return [{
              "pagePath": "/pages/tabbar/home/home",
              "text": "首页",
              "icon": "home"
            },
            {
              "pagePath": "/pages/tabbar/my/my",
              "text": "我的",
              "icon": "user"
            }
          ];
        }
      },
      /**
       * 文字颜色
       */
      fontColor: {
        type: String,
        default: ''
      },
      /**
       * 底部上边线颜色
       */
      borderColor: {
        type: String,
        default: ''
      },
      /**
       * 背景颜色
       */
      backgroundColor: {
        type: String,
        default: ''
      },
      /**
       * 激活文字颜色
       */
      activeFontColor: {
        type: String,
        default: ''
      },
      // 只在激活状态显示文本
      textAuto: {
        type: Boolean,
        default: false
      },
      // 固定到页面底部
      fixed: {
        type: Boolean,
        default: true
      }
    },

    data() {
      return {
        // 选中项
        select: this.current
      };
    },

    computed: {
      /**
       * 底部导航栏颜色样式
       */
      setColorStyle() {
        let colorStyle = '';
        // 文字颜色
        if (this.fontColor) {
          colorStyle += `color:${this.fontColor};`;
        }
        // 上边线颜色
        if (this.borderColor) {
          colorStyle += `border-top: 1px ${this.borderColor} solid;`;
        }
        // 背景颜色
        if (this.backgroundColor) {
          colorStyle += `background: ${this.backgroundColor};`;
        }
        return colorStyle;
      },
      /**
       * 激活文字样式
       */
      setActiveFontColorStyle() {
        let activeFontColorStyle = '';
        if (this.activeFontColor) {
          activeFontColorStyle += `color:${this.activeFontColor};`;
        }
        return activeFontColorStyle;
      }
    },

    methods: {
      /**
       * 点击事件
       */
      $_click(index) {
        this.select = index;
        this.$emit("click", {
          "item": this.list[index],
          "select": index
        });
      }
    }

  }
</script>

<style>
  /* 底部导航栏样式变量 */
  .cmd-bottom-nav {
    position: relative;
    overflow: hidden;
    white-space: nowrap;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    height: 118upx;
    padding: 0;
    margin: 0 auto;
    color: #000;
    background: #fff;
    border-top: 3upx #dadada solid;
  }

  .cmd-bottom-nav-box {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: 28upx;
    line-height: 1;
    text-align: center;
    text-overflow: ellipsis;
    overflow: hidden;
    user-select: none;
    opacity: 0.7;
    min-width: 64upx;
    height: 100%;
    background: transparent;
    transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
  }

  .cmd-bottom-nav-box-img {
    width: 64upx;
    height: 64upx;
    opacity: 0.7;
  }

  .cmd-bottom-nav-box-icon {
    opacity: 0.7;
  }

  .cmd-bottom-nav-box-text {
    margin-top: 8upx;
    display: block;
    transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
  }

  .cmd-bottom-nav-box.cmd-bottom-nav-active {
    color: #000;
    opacity: 1;
  }

  .cmd-bottom-nav-box.cmd-bottom-nav-active .cmd-bottom-nav-box-img {
    opacity: 1;
  }

  .cmd-bottom-nav-box.cmd-bottom-nav-active .cmd-bottom-nav-box-icon {
    opacity: 1;
  }

  /* 只在激活时显示文本 */
  .cmd-bottom-nav-text-auto .cmd-bottom-nav-box {
    padding-left: 0;
    padding-right: 0;
    min-width: 68upx;
  }

  .cmd-bottom-nav-text-auto .cmd-bottom-nav-box-img {
    padding-top: 0;
    transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
  }

  .cmd-bottom-nav-text-auto .cmd-bottom-nav-box-icon {
    padding-top: 34upx;
    transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
  }

  .cmd-bottom-nav-text-auto .cmd-bottom-nav-box-text {
    transform: scale(0);
  }

  .cmd-bottom-nav-text-auto .cmd-bottom-nav-box.cmd-bottom-nav-active {
    padding-left: 38upx;
    padding-right: 38upx;
  }

  .cmd-bottom-nav-text-auto .cmd-bottom-nav-box.cmd-bottom-nav-active .cmd-bottom-nav-box-img {
    padding-top: 0;
    opacity: 1;
  }

  .cmd-bottom-nav-text-auto .cmd-bottom-nav-box.cmd-bottom-nav-active .cmd-bottom-nav-box-icon {
    padding-top: 0;
    opacity: 1;
  }

  .cmd-bottom-nav-text-auto .cmd-bottom-nav-box.cmd-bottom-nav-active .cmd-bottom-nav-box-text {
    transform: scale(1);
  }

  /* 固定到页面底部 */
  .cmd-bottom-nav-fixed.cmd-bottom-nav {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 1000;
    margin-bottom: -3upx;
  }
</style>
