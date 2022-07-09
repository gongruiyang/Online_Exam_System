<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcomeAdmin' }"
        >首页</el-breadcrumb-item
      >
      <el-breadcrumb-item>试题管理</el-breadcrumb-item>
      <el-breadcrumb-item>主观题管理</el-breadcrumb-item>
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