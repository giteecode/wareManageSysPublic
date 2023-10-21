<template>
    <div>
      <canvas ref="drawingBoard" id="drawing-board"></canvas>
      <div class="color-group">
        <ul>
          <li id="white" ref="colorItem" class="color-item" style="background-color: white;"></li>
          <li id="black" ref="colorItem" class="color-item active" style="background-color: black;"></li>
          <li id="red" ref="colorItem" class="color-item" style="background-color: #FF3333;"></li>
          <li id="blue" ref="colorItem" class="color-item" style="background-color: #0066FF;"></li>
          <li id="yellow" ref="colorItem" class="color-item" style="background-color: #FFFF33;"></li>
          <li id="green" ref="colorItem" class="color-item" style="background-color: #33CC66;"></li>
          <li id="gray" ref="colorItem" class="color-item" style="background-color: gray;"></li>
        </ul>
      </div>
      <div id="range-wrap"><input type="range" id="range" min="1" max="30" value="5" title="调整笔刷粗细"></div>
      <div class="tools">
       <!-- <button ref="brush" class="active" title="画笔">
          <img src="@/assets/img/setup.png"/>
        </button>-->
        <button id="brush" ref="brush" class="active" title="画笔"><i class="iconfont icon-qianbi"></i></button>
        <button id="eraser" ref="eraser" @click="clearData" title="橡皮擦"><i class="iconfont icon-xiangpi"></i></button>
        <button id="clear" ref="clear" title="清空"><i class="iconfont icon-qingchu"></i></button>
        <button id="undo" ref="undo" title="撤销"><i class="iconfont icon-chexiao"></i></button>
        <button id="save" ref="save" title="保存"><i class="iconfont icon-fuzhi"></i></button>
      </div>
    </div>
</template>

