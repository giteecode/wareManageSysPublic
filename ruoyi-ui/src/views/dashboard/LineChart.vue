<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

import { statsIndexLower } from "@/api/stats/index";

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
      default: '350px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
  },
  data() {
    return {
      chart: null,
      lineChartData: {},
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
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
      statsIndexLower().then(response => {
        this.lineChartData = response.data;
        this.initChart(this.lineChartData);
      });
    },
    initChart(lineChartData) {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(lineChartData)
    },
    setOptions({ purchaseReturnArr, productionReturnArr, commonReturnArr } = {}) {
      this.chart.setOption({
        xAxis: {
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
          boundaryGap: false,
          axisTick: { show: false }
        },
        grid: { left: 10, right: 10, bottom: 20, top: 30, containLabel: true },
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'cross' },
          padding: [5, 10]
        },
        yAxis: { axisTick: { show: false } },
        legend: { data: ['采购退货', '生产退货', '通用退货'] },
        series: [
          {
            name: '采购退货', 
            itemStyle: {
              normal: { color: '#36a3f7', lineStyle: { color: '#36a3f7', width: 2 } }
            },
            smooth: true,
            type: 'line',
            data: purchaseReturnArr,
            animationDuration: 2800,
            animationEasing: 'cubicInOut'
          },
          {
            name: '生产退货',
            smooth: true,
            type: 'line',
            itemStyle: {
              normal: { 
                color: '#ffc000', 
                lineStyle: { color: '#ffc000', width: 2 },
                areaStyle: { color: '#ffc000' }
              }
            },
            data: productionReturnArr,
            animationDuration: 2800,
            animationEasing: 'quadraticOut'
          },
          {
            name: '通用退货',
            smooth: true,
            type: 'line',
            itemStyle: {
              normal: {
                color: '#34bfa3', 
                lineStyle: { color: '#34bfa3', width: 2 },
                areaStyle: { color: '#34bfa3' }
              }
            },
            data: commonReturnArr,
            animationDuration: 2800,
            animationEasing: 'quadraticOut'
          }
        ]
      })
    }
  }
}
</script>
