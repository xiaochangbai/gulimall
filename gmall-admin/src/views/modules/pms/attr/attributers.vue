<!--  -->
<template>
  <div class>
    <el-dialog :title="'【'+groupName+'】属性组'" :visible.sync="dialogVisible" width="60%">
      <el-table :data="attrTableData">
        <el-table-column prop="id" label="属性id"></el-table-column>
        <el-table-column prop="name" label="属性名"></el-table-column>
        <el-table-column prop="searchType" label="是否检索"></el-table-column>
        <el-table-column prop="type" label="基本属性"></el-table-column>
        <el-table-column prop="showDesc" label="快速展示"></el-table-column>
        <el-table-column header-align="center" align="center" width="250" label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="attrAddOrUpdateHandle(scope.row.id)">修改</el-button>
            <el-button
              type="text"
              size="small"
              @click="attrDeleteHandle(scope.row.id,scope.row.name)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="attrAddOrUpdateHandle()">新 增</el-button>
      </span>
    </el-dialog>
    <attribute-add-update
      :group-id="groupId"
      :cat-id="catId"
      v-if="addOrUpdateVisible"
      ref="addOrUpdate"
      @saveSuccess="attrsSaveSuccess"
    ></attribute-add-update>
  </div>
</template>

<script>
  //这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
  //例如：import 《组件名称》 from '《组件路径》';
  import AttributeAddUpdate from "./attribute-add-update";
  export default {
    //import引入的组件需要注入到对象中才能使用
    components: {AttributeAddUpdate},
    props: ["catId"],
    data () {
      //这里存放数据
      return {
        dialogVisible: false,
        addOrUpdateVisible: false,
        attrTableData: [],
        groupId: 0,
        groupName: ""
      };
    },
    //监听属性 类似于data概念
    computed: {},
    //监控data中的数据变化
    watch: {},
    //方法集合
    methods: {
      init (id, name) {
        this.groupId = id;
        this.groupName = name;
        //传入分组id
        this.dialogVisible = true;
        //获取当前分组和子属性详情
        this.$http({
          url: this.$http.adornApiUrl(
            "/pms/attr/group/" + this.groupId
          ),
          method: "get"
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.attrTableData = data.data;
          } else {
          }
        })
      },
      // 新增 / 修改
      attrAddOrUpdateHandle (id) {

        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      attrsSaveSuccess(type) {
        console.log("attrsSaveSuccess...", type)
        this.init(this.groupId, this.groupName)
      },
      // 删除
      attrDeleteHandle (id, name) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornApiUrl('/pms/attr/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.init(this.groupId, this.groupName)
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
<style lang='scss' scoped>
  //@import url(); 引入公共css类
</style>
