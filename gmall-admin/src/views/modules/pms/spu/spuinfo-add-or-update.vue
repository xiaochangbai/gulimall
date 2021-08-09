<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="商品名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="商品名称"></el-input>
    </el-form-item>
    <el-form-item label="所属分类" prop="categoryId">
      <el-input v-model="dataForm.categoryId" placeholder="所属分类id"></el-input>
    </el-form-item>
    <el-form-item label="品牌" prop="brandId">
      <el-select
        v-model="dataForm.brandId"
        filterable
        remote
        reserve-keyword
        placeholder="请选择品牌"
        :remote-method="loadBrand"
        :loading="loading">
        <el-option
          v-for="item in brands"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="上架状态" prop="publishStatus">
      <el-input v-model="dataForm.publishStatus" placeholder="上架状态"></el-input>
    </el-form-item>
    <el-form-item label="" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="updateTime">
      <el-input v-model="dataForm.updateTime" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        loading: false,
        brands: [],
        dataForm: {
          id: 0,
          name: '',
          catagoryId: '',
          brandId: '',
          publishStatus: '',
          createTime: '',
          updateTime: ''
        },
        dataRule: {
          name: [
            { required: true, message: '商品名称不能为空', trigger: 'blur' }
          ],
          catagoryId: [
            { required: true, message: '所属分类不能为空', trigger: 'blur' }
          ],
          brandId: [
            { required: true, message: '品牌不能为空', trigger: 'blur' }
          ],
          publishStatus: [
            { required: true, message: '上架状态不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          updateTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornApiUrl(`/pms/spu/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.spuName = data.data.spuName
                this.dataForm.catagoryId = data.data.catagoryId
                this.dataForm.brandId = data.data.brandId
                this.dataForm.publishStatus = data.data.publishStatus
                this.dataForm.createTime = data.data.createTime
                this.dataForm.updateTime = data.data.updateTime
              }
            })
          } else {
            this.dataForm = {
              id: 0,
              name: '',
              catagoryId: '',
              brandId: '',
              publishStatus: '',
              createTime: '',
              updateTime: ''
            }
          }
        })
      },
      loadBrand (key) {
        if (key !== '') {
          this.loading = true
          this.$http({
            url: this.$http.adornApiUrl(`/pms/brand/query/${key}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            this.loading = false
            if (data && data.code === 0) {
              this.brands = data.data
            }
          })
        }
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornApiUrl(`/pms/spu/${!this.dataForm.id ? '' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'spuName': this.dataForm.spuName,
                'spuDescription': this.dataForm.spuDescription,
                'catagoryId': this.dataForm.catagoryId,
                'brandId': this.dataForm.brandId,
                'publishStatus': this.dataForm.publishStatus,
                'createTime': this.dataForm.createTime,
                'updateTime': this.dataForm.updateTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
