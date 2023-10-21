<template>
  <div>
    <el-form-item>
      <el-select
        v-model="schoolType"
        filterable
        clearable
        placeholder="请选择阶段">
        <el-option
          v-for="item in schoolTypeList"
          :key="item.id"
          :label="item.value"
          :value="item.code">
        </el-option>
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-select
        v-model="gradeInfoId"
        filterable
        clearable
        placeholder="请选择年级">
        <el-option
          v-for="item in gradeInfoList"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-select
        v-if="subjectList.length > 0"
        v-model="subjectId"
        filterable
        clearable
        placeholder="请选择科目">
        <el-option
          v-for="item in subjectList"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
    </el-form-item>

    <el-form-item>
       <el-input v-model="keyWord" placeholder="输入题目内容" type="text"></el-input>
    </el-form-item>

    <el-form-item>
      <el-button v-if="hasPermission([permission])" type="success" icon="el-icon-search" @click="searchQuestion" >搜索</el-button>
    </el-form-item>
  </div>
</template>

<script>
  import {getGradeInfoList} from '@/api/gradeInfo'
  import {getSubjectInfoList} from '@/api/subjectInfo'
  export default {
    name: 'educationSearch',
    props: {
      school_type_list: {
        type: Array,
        default: []
      },
      permission: {
        type: String,
        default: ''
      }
    },

    mounted () {
      this.schoolTypeList = this.school_type_list
      getGradeInfoList({}).then(response => {
        this.gradeInfoList = response.data.data.dataList
      })
    },

    data () {
      return {
        schoolType: null,
        gradeInfoId: null,
        subjectId: null,
        schoolTypeList: [],
        gradeInfoList: [],
        keyWord: '',
        subjectList: []
      }
    },

    watch: {
      'school_type_list' (val) {
        this.schoolTypeList = val
      },

      'schoolType' (val) {
        let params = {
          schoolType: val
        }
        getGradeInfoList(params).then(response => {
          this.gradeInfoList = response.data.data.dataList
        })
      },

      'gradeInfoId' (val) {
        let params = {
          gradeInfoId: val
        }
        getSubjectInfoList(params).then(response => {
          this.subjectList = response.data.data.dataList
        })
      }
    },

    methods: {
      searchQuestion () {
        let form = {
          schoolType: this.schoolType,
          gradeInfoId: this.gradeInfoId,
          subjectId: this.subjectId,
          keyWord: this.keyWord
        }
        this.$emit('click', form)
      }
    }
  }
</script>
