<template>
  <div>
    <!-- 面包屑导航区 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/welcomeTeacher' }"
        >首页</el-breadcrumb-item
      >
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
      <el-breadcrumb-item>个人信息</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 内容主体区 -->
    <el-form
      ref="editFormRef"
      label-width="auto"
      :model="editForm"
      :rules="editFormRules"
    >
      <el-form-item label="登陆账号" prop="username">
        <el-input v-model="editForm.username"></el-input>
      </el-form-item>
      <el-form-item label="登陆密码" prop="password">
        <el-input v-model="editForm.password" show-password></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="teacherName">
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
      <el-form-item label="电话" prop="tel">
        <el-input v-model="editForm.tel"></el-input>
      </el-form-item>
    </el-form>
    <!-- 底部区域 -->
    <span slot="footer">
      <el-button type="primary" @click="updatePersonalInfor">保 存</el-button>
    </span>
  </div>
</template>

<script>
export default {
  data() {
    return {
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
        tel: [{ required: true, message: "电话不能为空！", trigger: "blur" }],
      },
    };
  },
  created() {
    this.getPersonalInfor();
  },
  methods: {
    async getPersonalInfor() {
      // username -> teacher
      const username = window.sessionStorage.getItem("token");
      const { data: ans } = await this.$http.get(
        "getTeacherByUsername?username=" + username
      );
      if (ans.code !== 200) {
        return this.$message.error("获取信息失败! ");
      }
      this.editForm = ans.data;
    },
    updatePersonalInfor() {
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
        this.getPersonalInfor();
      });
    },
  },
};
</script>

<style lang="less" scoped>
</style>