<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcomeTeacher' }"
        >首页</el-breadcrumb-item
      >
      <el-breadcrumb-item>考试管理</el-breadcrumb-item>
      <el-breadcrumb-item>考试安排</el-breadcrumb-item>
      <el-breadcrumb-item>成绩报表:{{ $route.query.id }}</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 返回上一页按钮 -->
    <el-row :gutter="20">
      <el-col :span="4">
        <el-button type="primary" @click="prev">返回</el-button>
      </el-col>
    </el-row>
    <!-- 成绩分布折线图 -->
    <el-card
      ><div id="main" style="width: 800px; height: 400px"></div>
    </el-card>
    <br />
    <!-- 每一题的正确率 -->
    <el-card
      ><div>
        <h3>题目正确率</h3>
      </div>
      <el-table
        height="1000px"
        width="100%"
        border
        stripe
        :data="correctRateList"
      >
        <el-table-column prop="id" label="题目ID" width="300">
        </el-table-column>
        <el-table-column
          prop="rightCount"
          label="正确人数"
          width="300"
        ></el-table-column>
        <el-table-column prop="rightRate" label="正确率" width="300">
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  data() {
    return {
      paperId: "",
      correctRateList: [],
    };
  },
  created() {
    this.paperId = this.$route.query.id;
    this.getCorrectRate();
  },
  mounted() {
    var myChart = echarts.init(document.getElementById("main"));

    // 指定图表的配置项和数据
    var option = {
      title: {
        text: "成绩报表",
      },
      tooltip: {},
      legend: {
        data: ["人数"],
      },
      xAxis: {
        data: ["60分以下", "60分~70分", "70分~80分", "80分~90分", "90分~100分"],
      },
      yAxis: {},
      series: [
        {
          name: "人数",
          type: "line",
          data: [1, 2, 4, 3, 1],
        },
      ],
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
  },
  methods: {
    prev() {
      this.$router.push("/teacherExam");
    },
    async getCorrectRate() {
      const { data: ans } = await this.$http.get(
        "getCorrectRate?paperId=" + this.paperId
      );
      if (ans.code !== 200) {
        return this.$message.error("获取失败! ");
      }
      this.correctRateList = ans.data;
    },
  },
};
</script>

<style lang="less" scoped>
</style>