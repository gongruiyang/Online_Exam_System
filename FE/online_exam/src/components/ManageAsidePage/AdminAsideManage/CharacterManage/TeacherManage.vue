<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcomeAdmin' }"
        >首页</el-breadcrumb-item
      >
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
      <el-breadcrumb-item>教师管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区 -->
    <el-card>
      <!-- 搜索区域/添加区域 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            type="text"
            placeholder="请输入要搜索的老师姓名"
            clearable
            v-model="queryInfo.query"
            @clear="getTeacherList"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="getTeacherList"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true"
            >添加用户</el-button
          >
        </el-col>
      </el-row>

      <!-- 数据显示区 -->
      <el-table height="650px" border stripe :data="teacherList">
        <!-- 所有数据列 -->
        <el-table-column type="index" label="#"> </el-table-column>
        <el-table-column prop="teacherName" label="姓名" width="100">
        </el-table-column>
        <el-table-column prop="email" label="邮箱" width="280">
        </el-table-column>
        <el-table-column prop="idNumber" label="身份证号码" width="280">
        </el-table-column>
        <el-table-column prop="institute" label="学院" width="100">
        </el-table-column>
        <el-table-column prop="major" label="专业" width="280">
        </el-table-column>
        <el-table-column prop="sex" label="性别" width="100"> </el-table-column>
        <el-table-column prop="title" label="教师职称" width="100">
        </el-table-column>
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
                @click="showEditDialog(scope.row.tid)"
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
                @click="isDelete(scope.row.tid)"
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
        title="添加老师信息"
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
          <el-form-item label="登陆账号" prop="username">
            <el-input v-model="addForm.username"></el-input>
          </el-form-item>
          <el-form-item label="登陆密码" prop="password">
            <el-input v-model="addForm.password"></el-input>
          </el-form-item>
          <el-form-item label="教师姓名" prop="teacherName">
            <el-input v-model="addForm.teacherName"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="addForm.email"></el-input>
          </el-form-item>
          <el-form-item label="身份证号码" prop="idNumber">
            <el-input v-model="addForm.idNumber"></el-input>
          </el-form-item>
          <el-form-item label="学院" prop="institute">
            <el-input v-model="addForm.institute"></el-input>
          </el-form-item>
          <el-form-item label="专业" prop="major">
            <el-input v-model="addForm.major"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-input v-model="addForm.sex"></el-input>
          </el-form-item>
          <el-form-item label="教师职称" prop="title">
            <el-input v-model="addForm.title"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="tel">
            <el-input v-model="addForm.tel"></el-input>
          </el-form-item>
        </el-form>

        <!-- 底部区域 -->
        <span slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addTeacher">确 定</el-button>
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
          <el-form-item label="登陆账号" prop="username">
            <el-input v-model="editForm.username"></el-input>
          </el-form-item>
          <el-form-item label="登陆密码" prop="password">
            <el-input v-model="editForm.password"></el-input>
          </el-form-item>
          <el-form-item label="教师姓名" prop="teacherName">
            <el-input v-model="editForm.teacherName"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="editForm.email"></el-input>
          </el-form-item>
          <el-form-item label="身份证号码" prop="idNumber">
            <el-input v-model="editForm.idNumber"></el-input>
          </el-form-item>
          <el-form-item label="学院" prop="institute">
            <el-input v-model="editForm.institute"></el-input>
          </el-form-item>
          <el-form-item label="专业" prop="major">
            <el-input v-model="editForm.major"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-input v-model="editForm.sex"></el-input>
          </el-form-item>
          <el-form-item label="教师职称" prop="title">
            <el-input v-model="editForm.title"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="tel">
            <el-input v-model="editForm.tel"></el-input>
          </el-form-item>
        </el-form>

        <!-- 底部区域 -->
        <span slot="footer">
          <el-button @click="modifyDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateTeacher">确 定</el-button>
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

      teacherList: [],
      total: 0,

      // 添加教师信息 相关数据
      addDialogVisible: false, // 控制对话框的vis
      //添加表单数据
      addForm: {
        username: "",
        password: "",
        teacherName: "",
        email: "",
        idNumber: "",
        institute: "",
        major: "",
        sex: "",
        title: "",
        tel: "",
        role: "2",
      },
      // 添加表单验证规则对象
      addFormRules: {
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
        teacherName: [
          { required: true, message: "教师姓名不能为空！", trigger: "blur" },
        ],
        email: [{ required: true, message: "邮箱不能为空！", trigger: "blur" }],
        idNumber: [
          { required: true, message: "身份证号码不能为空！", trigger: "blur" },
        ],
        institute: [
          { required: true, message: "学院不能为空！", trigger: "blur" },
        ],
        major: [{ required: true, message: "专业不能为空！", trigger: "blur" }],
        sex: [{ required: true, message: "性别不能为空！", trigger: "blur" }],
        title: [
          { required: true, message: "教师职称不能为空！", trigger: "blur" },
        ],
        tel: [{ required: true, message: "电话不能为空！", trigger: "blur" }],
      },

      // 修改教师信息 相关数据
      modifyDialogVisible: false,
      editForm: {},
      editFormRules: {
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
        teacherName: [
          { required: true, message: "教师姓名不能为空！", trigger: "blur" },
        ],
        email: [{ required: true, message: "邮箱不能为空！", trigger: "blur" }],
        idNumber: [
          { required: true, message: "身份证号码不能为空！", trigger: "blur" },
        ],
        institute: [
          { required: true, message: "学院不能为空！", trigger: "blur" },
        ],
        major: [{ required: true, message: "专业不能为空！", trigger: "blur" }],
        sex: [{ required: true, message: "性别不能为空！", trigger: "blur" }],
        title: [
          { required: true, message: "教师职称不能为空！", trigger: "blur" },
        ],
        tel: [{ required: true, message: "电话不能为空！", trigger: "blur" }],
      },

      // 删除教师信息 相关数据
      deleteDialogVisible: false,
      deleteTid: "",
    };
  },
  created() {
    this.getTeacherList();
  },
  methods: {
    async getTeacherList() {
      const { data: res } = await this.$http.get("showTeacherLists", {
        params: this.queryInfo,
      });
      if (res.code !== 200) {
        return this.$message.error("获取用户列表失败! ");
      }
      this.teacherList = res.data.teacherList;
      this.total = res.data.total;
    },

    // 分页事件
    handleSizeChange(newSize) {
      // pagesize改变
      this.queryInfo.pagesize = newSize;
      this.getTeacherList();
    },
    handleCurrentChange(newPageNum) {
      // 当前页码值改变
      this.queryInfo.pagenum = newPageNum;
      this.getTeacherList();
    },

    // 添加按钮事件
    addDialogClosed() {
      // 添加 - ×
      this.$refs.addFormRef.resetFields(); // 清空表单
    },
    addTeacher() {
      // 添加 - 确定
      this.$refs.addFormRef.validate(async (isValid) => {
        // 若 isValid 为false 则不发请求
        if (!isValid) return;
        const { data: res } = await this.$http.post("addTeacher", this.addForm);
        if (res.code != 200) {
          return this.$message.error("添加失败！");
        }
        this.$message.success("添加成功！");
        this.addDialogVisible = false;
        this.getTeacherList();
      });
    },

    // 编辑按钮事件
    async showEditDialog(tid) {
      // 展示dialog
      const { data: res } = await this.$http.get("getTeacherById?tid=" + tid);
      if (res.code != 200) {
        return this.$message.error("查询失败！");
      }
      this.editForm = res.data;
      this.modifyDialogVisible = true;
    },
    updateTeacher() {
      // 编辑 - 确定
      this.$refs.editFormRef.validate(async (isValid) => {
        // 若 isValid 为false 则不发请求
        if (!isValid) return;
        const { data: res } = await this.$http.post(
          "updateTeacherById",
          this.editForm
        );
        if (res.code != 200) {
          return this.$message.error("更新信息失败！");
        }
        this.$message.success("更新信息成功！");
        this.modifyDialogVisible = false;
        this.getTeacherList();
      });
    },

    // 删除按钮事件
    isDelete(tid) {
      this.deleteDialogVisible = true;
      this.deleteTid = tid;
    },
    async deleteById() {
      const { data: res } = await this.$http.delete(
        "deleteTeacherById/" + this.deleteTid
      );
      if (res.code != 200) {
        return this.$message.error("删除信息失败！");
      }
      this.deleteDialogVisible = false;
      this.$message.success("删除信息成功！");
      this.getTeacherList();
    },
  },
};
</script>


<style lang="less" scoped>
</style>