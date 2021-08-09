<template>
  <div>
    <el-row>
      <el-col :span="4">
        <div class="left-tree">
          <!--ref很重要，方便父组件触发子组件的一些方法  -->
          <CategoryTree @treeNode="acceptTreeNode" ref="categoryTreeComponent" />
        </div>
      </el-col>
      <el-col :span="20">
          <SpuInfoTable :cat-id="categoryId" ref="spuInfoTable"/>
      </el-col>
    </el-row>
  </div>
</template>
<script>

import SpuInfoTable from "../spu/spuinfo";
import CategoryTree from "../../common/category-tree";
export default {
  components: { CategoryTree,SpuInfoTable },
  data() {
    return {
      currentTreeNode: {}, //原始的treeNode
      categoryId: 0
    }
  },
  methods: {
    //接受子组件传递来的值，并保存
    acceptTreeNode(data) {
      this.categoryId = data.id
      //自动改变查询，一定要用nextTick，否则prop属性还没传递下去，实例还不能使用
      this.$nextTick(()=>{
        this.$refs.spuInfoTable.getDataList(0);
      });

    }
  }
};
</script>
<style scoped>
</style>
