<template>
  <el-row :gutter="40" class="panel-group">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-people">
          <svg-icon icon-class="documentation" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div @click="jumpPage('/base/mat')">
            <div class="card-panel-text">仓库物料种类</div>
            <count-to :start-val="0" :end-val="panelGroupData.matTotal" :duration="2800" class="card-panel-num" />
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-message">
          <svg-icon icon-class="tab" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div @click="jumpPage('/purchase/inOrder')">
            <div class="card-panel-text">采购入库单</div>
            <count-to :start-val="0" :end-val="panelGroupData.purchase" :duration="3000" class="card-panel-num" />
          </div>
          <div class="return-div" @click="jumpPage('/purchase/inReturn')">
            <div class="card-panel-text panel-return">退货单</div>
            <count-to :start-val="0" :end-val="panelGroupData.purchaseReturn" :duration="3200" class="card-panel-num" />
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-money">
          <svg-icon icon-class="tab" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div @click="jumpPage('/prod/outOrder')">
            <div class="card-panel-text">生产出库单</div>
            <count-to :start-val="0" :end-val="panelGroupData.production" :duration="3200" class="card-panel-num" />
          </div>
          <div class="return-div" @click="jumpPage('/prod/outReturn')">
            <div class="card-panel-text panel-return">退货单</div>
            <count-to :start-val="0" :end-val="panelGroupData.productionReturn" :duration="3200" class="card-panel-num" />
          </div>
        </div>
      </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
      <div class="card-panel">
        <div class="card-panel-icon-wrapper icon-shopping">
          <svg-icon icon-class="tab" class-name="card-panel-icon" />
        </div>
        <div class="card-panel-description">
          <div @click="jumpPage('/common/commonOutOrder')">
            <div class="card-panel-text">通用出库单</div>
            <count-to :start-val="0" :end-val="panelGroupData.common" :duration="3400" class="card-panel-num" />
          </div>
          <div class="return-div" @click="jumpPage('/common/commonOutReturn')">
            <div class="card-panel-text panel-return">退货单</div>
            <count-to :start-val="0" :end-val="panelGroupData.commonReturn" :duration="3200" class="card-panel-num" />
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'
import { statsIndexUpper } from "@/api/stats/index";

export default {
  data() {
    return {
      panelGroupData: {},
    }
  },
  components: { CountTo },
  created() {
    this.getData();
  },
  methods: {
    getData(){
      statsIndexUpper().then(response => {
        this.panelGroupData = response.data;
      });
    },
    jumpPage(pageUrl){
      this.$router.push({path: pageUrl});
    },
  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  // margin-top: 18px;

  .card-panel-col {
    margin-bottom: 10px;
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
        background: #ffc000;
      }

      .icon-shopping {
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
      color: #ffc000;
    }

    .icon-shopping {
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
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;
      display: flex;
      justify-content: flex-end;
      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
        display: flex;
        justify-content: center;
        text-decoration: underline;
      }
      .return-div{
        margin-left: 40px;
        color: red;
      }
      .panel-return{
        color: red;
      }
    }
    
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
