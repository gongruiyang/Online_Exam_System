<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcomeTeacher' }"
        >首页</el-breadcrumb-item
      >
      <el-breadcrumb-item>试题管理</el-breadcrumb-item>
      <el-breadcrumb-item>判断题管理</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  created() {},
  methods: {},
};
</script>

<style lang="less" scoped>
</style>