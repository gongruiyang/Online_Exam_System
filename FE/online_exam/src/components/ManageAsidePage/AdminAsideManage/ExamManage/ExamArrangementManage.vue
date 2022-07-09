<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcomeAdmin' }"
        >首页</el-breadcrumb-item
      >
      <el-breadcrumb-item>考试管理</el-breadcrumb-item>
      <el-breadcrumb-item>考试安排</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <!-- 搜索区域 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            type="text"
            placeholder="请输入需要搜索的组织考试的老师姓名"
            clearable
            v-model="queryInfo.query"
            @clear="getExamList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getExamList"
            ></el-button>
          </el-input>
        </el-col>
      </el-row>

      <!-- 数据显示区 -->
      <el-table height="650px" border stripe :data="examList">
        <!-- 所有数据列 -->
        <el-table-column type="index" label="#"> </el-table-column>
        <el-table-column
          prop="examId"
          label="考试场次id"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="tid"
          label="组织考试老师ID"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="teacherName"
          label="组织考试老师姓名"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="paperId"
          label="试卷ID"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="examTime"
          label="考试开始时间"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="examLastTime"
          label="考试持续时间"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="grade"
          label="年级"
          width="100"
        ></el-table-column>
        <el-table-column prop="term" label="学期" width="100"></el-table-column>
        <el-table-column
          prop="institute"
          label="学院"
          width="100"
        ></el-table-column>
        <el-table-column prop="major" label="专业" width="100">
        </el-table-column>
        <el-table-column
          prop="subject"
          label="科目"
          width="100"
        ></el-table-column>
        <el-table-column prop="description" label="考试描述" width="100">
        </el-table-column>
        <el-table-column prop="tips" label="考生须知" width="100">
        </el-table-column>
        <el-table-column prop="examType" label="考试类型" width="100">
        </el-table-column>
        <el-table-column
          prop="totalSingleChoiceScore"
          label="单选题总分"
          width="100"
        >
        </el-table-column>
        <el-table-column
          prop="totalFillGapScore"
          label="填空题总分"
          width="100"
        >
        </el-table-column>
        <el-table-column prop="totalJudgeScore" label="判断题总分" width="100">
        </el-table-column>
        <el-table-column
          prop="totalShortAnsScore"
          label="主观题总分"
          width="100"
        >
        </el-table-column>
        <el-table-column prop="totalScore" label="试卷总分" width="100">
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 请求用户列表的参数
      queryInfo: {
        query: "",
        pagenum: 1, //当前第几页
        pagesize: 5, // 一页多少条数据
      },

      examList: [],
      total: 0,
    };
  },
  created() {
    this.getExamList();
  },
  methods: {
    async getExamList() {
      const { data: res } = await this.$http.get("showExamLists", {
        params: this.queryInfo,
      });
      if (res.code !== 200) {
        return this.$message.error("获取考试列表失败! ");
      }
      this.examList = res.data.examList;
      this.total = res.data.total;
    },

    // 分页事件
    handleSizeChange(newSize) {
      // pagesize改变
      this.queryInfo.pagesize = newSize;
      this.getExamList();
    },
    handleCurrentChange(newPageNum) {
      // 当前页码值改变
      this.queryInfo.pagenum = newPageNum;
      this.getExamList();
    },
  },
};
</script>


<style lang="less" scoped>
</style>