# 富文本编辑器插件
uniapp 富文本编辑器插件

## 兼容性
|微信小程序|H5|APP|
|:--:|:--:|:--:|
|√|√ |x|

App键盘弹出界面高度未解决

## 使用方式
无需引入,直接使用

```
在 `template` 中使用组件
```html
<robin-editor class="editor" 
    @cancel="hideEditor" 
    @save="saveEditor" 
    v-model="html"
    :imageUploader="uploadImg" 
    :muiltImage="true">
</robin-editor>
```

## Demo
https://github.com/health901/uniapp-editor-demo

## 属性说明
|属性|类型|默认值|说明|
|--|--|--|--|
|v-model|String| |富文本,双向绑定|
|imageUploader|function(img,callback)| |上传图片处理函数 接受参数 img:本地图片地址,callback:上传成功回调传入图片链接|
|muiltImage|Boolean|false|是否支持多图上传|
|compressImage|Boolean|true|图片上传是否压缩|
|previewMode|Boolean|false|预览模式,不可编辑|
|autoHideToolbar|Boolean|false|失去焦点时自动隐藏工具栏|
|tools|Array|['bold', 'italic', 'underline', 'strike', 'align-left', 'align-center', 'align-right', 'remove', 'font', 'color', 'backgroundColor','image', 'clear', 'preview']|工具栏|

### 工具栏
|名称|值|
|--|--|
|加粗|`bold`|
|斜体|`italic`|
|下划线|`underline`|
|删除线|`strike`|
|右对齐|`align-left`|
|居中|`align-center`|
|左对齐|`align-right`|
|清除格式|`remove`|
|字体大小|`font`|
|字体颜色|`color`|
|背景色|`backgroundColor`|
|插入图片|`image`|
|清空|`clear`|
|预览|`preview`|
|插入日期|`date`|
|列表|`list-check`,`list-ordered`,`list-bullet`|
|上下标|`sub`,`super`|
|撤销,恢复撤销|`undo`,`redo`|
|缩进|`indent`,`outdent`|
|分割线|`divider`|
|标题|`h1`,`h2`,`h3`,`h4`,`h5`,`h6`|
|书写方向|`rtl`|

## 事件说明
|事件|说明|参数|
|--|--|--|
|cancel|点击取消按钮|
|save|点击保存按钮|e={html,text,delta}|

## 依赖
|组件|链接|备注|
|---|--|--|
|颜色选择器ColorPicker<sup>[[1]](#注)</sup>|https://ext.dcloud.net.cn/plugin?id=1237|字体颜色,背景色|


## 注
1. 修改:添加按钮,支持预设颜色值
