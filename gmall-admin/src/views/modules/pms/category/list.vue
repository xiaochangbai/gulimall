<template>
  <div>
    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-dark">
          <el-button type="primary" @click="addLevel1Category">添加一级分类</el-button>
          <el-button type="primary" @click="addChildCategory">添加子分类</el-button>
          <el-button type="danger" @click="deleteCategory">删除分类</el-button>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <div class="left-tree">
          <!--ref很重要，方便父组件触发子组件的一些方法  -->
          <CategoryTree @treeNode="acceptTreeNode" ref="categoryTreeComponent" />
        </div>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card">
          基本信息：
          <hr />
          <el-tag type="success">分类Id:{{categoryInfo.id}}</el-tag>
          <el-tag>名称:{{categoryInfo.name}}</el-tag>
        </el-card>
        <el-card class="box-card">
          修改信息：
          <hr />
          <el-form ref="categoryForm" :model="categoryInfo" label-position="right" size="mini">
            <el-form-item label="分类名称">
              <el-input v-model="categoryInfo.name"></el-input>
            </el-form-item>
            <el-form-item label="选择父分类">
              <el-input v-model="categoryInfo.parentName"></el-input>
            </el-form-item>
            <el-form-item label="图标">
              <el-input v-model="categoryInfo.icon"></el-input>
            </el-form-item>
            <el-form-item label="计量单位">
              <el-input v-model="categoryInfo.unit"></el-input>
            </el-form-item>
            <el-form-item label="是否显示">
              <el-switch v-model="categoryInfo.status" active-value="1" inactive-value="0"></el-switch>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="categoryInfoSubmit">{{btnMsg}}</el-button>
              <el-button>取消</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import CategoryTree from "../../common/category-tree";


export default {
  components: { CategoryTree },
  data() {
    return {
      btnMsg: "新增",
      treeObj: {},
      currentTreeNode: {}, //原始的treeNode
      categoryInfo: {
        id: "",
        parentId: "",
        status: "1",
        name: "",
        icon: "",
        unit: "",
        parentName: ""
      } //解构出来的category信息
    };
  },
  methods: {
    //接受子组件传递来的值，并保存
    acceptTreeNode(data, treeObj) {
      this.currentTreeNode = data;
      this.treeObj = treeObj;
      //获取当前节点的父节点
      let parentNode = this.treeObj.getNodeByParam(
        "id",
        this.currentTreeNode.parentId,
        null
      );
      console.log("父节点：", parentNode);
      this.categoryInfo = {
        id: data.id,
        // isParent: data.isParent == true ? 1 : 0,
        parentId: data.parentId,
        status: data.status + "",
        name: data.name,
        icon: data.icon,
        unit: data.unit,
        parentName: parentNode == null ? "无" : parentNode.name
      };
      this.btnMsg = "修改";
    },
    //post请求用data，get用params
    categoryInfoSubmit() {
      new Promise((resolve,reject)=>{
        if(this.btnMsg == "修改"){
            this.$http({
              url: this.$http.adornApiUrl("/pms/category/update"),
              method: "POST",
              data: this.$http.adornData(this.categoryInfo, true, "json")
            }).then(({ data }) => {
              if (data && data.code === 0) {
                //这个方法执行完成后再展开节点
                this.$message({
                  message: "操作成功",
                  type: "success",
                  duration: 1500
                });
                resolve(data);
              } else {
                reject(data);
              }
            });
        }else{
          this.$http({
            url: this.$http.adornApiUrl("/pms/category"),
            method: "post",
            data: this.$http.adornData(this.categoryInfo, true, "json")
            }).then(({ data }) => {
              if (data && data.code === 0) {
                this.$message({
                  type: 'success',
                  message: "新增成功"
                });
                resolve(data);
              } else {
                this.$message({
                  type: 'error',
                  message: "新增失败"
                });
                reject(data);
              }
          });
        }

      }).then((data)=>{
      //刷新树
        this.refreshTree();
      });


    },
    //删除选中的分类
    deleteCategory() {
      //1、先判断当前选中的节点是否还有子分类，如果没有就可以删除
      if (this.currentTreeNode.isParent) {
        //有子节点不能删除，没有才可以
        this.$alert("当前分类有子分类，不能删除", "提示");
      } else {
        this.$confirm("将要删除名为【" + this.categoryInfo.name + "】的分类节点?","提示", {confirmButtonText: "确定",cancelButtonText: "取消",type: "warning"})
            .then(() => {
              this.$http({
                  url: this.$http.adornApiUrl("/pms/category/delete"),
                  method: "post",
                  data: this.$http.adornData([this.categoryInfo.id], false, "json")
                })
                .then(({ data }) => {
                    if (data && data.code === 0) {
                      this.$message({ type: "success",  message: "删除成功!" });
                      //删除完成重新刷新树，并清空状态信息
                      this.categoryInfo = {};
                      this.btnMsg = "新增";
                      this.refreshTree();
                    } else {
                      this.$message({ type: "error",  message: "删除失败!" });
                    }
                });

            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消删除"
              });
          });
      }
    },
    //添加子分类
    addChildCategory() {

        this.btnMsg = "新增";
        this.categoryInfo = {
          parentName: this.categoryInfo.name==""?"无":this.categoryInfo.name, //空
          parentId: this.categoryInfo.id==""?0:this.categoryInfo.id ,
          icon: this.categoryInfo.icon ,
          unit: this.categoryInfo.unit ,
          status:"1"
        }
    },
    //添加根分类
    addLevel1Category(){
      this.btnMsg = "新增";
      this.categoryInfo = {parentName:"无",parentId:0,status:"1"};

    },
    //刷新树
    refreshTree(){
      var promise = this.$refs.categoryTreeComponent.initCategoryList();
      promise.then(() => {
        //找到当前节点的父节点
        var znode = this.treeObj.getNodeByParam(
          "id",
          this.currentTreeNode.parentId,
          null
        );
        //展开当前节点的父节点，方便观察变化;
        this.treeObj.expandNode(znode, true, false, false);})
    }
  }
};
</script>
<style scoped>
</style>

