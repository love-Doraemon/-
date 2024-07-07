<template>
  <div>
  <el-container>
    <el-aside style="width: 200px;min-height:100vh">
      <div style="height:60px;background-color:#8c939d;color:white;display: flex;align-items:center;justify-content:center">
        logo
      </div>
    <el-menu
      router
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      style="border:none">
      <el-menu-item index="/home">
        <i class="el-icon-location"></i>
        <span slot="title">系统首页</span>
      </el-menu-item>
      <el-menu-item index="/inform">
        <i class="el-icon-location"></i>
        <span slot="title">设备数据</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span>导航二</span>
        </template>
        <el-menu-item-group>
          <template slot="title">分组一</template>
          <el-menu-item index="1-1">选项1</el-menu-item>
          <el-menu-item index="1-2">选项2</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="分组2">
          <el-menu-item index="1-3">选项3</el-menu-item>
        </el-menu-item-group>
        <el-submenu index="1-4">
          <template slot="title">选项4</template>
          <el-menu-item index="1-4-1">选项1</el-menu-item>
        </el-submenu>
      </el-submenu>
      <el-menu-item index="3" disabled>
        <i class="el-icon-document"></i>
        <span slot="title">导航三</span>
      </el-menu-item>
      <el-menu-item index="/user" v-if="user.isadministrator=== true">
        <i class="el-icon-setting"></i>
        <span slot="title">管理员界面</span>
      </el-menu-item>

      <el-submenu >
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span>老师给的接口</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="/Alarm">get报警信息</el-menu-item>
          <el-menu-item index="/GetData">get遥测数据</el-menu-item>
          <el-menu-item index="/WsData">ws遥测数据</el-menu-item>
          <el-menu-item index="/postcom">post rpc命令</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

      <el-menu-item index="/1">
        <i class="el-icon-setting"></i>
        <span slot="title">选项</span>
      </el-menu-item>
    </el-menu>
    </el-aside>
    <el-container>
      <el-header>
  

          <div style="flex:1;width:0;display:flex;align-items: center;justify-content: flex-end">
            <el-dropdown>
              <img src="@/assets/avatar.jpg" style="width:40px;height:40px">
              <el-dropdown-menu slot="dropdown">
    <el-dropdown-item>个人信息</el-dropdown-item>
    <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
  </el-dropdown-menu>
            </el-dropdown>
          </div>

      
      </el-header>
      <el-main>
        <router-view/>
      </el-main>
<!--      <el-footer>Footer</el-footer>-->
    </el-container>
  </el-container>
  </div>
</template>




<script>
import router from "@/router";
import request from "@/utils/request";

export default {
  data() {
        return {
            name: 'DSY', // 默认用户名为DSY
            message: 2 ,// 默认未读消息数量为2
            users: [],
            user:JSON.parse(localStorage.getItem('user-inform')||'{}'),
        }
    },
  mounted(){
      request.get('/user/selectAll').then(res => {
        this.users = res.data
      })
    },
  computed: {
        username() {
            let username = localStorage.getItem('ms_username'); // 从localStorage中获取用户名
            return username ? username : this.name; // 如果存在用户名，则返回用户名，否则返回默认用户名
        }
    },


  components: {


    },
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      logout(){
        localStorage.removeItem('user-inform')
        this.$router.push('/login')
      }
    }

}
</script>

<style >
.el-header {
  box-shadow:2px 0 6px rgba(0,21,41,.35);
  display:flex;
  align-items:center;
}

</style>
