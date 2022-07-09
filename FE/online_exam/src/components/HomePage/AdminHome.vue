<template>
  <el-container class="container">
    <!-- 上：头部区域 -->
    <el-header>
      <!-- 图片和文字 -->
      <div class="img_box">
        <img src="../../assets/imgs/logo.png" alt="" />
        <span>在线考试系统-超级管理员后台</span>
      </div>

      <!-- 退出按钮 -->
      <el-button type="danger" round @click="logout">退出</el-button>
    </el-header>

    <!-- 下：页面主体区域 -->
    <el-container>
      <!-- 左：页面侧边栏菜单区 -->
      <el-aside :width="hiddenWidth">
        <div class="hide-button" @click="hiddenLeftMenu">|||</div>
        <!-- 角色管理 -->
        <el-menu
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#409EFF"
          unique-opened
          default-active="/welcomeAdmin"
          :collapse="isHidden"
          :collapse-transition="false"
          :router="true"
        >
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-user-solid"></i>
              <span>角色管理</span>
            </template>
            <!-- 二级菜单 -->
            <el-menu-item :index="'/teacherManage'">教师管理</el-menu-item>
            <el-menu-item :index="'/studentManage'">学生管理</el-menu-item>
          </el-submenu>

          <!-- 考试场次管理 -->
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>考试管理</span>
            </template>
            <!-- 二级菜单 -->
            <el-menu-item :index="'/examManage'">考试安排</el-menu-item>
          </el-submenu>

          <!-- 试卷管理 -->
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-folder"></i>
              <span>试题管理</span>
            </template>
            <!-- 二级菜单 -->
            <el-menu-item :index="'/singleChoiceManage'">单选题</el-menu-item>
            <el-menu-item :index="'/fillGapManage'">填空题</el-menu-item>
            <el-menu-item :index="'/judgeManage'">判断题</el-menu-item>
            <el-menu-item :index="'/shotAnsManage'">主观题</el-menu-item>
          </el-submenu>

          <!-- 个人信息修改 -->
          <el-submenu index="4">
            <template slot="title">
              <i class="el-icon-s-tools"></i>
              <span>个人信息</span>
            </template>
            <!-- 二级菜单 -->
            <el-menu-item :index="'/personalInfor'">信息修改</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <!-- 右：主体展示区 -->
      <el-main>
        <!-- 路由占位符 -->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      isHidden: false,
      hiddenWidth: "150px",
    };
  },
  methods: {
    logout() {
      // 清空 sessionStorage 中的 token
      window.sessionStorage.clear();
      this.$router.push("/login");
    },
    hiddenLeftMenu() {
      this.isHidden = !this.isHidden;
      if (this.isHidden == true) {
        this.hiddenWidth = "64px";
      } else if (this.isHidden == false) {
        this.hiddenWidth = "150px";
      }
    },
  },
};
</script>

<style lang="less" scoped>
.container {
  height: 100%;
}

.el-header {
  background-color: #545c64;
  color: #409eff;
  font-size: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  img {
    width: 50px;
    height: 50px;
  }

  > div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }
}

.el-aside {
  background-color: #545c64;
  color: #333;
  text-align: center;
  // line-height: 200px;
  .el-menu {
    border-right: none;
  }
}

.el-main {
  background-color: #e9eef3;
  // color: #333;
  // text-align: center;
  // line-height: 160px;
}

.hide-button {
  background-color: #545c64;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer; /* 鼠标放上去后变成小手 */
}
</style>