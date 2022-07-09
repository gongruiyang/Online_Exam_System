import Vue from 'vue'
import {
    Button, Form, FormItem, Input,
    Radio, Message, Container, Header,
    Aside, Main, Menu, Submenu,
    MenuItemGroup, MenuItem, Breadcrumb, BreadcrumbItem,
    Card, Row, Col,
    Table, TableColumn, Tooltip, Pagination,
    Dialog, DatePicker, TimePicker, Calendar,
    RadioGroup, Upload
} from 'element-ui'

// 全局注册组件
Vue.use(RadioGroup)
Vue.use(Button)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
Vue.use(Radio)
Vue.use(Container)
Vue.use(Header)
Vue.use(Aside)
Vue.use(Main)
Vue.use(Menu)
Vue.use(Submenu)
Vue.use(MenuItemGroup)
Vue.use(MenuItem)
Vue.use(Breadcrumb)
Vue.use(BreadcrumbItem)
Vue.use(Card)
Vue.use(Row)
Vue.use(Col)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Tooltip)
Vue.use(Pagination)
Vue.use(Dialog)
Vue.use(DatePicker)
Vue.use(TimePicker)
Vue.use(Calendar)
Vue.use(Upload)
// 挂载到全局
Vue.prototype.$message = Message