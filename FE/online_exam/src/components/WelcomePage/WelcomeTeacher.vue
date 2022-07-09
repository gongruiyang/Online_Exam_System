<template>
  <div>
    <h3>尊敬的老师您好!!欢迎使用在线考试系统！</h3>
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