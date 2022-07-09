<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcomeTeacher' }"
        >首页</el-breadcrumb-item
      >
      <el-breadcrumb-item>考试管理</el-breadcrumb-item>
      <el-breadcrumb-item>考试安排</el-breadcrumb-item>
      <el-breadcrumb-item>试卷ID: {{ $route.query.id }}</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 返回上一页按钮 -->
    <el-row :gutter="20">
      <el-col :span="4">
        <el-button type="primary" @click="prev">返回</el-button>
      </el-col>
    </el-row>

    <!-- 显示监考情况 -->
    <el-table
      height="1000px"
      size="medium"
      width="1000px"
      border
      stripe
      align="center"
      :data="studentList"
    >
      <el-table-column
        prop="studenName"
        label="学生姓名"
        width="550"
        align="center"
      >
      </el-table-column>
      <el-table-column label="是否交卷" width="550" align="center">
        <template slot-scope="scope"
          ><span v-if="scope.row.isSubmitted == 1"> 已交卷 </span>
          <span v-else-if="scope.row.isSubmitted == 0"><b> 未交卷 </b></span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      paperId: "",
      studentList: [],
    };
  },
  created() {
    this.getExamStudentList();
  },
  methods: {
    prev() {
      this.$router.push("/teacherExam");
    },
    async getExamStudentList() {
      this.paperId = this.$route.query.id;
      const { data: ans } = await this.$http.get(
        "getExamStudentList?paperId=" + this.paperId
      );
      if (ans.code !== 200) {
        return this.$message.error("获取考试学生列表失败! ");
      }
      this.studentList = ans.data.list;
    },
  },
};
</script>

<style lang="less" scoped>
</style>