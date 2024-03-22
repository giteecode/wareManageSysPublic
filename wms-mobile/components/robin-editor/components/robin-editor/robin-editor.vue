<template>
    <view class="wrapper" :style="{ 'padding-top': keyboardHeight }">
		<robin-editor-header v-if="false" class="header" @cancel="cancel" @save="save" :labelConfirm="labelConfirm" :labelCancel="labelCancel"></robin-editor-header>
        <view :style="'height:' + editorHeight + 'px;'" class="container" v-if="!previewMode" v-show="!showPreview">
            <editor
                v-if="!previewMode"
                v-show="!showPreview"
                id="editor"
                class="ql-container"
                placeholder="请填写内容..."
                showImgSize
                showImgToolbar
                showImgResize
                @statuschange="onStatusChange"
                :read-only="readOnly"
                @ready="onEditorReady"
            ></editor>
        </view>
        <view class="toolbar" @tap="format" v-if="!showPreview" v-show="keyboardHeight || !autoHideToolbar" :style="'bottom:' + (keyboardHeight > 0 ? keyboardHeight : 35) + 'px'">
            <block v-for="(t, i) in tools" :key="i">
                <view v-if="t == 'bold'" :class="formats.bold ? 'ql-active' : ''" class="iconfont icon-zitijiacu" data-name="bold" data-label="加粗"></view>
                <view v-if="t == 'italic'" :class="formats.italic ? 'ql-active' : ''" class="iconfont icon-zitixieti" data-name="italic" data-label="斜体"></view>
                <view v-if="t == 'underline'" :class="formats.underline ? 'ql-active' : ''" class="iconfont icon-zitixiahuaxian" data-name="underline" data-label="下滑线"></view>
                <view v-if="t == 'strike'" :class="formats.strike ? 'ql-active' : ''" class="iconfont icon-zitishanchuxian" data-name="strike" data-label="删除线"></view>
                <view
                    v-if="t == 'align-left'"
                    :class="formats.align === 'left' || !formats.align ? 'ql-active' : ''"
                    class="iconfont icon-zuoduiqi"
                    data-name="align"
                    data-value="left"
                    data-label="居左"
                ></view>
                <view
                    v-if="t == 'align-center'"
                    :class="formats.align === 'center' ? 'ql-active' : ''"
                    class="iconfont icon-juzhongduiqi"
                    data-name="align"
                    data-value="center"
                    data-label="居中"
                ></view>
                <view
                    v-if="t == 'align-right'"
                    :class="formats.align === 'right' ? 'ql-active' : ''"
                    class="iconfont icon-youduiqi"
                    data-name="align"
                    data-value="right"
                    data-label="居右"
                ></view>
                <view
                    v-if="t == 'align-justify'"
                    :class="formats.align === 'justify' ? 'ql-active' : ''"
                    class="iconfont icon-zuoyouduiqi"
                    data-name="align"
                    data-value="justify"
                    data-label="平铺"
                ></view>
                <!--                  <view :class="formats.lineHeight ? 'ql-active' : ''" class="iconfont icon-line-height" data-name="lineHeight"
                             data-value="2"></view>
                    <view :class="formats.letterSpacing ? 'ql-active' : ''" class="iconfont icon-Character-Spacing" data-name="letterSpacing"
                             data-value="2em"></view>
                    <view :class="formats.marginTop ? 'ql-active' : ''" class="iconfont icon-722bianjiqi_duanqianju" data-name="marginTop"
                             data-value="20px"></view>
                    <view :class="formats.previewarginBottom ? 'ql-active' : ''" class="iconfont icon-723bianjiqi_duanhouju"
                             data-name="marginBottom" data-value="20px"></view> -->
                <view v-if="t == 'remove'" class="iconfont icon-clearedformat" @tap.stop="removeFormat"></view>
                <picker v-if="t == 'font'" class="iconfont" mode="selector" :range="fontSizeRange" @change="fontSize"><view class="icon-fontsize"></view></picker>
                <view
                    v-if="t == 'color'"
                    :style="fontColor != '#FFFFFF' ? 'color:' + formats.color : ''"
                    class="iconfont icon-text_color"
                    data-name="color"
                    @tap.stop="openColor"
                ></view>
                <view
                    v-if="t == 'backgroundColor'"
                    :style="bgColor ? 'color:' + formats.backgroundColor : ''"
                    class="iconfont icon-fontbgcolor"
                    data-name="backgroundColor"
                    @tap.stop="openColor"
                ></view>
                <view v-if="t == 'image'" class="iconfont icon-charutupian" @tap.stop="insertImage"></view>
                <view v-if="t == 'clear'" class="iconfont icon-shanchu" @tap.stop="clear"></view>
                <view v-if="t == 'preview'" class="iconfont icon-preview" @tap.stop="preview"></view>
                <view v-if="t == 'date'" class="iconfont icon-date" @tap="insertDate"></view>
                <view v-if="t == 'list-check'" class="iconfont icon-checklist" data-name="list" data-value="check"></view>
                <view
                    v-if="t == 'list-ordered'"
                    :class="formats.list === 'ordered' ? 'ql-active' : ''"
                    class="iconfont icon-youxupailie"
                    data-name="list"
                    data-value="ordered"
                ></view>
                <view v-if="t == 'list-bullet'" :class="formats.list === 'bullet' ? 'ql-active' : ''" class="iconfont icon-wuxupailie" data-name="list" data-value="bullet"></view>
                <view v-if="t == 'undo'" class="iconfont icon-undo" @tap="undo"></view>
                <view v-if="t == 'redo'" class="iconfont icon-redo" @tap="redo"></view>
                <view v-if="t == 'outdent'" class="iconfont icon-outdent" data-name="indent" data-value="-1"></view>
                <view v-if="t == 'indent'" class="iconfont icon-indent" data-name="indent" data-value="+1"></view>
                <view v-if="t == 'divider'" class="iconfont icon-fengexian" @tap="insertDivider"></view>
                <view v-if="t == 'h1'" :class="formats.header === 1 ? 'ql-active' : ''" class="iconfont icon-format-header-1" data-name="header" :data-value="1"></view>
                <view v-if="t == 'h2'" :class="formats.header === 2 ? 'ql-active' : ''" class="iconfont icon-format-header-2" data-name="header" :data-value="2"></view>
                <view v-if="t == 'h3'" :class="formats.header === 3 ? 'ql-active' : ''" class="iconfont icon-format-header-3" data-name="header" :data-value="3"></view>
                <view v-if="t == 'h4'" :class="formats.header === 4 ? 'ql-active' : ''" class="iconfont icon-format-header-4" data-name="header" :data-value="4"></view>
                <view v-if="t == 'h5'" :class="formats.header === 5 ? 'ql-active' : ''" class="iconfont icon-format-header-5" data-name="header" :data-value="5"></view>
                <view v-if="t == 'h6'" :class="formats.header === 6 ? 'ql-active' : ''" class="iconfont icon-format-header-6" data-name="header" :data-value="6"></view>
                <view v-if="t == 'sub'" :class="formats.script === 'sub' ? 'ql-active' : ''" class="iconfont icon-zitixiabiao" data-name="script" data-value="sub"></view>
                <view v-if="t == 'super'" :class="formats.script === 'super' ? 'ql-active' : ''" class="iconfont icon-zitishangbiao" data-name="script" data-value="super"></view>
                <view
                    v-if="t == 'rtl'"
                    :class="formats.direction === 'rtl' ? 'ql-active' : ''"
                    class="iconfont icon-direction-rtl"
                    data-name="direction"
                    :data-value="formats.direction === 'rtl' ? '' : 'rtl'"
                ></view>
            </block>
        </view>
        <uni-popup type="bottom" ref="color"><robinColorPicker :color="color" @confirm="colorChanged" ref="color"></robinColorPicker></uni-popup>
        <view class="preview" v-show="showPreview"><rich-text :nodes="htmlData" class="previewNodes"></rich-text></view>
    </view>
