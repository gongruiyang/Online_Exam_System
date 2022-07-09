import Vue from 'vue'
import Router from 'vue-router'


// login 组件
import Login from '../components/LoginPage/Login.vue'

// home 组件
import AdminHome from '../components/HomePage/AdminHome.vue'
import TeacherHome from '../components/HomePage/TeacherHome.vue'
import StudentHome from '../components/HomePage/StudentHome.vue'

// welcome page 组件
import WelcomeAdmin from '../components/WelcomePage/WelcomeAdmin.vue'
import WelcomeStudent from '../components/WelcomePage/WelcomeStudent.vue'
import WelcomeTeacher from '../components/WelcomePage/WelcomeTeacher.vue'

// admin home aside page 组件
import AdminAsideTeacherManage from '../components/ManageAsidePage/AdminAsideManage/CharacterManage/TeacherManage.vue'
import AdminAsideStudentManage from '../components/ManageAsidePage/AdminAsideManage/CharacterManage/StudentManage.vue'
import AdminAsideExamArrangeManage from '../components/ManageAsidePage/AdminAsideManage/ExamManage/ExamArrangementManage.vue'
import AdminAsideExamPaperManage from '../components/ManageAsidePage/AdminAsideManage/ExamManage/ExamPaperManage.vue'
import AdminPersonalInformationManage from '../components/ManageAsidePage/AdminAsideManage/PersonalInfor.vue'
import AdminFillGapManage from '../components/ManageAsidePage/AdminAsideManage/QuesManage/FillGapManage.vue'
import AdminJudgeManage from '../components/ManageAsidePage/AdminAsideManage/QuesManage/JudgeManage.vue'
import AdminShortAnsManage from '../components/ManageAsidePage/AdminAsideManage/QuesManage/ShortAnsManage.vue'
import AdminSingleChoiceManage from '../components/ManageAsidePage/AdminAsideManage/QuesManage/SingleChoiceManage.vue'

// teacher home aside page 组件
import TeacherAsideStudentManage from '../components/ManageAsidePage/TeacherAsideManage/CharacterManage/StudentManage.vue'
import TeacherAsideTeacherInfo from '../components/ManageAsidePage/TeacherAsideManage/CharacterManage/PersonalInfo.vue'
import TeacgerAsideExam from '../components/ManageAsidePage/TeacherAsideManage/ExamManage/ExamArrangementManage.vue'
import TeacgerAsidePaper from '../components/ManageAsidePage/TeacherAsideManage/ExamManage/ExamPaperManage.vue'
import TeacgerAsideFillGap from '../components/ManageAsidePage/TeacherAsideManage/QuesManage/FillGapManage.vue'
import TeacgerAsideJudge from '../components/ManageAsidePage/TeacherAsideManage/QuesManage/JudgeManage.vue'
import TeacgerAsideShortAns from '../components/ManageAsidePage/TeacherAsideManage/QuesManage/ShortAnsManage.vue'
import TeacgerAsideSingleChoice from '../components/ManageAsidePage/TeacherAsideManage/QuesManage/SingleChoiceManage.vue'
import ShowExamPaper from '../components/ManageAsidePage/TeacherAsideManage/ExamManage/ShowExamPaper.vue'
import CheckExam from '../components/ManageAsidePage/TeacherAsideManage/ExamManage/CheckExam.vue'
import DataReport from '../components/ManageAsidePage/TeacherAsideManage/ExamManage/DataReport.vue'

// student home aside page 组件
import StudentAsideStudentInfo from '../components/ManageAsidePage/StudentAsideManage/CharacterManage/PersonalInfo.vue'
import StudentAsideExam from '../components/ManageAsidePage/StudentAsideManage/ExamManage/ExamArrangementManage.vue'
import StudentAsideMyPaper from '../components/ManageAsidePage/StudentAsideManage/ExamManage/StudentPaper.vue'
import WritePaper from '../components/ManageAsidePage/StudentAsideManage/ExamManage/WritePaper.vue'

Vue.use(Router)

const router = new Router({
  routes: [
    // 给Login组件定义路由规则
    { path: '/', redirect: "/login" },
    { path: '/login', component: Login },
    // 给admin home组件定义路由规则
    {
      path: '/adminHome', component: AdminHome,
      redirect: '/welcomeAdmin',
      children:
        [
          { path: '/welcomeAdmin', component: WelcomeAdmin },

          { path: '/teacherManage', component: AdminAsideTeacherManage },
          { path: '/studentManage', component: AdminAsideStudentManage },

          { path: '/examManage', component: AdminAsideExamArrangeManage },
          { path: '/paperManage', component: AdminAsideExamPaperManage },

          { path: '/fillGapManage', component: AdminFillGapManage },
          { path: '/judgeManage', component: AdminJudgeManage },
          { path: '/shotAnsManage', component: AdminShortAnsManage },
          { path: '/singleChoiceManage', component: AdminSingleChoiceManage },

          { path: '/personalInfor', component: AdminPersonalInformationManage }
        ]
    },
    {
      path: '/teacherHome', component: TeacherHome,
      redirect: '/welcomeTeacher',
      children:
        [
          { path: '/welcomeTeacher', component: WelcomeTeacher },

          { path: '/myStudent', component: TeacherAsideStudentManage },
          { path: '/teacherInfo', component: TeacherAsideTeacherInfo },

          { path: '/teacherExam', component: TeacgerAsideExam },
          { path: '/teacherPaper', component: TeacgerAsidePaper },
          { path: '/report', component: DataReport },

          { path: '/teacherQuesFillGap', component: TeacgerAsideFillGap },
          { path: '/teacherQuesJudge', component: TeacgerAsideJudge },
          { path: '/teacherQuesShortAns', component: TeacgerAsideShortAns },
          { path: '/teacherQuesSingleChoice', component: TeacgerAsideSingleChoice },

          { path: '/showPaper', component: ShowExamPaper },
          { path: '/checkExam', component: CheckExam },
        ]
    },
    {
      path: '/studentHome', component: StudentHome,
      redirect: '/WelcomeStudent',
      children:
        [
          { path: '/WelcomeStudent', component: WelcomeStudent },

          { path: '/studentInfo', component: StudentAsideStudentInfo },

          { path: '/studentExam', component: StudentAsideExam },
          { path: '/myPaper', component: StudentAsideMyPaper },
          { path: '/writePaper', component: WritePaper }
        ]
    }
  ]
})

// 挂载路由导航守卫：作用是拦截没登录的请求
// to 表示将要访问的路径
// from 表示从哪个路径跳转而来
// next 是一个函数 表示放行 : next() \ next('/login')
router.beforeEach((to, from, next) => {
  const tokenStr = window.sessionStorage.getItem('token');
  // 1. 放行 login 请求
  if (to.path === '/login')
    return next();
  // 2. 拦截没有登录的请求
  if (!tokenStr) return next('/login');
  // 3. 放行已经登录的请求
  next();
})

// 暴露路由对象
export default router