<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

const animationDuration = 6000

import { statsIndexMiddle } from "@/api/stats/index";

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '322px'
    },
  },
  data() {
    return {
      chart: null,
      barChartData: {},
    }
  },
  created() {
    this.loadDate();
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    loadDate(){
      statsIndexMiddle().then(response => {
        this.barChartData = response.data;
        this.initChart(this.barChartData);
      });
    },
    initChart(barChartData) {
      this.chart = echarts.init(this.$el, 'macarons')
      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          top: 10,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        color: ['#36a3f7','#ffc000', '#34bfa3'],
        xAxis: [{
          type: 'category',
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
          axisTick: {
            alignWithLabel: true
          }
        }],
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          }
        }],
        series: [
          {
            name: '采购入库单',
            type: 'bar',
            stack: 'vistors',
            barWidth: '60%',
            data: barChartData.purchaseArr,
            animationDuration
          }, 
          {
            name: '生产出库单',
            type: 'bar',
            stack: 'vistors',
            barWidth: '60%',
            data: barChartData.productionArr,
            animationDuration
          }, 
          {
            name: '通用出库单',
            type: 'bar',
            stack: 'vistors',
            barWidth: '60%',
            data: barChartData.commonArr,
            animationDuration
          }
        ]
      })
    }
  }
}
</script>
