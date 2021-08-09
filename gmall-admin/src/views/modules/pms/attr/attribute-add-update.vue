<template>
  <el-dialog :title="dataForm.id ? '修改' : '新增'" :visible.sync="visible">
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      label-width="120px"
      @keyup.enter.native="dataFormSubmit()"
    >
      <el-form-item label="属性名称" prop="name">
        <el-input v-model="dataForm.name"></el-input>
      </el-form-item>
      <el-form-item
        v-for="(itemAttr, index) in dataForm.valueSelect"
        :label="'属性值'+(index+1)"
        :key="index"
        :prop="dataForm.valueSelect[index]"
      >
        <el-input v-model="dataForm.valueSelect[index]" style="width:40%;margin-right:20px;"></el-input>
        <el-button @click.prevent="removeAttr(itemAttr,index)">删除</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" class="el-button--mini" @click.prevent="addAttribute()">添加属性值</el-button>
      </el-form-item>
      <el-form-item label="属性类型" prop="type">
        <el-select v-model="dataForm.type" placeholder="请选择属性类型">
          <el-option label="销售属性" :value="0"></el-option>
          <el-option label="基本属性" :value="1"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="是否需要检索" prop="searchType">
        <el-switch
          v-model="dataForm.searchType"
          active-color="#13ce66"
          inactive-color="#ff4949"
          :active-value="1"
          :inactive-value="0"
        ></el-switch>
      </el-form-item>
      <el-form-item label="是否启用" prop="enable">
        <el-switch
          v-model="dataForm.enable"
          active-color="#13ce66"
          inactive-color="#ff4949"
          :active-value="1"
          :inactive-value="0"
        ></el-switch>
      </el-form-item>
      <el-form-item label="展示在介绍" prop="showDesc">
        <el-switch
          v-model="dataForm.showDesc"
          active-color="#13ce66"
          inactive-color="#ff4949"
          :active-value="1"
          :inactive-value="0"
        ></el-switch>
      </el-form-item>
      <!-- <el-input v-model="dataForm.logo" placeholder="品牌logo地址"></el-input> -->
      <el-form-item label="图标" prop="icon">
        <SingleUpload
          v-model="dataForm.icon"
          style="width: 300px;display: inline-block;margin-left: 10px"
        ></SingleUpload>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import SingleUpload from "@/components/upload/singleUpload";
export default {
  props: ["groupId", "catId"],
  data() {
    return {
      visible: false,
      dataForm: {
        id: 0,
        name: "",
        valueSelect: [""],
        type: "",
        groupId: "",
        searchType: 0,
        icon: "",
        categoryId: 0,
        enable: 1,
        showDesc: 0
      },
      dataRule: {
        name: [
          { required: true, message: "属性名称不能为空", trigger: "blur" }
        ],
        valueSelect: [
          {
            required: true,
            message: "属性值不能为空",
            trigger: "blur",
            type: Array
          }
        ],
        type: [
          { required: true, message: "属性类型为必选项", trigger: "blur" }
        ]
      }
    }
  },
  watch: {
  },
  components: { SingleUpload },
  methods: {
    init(id) {
      this.visible = true;
      if (id) {
        this.$nextTick(() => {
          // this.$refs["dataForm"].resetFields();
          // 修改的时候回显分组数据
          this.$http({
            url: this.$http.adornApiUrl(`/pms/attr/` + id),
            method: "get"
          }).then(({ data }) => {
            console.log(data);
            if (data && data.code === 0) {
              this.dataForm = data.data
              this.dataForm.valueSelect = this.dataForm.valueSelect.split(",")
            }
          })
        })
      } else {
        this.dataForm = {
          id: 0,
          name: "",
          valueSelect: [""],
          type: "",
          groupId: this.groupId,
          searchType: 0,
          icon: "",
          categoryId: this.catId,
          enable: 1,
          showDesc: 0
        }
      }
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        console.log(this.dataForm);
        if (valid) {
          //验证通过
          var data = this.dataForm;
          data.valueSelect = data.valueSelect.join(",");
          //1、保存属性信息
          var url =
            this.dataForm.id && this.dataForm.id > 0
              ? "/pms/attr/update"
              : "/pms/attr";
          this.$http({
            url: this.$http.adornApiUrl(url),
            method: "post",
            data: this.$http.adornData(data, false, "json")
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "属性保存成功",
                type: "success"
              });
              this.$emit("saveSuccess", this.dataForm.type);
              //3、清空数据
              this.clearFileds();
              //2、关闭模态框
              this.visible = false;
            } else {
              this.$message({
                message: "属性保存失败,请检查填写的值",
                type: "error"
              });
            }
          });
        }
      });
    },
    addAttribute() {
      this.dataForm.valueSelect.push("");
    },
    removeAttr(itemAttr, index) {
      if (index == 0) {
        return false;
      }
      this.dataForm.valueSelect.splice(index, 1);
    },
    clearFileds() {
      this.dataForm = {
        id: 0,
        name: "",
        valueSelect: [""],
        type: "",
        groupId: "",
        searchType: 0,
        icon: "",
        categoryId: 0,
        enable: 1
      };
    },
    showDetails(data) {
      //当前属性赋值；
      // this.dataForm = data;
      // //将valueSelect转化为数组
      // this.dataForm.valueSelect = ;
      this.dataForm = {
        id: data.id,
        name: data.name,
        valueSelect: data.valueSelect.split(","),
        type: data.type,
        groupId: data.group ? data.group.groupId : 0,
        searchType: data.searchType,
        icon: data.icon,
        categoryId: data.categoryId,
        enable: data.enable,
        showDesc: data.showDesc
      };
      console.log("准备回显的数据，", this.dataForm);
      //初始化弹出
      this.init();
    }
  }
};
</script>
