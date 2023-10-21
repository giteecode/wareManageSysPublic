<template>
    <div>
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="首页轮播图">
          <el-upload
            action="https://jsonplaceholder.typicode.com/posts/"
            list-type="picture-card"
            :file-list="carouselList"
            multiple
            :before-upload="beforeUploadCarouseImage"
            :on-success="uploadCarouseImageSuccess"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>

        <el-form-item label="课程学习背景图">
          <el-upload
            action="https://jsonplaceholder.typicode.com/posts/"
            multiple
            list-type="picture-card"
            :file-list="backgroundImageList"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>

        <el-form-item label="网站logo">
          <el-upload
            action="https://jsonplaceholder.typicode.com/posts/"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>

        <el-form-item label="校企合作">
          <el-upload
            action="https://jsonplaceholder.typicode.com/posts/"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>

        <el-form-item label="关于我们">
          <el-upload
            action="https://jsonplaceholder.typicode.com/posts/"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>
    export default {
      name: 'image-setting.vue',

      data () {
        return {
          carouselList: [],
          backgroundImageList: []
        }
      },

      mounted () {

      },

      methods: {

        beforeUploadCarouseImage (file) {
          let isImage = (file.type === 'image/jpeg' || file.type === 'image/png')
          let isLimit = file.size / 1024 / 1024 < 5
          if (!isImage) {
            this.$message.error('上传图片只能是 JPG/PNG 格式!')
          }
          if (!isLimit) {
            this.$message.error('上传图片大小不能超过 5MB!')
          }
        },

        uploadCarouseImageSuccess (res) {
          console.log(res.message)
          this.carouselList.push({

          })
        },

        handlePictureCardPreview () {

        },

        handleRemove () {

        }
      }
    }
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .el-upload--picture-card {
    background-color: #fbfdff;
    border: 1px dashed #c0ccda;
    border-radius: 6px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    width: 110px;
    height: 112px;
    line-height: 115px;
    vertical-align: top;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
