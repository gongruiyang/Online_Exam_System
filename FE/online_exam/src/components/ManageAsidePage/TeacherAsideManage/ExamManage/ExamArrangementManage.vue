<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcomeTeacher' }"
        >首页</el-breadcrumb-item
      >
      <el-breadcrumb-item>考试管理</el-breadcrumb-item>
      <el-breadcrumb-item>考试安排</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 添加区域 -->
    <el-row>
      <el-col>
        <el-button type="primary" @click="addDialogVisible = true"
          >自动组卷添加考试</el-button
        >
        <el-button type="primary" @click="uploadDialogVisible = true"
          >上传试卷添加考试</el-button
        >
      </el-col>
    </el-row>
    <!-- 数据显示区域 -->
    <el-table height="650px" width="100%" stripe :data="examList">
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
      <el-table-column prop="examLastTime" label="考试时长(分)" width="80">
      </el-table-column>
      <el-table-column prop="grade" label="年级" width="100"> </el-table-column>
      <el-table-column prop="term" label="学期" width="100"> </el-table-column>
      <el-table-column prop="institute" label="学院" width="100">
      </el-table-column>
      <el-table-column prop="major" label="专业" width="200"> </el-table-column>
      <el-table-column prop="subject" label="科目" width="150">
      </el-table-column>
      <el-table-column prop="examType" label="考试类型" width="100">
      </el-table-column>
      <!-- 操作区 -->
      <el-table-column label="操作" width="500">
        <template slot-scope="props">
          <el-tooltip
            effect="dark"
            content="查看该场考试的试卷具体信息"
            placement="top"
            :enterable="false"
          >
            <el-button
              type="primary"
              plain
              @click="viewPaper(props.row.paperId)"
              >查看试卷</el-button
            >
          </el-tooltip>
          <el-tooltip
            effect="dark"
            placement="top"
            :enterable="false"
            content="查看考生考试情况"
          >
            <el-button
              type="success"
              v-if="compare(props.row.examTime, props.row.examLastTime) === 2"
              plain
              @click="checkExam(props.row.paperId)"
              >监考</el-button
            >
            <el-button
              type="info"
              v-else-if="
                compare(props.row.examTime, props.row.examLastTime) === 1
              "
              plain
              disabled
              >未开考</el-button
            >
            <el-button
              type="info"
              v-else-if="
                compare(props.row.examTime, props.row.examLastTime) === 3
              "
              plain
              disabled
              >考试已结束</el-button
            >
          </el-tooltip>
          <el-tooltip
            effect="dark"
            content="下载该场考试学生成绩单"
            placement="top"
            :enterable="false"
          >
            <el-button type="primary" @click="downloadSocre(props.row.paperId)">
              下载考生成绩
            </el-button>
          </el-tooltip>
          <el-tooltip
            effect="dark"
            content="查看考试的成绩报表"
            placement="top"
            :enterable="false"
          >
            <el-button
              type="primary"
              plain
              @click="viewReport(props.row.paperId)"
              >成绩报表</el-button
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

    <!-- 组卷添加试卷 -->
    <el-dialog
      title="添加考试信息"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClosed"
    >
      <!-- 内容主体区 -->
      <el-form
        ref="addFormRef"
        label-width="100px"
        :model="addForm"
        :rules="addFormRules"
      >
        <el-form-item label="开始时间" prop="examTime">
          <el-date-picker
            placeholder="选择日期和时间"
            type="datetime"
            format="yyyy/MM/dd HH:mm"
            value-format="yyyy/MM/dd HH:mm"
            v-model="addForm.examTime"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="持续时间" prop="examLastTime">
          <el-radio v-model="addForm.examLastTime" label="60">60分钟</el-radio>
          <el-radio v-model="addForm.examLastTime" label="90">90分钟</el-radio>
          <el-radio v-model="addForm.examLastTime" label="120"
            >120分钟</el-radio
          >
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-input v-model="addForm.grade"></el-input>
        </el-form-item>
        <el-form-item label="学期" prop="term">
          <el-input v-model="addForm.term"></el-input>
        </el-form-item>
        <el-form-item label="学院" prop="institute">
          <el-input v-model="addForm.institute"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="addForm.major"></el-input>
        </el-form-item>
        <el-form-item label="科目" prop="subject">
          <el-input v-model="addForm.subject"></el-input>
        </el-form-item>
        <el-form-item label="考试描述" prop="description">
          <el-input v-model="addForm.description"></el-input>
        </el-form-item>
        <el-form-item label="考生须知" prop="tips">
          <el-input v-model="addForm.tips"></el-input>
        </el-form-item>
        <el-form-item label="考试类型" prop="examType">
          <el-radio v-model="addForm.examType" label="1">闭卷</el-radio>
          <el-radio v-model="addForm.examType" label="2">开卷</el-radio>
        </el-form-item>
        <el-form-item label="单选题总分" prop="totalSingleChoiceScore">
          <el-radio v-model="addForm.totalSingleChoiceScore" label="0">
            0分(0题)</el-radio
          >
          <el-radio v-model="addForm.totalSingleChoiceScore" label="10"
            >10分(5题)</el-radio
          >
          <el-radio v-model="addForm.totalSingleChoiceScore" label="20"
            >20分(10题)</el-radio
          >
          <el-radio v-model="addForm.totalSingleChoiceScore" label="30"
            >30分(15题)</el-radio
          >
          <el-radio v-model="addForm.totalSingleChoiceScore" label="40"
            >40分(20题)</el-radio
          >
        </el-form-item>
        <el-form-item label="填空题总分" prop="totalFillGapScore">
          <el-radio v-model="addForm.totalFillGapScore" label="0">
            0分(0题)</el-radio
          >
          <el-radio v-model="addForm.totalFillGapScore" label="10"
            >10分(5题)</el-radio
          >
          <el-radio v-model="addForm.totalFillGapScore" label="20"
            >20分(10题)</el-radio
          >
          <el-radio v-model="addForm.totalFillGapScore" label="30"
            >30分(15题)</el-radio
          >
          <el-radio v-model="addForm.totalFillGapScore" label="40"
            >40分(20题)</el-radio
          >
        </el-form-item>
        <el-form-item label="判断题总分" prop="totalJudgeScore">
          <el-radio v-model="addForm.totalJudgeScore" label="0">
            0分(0题)</el-radio
          >
          <el-radio v-model="addForm.totalJudgeScore" label="10"
            >10分(5题)</el-radio
          >
          <el-radio v-model="addForm.totalJudgeScore" label="20"
            >20分(10题)</el-radio
          >
          <el-radio v-model="addForm.totalJudgeScore" label="30"
            >30分(15题)</el-radio
          >
          <el-radio v-model="addForm.totalJudgeScore" label="40"
            >40分(20题)</el-radio
          >
        </el-form-item>
        <el-form-item label="主观题总分" prop="totalShortAnsScore">
          <el-radio v-model="addForm.totalShortAnsScore" label="0">
            0分(0题)</el-radio
          >
          <el-radio v-model="addForm.totalShortAnsScore" label="10"
            >10分(2题)</el-radio
          >
          <el-radio v-model="addForm.totalShortAnsScore" label="20"
            >20分(4题)</el-radio
          >
          <el-radio v-model="addForm.totalShortAnsScore" label="30"
            >30分(6题)</el-radio
          >
          <el-radio v-model="addForm.totalShortAnsScore" label="40"
            >40分(8题)</el-radio
          >
          <el-radio v-model="addForm.totalShortAnsScore" label="50"
            >50分(10题)</el-radio
          >
          <el-radio v-model="addForm.totalShortAnsScore" label="60"
            >60分(12题)</el-radio
          >
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addExam">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 上传添加试卷 -->
    <el-dialog
      title="添加考试信息"
      :visible.sync="uploadDialogVisible"
      width="50%"
      @close="addDialogClosed"
    >
      <!-- 内容主体区 -->
      <el-form
        ref="uploadFormRef"
        label-width="100px"
        :model="uploadForm"
        :rules="uploadFormRules"
      >
        <el-form-item label="开始时间" prop="examTime">
          <el-date-picker
            placeholder="选择日期和时间"
            type="datetime"
            format="yyyy/MM/dd HH:mm"
            value-format="yyyy/MM/dd HH:mm"
            v-model="uploadForm.examTime"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="持续时间" prop="examLastTime">
          <el-radio v-model="uploadForm.examLastTime" label="60"
            >60分钟</el-radio
          >
          <el-radio v-model="uploadForm.examLastTime" label="90"
            >90分钟</el-radio
          >
          <el-radio v-model="uploadForm.examLastTime" label="120"
            >120分钟</el-radio
          >
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-input v-model="uploadForm.grade"></el-input>
        </el-form-item>
        <el-form-item label="学期" prop="term">
          <el-input v-model="uploadForm.term"></el-input>
        </el-form-item>
        <el-form-item label="学院" prop="institute">
          <el-input v-model="uploadForm.institute"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="uploadForm.major"></el-input>
        </el-form-item>
        <el-form-item label="科目" prop="subject">
          <el-input v-model="uploadForm.subject"></el-input>
        </el-form-item>
        <el-form-item label="考试描述" prop="description">
          <el-input v-model="uploadForm.description"></el-input>
        </el-form-item>
        <el-form-item label="考生须知" prop="tips">
          <el-input v-model="uploadForm.tips"></el-input>
        </el-form-item>
        <el-form-item label="考试类型" prop="examType">
          <el-radio v-model="uploadForm.examType" label="1">闭卷</el-radio>
          <el-radio v-model="uploadForm.examType" label="2">开卷</el-radio>
        </el-form-item>
        <el-upload
          accept=".xlsx"
          drag
          multiple
          name="MultipartFile"
          action="http://localhost:8088/uploadQuestion"
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">
            只能上传xlsx文件, 且不超过100MB!
          </div>
        </el-upload>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="uploadDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="uploadExam">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 请求用户列表的参数
      queryInfo: {
        tid: "",
        pagenum: 1, //当前第几页
        pagesize: 5, // 一页多少条数据
      },

      examList: [],
      total: 0,

      addDialogVisible: false,
      addForm: {
        tid: "",
        teacherName: "",
        examTime: "",
        examLastTime: "120",
        grade: "",
        term: "",
        institute: "",
        major: "",
        subject: "",
        description: "",
        tips: "",
        examType: "1",
        totalSingleChoiceScore: "20",
        totalFillGapScore: "10",
        totalJudgeScore: "10",
        totalShortAnsScore: "60",
        totalScore: "",
      },
      addFormRules: {
        examTime: [
          {
            required: true,
            message: "考试开始时间不能为空！",
            trigger: "blur",
          },
        ],
        examLastTime: [
          {
            required: true,
            message: "考试持续时间不能为空！",
            trigger: "blur",
          },
        ],
        grade: [{ required: true, message: "年级不能为空！", trigger: "blur" }],
        term: [{ required: true, message: "学期不能为空！", trigger: "blur" }],
        institute: [
          { required: true, message: "学院不能为空！", trigger: "blur" },
        ],
        major: [{ required: true, message: "专业不能为空！", trigger: "blur" }],
        subject: [
          { required: true, message: "科目不能为空！", trigger: "blur" },
        ],
        description: [
          { required: true, message: "考试描述不能为空！", trigger: "blur" },
        ],
        tips: [
          { required: true, message: "考生须知不能为空！", trigger: "blur" },
        ],
        examType: [
          { required: true, message: "考试类型不能为空！", trigger: "blur" },
        ],
      },

      uploadDialogVisible: false,
      uploadForm: {
        tid: "",
        teacherName: "",
        examTime: "",
        examLastTime: "120",
        grade: "",
        term: "",
        institute: "",
        major: "",
        subject: "",
        description: "",
        tips: "",
        examType: "1",
      },
      uploadFormRules: {
        examTime: [
          {
            required: true,
            message: "考试开始时间不能为空！",
            trigger: "blur",
          },
        ],
        examLastTime: [
          {
            required: true,
            message: "考试持续时间不能为空！",
            trigger: "blur",
          },
        ],
        grade: [{ required: true, message: "年级不能为空！", trigger: "blur" }],
        term: [{ required: true, message: "学期不能为空！", trigger: "blur" }],
        institute: [
          { required: true, message: "学院不能为空！", trigger: "blur" },
        ],
        major: [{ required: true, message: "专业不能为空！", trigger: "blur" }],
        subject: [
          { required: true, message: "科目不能为空！", trigger: "blur" },
        ],
        description: [
          { required: true, message: "考试描述不能为空！", trigger: "blur" },
        ],
        tips: [
          { required: true, message: "考生须知不能为空！", trigger: "blur" },
        ],
        examType: [
          { required: true, message: "考试类型不能为空！", trigger: "blur" },
        ],
      },
      currentTime: "",
    };
  },
  created() {
    this.getExamList();
    this.getCurrentTime();
  },
  methods: {
    uploadExam() {
      // upload exam information
      this.$refs.uploadFormRef.validate(async (isValid) => {
        if (!isValid) return;
        // get tid & teacherName
        const username = window.sessionStorage.getItem("token");
        const { data: ans } = await this.$http.get(
          "getTeacherByUsername?username=" + username
        );
        if (ans.code !== 200) {
          return this.$message.error("获取列表失败! ");
        }
        this.uploadForm.tid = ans.data.tid;
        this.uploadForm.teacherName = ans.data.teacherName;
        // send a request
        const { data: res } = await this.$http.post(
          "uploadExamInfor",
          this.uploadForm
        );
        if (res.code != 200) {
          return this.$message.error("添加失败！");
        }
        this.$message.success("添加成功！");
        this.uploadDialogVisible = false;
        this.getExamList();
      });
    },
    checkExam(paperId) {
      this.$router.push({ path: "/checkExam", query: { id: paperId } });
    },
    compare(examTime, examLastTime) {
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
      // current time < start time -> 1
      // start time <= current time <= end time -> 2
      // end time < current time -> 3
      if (oDate1.getTime() == oDate2.getTime()) {
        //全部转换成分， 便于比较
        let begintM = hh1 * 60 + mm1;
        let currentM = hh2 * 60 + mm2;
        let endM = hh3 * 60 + mm3;
        if (currentM >= begintM && currentM <= endM) {
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
      // 通过当前老师的账号获取 tid
      const username = window.sessionStorage.getItem("token");
      const { data: ans } = await this.$http.get(
        "getTeacherByUsername?username=" + username
      );
      if (ans.code !== 200) {
        return this.$message.error("获取列表失败! ");
      }
      this.queryInfo.tid = ans.data.tid;

      // 根据 tid 获取该老师的所有试卷列表
      const { data: res } = await this.$http.get("getExamListByTid", {
        params: this.queryInfo,
      });
      if (res.code !== 200) {
        return this.$message.error("获取列表失败! ");
      }
      this.examList = res.data.examList;
      this.total = res.data.total;
      for (var i = 0; i < this.examList.length; i++) {
        if (this.examList[i]["examType"] == "1") {
          // 闭卷
          this.examList[i]["examType"] = "闭卷";
        } else if (this.examList[i]["examType"] == "2") {
          // 开卷
          this.examList[i]["examType"] = "开卷";
        }
      }
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
    // 添加按钮事件
    addDialogClosed() {
      this.$refs.addFormRef.resetFields(); // 清空表单
    },
    addExam() {
      // 添加 - 确定
      this.$refs.addFormRef.validate(async (isValid) => {
        // 若 isValid 为false 则不发请求
        if (!isValid) return;

        // 获取当前老师 tid 和 teacherName
        const username = window.sessionStorage.getItem("token");
        const { data: ans } = await this.$http.get(
          "getTeacherByUsername?username=" + username
        );
        if (ans.code !== 200) {
          return this.$message.error("获取列表失败! ");
        }
        this.addForm.tid = ans.data.tid;
        this.addForm.teacherName = ans.data.teacherName;

        // 添加一场考试
        const { data: res } = await this.$http.post("addExam", this.addForm);
        if (res.code != 200) {
          return this.$message.error("添加失败！");
        }
        this.$message.success("添加成功！");
        this.addDialogVisible = false;
        this.getExamList();
      });
    },
    viewPaper(paperId) {
      this.$router.push({ path: "/showPaper", query: { id: paperId } });
    },
    viewReport(paperId) {
      this.$router.push({ path: "/report", query: { id: paperId } });
    },
    uploadDialogClosed() {
      this.$refs.addFormRef.resetFields(); // 清空表单
    },
    downloadSocre(paperId) {
      location.href =
        this.$http.defaults.baseURL + "downloadScore?paperId=" + paperId;
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