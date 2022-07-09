<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcomeAdmin' }"
        >首页</el-breadcrumb-item
      >
      <el-breadcrumb-item>试题管理</el-breadcrumb-item>
      <el-breadcrumb-item>单选题管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card>
      <!-- 搜索区域/添加区域 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            type="text"
            placeholder="请输入要搜索的科目"
            clearable
            v-model="queryInfo.query"
            @clear="getSingleChoiceQuesList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getSingleChoiceQuesList"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true"
            >添加单选题</el-button
          >
        </el-col>
      </el-row>
      <!-- 数据显示区 -->
      <el-table height="650px" border stripe :data="singleChoiceQuesList">
        <!-- 所有数据列 -->
        <el-table-column type="index" label="#"> </el-table-column>
        <el-table-column prop="quesDesc" label="题目描述" width="100">
        </el-table-column>
        <el-table-column prop="ansA" label="A" width="100"> </el-table-column>
        <el-table-column prop="ansB" label="B" width="100"> </el-table-column>
        <el-table-column prop="ansC" label="C" width="100"> </el-table-column>
        <el-table-column prop="ansD" label="D" width="100"> </el-table-column>
        <el-table-column prop="rightAns" label="正确答案" width="100">
        </el-table-column>
        <el-table-column prop="ansAnalysis" label="答案解析" width="100">
        </el-table-column>
        <el-table-column prop="score" label="分值" width="100">
        </el-table-column>
        <el-table-column prop="blgSub" label="所属科目" width="100">
        </el-table-column>
        <el-table-column prop="tid" label="题目贡献老师ID" width="100">
        </el-table-column>

        <!-- 操作区 -->
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-tooltip
              effect="dark"
              content="编辑该条信息"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="primary"
                icon="el-icon-edit"
                circle
                @click="showEditDialog(scope.row.quesId)"
              ></el-button>
            </el-tooltip>

            <el-tooltip
              effect="dark"
              content="删除该条信息"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                circle
                @click="isDelete(scope.row.quesId)"
              ></el-button>
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

      <!-- 添加对话框 -->
      <el-dialog
        title="添加单选题"
        :visible.sync="addDialogVisible"
        width="70%"
        @close="addDialogClosed"
      >
        <!-- 内容主体区 -->
        <el-form
          ref="addFormRef"
          label-width="100px"
          :model="addForm"
          :rules="addFormRules"
        >
          <el-form-item label="题目描述" prop="quesDesc">
            <el-input v-model="addForm.quesDesc"></el-input>
          </el-form-item>
          <el-form-item label="A" prop="ansA">
            <el-input v-model="addForm.ansA"></el-input>
          </el-form-item>
          <el-form-item label="B" prop="ansB">
            <el-input v-model="addForm.ansB"></el-input>
          </el-form-item>
          <el-form-item label="C" prop="ansC">
            <el-input v-model="addForm.ansC"></el-input>
          </el-form-item>
          <el-form-item label="D" prop="ansD">
            <el-input v-model="addForm.ansD"></el-input>
          </el-form-item>
          <el-form-item label="正确答案" prop="rightAns">
            <el-input v-model="addForm.rightAns"></el-input>
          </el-form-item>
          <el-form-item label="答案解析" prop="ansAnalysis">
            <el-input v-model="addForm.ansAnalysis"></el-input>
          </el-form-item>
          <el-form-item label="分值" prop="score">
            <el-input v-model="addForm.score"></el-input>
          </el-form-item>
          <el-form-item label="所属科目" prop="blgSub">
            <el-input v-model="addForm.blgSub"></el-input>
          </el-form-item>
        </el-form>

        <!-- 底部区域 -->
        <span slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addSingleChoice">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 修改对话框 -->
      <el-dialog
        title="修改当前教师信息"
        :visible.sync="modifyDialogVisible"
        width="70%"
      >
        <!-- 内容主体区 -->
        <el-form
          ref="editFormRef"
          label-width="100px"
          :model="editForm"
          :rules="editFormRules"
        >
          <el-form-item label="题目描述" prop="quesDesc">
            <el-input v-model="editForm.quesDesc"></el-input>
          </el-form-item>
          <el-form-item label="A" prop="ansA">
            <el-input v-model="editForm.ansA"></el-input>
          </el-form-item>
          <el-form-item label="B" prop="ansB">
            <el-input v-model="editForm.ansB"></el-input>
          </el-form-item>
          <el-form-item label="C" prop="ansC">
            <el-input v-model="editForm.ansC"></el-input>
          </el-form-item>
          <el-form-item label="D" prop="ansD">
            <el-input v-model="editForm.ansD"></el-input>
          </el-form-item>
          <el-form-item label="正确答案" prop="rightAns">
            <el-input v-model="editForm.rightAns"></el-input>
          </el-form-item>
          <el-form-item label="答案解析" prop="ansAnalysis">
            <el-input v-model="editForm.ansAnalysis"></el-input>
          </el-form-item>
          <el-form-item label="分值" prop="score">
            <el-input v-model="editForm.score"></el-input>
          </el-form-item>
          <el-form-item label="所属科目" prop="blgSub">
            <el-input v-model="editForm.blgSub"></el-input>
          </el-form-item>
        </el-form>

        <!-- 底部区域 -->
        <span slot="footer">
          <el-button @click="modifyDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateSingleChoice"
            >确 定</el-button
          >
        </span>
      </el-dialog>

      <!-- 删除提示框 -->
      <el-dialog
        title="确认删除？"
        :visible.sync="deleteDialogVisible"
        width="30%"
        center
      >
        <span slot="footer" class="dialog-footer">
          <el-button @click="deleteDialogVisible = false">取消删除</el-button>
          <el-button type="danger" @click="deleteById()">确定删除</el-button>
        </span>
      </el-dialog>
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

      singleChoiceQuesList: [],
      total: 0,

      // 添加教师信息 相关数据
      addDialogVisible: false, // 控制对话框的vis
      //添加表单数据
      addForm: {
        quesDesc: "",
        ansA: "",
        ansB: "",
        ansC: "",
        ansD: "",
        rightAns: "",
        ansAnalysis: "",
        score: "",
        blgSub: "",
        tid: "",
      },
      // 添加表单验证规则对象
      addFormRules: {
        quesDesc: [
          { required: true, message: "题目描述不能为空！", trigger: "blur" },
        ],
        ansA: [
          { required: true, message: "选项 A 不能为空！", trigger: "blur" },
        ],
        ansB: [
          { required: true, message: "选项 B 不能为空！", trigger: "blur" },
        ],
        ansC: [
          { required: true, message: "选项 C 不能为空！", trigger: "blur" },
        ],
        ansD: [
          { required: true, message: "选项 D 不能为空！", trigger: "blur" },
        ],
        rightAns: [
          { required: true, message: "正确选项不能为空！", trigger: "blur" },
        ],
        ansAnalysis: [
          { required: true, message: "题目描述不能为空！", trigger: "blur" },
        ],
        score: [
          { required: true, message: "题目分值不能为空！", trigger: "blur" },
        ],
        blgSub: [
          { required: true, message: "所属科目不能为空！", trigger: "blur" },
        ],
      },

      // 修改教师信息 相关数据
      modifyDialogVisible: false,
      editForm: {},
      editFormRules: {
        quesDesc: [
          { required: true, message: "题目描述不能为空！", trigger: "blur" },
        ],
        ansA: [
          { required: true, message: "选项 A 不能为空！", trigger: "blur" },
        ],
        ansB: [
          { required: true, message: "选项 B 不能为空！", trigger: "blur" },
        ],
        ansC: [
          { required: true, message: "选项 C 不能为空！", trigger: "blur" },
        ],
        ansD: [
          { required: true, message: "选项 D 不能为空！", trigger: "blur" },
        ],
        rightAns: [
          { required: true, message: "正确选项不能为空！", trigger: "blur" },
        ],
        ansAnalysis: [
          { required: true, message: "题目描述不能为空！", trigger: "blur" },
        ],
        score: [
          { required: true, message: "题目分值不能为空！", trigger: "blur" },
        ],
        blgSub: [
          { required: true, message: "所属科目不能为空！", trigger: "blur" },
        ],
      },

      // 删除教师信息 相关数据
      deleteDialogVisible: false,
      deleteQid: "",
    };
  },
  created() {
    this.getSingleChoiceQuesList();
  },
  methods: {
    async getSingleChoiceQuesList() {
      const { data: res } = await this.$http.get("showSingleChoiceQuesLists", {
        params: this.queryInfo,
      });
      if (res.code !== 200) {
        return this.$message.error("获取用户列表失败! ");
      }
      this.singleChoiceQuesList = res.data.singleChoiceQuesList;
      this.total = res.data.total;
    },

    // 分页事件
    handleSizeChange(newSize) {
      // pagesize改变
      this.queryInfo.pagesize = newSize;
      this.getSingleChoiceQuesList();
    },
    handleCurrentChange(newPageNum) {
      // 当前页码值改变
      this.queryInfo.pagenum = newPageNum;
      this.getSingleChoiceQuesList();
    },

    // 添加按钮事件
    addDialogClosed() {
      // 添加 - ×
      this.$refs.addFormRef.resetFields(); // 清空表单
    },
    addSingleChoice() {
      // 添加 - 确定
      this.$refs.addFormRef.validate(async (isValid) => {
        // 若 isValid 为false 则不发请求
        if (!isValid) return;
        this.addForm.tid = window.sessionStorage.getItem("token");
        const { data: res } = await this.$http.post(
          "addSingleChoice",
          this.addForm
        );
        if (res.code != 200) {
          return this.$message.error("添加失败！");
        }
        this.$message.success("添加成功！");
        this.addDialogVisible = false;
        this.getSingleChoiceQuesList();
      });
    },

    // 编辑按钮事件
    async showEditDialog(quesId) {
      // 展示dialog
      const { data: res } = await this.$http.get(
        "getSingleChoiceById?quesId=" + quesId
      );
      if (res.code != 200) {
        return this.$message.error("查询失败！");
      }
      this.editForm = res.data;
      this.modifyDialogVisible = true;
    },
    updateSingleChoice() {
      // 编辑 - 确定
      this.$refs.editFormRef.validate(async (isValid) => {
        // 若 isValid 为false 则不发请求
        if (!isValid) return;
        const { data: res } = await this.$http.post(
          "updateSingleChoiceByQid",
          this.editForm
        );
        if (res.code != 200) {
          return this.$message.error("更新信息失败！");
        }
        this.$message.success("更新信息成功！");
        this.modifyDialogVisible = false;
        this.getSingleChoiceQuesList();
      });
    },

    // 删除按钮事件
    isDelete(quesId) {
      this.deleteDialogVisible = true;
      this.deleteQid = quesId;
    },
    async deleteById() {
      const { data: res } = await this.$http.delete(
        "deleteSingleChoiceByQid/" + this.deleteQid
      );
      if (res.code != 200) {
        return this.$message.error("删除信息失败！");
      }
      this.deleteDialogVisible = false;
      this.$message.success("删除信息成功！");
      this.getSingleChoiceQuesList();
    },
  },
};
</script>

<style lang="less" scoped>
</style>