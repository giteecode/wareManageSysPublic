<template>
  <div class="mod-demo-echarts">

    <div class="dashboard-editor-container">
      <el-row :gutter="40" class="panel-group">
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-message">
              <icon-svg style="font-size: 45px" name="student" class="site-sidebar__menu-icon"></icon-svg>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">学员数量</div>
              <div style="font-size: 20px">{{countInfo.studentNumber}}</div>
            </div>
          </div>
        </el-col>
        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-shoppingCard">
              <icon-svg style="font-size: 45px" name="question" class="site-sidebar__menu-icon"></icon-svg>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">试题数量</div>
              <div style="font-size: 20px">{{countInfo.questionNumber}}</div>
            </div>
          </div>
        </el-col>

        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-shoppingCard">
              <icon-svg style="font-size: 45px" name="paper-setting" class="site-sidebar__menu-icon"></icon-svg>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">试卷数量</div>
              <div style="font-size: 20px">{{countInfo.testPaperInfoNumber}}</div>
            </div>
          </div>
        </el-col>

        <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
          <div class="card-panel">
            <div class="card-panel-icon-wrapper icon-shoppingCard">
              <icon-svg style="font-size: 45px" name="paper" class="site-sidebar__menu-icon"></icon-svg>
            </div>
            <div class="card-panel-description">
              <div class="card-panel-text">待批改试卷</div>
              <div style="font-size: 20px">{{countInfo.toBeCorrectedExamNumber}}</div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

     <el-row style="margin-top: 10px;" :gutter="20">
       <el-col :span="24">
         <el-card>
           <div id="J_chartLineBox" class="chart-box"></div>
         </el-card>
       </el-col>
       <!-- <el-col :span="24">
          <el-card>
            <div id="J_chartBarBox" class="chart-box"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card>
            <div id="J_chartPieBox" class="chart-box"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card>
            <div id="J_chartScatterBox" class="chart-box"></div>
          </el-card>
        </el-col>-->
      </el-row>
  </div>
</template>

