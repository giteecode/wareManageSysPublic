<template>
    <view class="content">
        <robin-editor-header class="head" @cancel="cancel" @save="confirm"></robin-editor-header>
        <view class="color-picker">
            <view class="color-name">{{ colorName }}</view>
            <view class="show-view" :style="{ background: colorName }"></view>
            <view class="hue-view" @touchstart="pickHue" @touchmove="pickHue"><text class="anchor" :style="{ left: hueView.anchorLeft + 'px' }"></text></view>
            <view class="color-view" @touchstart="pickColor" @touchmove="pickColor" :style="{ backgroundColor: 'hsl(' + hueView.H + ', 100%, 50%)' }">
                <text class="anchor" :style="{ top: colorView.anchorTop + 'px', left: colorView.anchorLeft + 'px' }"></text>
            </view>
        </view>
    </view>
</template>

<script>
import robinEditorHeader from '../robin-editor-header/robin-editor-header.vue'
import popup from '@/uni_modules/uni-popup/components/uni-popup/popup.js';
export default {
	components: { robinEditorHeader },
    mixins: [popup],
    props: {
        color: {
            type: String,
            default: ''
        }
    },
    data() {
        return {
            hueView: {},
            colorView: {},
            colorName: '',
            hueLeft: 0.5, // 色相选择器初始位置 [0, 1]
            anchorTop: 0.5, // 颜色选择器初始 top [0, 1]
            anchorLeft: 0.5 // 颜色选择器初始 left [0, 1],
        };
    },
    created() {
        this.popup.messageChild = this;
    },
	mounted() { 
		Promise.all([this.getHueViewOffset(), this.getColorViewOffset()]).then(() => { 
			this.colorName = this.getColorString() // 根据 HLS 计算 RGB 字符串 }) 
		});
	},
    methods: {
        open: function() {
            this.popup.open();
            setTimeout(() => {
                this.init();
            }, this.popup.duration);
        },
        init() {
            const reg = /^#[0-9a-fA-F]{3}([0-9a-fA-F]{3})*$/;
            if (this.color !== '' && reg.test(this.color)) {
                this.getColorOffset();
            }
            Promise.all([this.getHueViewOffset(), this.getColorViewOffset()]).then(() => {
                this.colorName = this.getColorString(); // 根据 HLS 计算 RGB 字符串
            });
        },
        getHueViewOffset() {
            // 获取色相选择区域尺寸
            return new Promise(resolve =>
                uni
                    .createSelectorQuery()
                    .in(this)
                    .select('.hue-view')
                    .boundingClientRect(data => {
                        this.hueView = {
                            ...data,
                            anchorLeft: data.width * this.hueLeft,
                            H: this.hueLeft * 360
                        };
                        resolve();
                    })
                    .exec()
            );
        },
        getColorViewOffset() {
            // 获取颜色选择区域尺寸
            return new Promise(resolve =>
                uni
                    .createSelectorQuery()
                    .in(this)
                    .select('.color-view')
                    .boundingClientRect(data => {
                        this.colorView = {
                            ...data,
                            anchorTop: data.height * this.anchorTop,
                            anchorLeft: data.width * this.anchorLeft,
                            S: this.anchorLeft,
                            L: 1 - this.anchorLeft * 0.5 - this.anchorTop / (this.anchorLeft + 1)
                        };
                        resolve();
                    })
                    .exec()
            );
        },
        getColorString() {
            // 获取 RGB 颜色字符串
            const arr = hslToRgb(this.hueView.anchorLeft / this.hueView.width, this.colorView.S, this.colorView.L);
            const r = arr[0].toString(16).length === 1 ? `0${arr[0].toString(16)}` : arr[0].toString(16);
            const g = arr[1].toString(16).length === 1 ? `0${arr[1].toString(16)}` : arr[1].toString(16);
            const b = arr[2].toString(16).length === 1 ? `0${arr[2].toString(16)}` : arr[2].toString(16);
            return `#${r.toUpperCase()}${g.toUpperCase()}${b.toUpperCase()}`;
        },
        getColorOffset() {
            var color = this.color.substr(1);
            color = color.length == 6 ? color : color.charAt(0) + color.charAt(0) + color.charAt(1) + color.charAt(1) + color.charAt(2) + color.charAt(2);
            const r = parseInt('0x' + color.substr(0, 2));
            const g = parseInt('0x' + color.substr(2, 2));
            const b = parseInt('0x' + color.substr(4, 2));
            const arr = rgbToHsl(r, g, b);
            this.hueLeft = arr[0];
            this.anchorLeft = arr[1];
            this.anchorTop = (1 - arr[1] * 0.5 - arr[2]) * (arr[1] + 1);
        },
        pickColor(e) {
            // 选择颜色
            const top = e.touches[0].clientY - this.colorView.top;
            const left = e.touches[0].clientX - this.colorView.left;
            if (top < 0) {
                this.colorView.anchorTop = 0;
            } else if (top > this.colorView.height) {
                this.colorView.anchorTop = this.colorView.height;
            } else {
                this.colorView.anchorTop = top;
            }
            if (left < 0) {
                this.colorView.anchorLeft = 0;
            } else if (left > this.colorView.width) {
                this.colorView.anchorLeft = this.colorView.width;
            } else {
                this.colorView.anchorLeft = e.touches[0].clientX - this.colorView.left;
            }
            this.colorView.S = this.colorView.anchorLeft / this.colorView.width;
            this.colorView.L = this.floor(1 - this.colorView.S * 0.5 - this.colorView.anchorTop / this.colorView.height / (this.colorView.S + 1));
            this.colorName = this.getColorString(); // 根据 HLS 计算 RGB 字符串
        },
        pickHue(e) {
            // 选择色相
            if (e.touches[0].clientX >= this.hueView.left && e.touches[0].clientX <= this.hueView.right) {
                this.hueView.anchorLeft = e.touches[0].clientX - this.hueView.left;
                this.hueView.H = (this.hueView.anchorLeft / this.hueView.width) * 360;
                this.colorName = this.getColorString(); // 根据 HLS 计算 RGB 字符串
            }
        },
        floor(num) {
            return num < 0.09 ? 0 : num;
        },
        confirm() {
            this.$emit('confirm', {
                color: this.colorName
            });
            this.popup.close();
        },
        cancel() {
            this.$emit('cancel');
            this.popup.close();
        },
        close() {}
    }
};

