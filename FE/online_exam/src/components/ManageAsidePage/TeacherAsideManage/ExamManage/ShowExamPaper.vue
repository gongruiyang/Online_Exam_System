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
    <!-- 显示试卷 -->
    <el-table
      height="1000px"
      width="100%"
      border
      stripe
      :data="choiceList"
      v-show="choiceNums != 0"
    >
      <el-table-column type="index" label="选择题" width="100">
      </el-table-column>
      <el-table-column prop="score" label="分值" width="50"></el-table-column>
      <el-table-column prop="quesDesc" label="题目" width="500">
      </el-table-column>
      <el-table-column prop="rightAns" label="答案" width="100">
      </el-table-column>
      <el-table-column label="选项">
        <el-table-column prop="ansA" label="A" width="300"> </el-table-column>
        <el-table-column prop="ansA" label="B" width="300"> </el-table-column>
        <el-table-column prop="ansA" label="C" width="300"> </el-table-column>
        <el-table-column prop="ansA" label="D" width="300"> </el-table-column>
      </el-table-column>
    </el-table>
    <el-table
      height="1000px"
      width="100%"
      border
      stripe
      :data="fillGapList"
      v-show="fillGapNums != 0"
    >
      <el-table-column type="index" label="填空题" width="100">
      </el-table-column>
      <el-table-column prop="score" label="分值" width="50"></el-table-column>
      <el-table-column prop="quesDesc" label="题目" width="1000">
      </el-table-column>
      <el-table-column prop="rightAns" label="答案" width="1000">
      </el-table-column>
    </el-table>
    <el-table
      height="1000px"
      width="100%"
      border
      stripe
      :data="judgeList"
      v-show="judgeNums != 0"
    >
      <el-table-column type="index" label="判断题" width="100">
      </el-table-column>
      <el-table-column prop="score" label="分值" width="50"></el-table-column>
      <el-table-column prop="quesDesc" label="题目" width="500">
      </el-table-column>
      <el-table-column label="答案" width="300">
        <template slot-scope="scope"
          ><span v-if="scope.row.rightAns == 0"> × </span>
          <span v-else-if="scope.row.rightAns == 1"> √ </span>
        </template>
      </el-table-column>
      <el-table-column label="解析" width="1000">
        <template slot-scope="scope"
          ><span v-if="scope.row.ansAnalysis == null"> 暂无解析 </span>
          <span v-else-if="scope.row.ansAnalysis != null">
            {{ scope.row.ansAnalysis }}
          </span>
        </template>
      </el-table-column>
    </el-table>
    <el-table
      height="1000px"
      width="100%"
      border
      stripe
      :data="shortAnsList"
      v-show="shortAnsNums != 0"
    >
      <el-table-column type="index" label="主观题" width="100">
      </el-table-column>
      <el-table-column prop="score" label="分值" width="50"></el-table-column>
      <el-table-column prop="quesDesc" label="题目" width="1000">
      </el-table-column>
      <el-table-column prop="refAns" label="参考答案" width="1000">
      </el-table-column>
      <el-table-column label="解析" width="1000">
        <template slot-scope="scope"
          ><span v-if="scope.row.ansAnalysis == null"> 暂无解析 </span>
          <span v-else-if="scope.row.ansAnalysis != null">
            {{ scope.row.ansAnalysis }}
          </span>
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
      choiceNums: 0,
      choiceList: [],
      fillGapNums: 0,
      fillGapList: [],
      judgeNums: 0,
      judgeList: [],
      shortAnsNums: 0,
      shortAnsList: [],
    };
  },
  created() {
    this.getPaperList();
  },
  methods: {
    prev() {
      this.$router.push("/teacherExam");
    },
    async getPaperList() {
      this.paperId = this.$route.query.id;
      const { data: ans } = await this.$http.get(
        "getPaperByPaperId?paperId=" + this.paperId
      );
      if (ans.code !== 200) {
        return this.$message.error("获取试卷列表失败! ");
      }
      this.choiceNums = ans.data.choiceNums;
      this.fillGapNums = ans.data.fillGapNums;
      this.judgeNums = ans.data.judgeNums;
      this.shortAnsNums = ans.data.shortAnsNums;
      this.choiceList = ans.data.choiceList;
      this.fillGapList = ans.data.fillGapList;
      this.judgeList = ans.data.judgeList;
      this.shortAnsList = ans.data.shortAnsList;
    },
  },
};
</script>

<style lang="less" scoped>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>