<script>
   import echarts from 'echarts'
   export default {
     data () {
       return {
         chartLine: null,
         chartBar: null,
         chartPie: null,
         countInfo: {},
         chartScatter: null
       }
     },
     mounted () {
      // this.initChartBar()
      // this.initChartPie()
      // this.initChartScatter()
       this.getHeaderData()
     //  this.initRegionInfo()
     },

     activated () {
      // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
       if (this.chartLine) {
         this.chartLine.resize()
       }
       if (this.chartBar) {
         this.chartBar.resize()
       }
       if (this.chartPie) {
         this.chartPie.resize()
       }
       if (this.chartScatter) {
         this.chartScatter.resize()
       }
     },
     methods: {

       getHeaderData () {
         this.axios.get(this.$http.httpUrl('/system/home/headDataCount'))
           .then(response => {
             if (response.data.code === 1) {
               this.countInfo = response.data.data
               this.initChartLine(response.data.data.examInfoData)
             }
           })
       },

      // 初始化考试记录统计
       initChartLine (data) {
         let x = [] // x轴数据
         let y = []
         data.forEach(item => {
           x.push(item.dayGroup)
           y.push(item.examNumber)
         })

         let option = {
           'title': {
             'text': '考试记录统计'
           },
           'tooltip': {
             'trigger': 'axis'
           },
           'legend': {
             // eslint-disable-next-line standard/array-bracket-even-spacing
             'data': [ '考试记录']
           },
           'grid': {
             'left': '3%',
             'right': '4%',
             'bottom': '3%',
             'containLabel': true
           },
           'toolbox': {
             'feature': {
               'saveAsImage': { }
             }
           },
           'xAxis': {
             'type': 'category',
             'boundaryGap': false,
             'data': x
           },
           'yAxis': {
             'type': 'value'
           },
           'series': [
             {
               'name': '考试数量',
               'type': 'line',
               'stack': '总量',
               'data': y
             }
           ]
         }
         this.chartLine = echarts.init(document.getElementById('J_chartLineBox'))
         this.chartLine.setOption(option)
         window.addEventListener('resize', () => {
           this.chartLine.resize()
         })
       },

      // 柱状图
       initChartBar () {
         let option = {
           tooltip: {
             trigger: 'axis',
             axisPointer: {
               type: 'shadow'
             }
           },
           legend: {
             data: ['新增学员', '新增学校', '新增试题', '新增试卷', '新增考试']
           },
           grid: {
             left: '3%',
             right: '4%',
             bottom: '3%',
             containLabel: true
           },
           xAxis: [
             {
               type: 'category',
               // eslint-disable-next-line standard/array-bracket-even-spacing
               'data': [ '2020/02/26', '2020/02/27', '2020/02/28', '2020/02/29', '2020/03/01', '2020/03/02', '2020/03/03']
             }
           ],
           yAxis: [
             {
               type: 'value'
             }
           ],
           series: [
             {
               name: '新增学员',
               type: 'bar',
               data: [2, 5, 3, 6, 8, 9, 7]
             },
             {
               name: '新增学校',
               type: 'bar',
               stack: '广告',
               data: [2, 0, 0, 0, 0, 0, 0]
             },
             {
               name: '新增试题',
               type: 'bar',
               stack: '广告',
               data: [2, 0, 0, 0, 0, 0, 0]
             },
             {
               name: '新增试卷',
               type: 'bar',
               stack: '广告',
               data: [2, 5, 3, 6, 8, 9, 7]
             },
             {
               name: '新增考试',
               type: 'bar',
               data: [2, 5, 3, 6, 8, 9, 7],
               markLine: {
                 lineStyle: {
                   normal: {
                     type: 'dashed'
                   }
                 },
                 data: [
                  [{ type: 'min' }, { type: 'max' }]
                 ]
               }
             }
           ]
         }
         this.chartBar = echarts.init(document.getElementById('J_chartBarBox'))
         this.chartBar.setOption(option)
         window.addEventListener('resize', () => {
           this.chartBar.resize()
         })
       },
      // 饼状图
       initChartPie () {
         let option = {
           title: {
             text: '学员地区分布图',
             subtext: '',
             left: 'center'
           },
           tooltip: {
             trigger: 'item',
             formatter: '{a} <br/>{b} : {c} ({d}%)'
           },
           legend: {
             orient: 'vertical',
             left: 'left',
             data: ['江西', '湖北', '四川', '广东', '北京']
           },
           series: [
             {
               name: '访问来源',
               type: 'pie',
               radius: '55%',
               center: ['50%', '60%'],
               data: [
                {value: 335, name: '江西'},
                {value: 310, name: '湖北'},
                {value: 234, name: '四川'},
                {value: 135, name: '广东'},
                {value: 1548, name: '北京'}
               ],
               emphasis: {
                 itemStyle: {
                   shadowBlur: 10,
                   shadowOffsetX: 0,
                   shadowColor: 'rgba(0, 0, 0, 0.5)'
                 }
               }
             }
           ]
         }

         this.chartPie = echarts.init(document.getElementById('J_chartPieBox'))
         this.chartPie.setOption(option)
         window.addEventListener('resize', () => {
           this.chartPie.resize()
         })
       },
       initRegionInfo () {
         this.axios.get(this.$http.httpUrl('/system/home/getRegionInfoData')).then(response => {
           if (response.data.code === 1) {
             this.initRegionDataInfo(response.data.data)
           }
         })
       },

      // 散点图
       initRegionDataInfo (data) {
         let x = []
         let y = []
         data.forEach(item => {
           x.push(item.name)
           y.push(item.value)
         })
         let option = {
           title: {
             text: '加盟学校地区分布图',
             subtext: '',
             left: 'center'
           },
           tooltip: {
             trigger: 'item',
             formatter: '{a} <br/>{b} : {c} ({d}%)'
           },
           legend: {
             orient: 'vertical',
             left: 'left',
             data: x
           },
           series: [
             {
               name: '访问来源',
               type: 'pie',
               radius: '55%',
               center: ['50%', '60%'],
               data: data,
               emphasis: {
                 itemStyle: {
                   shadowBlur: 10,
                   shadowOffsetX: 0,
                   shadowColor: 'rgba(0, 0, 0, 0.5)'
                 }
               }
             }
           ]
         }
         this.chartPie = echarts.init(document.getElementById('J_chartScatterBox'))
         this.chartPie.setOption(option)
         window.addEventListener('resize', () => {
           this.chartPie.resize()
         })
       }
     }
}
</script>

<style lang="scss">

  .dashboard-editor-container {
    padding: 32px;
    background-color: rgb(240, 242, 245);
    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }
  }

  .panel-group {
    margin-top: 18px;

    .card-panel-col{
      margin-bottom: 32px;
    }
    .card-panel {
      height: 108px;
      cursor: pointer;
      font-size: 12px;
      position: relative;
      overflow: hidden;
      color: #666;
      background: #fff;
      box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
      border-color: rgba(0, 0, 0, .05);
      &:hover {
        .card-panel-icon-wrapper {
          color: #fff;
        }
        .icon-people {
          background: #40c9c6;
        }
        .icon-message {
          background: #36a3f7;
        }
        .icon-money {
          background: #f4516c;
        }
        .icon-shoppingCard {
          background: #34bfa3
        }
      }
      .icon-people {
        color: #40c9c6;
      }
      .icon-message {
        color: #36a3f7;
      }
      .icon-money {
        color: #f4516c;
      }
      .icon-shoppingCard {
        color: #34bfa3
      }
      .card-panel-icon-wrapper {
        float: left;
        margin: 14px 0 0 14px;
        padding: 16px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }
      .card-panel-icon {
        float: left;
        font-size: 48px;
      }
      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 26px;
        margin-left: 0px;
        .card-panel-text {
          line-height: 18px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 16px;
          margin-bottom: 12px;
        }
        .card-panel-num {
          font-size: 20px;
        }
      }
    }
  }
  .mod-demo-echarts {
    > .el-alert {
      margin-bottom: 10px;
    }
    > .el-row {
      margin-top: -10px;
      margin-bottom: -10px;
      .el-col {
        padding-top: 10px;
        padding-bottom: 10px;
      }
    }
    .chart-box {
      min-height: 400px;
    }
  }
</style>
