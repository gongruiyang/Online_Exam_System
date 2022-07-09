<template>
  <div>
    <h3>超级管理员你好，欢迎使用在线考试系统！</h3>
    <el-calendar v-model="value"> </el-calendar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      value: new Date(),
    };
  },
};
</script>

