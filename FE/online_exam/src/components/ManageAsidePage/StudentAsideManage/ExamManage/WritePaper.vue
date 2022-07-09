<template>
  <div class="answer">
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>试卷ID: {{ $route.query.id }}</el-breadcrumb-item>
    </el-breadcrumb>
    <div class="personInfor">
      <!-- 上部：考生及考试信息区域 -->
      <el-button type="primary" @click="showPersonInfor = !showPersonInfor"
        >>>> 展示个人信息</el-button
      >
      <transition name="slide-fade">
        <div v-if="showPersonInfor">
          <div>
            <li>姓名: {{ this.paper.student.studentName }}</li>
            <li>准考证号: {{ this.paper.student.sid }}</li>
            <li>
              欢迎您参加{{ this.paper.exam.institute }}-{{
                this.paper.exam.grade
              }}-{{ this.paper.exam.term }}-{{ this.paper.exam.major }}-{{
                this.paper.exam.subject
              }}科目的考试
            </li>
            <li>考试须知: {{ this.paper.exam.tips }}</li>
            <li>
              考试类型:
              <template>
                <span v-if="this.paper.exam.examType == 1"> 闭卷 </span>
                <span v-else-if="this.paper.exam.examType == 2"> 开卷 </span>
              </template>
            </li>
            <li>试卷总分为{{ this.paper.exam.totalScore }}分</li>
          </div>
          <div>
            <span
              >全卷共{{
                paper.choiceNums +
                paper.fillGapNums +
                paper.judgeNums +
                paper.shortAnsNums
              }}题 <i class="iconfont icon-time"></i>倒计时：<b>{{
                tiktok
              }}</b></span
            >
          </div>
        </div>
      </transition>
    </div>
    <br />
    <br />
    <br />
    <div class="ansersheet">
      <br />
      <el-button
        type="primary"
        @click="showChoice = !showChoice"
        v-show="paper.choiceNums != 0"
        >>>> 展示选择题</el-button
      >
      <transition name="slide-fade">
        <div v-show="paper.choiceNums != 0" v-if="showChoice">
          <template v-for="(choice, index) in paper.choiceList">
            <div :key="index">
              <h4>{{ index + 1 }}、{{ choice.quesDesc }}</h4>
              <el-radio-group
                v-model="answer.choiceAnsList[index]"
                size="medium"
                border="true"
              >
                <el-radio :label="1">A: {{ choice.ansA }}</el-radio>
                <el-radio :label="2">B: {{ choice.ansB }}</el-radio>
                <el-radio :label="3">C: {{ choice.ansC }}</el-radio>
                <el-radio :label="4">D: {{ choice.ansD }}</el-radio>
              </el-radio-group>
              <br />
              <br />
              <br />
            </div>
          </template>
        </div>
      </transition>
      <br />
      <br />
      <br />
      <el-button
        type="primary"
        @click="showFillGap = !showFillGap"
        v-show="paper.fillGapNums != 0"
        >>>> 展示填空题</el-button
      >
      <transition name="slide-fade">
        <div
          class="fillgappart"
          v-show="paper.fillGapNums != 0"
          v-if="showFillGap"
        >
          <template v-for="(choice, index) in paper.fillGapList">
            <div :key="index">
              <h4>{{ index + 1 }}、{{ choice.quesDesc }}</h4>
              <el-input
                v-model="answer.fillGapAnsList[index]"
                placeholder="请输入答案"
              ></el-input>
              <br />
              <br />
              <br />
            </div>
          </template>
        </div>
      </transition>
      <br />
      <br />
      <br />
      <el-button
        type="primary"
        @click="showJudge = !showJudge"
        v-show="paper.judgeNums != 0"
        >>>> 展示判断题</el-button
      >
      <transition name="slide-fade">
        <div class="judgepart" v-show="paper.judgeNums != 0" v-if="showJudge">
          <template v-for="(choice, index) in paper.judgeList">
            <div :key="index">
              <h4>{{ index + 1 }}、{{ choice.quesDesc }}</h4>
              <el-radio-group v-model="answer.judgeAnsList[index]">
                <el-radio :label="1"> × </el-radio>
                <el-radio :label="2"> √ </el-radio>
              </el-radio-group>
              <br />
              <br />
              <br />
            </div>
          </template>
        </div>
      </transition>
      <br />
      <br />
      <br />
      <el-button
        type="primary"
        @click="showShortAns = !showShortAns"
        v-show="paper.shortAnsNums != 0"
        >>>> 展示主观题</el-button
      >
      <transition name="slide-fade">
        <div v-show="paper.shortAnsNums != 0" v-if="showShortAns">
          <template v-for="(choice, index) in paper.shortAnsList">
            <div :key="index">
              <h4>{{ index + 1 }}、{{ choice.quesDesc }}</h4>
              <el-input
                v-model="answer.shortList[index]"
                type="textarea"
                :autosize="{ minRows: 10, maxRows: 10 }"
                placeholder="请输入答案"
              ></el-input>
              <br />
              <br />
              <br />
            </div>
          </template>
        </div>
      </transition>
      <br />
      <br />
      <br />
    </div>
    <!-- 提交试卷确认对话框 -->
    <el-dialog
      title="确认提交试题吗？"
      :visible.sync="submitConfirm"
      width="70%"
    >
      <p>总题目数量 / 已做题目数量</p>
      <li v-show="paper.choiceNums != 0">
        选择题：{{ paper.choiceNums }}/{{ answer.choiceNums }}
      </li>
      <li v-show="paper.fillGapNums != 0">
        填空题：{{ paper.fillGapNums }}/{{ answer.fillGapNums }}
      </li>
      <li v-show="paper.judgeNums != 0">
        判断题：{{ paper.judgeNums }}/{{ answer.judgeNums }}
      </li>
      <li v-show="paper.shortAnsNums != 0">
        主观题：{{ paper.shortAnsNums }}/{{ answer.shortAnsNums }}
      </li>
      <p>
        您还差{{
          paper.choiceNums +
          paper.fillGapNums +
          paper.judgeNums +
          paper.shortAnsNums -
          answer.choiceNums -
          answer.fillGapNums -
          answer.judgeNums -
          answer.shortAnsNums
        }}
        题没有做呢
      </p>
      <!-- 操作按钮 -->
      <span slot="footer">
        <el-button @click="submitConfirm = false">算了吧，再检查检查</el-button>
        <el-button type="primary" @click="submit"
          >我做完了，确定提交试卷</el-button
        >
      </span>
    </el-dialog>
    <el-button type="primary" @click="calculateQuesHaveDone">submit</el-button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      paper: {
        student: {},
        exam: {},
        paperId: "",
        choiceNums: 0,
        choiceList: [],
        fillGapNums: 0,
        fillGapList: [],
        judgeNums: 0,
        judgeList: [],
        shortAnsNums: 0,
        shortAnsList: [],
      },
      answer: {
        choiceNums: 0,
        choiceAnsList: [],
        fillGapNums: 0,
        fillGapAnsList: [],
        judgeNums: 0,
        judgeAnsList: [],
        shortAnsNums: 0,
        shortList: [],
      },

      showPersonInfor: true,
      showChoice: true,
      showFillGap: true,
      showJudge: true,
      showShortAns: true,

      time: null,
      tiktok: null,
      submitConfirm: false,
    };
  },
  created() {
    this.paper.paperId = this.$route.query.id;
    this.getPaperList();
    this.getStudent();
    this.getExam();
    this.showTime();
  },
  methods: {
    async getStudent() {
      const username = window.sessionStorage.getItem("token");
      const { data: ans } = await this.$http.get(
        "getStudentByUsername?username=" + username
      );
      if (ans.code !== 200) {
        return this.$message.error("获取试卷列表失败! ");
      }
      this.paper.student = ans.data;
    },
    async getExam() {
      const { data: ans } = await this.$http.get(
        "getExamByPaperId?paperId=" + this.paper.paperId
      );
      if (ans.code !== 200) {
        return this.$message.error("获取试卷列表失败! ");
      }
      this.paper.exam = ans.data;
      this.time = this.paper.exam.examLastTime * 60;
    },
    async getPaperList() {
      const { data: ans } = await this.$http.get(
        "getPaperByPaperId?paperId=" + this.paper.paperId
      );
      if (ans.code !== 200) {
        return this.$message.error("获取试卷列表失败! ");
      }
      this.paper.choiceNums = ans.data.choiceNums;
      this.paper.fillGapNums = ans.data.fillGapNums;
      this.paper.judgeNums = ans.data.judgeNums;
      this.paper.shortAnsNums = ans.data.shortAnsNums;
      this.paper.choiceList = ans.data.choiceList;
      this.paper.fillGapList = ans.data.fillGapList;
      this.paper.judgeList = ans.data.judgeList;
      this.paper.shortAnsList = ans.data.shortAnsList;
    },
    countDown() {
      let h = parseInt((this.time / (60 * 60)) % 24);
      h = h < 10 ? "0" + h : h;
      let m = parseInt((this.time / 60) % 60);
      m = m < 10 ? "0" + m : m;
      let s = parseInt(this.time % 60);
      s = s < 10 ? "0" + s : s;
      this.tiktok = h + "时" + m + "分" + s + "秒";
    },
    showTime() {
      setInterval(() => {
        this.time -= 1;
        this.countDown();
        if (this.time == 60) {
          this.$message({
            showClose: true,
            type: "error",
            message: "考生注意,考试时间还剩 1 分钟！！！",
          });
        } else if (this.time == 0) {
          this.submit();
        }
      }, 1000);
    },
    calculateQuesHaveDone() {
      // Calculate how many ques have not been done.
      this.answer.choiceNums =
        this.answer.fillGapNums =
        this.answer.judgeNums =
        this.answer.shortAnsNums =
          0;
      for (var i = 0; i < this.answer.choiceAnsList.length; i++) {
        if (
          this.answer.choiceAnsList[i] != "" &&
          this.answer.choiceAnsList[i] != null
        ) {
          this.answer.choiceNums++;
        }
      }
      for (var i = 0; i < this.answer.fillGapAnsList.length; i++) {
        if (
          this.answer.fillGapAnsList[i] != "" &&
          this.answer.fillGapAnsList[i] != null
        ) {
          this.answer.fillGapNums++;
        }
      }
      for (var i = 0; i < this.answer.judgeAnsList.length; i++) {
        if (
          this.answer.judgeAnsList[i] != "" &&
          this.answer.judgeAnsList[i] != null
        ) {
          this.answer.judgeNums++;
        }
      }
      for (var i = 0; i < this.answer.shortList.length; i++) {
        if (
          this.answer.shortList[i] != "" &&
          this.answer.shortList[i] != null
        ) {
          this.answer.shortAnsNums++;
        }
      }
      this.submitConfirm = true;
    },
    submit() {
      // submit paper
      const { data: res } = this.$http.post("addStudentPaperAnswer", {
        student: this.paper.student,
        exam: this.paper.exam,
        choiceList: this.paper.choiceList,
        fillGapList: this.paper.fillGapList,
        judgeList: this.paper.judgeList,
        shortAnsList: this.paper.shortAnsList,
        choiceAnsList: this.answer.choiceAnsList,
        fillGapAnsList: this.answer.fillGapAnsList,
        judgeAnsList: this.answer.judgeAnsList,
        shortList: this.answer.shortList,
      });
      this.submitConfirm = false;
      this.$router.push("/studentExam");
      this.$message.success("交卷成功！");
    },
  },
};
</script>

<style lang="less" scoped>
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}
.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}
.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateX(20px);
  opacity: 0;
}

.iconfont.icon-time {
  color: #2776df;
  margin: 0px 6px 0px 20px;
}
</style>