</template>

<script>
import robinEditorHeader from '../robin-editor-header/robin-editor-header.vue'
import robinColorPicker from '../robin-color-picker/robin-color-picker.vue'
export default {
	components: { robinEditorHeader, robinColorPicker },
    props: {
        value: {
            type: String
        },
        imageUploader: {
            type: Function
        },
        muiltImage: {
            type: Boolean,
            default: false
        },
        compressImage: {
            type: Boolean,
            default: false
        },
        previewMode: {
            type: Boolean,
            default: false
        },
        autoHideToolbar: {
            type: Boolean,
            default: false
        },
        tools: {
            type: Array,
            default: function() {
                return [
                    'bold',
                    'italic',
                    'underline',
                    'strike',
                    'align-left',
                    'align-center',
                    'align-right',
                    'remove',
                    'font',
                    'color',
                    'backgroundColor',
                    'image',
                    'clear',
                    'preview'
                ];
            }
        }
    },
    data() {
        return {
            show: true,
            readOnly: false,
            formats: {},
            fontColor: '#000000',
            bgColor: '',
            color: '',
            colorPickerName: '',
            showColor: true,
            fontSizeRange: [10, 12, 14, 16, 18, 24, 32],
            showPreview: false,
            htmlData: '',
            html: '',
            keyboardHeight: 0,
            editorHeight: 0,
            isIOS: false
        };
    },
    watch: {
        value: function(newvar) {
            this.html = newvar;
        },
        html: function(newvar) {
            if (this.previewMode) {
                this.previewData(this.html);
            }
            if (this.editorCtx) {
                this.editorCtx.setContents({
                    html: this.html
                });
            }
        }
    },
    created() {
        this.html = this.value;
    },
    mounted: function() {
        const platform = uni.getSystemInfoSync().platform;
        this.isIOS = platform === 'ios';
        if (this.previewMode) {
            this.previewData(this.html);
        }
        let keyboardHeight = 0;
        this.updatePosition(0);
        uni.onKeyboardHeightChange(res => {
            if (res.height === keyboardHeight) return;
            const duration = res.height > 0 ? res.duration * 1000 : 0;
            keyboardHeight = res.height;
            setTimeout(() => {
                uni.pageScrollTo({
                    scrollTop: 0,
                    success: () => {
                        this.updatePosition(keyboardHeight);
                        this.editorCtx && this.editorCtx.scrollIntoView();
                    }
                });
            }, duration);
        });
    },
    computed: {
        labelConfirm: function() {
            return this.showPreview ? '关闭' : '保存';
        },
        labelCancel: function() {
            return this.showPreview ? '' : '取消';
        }
    },
    methods: {
        updatePosition(keyboardHeight) {
            const { windowHeight, windowWidth, platform } = uni.getSystemInfoSync();
            const rpx = windowWidth / 750;
            let titleHeight = 0;
            //#ifdef H5
            titleHeight = 44; //H5标题栏高度
            //#endif
            const toolbarHeight = (70 * Math.ceil(this.tools.length / 15) + 1) * rpx; //底部工具栏高度

            const bodyHeight = windowHeight - titleHeight;
            this.keyboardHeight = keyboardHeight;
            this.editorHeight = keyboardHeight > 0 ? bodyHeight - keyboardHeight - toolbarHeight : this.autoHideToolbar ? bodyHeight : bodyHeight - toolbarHeight;
        },
        openColor(e) {
            var name = e.currentTarget.dataset.name;
            var color = this.formats[name];
            this.colorPickerName = name;
            if (name == 'backgroundColor' && !color) {
                color = '#FFFFFF';
            }
            if (name == 'color' && !color) {
                color = '#000000';
            }
            this.color = color;
            this.$refs.color.open(color);
            
        },
        colorChanged(e) {
            let label = '';
            switch (this.colorPickerName) {
                case 'backgroundColor':
                    if (e.color == '#FFFFFF') {
                        e.color = '';
                    }
                    this.bgColor = e.color;
                    label = '背景色';
                    break;
                case 'color':
                    this.fontColor = e.color;
                    label = '颜色';
                    break;
            }
            this._format(this.colorPickerName, e.color, label + e.color);
        },
        readOnlyChange() {
            this.readOnly = !this.readOnly;
        },
        onEditorReady() {
            uni.createSelectorQuery()
                .in(this)
                .select('#editor')
                .context(res => {
                    this.editorCtx = res.context;
                    if (this.html) {
                        this.editorCtx.setContents({
                            html: this.html
                        });
                    }
                })
                .exec();
        },
        undo() {
            this.editorCtx.undo();
            this.toast('撤销');
        },
        redo() {
            this.editorCtx.redo();
            this.toast('重做');
        },
        format(e) {
            let { name, value, label } = e.target.dataset;
            if (!name) return;
            this._format(name, value, label);
        },
        _format(name, value, label) {
            this.editorCtx.format(name, value);
            this.toast(label);
        },
        toast(label) {
            uni.showToast({
                duration: 600,
                icon: 'none',
                title: label
            });
        },
        onStatusChange(e) {
            const formats = e.detail;
            this.formats = formats;
        },
        insertDivider() {
            this.editorCtx.insertDivider({
                success: function() {
                    this.toast('插入分割线');
                }
            });
        },
        clear() {
            this.editorCtx.clear({
                success: res => {
                    this.toast('清空');
                }
            });
        },
        removeFormat() {
            this.editorCtx.removeFormat();
            this.toast('清除格式');
        },
        insertDate() {
            const date = new Date();
            const formatDate = `${date.getFullYear()}/${date.getMonth() + 1}/${date.getDate()}`;
            this.editorCtx.insertText({
                text: formatDate
            });
            this.toast('插入日期');
        },
        insertImage() {
            let params = {};
            params.count = this.muiltImage ? 9 : 1;
            params.sizeType = this.compressImage ? ['compressed'] : ['original'];
            uni.chooseImage({
                ...params,
                success: res => {
                    res.tempFilePaths.map(path => {
                        this.imageUploader(path, url => {
                            this.editorCtx.insertImage({
                                src: url,
                                alt: '图像'
                            });
                        });
                    });
                }
            });
        },
        fontSize(e) {
            const index = e.detail.value;
            const fz = this.fontSizeRange[index] + 'px';
            this._format('fontSize', fz, '字体大小:' + fz);
        },
        cancel() {
            this.$emit('cancel');
        },
        save() {
            if (this.showPreview) {
                if (this.previewMode) {
                    this.cancel();
                } else {
                    this.showPreview = false;
                }
            } else {
                this.editorCtx.getContents({
                    success: res => {
                        this.$emit('save', res);
                        this.$emit('input', res.html);
                    }
                });
            }
        },
        previewData: function(html) {
            this.htmlData = html.replace(/\<img/gi, '<img style="max-width:100%;height:auto"');
            this.showPreview = true;
        },
        preview: function() {
            this.editorCtx.getContents({
                success: res => {
                    this.previewData(res.html);
                }
            });
        }
    }
};
</script>

