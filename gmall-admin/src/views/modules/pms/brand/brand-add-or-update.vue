<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="品牌名" prop="name">
      <el-input v-model="dataForm.name" placeholder="品牌名"></el-input>
    </el-form-item>
    <el-form-item label="品牌logo" prop="logo">
      <!-- <el-input v-model="dataForm.logo" placeholder="品牌logo地址"></el-input> -->
      <SingleUpload v-model="dataForm.logo"
                           style="width: 300px;display: inline-block;margin-left: 10px"></SingleUpload>
    </el-form-item>

    <el-form-item label="检索首字母" prop="firstLetter">
      <el-input v-model="dataForm.firstLetter" placeholder="检索首字母" maxlength="1" show-word-limit></el-input>
    </el-form-item>
    <el-form-item label="是否展示" prop="status">
      <el-switch v-model="dataForm.status"  active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="1"
              :inactive-value="0"></el-switch>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import SingleUpload from "@/components/upload/singleUpload"
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          name: '',
          logo: '',
          remark: '',
          firstLetter: '',
          status: ''
        },
        dataRule: {
          name: [
            { required: true, message: '品牌名不能为空', trigger: 'blur' }
          ],
          logo: [
            { required: true, message: '品牌logo地址不能为空', trigger: 'blur' }
          ],
          firstLetter: [
            { required: true, message: '检索首字母不能为空', trigger: 'blur' }
          ],
           status: [
            { required: true, message: '是否展示不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    components:{SingleUpload},
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornApiUrl(`/pms/brand/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.data.name
                this.dataForm.logo = data.data.logo
                this.dataForm.remark = data.data.remark
                this.dataForm.status = data.data.status
                this.dataForm.firstLetter = data.data.firstLetter
                this.dataForm.sort = data.data.sort
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornApiUrl(`/pms/brand${!this.dataForm.id ? '' : '/update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'name': this.dataForm.name,
                'logo': this.dataForm.logo,
                'remark': this.dataForm.remark,
                'status': this.dataForm.status,
                'firstLetter': this.dataForm.firstLetter,
                'sort': this.dataForm.sort
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
            }).catch((err)=>{
              this.$message.error("提交数据非法，请重新录入")
            })
          }
        })
      }
    }
  }
</script>
