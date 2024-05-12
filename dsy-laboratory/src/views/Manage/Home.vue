<template>
    <div>
        <el-card >
        <el-table :data="users">
          <el-table-column label="id" prop="id"></el-table-column>
          <el-table-column label="用户名" prop="username"></el-table-column>
          <el-table-column label="密码" prop="password"></el-table-column>
          <el-table-column label="是否管理员" prop="isadministrator" :formatter="(row, column, cellValue) => cellValue == 1 ? '是' : '否'"></el-table-column>
        </el-table>
      </el-card>
    </div>
</template>


<script>
import router from "@/router";
import request from "@/utils/request";

export default {
  data() {
        return {
            users: []
        }
    },
  mounted(){
      request.get('/user/selectAll').then(res => {
        this.users = res.data
      })
    },

}
</script>