<script>
    let that = null
    export default {
      name: 'writingBoard',
      data () {
        return {
          canvas: null,
          ctx: null,
          eraser: null,
          brush: null,
          reSetCanvas: null,
          aColorBtn: [],
          save: null,
          undo: null,
          range: null,
          clear: null,
          historyDeta: [],
          activeColor: 'black',
          lWidth: 4
        }
      },

      beforeCreate () {
        that = this
      },

      mounted () {
        this.canvas = this.$refs.drawingBoard;
        this.ctx = this.canvas.getContext("2d");
        this.eraser = this.$refs.eraser;
        this.brush = this.$refs.brush;
        this.reSetCanvas = this.$refs.clear;
        this.aColorBtn = this.$refs.colorItem;
        this.save = this.$refs.save;
        this.undo = this.$refs.undo;
        this.range = this.$refs.range;
        this.clear = false;
        this.autoSetSize(this.canvas);
        this.setCanvasBg('white');
        this.listenToUser(this.canvas);
        this.getColor();
      },

      methods: {
        autoSetSize(canvas) {
          canvasSetSize();

          function canvasSetSize() {
            let pageWidth = document.documentElement.clientWidth;
            let pageHeight = document.documentElement.clientHeight;

            canvas.width = pageWidth;
            canvas.height = pageHeight;
          }

          window.onresize = function () {
            canvasSetSize();
          }
        },

        setCanvasBg(color) {
          this.ctx.fillStyle = color;
          this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
          this.ctx.fillStyle = "black";
        },

        listenToUser(canvas) {
          let that = this
          let painting = false;
          let lastPoint = {x: undefined, y: undefined};

          if (document.body.ontouchstart !== undefined) {
            canvas.ontouchstart = function (e) {
              let firstDot = that.ctx.getImageData(0, 0, that.canvas.width, that.canvas.height);//在这里储存绘图表面
              saveData(that.firstDot);
              painting = true;
              let x = e.touches[0].clientX;
              let y = e.touches[0].clientY;
              lastPoint = {"x": x, "y": y};
              that.ctx.save();
              that.drawCircle(x, y, 0);
            };
            canvas.ontouchmove = function (e) {
              if (painting) {
                let x = e.touches[0].clientX;
                let y = e.touches[0].clientY;
                let newPoint = {"x": x, "y": y};
                that.drawLine(lastPoint.x, lastPoint.y, newPoint.x, newPoint.y);
                lastPoint = newPoint;
              }
            };

            canvas.ontouchend = function () {
              painting = false;
            }
          } else {
            canvas.onmousedown = function (e) {
              let firstDot = that.ctx.getImageData(0, 0, that.canvas.width, that.canvas.height);//在这里储存绘图表面
              that.saveData(firstDot);
              painting = true;
              let x = e.clientX;
              let y = e.clientY;
              lastPoint = {"x": x, "y": y};
              that.ctx.save();
              that.drawCircle(x, y, 0);
            };
            canvas.onmousemove = function (e) {
              if (painting) {
                let x = e.clientX;
                let y = e.clientY;
                let newPoint = {"x": x, "y": y};
                that.drawLine(lastPoint.x, lastPoint.y, newPoint.x, newPoint.y, that.clear);
                lastPoint = newPoint;
              }
            };

            canvas.onmouseup = function () {
              painting = false;
            };

            canvas.mouseleave = function () {
              painting = false;
            }
          }
        },

        drawCircle(x, y, radius) {
          that.ctx.save();
          that.ctx.beginPath();
          that.ctx.arc(x, y, radius, 0, Math.PI * 2);
          that.ctx.fill();
          if (that.clear) {
            that.ctx.clip();
            that.ctx.clearRect(0, 0, that.canvas.width, canvas.height);
            that.ctx.restore();
          }
        },

        getColor() {
          for (let i = 0; i < that.aColorBtn.length; i++) {
            that.aColorBtn[i].onclick = function () {
              for (let i = 0; i < aColorBtn.length; i++) {
                that.aColorBtn[i].classList.remove("active");
                that.classList.add("active");
                activeColor = that.style.backgroundColor;
                that.ctx.fillStyle = activeColor;
                that.ctx.strokeStyle = activeColor;
              }
            }
          }
        },
       // let historyDeta = [];

        saveData (data) {
          (that.historyDeta.length === 10) && (that.historyDeta.shift());// 上限为储存10步，太多了怕挂掉
          that.historyDeta.push(data);
        },

        clearData () {

        },

        drawLine(x1, y1, x2, y2) {
          that.ctx.lineWidth = that.lWidth;
          that.ctx.lineCap = "round";
          that.ctx.lineJoin = "round";
          if (that.clear) {
            that.ctx.save();
            that.ctx.globalCompositeOperation = "destination-out";
            that.ctx.moveTo(x1, y1);
            that.ctx.lineTo(x2, y2);
            that.ctx.stroke();
            that.ctx.closePath();
            that.ctx.clip();
            that.ctx.clearRect(0, 0, canvas.width, canvas.height);
            that.ctx.restore();
          } else {
            that.ctx.moveTo(x1, y1);
            that.ctx.lineTo(x2, y2);
            that.ctx.stroke();
            that.ctx.closePath();
          }
        }
      }
    }
</script>

