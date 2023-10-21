<template>
  <div>
    <el-form ref="form" :model="webSiteConfig" :rules="rules" label-width="120px">

      <el-form-item label="网站logo">
        <div v-if="webSiteConfig.logoUrl">
          <ul class="el-upload-list el-upload-list--picture-card"
              style="display: inline-block;">
            <li class="el-upload-list__item is-success">
              <img :src="fileUrl + webSiteConfig.logoUrl" alt=""
                   class="el-upload-list__item-thumbnail">
              <a class="el-upload-list__item-name"><i class="el-icon-document"></i></a>
              <label class="el-upload-list__item-status-label">
                <i class="el-icon-upload-success el-icon-check"></i></label> <i
              class="el-icon-close"></i>
              <i class="el-icon-close-tip">按 delete 键可删除</i>
              <span class="el-upload-list__item-actions">
                  <span class="el-upload-list__item-preview" @click="lookLogoImage"><i
                    class="el-icon-zoom-in"></i></span>
                    <span class="el-upload-list__item-delete" @click="deleteLogo"><i
                      class="el-icon-delete"></i></span>
                  </span>
              <el-dialog
                :visible.sync="headImageVisible"
                custom-class="image-dialog"
                append-to-body>
                <img width="100%" :src="fileUrl + webSiteConfig.logoUrl" alt="">
              </el-dialog>
            </li>
          </ul>
        </div>

        <div v-else>
          <el-upload
            :action="uploadAction"
            :headers="headers"
            list-type="picture-card"
            :on-success="uploadLogoImageSuccess"
            :before-upload="beforeUploadLogo"
            >
            <i class="el-icon-plus"></i>
          </el-upload>
        </div>
      </el-form-item>

      <el-form-item label="首页轮播图">

        <ul class="el-upload-list el-upload-list--picture-card" style="display: inline-block; margin-top: 20px">
          <li v-for="(image, index) in webSiteConfig.carouselImageList" :key="index" class="el-upload-list__item is-success">
            <img :src="image" alt="" class="el-upload-list__item-thumbnail">
            <label class="el-upload-list__item-status-label"><i
              class="el-icon-upload-success el-icon-check"></i></label>
            <span class="el-upload-list__item-actions">
                                <span class="el-upload-list__item-preview" @click="lookCarouselImage"><i
                                  class="el-icon-zoom-in"></i></span>
                                <span class="el-upload-list__item-delete" @click="deleteCarouselImage(index)"><i
                                  class="el-icon-delete"></i></span>
                            </span>

            <el-dialog
              :visible.sync="headImageVisible"
              custom-class="image-dialog"
              append-to-body>
              <img width="100%" :src="image" alt="">
            </el-dialog>
          </li>
        </ul>

        <el-upload
          :action="uploadAction"
          :headers="headers"
          list-type="picture-card"
          :show-file-list="false"
          :before-upload="beforeUploadCarouselImage"
          :on-success="uploadCarouselImageSuccess"
          >
          <i class="el-icon-plus"></i>
        </el-upload>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="saveWebSiteConfig()">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  export default {
    name: 'webSiteConfig',
    data () {
      return {
        headers: {
          Authorization: localStorage.getItem('Authorization'),
          Platform: 'educationAdmin'
        },
        headImageVisible: false,
        imageList: [],
        webSiteConfig: {
          logoUrl: '',
          carouselImage: '',
          carouselImageList: []
        },
        uploadAction: this.$http.httpUrl('/api/upload/2'),
        fileUrl: this.$http.getFileHost(),
        rules: {
          logoUrl: [
            { required: true, message: '请上传网站logo', trigger: 'blur' }
          ],
          carouselImage: [
            { required: true, message: '请上传网站轮播图', trigger: 'change' }
          ]
        }
      }
    },

    mounted () {
      this.getWebSiteConfig()
    },

    methods: {

      getWebSiteConfig () {
        this.axios.get(this.$http.httpUrl('/system/webSite')).then(response => {
          if (response.data.data) {
            this.webSiteConfig = response.data.data
            let carouselImageList = response.data.data.carouselImageList
            this.webSiteConfig.carouselImageList = []
            carouselImageList.forEach(item => {
              this.webSiteConfig.carouselImageList.push(this.fileUrl + item)
            })
          }
        })
      },

      beforeUploadLogo (file) {
        let isImage = (file.type === 'image/jpeg' || file.type === 'image/png')
        let isLimit = file.size / 1024 / 1024 < 5
        if (!isImage) {
          this.$message.error('上传图片只能是 JPG/PNG 格式!')
        }
        if (!isLimit) {
          this.$message.error('上传图片大小不能超过 5MB!')
        }
      },

      saveWebSiteConfig () {
        this.axios.post(this.$http.httpUrl('/system/webSite'), this.webSiteConfig)
           .then(response => {
             if (response.data.code === 1) {
               this.$message.success(response.data.message)
             } else {
               this.$message.error(response.data.message)
             }
           })
      },

      uploadLogoImageSuccess (res) {
        if (res.code === 1) {
          this.webSiteConfig.logoUrl = res.url
        } else {
          this.$message.error(res.message)
        }
      },

      uploadCarouselImageSuccess (res) {
        if (res.code === 1) {
          this.webSiteConfig.carouselImageList.push(this.fileUrl + res.url)
        } else {
          this.$message.error(res.message)
        }
      },

      deleteLogo () {
        this.$confirm('确定移除吗？')
          .then(() => {
            this.webSiteConfig.logoUrl = ''
          })
          .catch(function (error) {
            console.log(error)
          })
      },

      lookLogoImage () {
        this.headImageVisible = !this.headImageVisible
      },

      lookCarouselImage () {
        this.headImageVisible = !this.headImageVisible
      },

      deleteCarouselImage (index) {
        this.webSiteConfig.carouselImageList.splice(index, 1)
      },

      beforeUploadCarouselImage (file) {
        if (this.webSiteConfig.carouselImageList.length >= 3) {
          this.$message.error('最多只能上传三张轮播图')
          return false
        }
        this.beforeUploadLogo(file)
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
