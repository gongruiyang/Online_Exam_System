// 单文件组件:用于登录的组件

// 模板区
<template>
  <div class="login_container">
    <!-- 登录框 -->
    <div class="login_box">
      <!-- 1.图片区域 -->
      <div class="img_box">
        <img src="../../assets/imgs/logo.png" alt="" />
      </div>

      <!-- 2.登录表单区域 对应数据区的 form 对象 -->
      <el-form
        class="login_frm"
        label-width="0px"
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginFormRules"
      >
        <!-- 2.1 权限选择区域 -->
        <el-form-item>
          <el-radio label="1" v-model="loginForm.role">超级权限</el-radio>
          <el-radio label="2" v-model="loginForm.role">教师</el-radio>
          <el-radio label="3" v-model="loginForm.role">学生</el-radio>
        </el-form-item>

        <!-- 2.2 账号 -->
        <el-form-item prop="username">
          <el-input
            placeholder="请输入您的账号"
            prefix-icon="el-icon-user-solid"
            v-model="loginForm.username"
          ></el-input>
        </el-form-item>

        <!-- 2.3 密码 -->
        <el-form-item prop="password">
          <el-input
            placeholder="请输入您的密码"
            prefix-icon="el-icon-key"
            show-password
            v-model="loginForm.password"
          ></el-input>
        </el-form-item>

        <!-- 2.4 按钮区 -->
        <el-form-item class="btns">
          <el-button type="success" round @click="signIn">登录</el-button>
          <el-button type="primary" round @click="reset">重置</el-button>
          <el-button
            type="info"
            v-show="loginForm.role != 1"
            round
            @click="register"
            >注册</el-button
          >
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

// 行为区
<script>
export default {
  data() {
    // 登录表单的数据绑定对象
    return {
      loginForm: {
        username: "",
        password: "",
        role: "3",
      },
      loginFormRules: {
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
      },
    };
  },
  methods: {
    signIn() {
      // 表单预验证 rules
      this.$refs.loginFormRef.validate(async (isValid) => {
        // 若 isValid 为false 则不发请求
        if (!isValid) return;

        // 可以发请求
        const { data: res } = await this.$http.post("login", this.loginForm);
        var code = res.code;
        // 判断请求是否成功，若成功跳转至对应home页面
        if (code == "404") return this.$message.error("账号或密码错误！");

        // 将登录成功之后的 账号 作为 token 存储到客户端的 sessionStorage 中
        window.sessionStorage.setItem("token", res.data);

        // 跳转到对应权限的 home 页面中
        this.$message.success("登陆成功");
        if (code == "1") {
          this.$router.push("/adminHome");
        } else if (code == "2") {
          this.$router.push("/teacherHome");
        } else if (code == "3") {
          this.$router.push("/studentHome");
        }
      });
    },
    register() {
      // TODO
      console.log("注册!");
    },
    reset() {
      //  拿到表单引用
      this.$refs["loginFormRef"].resetFields();
      this.loginForm.role = "3";
    },
  },
};
</script>

// 样式区
<style lang="less" scoped>
// .是类选择器
.login_container {
  background-color: #2b4b6b;
  height: 100%;
}
.login_box {
  width: 450px;
  height: 350px;
  background-color: #fff;
  border-radius: 3px; // 圆角边框
  position: absolute; //绝对定位
  // 距离左侧和上侧50%
  left: 50%;
  top: 50%;
  // 向左和向下移动50%
  transform: translate(-50%, -50%);
  .img_box {
    height: 130px;
    width: 130px;
    background-color: #fff;
    border: 1px solid #eee;
    box-shadow: 0 0 10px #ddd; // 边框阴影
    border-radius: 50%; // 圆角边框
    padding: 10px;
    position: absolute; //绝对定位
    left: 50%;
    transform: translate(-50%, -50%);

    img {
      width: 100%;
      height: 100%;
      border-radius: 50%; // 圆角边框
      background-color: #eee;
    }
  }
}
.btns {
  // 居右对齐
  display: flex;
  justify-content: flex-end;
}
.login_frm {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}
</style>