<style lang="scss" scoped>
@import './editor-icon.css';

.wrapper {
    padding: 5px;
    width: 100%;
    position: relative;
    .header {
        width: 100%;
        position: fixed;
        z-index: 9;
        left: 0;
        height: 75rpx;
        /* #ifndef H5 */
        top: 0;
        /* #endif */
        /* #ifdef H5 */
        top: 44px;
        /* #endif */
    }

    .container {
        width: 100%;
        background: #fff;

        .ql-container {
            box-sizing: border-box;
            width: 100%;
            height: 100%;
            font-size: 16px;
            line-height: 1.5;
            overflow: auto;
        }
    }

    .toolbar {
        position: fixed;
        width: 100%;
        left: 0;
        bottom: 0;
        box-sizing: border-box;
        font-family: 'Helvetica Neue', 'Helvetica', 'Arial', sans-serif;
        background-color: #fff;
        border-top: 1px solid #eee;
        line-height: 50rpx;

        .iconfont {
            display: inline-block;
            padding: 10rpx 0;
            width: 50rpx;
            text-align: center;
            font-size: 34rpx;
            box-sizing: border-box;
        }
    }
}

.preview {
    width: 100%;
    margin-top: 90rpx;

    .previewNodes {
        width: 100%;
        word-break: break-all;
    }
}

.ql-active {
    color: #06c;
}

</style>