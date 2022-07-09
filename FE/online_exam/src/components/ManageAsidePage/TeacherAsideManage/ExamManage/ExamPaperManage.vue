<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcomeTeacher' }"
        >首页</el-breadcrumb-item
      >
      <el-breadcrumb-item>考试管理</el-breadcrumb-item>
      <el-breadcrumb-item>考试试卷</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 添加区域 -->
    <el-row>
      <el-col> </el-col>
    </el-row>
    <!-- 数据显示区域 -->
    <el-table height="800px" width="100%" stripe :data="paperList">
      <!-- 所有数据列 -->
      <el-table-column type="index" label="#"> </el-table-column>
      <el-table-column prop="studentName" label="姓名" width="100">
      </el-table-column>
      <el-table-column prop="sid" label="学号" width="150"> </el-table-column>
      <el-table-column prop="examName" label="考试名称" width="450">
      </el-table-column>
      <el-table-column prop="examTime" label="考试开始时间" width="200">
      </el-table-column>
      <el-table-column
        prop="totalSingleChoiceScore"
        label="单选题总得分"
        width="100"
      >
      </el-table-column>
      <el-table-column
        prop="totalFillGapScore"
        label="填空题总得分"
        width="100"
      >
      </el-table-column>
      <el-table-column prop="totalJudgeScore" label="判断题总得分" width="100">
      </el-table-column>
      <el-table-column
        prop="totalShortAnsScore"
        label="主观题总得分"
        width="100"
      >
      </el-table-column>
      <el-table-column prop="totalScore" label="试卷总得分" width="100">
      </el-table-column>
      <!-- 操作区 -->
      <el-table-column label="操作" width="150">
        <template slot-scope="props">
          <el-tooltip
            effect="primary"
            content="下载该考试试卷"
            placement="top"
            :enterable="false"
          >
            <el-button
              type="primary"
              plain
              v-if="props.row.isSubmitted == 1"
              @click="downloadStudentPaper(props.row.paperId, props.row.sid)"
              >下载试卷</el-button
            >
            <el-button type="info" plain v-else-if="props.row.isSubmitted != 1"
              >未参加考试</el-button
            >
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      queryInfo: {
        tid: "",
      },
      paperList: [],
    };
  },
  created() {
    this.getPaperList();
  },
  methods: {
    async getPaperList() {
      // username -> tid
      const username = window.sessionStorage.getItem("token");
      const { data: ans } = await this.$http.get(
        "getTeacherByUsername?username=" + username
      );
      if (ans.code !== 200) {
        return this.$message.error("获取列表失败! ");
      }
      this.queryInfo.tid = ans.data.tid;

      // tid -> student paper list
      const { data: res } = await this.$http.get("getPaperListByTid", {
        params: this.queryInfo,
      });
      if (res.code !== 200) {
        return this.$message.error("获取列表失败! ");
      }
      this.paperList = res.data.paperList;
      console.log(this.paperList);
    },
    downloadStudentPaper(paperId, sid) {
      location.href =
        this.$http.defaults.baseURL +
        "downloadStudentPaper?paperId=" +
        paperId +
        "&sid=" +
        sid;
      console.log(
        this.$http.defaults.baseURL +
          "downloadStudentPaper?paperId=" +
          paperId +
          "&sid=" +
          sid
      );
    },
  },
};
</script>

<style lang="less" scoped>
</style>