<!--  -->
<template>
  <el-cascader :props="props" size="medium" style="width: 100%" clearable v-model="id" @change="change"></el-cascader>
</template>

<script>
//这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
//例如：import 《组件名称》 from '《组件路径》';
import log from '../sys/log'

export default {
  //import引入的组件需要注入到对象中才能使用
  components: {},
  data() {
    let slef = this;
    //这里存放数据
    return {
      id: 1,
      props: {
        lazy: true,
        value: "id",
        label: "name",
        lazyLoad(node, resolve) {
          console.log("node: ", node)
          const { data } = node;
          const pid = (data && data.id) || 0;
          slef.getCategory(pid).then(result => {
            resolve(result);
          })
        }
      }
    }
  },
  //监听属性 类似于data概念
  computed: {},
  //监控data中的数据变化
  watch: {},
  //方法集合
  methods: {
    getCategory(pid) {
      return new Promise((resolve, reject) => {
        this.$http({
          url: this.$http.adornApiUrl("/pms/category/parent/" + pid),
          method: "get"
        }).then(({ data }) => {
          if (data && data.code === 0) {
            resolve(data.data)
          } else {
            reject(data.data)
          }
        })
      })
    },
    change(val){
      console.log("选择了的分类信息：" + val);
        this.$emit("selectedValue",val[val.length-1])
    }
  }
}
</script>
<style lang='scss' scoped>
//@import url(); 引入公共css类
</style>
