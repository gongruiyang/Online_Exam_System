<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcomeStudent' }"
        >首页</el-breadcrumb-item
      >
      <el-breadcrumb-item>考试管理</el-breadcrumb-item>
      <el-breadcrumb-item>考试安排</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 数据显示区域 -->
    <el-table height="650px" width="100%" border stripe :data="examList">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="right" inline class="demo-table-expand">
            <el-form-item label="考试描述:">
              <span>{{ props.row.description }}</span>
            </el-form-item>
            <el-form-item label="考生须知:">
              <span>{{ props.row.tips }}</span>
            </el-form-item>
            <el-form-item label="单选题总分:">
              <span>{{ props.row.totalSingleChoiceScore }} 分</span>
            </el-form-item>
            <el-form-item label="填空题总分:">
              <span>{{ props.row.totalFillGapScore }} 分</span>
            </el-form-item>
            <el-form-item label="判断题总分:">
              <span>{{ props.row.totalJudgeScore }} 分</span>
            </el-form-item>
            <el-form-item label="主观题总分:">
              <span>{{ props.row.totalShortAnsScore }} 分</span>
            </el-form-item>
            <el-form-item label="试卷总分:">
              <span>{{ props.row.totalScore }} 分</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <!-- 所有数据列 -->
      <el-table-column type="index" label="#"> </el-table-column>
      <el-table-column prop="teacherName" label="考试组织者" width="100">
      </el-table-column>
      <el-table-column prop="examTime" label="考试开始时间" width="200">
      </el-table-column>
      <el-table-column prop="examLastTime" label="考试时长(分)" width="100">
      </el-table-column>
      <el-table-column prop="grade" label="年级" width="100"> </el-table-column>
      <el-table-column prop="term" label="学期" width="100"> </el-table-column>
      <el-table-column prop="institute" label="学院" width="100">
      </el-table-column>
      <el-table-column prop="major" label="专业" width="200"> </el-table-column>
      <el-table-column prop="subject" label="科目" width="180">
      </el-table-column>
      <el-table-column label="考试类型" width="100">
        <template slot-scope="props">
          <span v-if="props.row.examType == 1"> 闭卷 </span>
          <span v-else-if="props.row.examType == 2"> 开卷 </span>
        </template>
      </el-table-column>
      <!-- 操作区 -->
      <el-table-column label="操作" width="320">
        <template slot-scope="props">
          <el-tooltip
            effect="dark"
            placement="top"
            :enterable="false"
            content="查看考生考试情况"
          >
            <el-button
              type="success"
              v-if="
                compare(
                  props.row.examTime,
                  props.row.examLastTime,
                  props.row.paperId
                ) == 2
              "
              plain
              @click="writePaper(props.row.paperId)"
              >参加考试</el-button
            >
            <el-button
              type="info"
              v-else-if="
                compare(
                  props.row.examTime,
                  props.row.examLastTime,
                  props.row.paperId
                ) == 1
              "
              plain
              disabled
              >未开考</el-button
            >
            <el-button
              type="info"
              v-else-if="
                compare(
                  props.row.examTime,
                  props.row.examLastTime,
                  props.row.paperId
                ) == 3
              "
              plain
              disabled
              >考试已结束</el-button
            >
            <el-button
              type="info"
              v-else-if="
                compare(
                  props.row.examTime,
                  props.row.examLastTime,
                  props.row.paperId
                ) == 4
              "
              plain
              disabled
              >已经交卷</el-button
            >
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页区域 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryInfo.pagenum"
      :page-size="queryInfo.pagesize"
      :total="total"
      :page-sizes="[5, 10, 20, 50]"
      layout="total, sizes, prev, pager, next, jumper"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 请求用户列表的参数
      queryInfo: {
        institute: "",
        major: "",
        pagenum: 1, //当前第几页
        pagesize: 5, // 一页多少条数据
      },

      examList: [],
      total: 0,
      currentTime: "",
      submit: false,
    };
  },
  created() {
    this.getExamList();
    this.getCurrentTime();
  },
  methods: {
    async checkIsSubmitted(paperId) {
      const username = window.sessionStorage.getItem("token");
      const { data: ans } = await this.$http.get("getSubmitStatus", {
        params: {
          username: username,
          paperId: paperId,
        },
      });
      if (ans.code !== 200) {
        return this.$message.error("获取列表失败! ");
      }
      if (ans.data == "1") {
        this.submit = true;
      } else {
        this.submit = false;
      }
    },
    writePaper(paperId) {
      this.$router.push({ path: "/writePaper", query: { id: paperId } });
    },
    compare(examTime, examLastTime, paperId) {
      let date_time2 = this.currentTime.trim().split(" ");
      let date_time1 = examTime.trim().split(" ");
      let date1 = date_time1[0];
      let date2 = date_time2[0];
      let time1 = date_time1[1];
      let time2 = date_time2[1];
      // begin date & current date
      let oDate1 = new Date(date1);
      let oDate2 = new Date(date2);
      // begin time
      let hh1 = parseInt(time1.substr(0, 2));
      let mm1 = parseInt(time1.substr(3, 2));
      // current time
      let hh2 = parseInt(time2.substr(0, 2));
      let mm2 = parseInt(time2.substr(3, 2));
      // end time
      let mm3 = (parseInt(examLastTime) % 60) + mm1;
      let tmp = parseInt(mm3 / 60);
      mm3 = mm3 % 60;
      let i = parseInt(examLastTime) / 60;
      let hh3 = hh1 + parseInt(examLastTime) / 60 + tmp;
      if (oDate1.getTime() == oDate2.getTime()) {
        //全部转换成分钟， 便于比较
        let begintM = hh1 * 60 + mm1;
        let currentM = hh2 * 60 + mm2;
        let endM = hh3 * 60 + mm3;
        if (currentM >= begintM && currentM <= endM) {
          this.checkIsSubmitted(paperId);
          if (this.submit) {
            return 4;
          }
          return 2;
        }
        if (currentM < begintM) {
          return 1;
        }
        return 3;
      } else if (oDate1.getTime() > oDate2.getTime()) {
        return 1;
      } else {
        return 3;
      }
    },
    getCurrentTime() {
      let yy = new Date().getFullYear();
      let MM = new Date().getMonth() + 1;
      let dd = new Date().getDate();
      let hh = new Date().getHours();
      let mm =
        new Date().getMinutes() < 10
          ? "0" + new Date().getMinutes()
          : new Date().getMinutes();
      let ss =
        new Date().getSeconds() < 10
          ? "0" + new Date().getSeconds()
          : new Date().getSeconds();
      this.currentTime =
        yy + "/" + MM + "/" + dd + " " + hh + ":" + mm + ":" + ss;
    },
    async getExamList() {
      // student username -> tid
      const username = window.sessionStorage.getItem("token");
      const { data: ans } = await this.$http.get(
        "getTeacherByStudentUsername?username=" + username
      );
      if (ans.code !== 200) {
        return this.$message.error("获取列表失败! ");
      }
      this.queryInfo.institute = ans.data.institute;
      this.queryInfo.major = ans.data.major;
      // sid -> exam list
      const { data: res } = await this.$http.get(
        "getExamListByInstituteAndMajor",
        {
          params: this.queryInfo,
        }
      );
      if (res.code !== 200) {
        return this.$message.error("获取列表失败! ");
      }
      this.examList = res.data.examList;
      this.total = res.data.total;
    },

    // 监听 pagesize 改变的事件
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize;
      this.getExamList();
    },
    // 监听 页码值 改变的事件
    handleCurrentChange(newPageNum) {
      this.queryInfo.pagenum = newPageNum;
      this.getExamList();
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