<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcomeAdmin' }"
        >首页</el-breadcrumb-item
      >
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
      <el-breadcrumb-item>学生管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card>
      <!-- 搜索区域/添加区域 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            type="text"
            placeholder="请输入要搜索的学生姓名"
            clearable
            v-model="queryInfo.query"
            @clear="getStudentList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getStudentList"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true"
            >添加用户</el-button
          >
        </el-col>
      </el-row>

      <!-- 数据显示区域 -->
      <el-table height="650px" border stripe :data="studentList">
        <!-- 所有数据列 -->
        <el-table-column type="index" label="#"> </el-table-column>
        <el-table-column prop="sid" label="学号" width="100"> </el-table-column>
        <el-table-column prop="studentName" label="姓名" width="100">
        </el-table-column>
        <el-table-column prop="email" label="邮箱" width="280">
        </el-table-column>
        <el-table-column prop="idNumber" label="身份证号码" width="280">
        </el-table-column>
        <el-table-column prop="grade" label="年级" width="100">
        </el-table-column>
        <el-table-column prop="institute" label="学院" width="100">
        </el-table-column>
        <el-table-column prop="major" label="专业" width="280">
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="100"> </el-table-column>
        <el-table-column prop="tel" label="电话" width="280"> </el-table-column>
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
                @click="showEditDialog(scope.row.sid)"
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
                @click="isDelete(scope.row.sid)"
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
        title="添加学生信息"
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
          <el-form-item label="学号" prop="sid">
            <el-input v-model="addForm.sid"></el-input>
          </el-form-item>
          <el-form-item label="登陆账号" prop="username">
            <el-input v-model="addForm.username"></el-input>
          </el-form-item>
          <el-form-item label="登陆密码" prop="password">
            <el-input v-model="addForm.password"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="studentName">
            <el-input v-model="addForm.studentName"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="addForm.email"></el-input>
          </el-form-item>
          <el-form-item label="身份证号码" prop="idNumber">
            <el-input v-model="addForm.idNumber"></el-input>
          </el-form-item>
          <el-form-item label="年级" prop="grade">
            <el-input v-model="addForm.grade"></el-input>
          </el-form-item>
          <el-form-item label="学院" prop="institute">
            <el-input v-model="addForm.institute"></el-input>
          </el-form-item>
          <el-form-item label="专业" prop="major">
            <el-input v-model="addForm.major"></el-input>
          </el-form-item>
          <el-form-item label="班级" prop="class_">
            <el-input v-model="addForm.class_"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-input v-model="addForm.sex"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="tel">
            <el-input v-model="addForm.tel"></el-input>
          </el-form-item>
        </el-form>
        <!-- 底部区域 -->
        <span slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addStudent">确 定</el-button>
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
          <el-form-item label="学号" prop="sid">
            <el-input v-model="editForm.sid"></el-input>
          </el-form-item>
          <el-form-item label="登陆账号" prop="username">
            <el-input v-model="editForm.username"></el-input>
          </el-form-item>
          <el-form-item label="登陆密码" prop="password">
            <el-input v-model="editForm.password"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="studentName">
            <el-input v-model="editForm.studentName"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="editForm.email"></el-input>
          </el-form-item>
          <el-form-item label="身份证号码" prop="idNumber">
            <el-input v-model="editForm.idNumber"></el-input>
          </el-form-item>
          <el-form-item label="年级" prop="grade">
            <el-input v-model="editForm.grade"></el-input>
          </el-form-item>
          <el-form-item label="学院" prop="institute">
            <el-input v-model="editForm.institute"></el-input>
          </el-form-item>
          <el-form-item label="专业" prop="major">
            <el-input v-model="editForm.major"></el-input>
          </el-form-item>
          <el-form-item label="班级" prop="class_">
            <el-input v-model="editForm.class_"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-input v-model="editForm.sex"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="tel">
            <el-input v-model="editForm.tel"></el-input>
          </el-form-item>
        </el-form>

        <!-- 底部区域 -->
        <span slot="footer">
          <el-button @click="modifyDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateStudent">确 定</el-button>
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

      studentList: [],
      total: 0,

      // 添加信息 相关数据
      addDialogVisible: false,
      addForm: {
        sid: "",
        username: "",
        password: "",
        studentName: "",
        email: "",
        idNumber: "",
        grade: "",
        institute: "",
        major: "",
        class_: "",
        sex: "",
        tel: "",
        role: "3",
      },
      addFormRules: {
        sid: [
          { required: true, message: "学生学号不能为空！", trigger: "blur" },
        ],
        username: [
          { required: true, message: "用户名不能为空！", trigger: "blur" },
          {
            min: 3,
            max: 15,
            message: "用户名长度在 3 到 15 个字符！",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "密码不能为空！", trigger: "blur" },
          {
            min: 3,
            max: 15,
            message: "密码长度在 3 到 15 个字符！",
            trigger: "blur",
          },
        ],
        studentName: [
          { required: true, message: "教师姓名不能为空！", trigger: "blur" },
        ],
        email: [{ required: true, message: "邮箱不能为空！", trigger: "blur" }],
        idNumber: [
          { required: true, message: "身份证号码不能为空！", trigger: "blur" },
        ],
        grade: [{ required: true, message: "年级不能为空！", trigger: "blur" }],
        institute: [
          { required: true, message: "学院不能为空！", trigger: "blur" },
        ],
        major: [{ required: true, message: "专业不能为空！", trigger: "blur" }],
        class_: [
          { required: true, message: "班级不能为空！", trigger: "blur" },
        ],
        sex: [{ required: true, message: "性别不能为空！", trigger: "blur" }],
        tel: [{ required: true, message: "电话不能为空！", trigger: "blur" }],
      },

      // 修改信息 相关数据
      modifyDialogVisible: false,
      editForm: {},
      editFormRules: {
        sid: [
          { required: true, message: "学生学号不能为空！", trigger: "blur" },
        ],
        username: [
          { required: true, message: "用户名不能为空！", trigger: "blur" },
          {
            min: 3,
            max: 15,
            message: "用户名长度在 3 到 15 个字符！",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "密码不能为空！", trigger: "blur" },
          {
            min: 3,
            max: 15,
            message: "密码长度在 3 到 15 个字符！",
            trigger: "blur",
          },
        ],
        studentName: [
          { required: true, message: "教师姓名不能为空！", trigger: "blur" },
        ],
        email: [{ required: true, message: "邮箱不能为空！", trigger: "blur" }],
        idNumber: [
          { required: true, message: "身份证号码不能为空！", trigger: "blur" },
        ],
        grade: [{ required: true, message: "年级不能为空！", trigger: "blur" }],
        institute: [
          { required: true, message: "学院不能为空！", trigger: "blur" },
        ],
        major: [{ required: true, message: "专业不能为空！", trigger: "blur" }],
        class_: [
          { required: true, message: "班级不能为空！", trigger: "blur" },
        ],
        sex: [{ required: true, message: "性别不能为空！", trigger: "blur" }],
        tel: [{ required: true, message: "电话不能为空！", trigger: "blur" }],
      },

      // 删除信息 相关数据
      deleteDialogVisible: false,
      deleteSid: "",
    };
  },
  created() {
    this.getStudentList();
  },
  methods: {
    async getStudentList() {
      const { data: res } = await this.$http.get("showStudentLists", {
        params: this.queryInfo,
      });
      if (res.code !== 200) {
        return this.$message.error("获取用户列表失败! ");
      }
      this.studentList = res.data.studentList;
      this.total = res.data.total;
    },

    // 监听 pagesize 改变的事件
    handleSizeChange(newSize) {
      this.queryInfo.pagesize = newSize;
      this.getStudentList();
    },
    // 监听 页码值 改变的事件
    handleCurrentChange(newPageNum) {
      this.queryInfo.pagenum = newPageNum;
      this.getStudentList();
    },

    // 添加按钮事件
    addDialogClosed() {
      // 添加 - ×
      this.$refs.addFormRef.resetFields(); // 清空表单
    },
    addStudent() {
      // 添加 - 确定
      this.$refs.addFormRef.validate(async (isValid) => {
        // 若 isValid 为false 则不发请求
        if (!isValid) return;
        const { data: res } = await this.$http.post("addStudent", this.addForm);
        if (res.code != 200) {
          return this.$message.error("添加失败！");
        }
        this.$message.success("添加成功！");
        this.addDialogVisible = false;
        this.getStudentList();
      });
    },

    // 编辑按钮事件
    async showEditDialog(sid) {
      // 展示dialog
      const { data: res } = await this.$http.get("getStudentById?sid=" + sid);
      if (res.code != 200) {
        return this.$message.error("查询失败！");
      }
      this.editForm = res.data;
      this.modifyDialogVisible = true;
    },
    updateStudent() {
      // 编辑 - 确定
      this.$refs.editFormRef.validate(async (isValid) => {
        // 若 isValid 为false 则不发请求
        if (!isValid) return;
        const { data: res } = await this.$http.post(
          "updateStudentById",
          this.editForm
        );
        if (res.code != 200) {
          return this.$message.error("更新信息失败！");
        }
        this.$message.success("更新信息成功！");
        this.modifyDialogVisible = false;
        this.getStudentList();
      });
    },

    // 删除按钮事件
    isDelete(tid) {
      this.deleteDialogVisible = true;
      this.deleteSid = tid;
    },
    async deleteById() {
      const { data: res } = await this.$http.delete(
        "deleteStudentById/" + this.deleteSid
      );
      if (res.code != 200) {
        return this.$message.error("删除信息失败！");
      }
      this.deleteDialogVisible = false;
      this.$message.success("删除信息成功！");
      this.getStudentList();
    },
  },
};
</script>


<style lang="less" scoped>
</style>