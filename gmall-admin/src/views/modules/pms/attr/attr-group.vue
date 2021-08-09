<template>
  <div class>
    <div>
      <el-row>
        <el-button type="primary" @click="attrGroupAddHandle()">新增属性组</el-button>
        <el-table :data="attrGroupTableData">
          <el-table-column label="属性分组">
            <el-table-column type="selection" align="center"></el-table-column>
            <el-table-column prop="sort" label="序号"></el-table-column>
            <el-table-column prop="id" label="分组ID"></el-table-column>
            <el-table-column prop="name" label="分组名"></el-table-column>
            <el-table-column prop="icon" label="组图标">
              <template slot-scope="scope">
                <el-image style="width: 64px; height: 24px" :src="scope.row.icon" fit="contain"></el-image>
              </template>
            </el-table-column>
            <el-table-column prop="remark" label="描述"></el-table-column>
            <el-table-column header-align="center" align="center" width="250" label="操作">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="attrHandle(scope.row.id, scope.row.name)">维护属性</el-button>
                <el-button type="text" size="small" @click="attrGroupAddHandle(scope.row.id)">修改</el-button>
                <el-button
                  type="text"
                  size="small"
                  @click="attrGroupDeleteHandle(scope.row.id,scope.row.name)"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table-column>
        </el-table>
      </el-row>
      <!--<el-row>
        <div class="block">
          <el-pagination
            @size-change="sizeChangeHandle"
            @current-change="currentChangeHandle"
            :current-page="pageNum"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
          ></el-pagination>
        </div>
      </el-row>-->
    </div>
    <attr-group-add-update v-if="attrGroupDialogShow" :cat-id="catId" ref="attrGroupAddUpdate" @saveSuccess="saveSuccess"></attr-group-add-update>
<!--    <attr-group-relation v-if="attrGroupRelationDialogShow" ref="attrGroupRelation"></attr-group-relation>-->
    <attr-attributes v-if="attrDialogShow" :cat-id="catId" ref="attrAttributes"></attr-attributes>
  </div>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import AttrGroupAddUpdate from "./attr-group-add-update";
import AttrAttributes from "./attributers";
export default {
  //import引入的组件需要注入到对象中才能使用
  components: { AttrGroupAddUpdate, AttrAttributes },
  props: ["catId", "catName"],
  data() {
    //这里存放数据
    return {
      attrGroupTableData: [],
      attrGroupDialogShow: false,
      attrDialogShow: false
    }
  },
  //监听属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {
    catId(val) {
      this.initAttrGroup(val);
    }
  },
  //方法集合
  methods: {
    saveSuccess(){
      this.initAttrGroup(this.catId);
    },
    // 初始化屬性分組
    initAttrGroup(catId) {
      this.$nextTick(() => {
        this.$http({
          url: this.$http.adornApiUrl("/pms/attrgroup/category/" + catId),
          method: "get"
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.attrGroupTableData = data.data;
            // this.total = data.data.total;
            // this.pageNum = data.data.pageNum;
          }
        })
      })
    },
    attrGroupDeleteHandle(val, name) {
      this.$confirm(
        "确认删除分组【" +
          name +
          "】,这个组关联的属性将自动归为【其他】组，是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.$http({
            url: this.$http.adornApiUrl("/pms/attrgroup/delete"),
            method: "post",
            data: this.$http.adornData([val], false, "json")
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({ type: "success", message: "删除成功..." });
              this.initAttrGroup(this.catId);
            } else {
            }
          });
        })
        .catch(() => {
          this.$message({ type: "info", message: "已取消" });
        });
    },
    attrGroupAddHandle(id) {
      this.attrGroupDialogShow = true;
      //按照属性分组id查出属性分组信息，进行回显并修改
      if (id) {
        this.$http({
          url: this.$http.adornApiUrl("/pms/attrgroup/" + id),
          method: "get"
        }).then(({ data }) => {
          if (data && data.code === 0) {
            this.$nextTick(() => {
              this.$refs.attrGroupAddUpdate.showDetails(data.data);
            });
          } else {
          }
        })
      } else {
        console.log("================")
        this.$confirm(`请确认已经选择了第三级分类：` + this.catName, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$nextTick(() => {
            this.$refs.attrGroupAddUpdate.init();
          })
        })
      }
    },
    attrHandle(id, name){
        this.attrDialogShow = true;
        this.$nextTick(()=>{
          this.$refs.attrAttributes.init(id, name);
        });
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