<style scoped>
  @font-face {font-family: "iconfont";
    src: url('/static/writingBoard/fonts/font_584725_0nyjbeaxjw2ep14i.eot?t=1520519383959'); /* IE9*/
    src: url('/static/writingBoard/fonts/font_584725_0nyjbeaxjw2ep14i.eot?t=1520519383959#iefix') format('embedded-opentype'), /* IE6-IE8 */
    url('data:application/x-font-woff;charset=utf-8;base64,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') format('woff'),
    url('/static/writingBoard/fonts/font_584725_0nyjbeaxjw2ep14i.ttf?t=1520519383959') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+*/
    url('/static/writingBoard/fonts/font_584725_0nyjbeaxjw2ep14i.svg?t=1520519383959#iconfont') format('svg'); /* iOS 4.1- */
  }

  .iconfont {
    font-family:"iconfont" !important;
    font-size:16px;
    font-style:normal;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  .icon-fuzhi:before { content: "\e61a"; }

  .icon-qingchu:before { content: "\e679"; }

  .icon-color:before { content: "\e628"; }

  .icon-xiangpi:before { content: "\e66d"; }

  .icon-icon:before { content: "\e600"; }

  .icon-qianbi:before { content: "\e601"; }

  .icon-cuxixiantiao:before { content: "\e602"; }

  .icon-chexiao:before { content: "\e626"; }
  *{margin:0; padding: 0;user-select: none;}
  body{overflow:hidden}
  #drawing-board{background: white;position:fixed; display: block;cursor: crosshair;}
  .tools{position: fixed;left:0;bottom: 30px; width:100%;display: flex;justify-content: center;text-align: center}
  .tools button{border-radius: 50%;width: 50px;height: 50px; background-color: rgba(255,255,255,0.7);border: 1px solid #eee;outline: none;cursor: pointer;box-sizing: border-box;margin: 0 10px;text-align: center;color:#ccc;line-height: 50px;box-shadow:0 0 8px rgba(0,0,0,0.1); transition: 0.3s;}
  .tools button.active,.tools button:active{box-shadow: 0 0 15px #00CCFF; color:#00CCFF;}
  .tools button i{font-size: 24px;}
  .color-group{position:fixed;width: 30px;left: 30px;top:50%;transform: translate(0,-150px)}
  .color-group ul{list-style: none;}
  .color-group ul li{width: 30px;height: 30px;margin: 10px 0;border-radius: 50%;box-sizing: border-box;border:3px solid white;box-shadow: 0 0 8px rgba(0,0,0,0.2);cursor: pointer;transition: 0.3s;}
  .color-group ul li.active{box-shadow:0 0 15px #00CCFF;}
  #range-wrap{position: fixed;top: 50%;right:30px;width: 30px;height: 150px;margin-top: -75px;}
  #range-wrap input{transform: rotate(-90deg);width: 150px;height: 20px;margin: 0;transform-origin: 75px 75px;    border-radius: 15px;-webkit-appearance: none;outline: none;position: relative;}
  #range-wrap input::after{display: block;content:"";width:0;height: 0;border:5px solid transparent;
    border-right:150px solid #00CCFF;border-left-width:0;position: absolute;left: 0;top: 5px;border-radius:15px; z-index: 0; }
  #range-wrap input[type=range]::-webkit-slider-thumb,#range-wrap input[type=range]::-moz-range-thumb{-webkit-appearance: none;}
  #range-wrap input[type=range]::-webkit-slider-runnable-track,#range-wrap input[type=range]::-moz-range-track {height: 10px;border-radius: 10px;box-shadow: none;}
  #range-wrap input[type=range]::-webkit-slider-thumb{-webkit-appearance: none;height: 20px;width: 20px;margin-top: -1px;background: #ffffff;border-radius: 50%;box-shadow: 0 0 8px #00CCFF;position: relative;z-index: 999;}

  @media screen and (max-width: 768px) {
    .tools{bottom:auto;top:20px;}
    .tools button{width: 35px;height: 35px;line-height: 35px;margin-bottom: 15px;box-shadow:0 0 5px rgba(0,0,0,0.1);}
    .tools button.active,.tools button:active{box-shadow: 0 0 5px #00CCFF;}
    .tools button i{font-size: 18px;}
    .tools #swatches{display: none}
    .color-group{left: 0;top:auto;bottom: 20px;display: flex;width:100%;justify-content: center;text-align: center;transform: translate(0,0)}
    .color-group ul li{display: inline-block;margin:0 5px;}
    .color-group ul li.active{box-shadow:0 0 10px #00CCFF;}
    #range-wrap{right:auto;left: 20px;}
  }
</style>