function hslToRgb(h, s, l) {
    // HSL 转 RGB 方法
    var r, g, b;
    if (s == 0) {
        r = g = b = l; // achromatic
    } else {
        var hue2rgb = function hue2rgb(p, q, t) {
            if (t < 0) t += 1;
            if (t > 1) t -= 1;
            if (t < 1 / 6) return p + (q - p) * 6 * t;
            if (t < 1 / 2) return q;
            if (t < 2 / 3) return p + (q - p) * (2 / 3 - t) * 6;
            return p;
        };
        var q = l < 0.5 ? l * (1 + s) : l + s - l * s;
        var p = 2 * l - q;
        r = hue2rgb(p, q, h + 1 / 3);
        g = hue2rgb(p, q, h);
        b = hue2rgb(p, q, h - 1 / 3);
    }
    return [Math.round(r * 255), Math.round(g * 255), Math.round(b * 255)];
}

function rgbToHsl(r, g, b) {
    (r /= 255), (g /= 255), (b /= 255);
    var max = Math.max(r, g, b),
        min = Math.min(r, g, b);
    var h,
        s,
        l = (max + min) / 2;
    if (max == min) {
        h = s = 0; // achromatic
    } else {
        var d = max - min;
        s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
        switch (max) {
            case r:
                h = (g - b) / d + (g < b ? 6 : 0);
                break;
            case g:
                h = (b - r) / d + 2;
                break;
            case b:
                h = (r - g) / d + 4;
                break;
        }
        h /= 6;
    }

    var round = function(n, l) {
        return Math.round(n * Math.pow(10, l)) / Math.pow(10, l);
    };
    return [round(h, 3), round(s, 3), round(l, 3)];
}
</script>

<style lang="scss" scoped>
.content {
    height: 100%;
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: center;
    background-color: #fff;

    .head {
        width: 100%;
    }

    .color-picker {
        display: flex;
        align-items: center;
        flex-direction: column;
        justify-content: center;

        .color-name {
            margin: 23rpx;
            font-size: 45rpx;
            font-weight: bold;
            letter-spacing: 8rpx;
        }

        .show-view {
            height: 56rpx;
            width: 567rpx;
        }

        .hue-view {
            width: 567rpx;
            height: 56rpx;
            margin: 12rpx 0;
            position: relative;
            background: linear-gradient(to right, #f00, #ff0, #0f0, #0ff, #00f, #f0f, #f00);

            .anchor {
                width: 12rpx;
                height: 100%;
                position: absolute;
                background: #ffffff;
                transform: translate(-50%);
                box-shadow: 0 0 2rpx rgba(0, 0, 0, 0.6);
            }
        }

        .color-view {
            width: 567rpx;
            height: 345rpx;
            position: relative;
            margin-bottom: 12upx;

            &::before,
            &::after {
                content: '';
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                position: absolute;
            }

            &::before {
                background: linear-gradient(to right, white, transparent);
            }

            &::after {
                background: linear-gradient(to top, black, transparent);
            }

            .anchor {
                z-index: 1;
                width: 24rpx;
                height: 24rpx;
                border-radius: 50%;
                position: absolute;
                border: 4rpx solid #ffffff;
                background: rgba(0, 0, 0, 0.3);
                transform: translate(-50%, -50%);
            }
        }
    }
}
</style>
