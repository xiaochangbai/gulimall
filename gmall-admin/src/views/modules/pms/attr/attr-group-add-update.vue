<!--  -->
<template>
  <div class>
    <el-dialog :title="title" :visible.sync="dialogVisible">
      <el-form ref="attrGroupform" :model="dataForm" label-width="80px">
        <el-form-item label="分组名">
          <el-input v-model="dataForm.name"></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="dataForm.sort"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="dataForm.remark"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <single-upload
            v-model="dataForm.icon"
            style="width: 300px;display: inline-block;margin-left: 10px"
          ></single-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import SingleUpload from "@/components/upload/singleUpload";
export default {
  //import引入的组件需要注入到对象中才能使用
  components: { SingleUpload },
  data() {
    //这里存放数据
    return {
      title: "属性组添加",
      dialogVisible: false,
      dataForm: {
        categoryId: 0,
        name: "",
        remark: "",
        icon: "",
        sort: 0
      }
    };
  },
  props: ["catId"],
  //监听属性 类似于data概念
  computed: {
  },
  //监控data中的数据变化
  watch: {
    catId(val) {
      this.categoryId = this.catId;
    }
  },
  //方法集合
  methods: {
    handleSubmit() {
      this.dialogVisible = false;
      //清除数据；
      var url = (this.dataForm.id && this.dataForm.id > 0)?"/pms/attrgroup/update":"/pms/attrgroup";

      this.$http({
        url: this.$http.adornApiUrl(url),
        method: "post",
        data: this.$http.adornData(this.dataForm, false, "json")
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.$message({
            type: "success",
            message: "属性分组保存成功"
          });
          this.$emit("saveSuccess",data);
        } else {
        }
      });
    },
    init() {
      this.title = "属性组添加";
      this.dataForm = {
        categoryId: this.catId,
        name: "",
        remark: "",
        icon: "",
        sort: 0,
        id: 0
      }
      this.dialogVisible = true
    },
    showDetails(data) {
      this.dataForm = data;
      this.dialogVisible = true;
      this.title = "属性组修改";
      console.log("修改传来的数据",data);
    }
  },
  //生命周期 - 创建完成（可以访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {},
  beforeCreate() {}, //生命周期 - 创建之前
  beforeMount() {}, //生命周期 - 挂载之前
  beforeUpdate() {}, //生命周期 - 更新之前
  updated() {}, //生命周期 - 更新之后
  beforeDestroy() {}, //生命周期 - 销毁之前
  destroyed() {}, //生命周期 - 销毁完成
  activated() {} //如果页面有keep-alive缓存功能，这个函数会触发
};
</script>
<style lang='scss' scoped>
//@import url(); 引入公共css类
</style>
