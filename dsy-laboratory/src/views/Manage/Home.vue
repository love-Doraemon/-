<template>
    <div>

      <div >
        <el-input style="width:200px" placeholder="查询用户名" v-model="username"></el-input>
        <el-button @click="load(1)">查询</el-button>
      </div>
      <div style ="margin-bottom: 10px">
        <el-button type="primary" plain @click="handleAdd">新增</el-button>
        <el-button type="danger" >批量删除</el-button>
      </div>


        <el-card >
        <el-table :data="users">
          <el-table-column label="id" prop="id"></el-table-column>
          <el-table-column label="用户名" prop="username"></el-table-column>
          <el-table-column label="密码" prop="password"></el-table-column>
          <el-table-column label="是否管理员" prop="isadministrator" :formatter="(row, column, cellValue) => cellValue == 1 ? '是' : '否'"></el-table-column>
        </el-table>

          <div style="margin: 10px 0">
            <el-pagination
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[10, 20, 30, 40]"
              :page-size="pageSize"
              layout="total, prev, pager, next"
              :total="total">
            </el-pagination>
          </div>

          <el-dialog title="" :visible.sync="formVisible" width="30%">
            <el-form :model="form" label-width="80px" style="padding-right:20px">
              <div>
              <el-upload
                class="avatar-uploader"
                action="http://localhost:9090/file/upload"
                :headers="{token:user.token}"
                :show-file-list="false">
<!--                :on-success="handleAvatarSuccess">-->
<!--                <img v-if="form.avatar" :src="form.avatar" class="avatar">-->
<!--                <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
              </el-upload>
              </div>
              <el-form-item label="id" prop="id">
                <el-input v-model="form.id" placeholder="ID"></el-input>
              </el-form-item>
              <el-form-item label="用户名" prop="username">
                <el-input v-model="form.username" placeholder="用户名" disabled></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input v-model="form.password" placeholder="密码" ></el-input>
              </el-form-item>










            </el-form>

            <div slot="footer" class="dialog-footer">
              <el-button @click="formVisible = false">取 消</el-button>
              <el-button type="primary" @click="save">确 定</el-button>

            </div>
          </el-dialog>

      </el-card>
    </div>
</template>


<script>
import router from "@/router";
import request from "@/utils/request";

export default {
  data() {
        return {
          users: [],
          pageNum:1,
          pageSize:10,
          total:0,
          username:'',
          formVisible:false,
          form:{},
          user:JSON.parse(localStorage.getItem('user')||'{}')
        }
    },
  created(){
    this.load()
  },
  mounted(){
      this.load(1)
    },
  methods: {
    handleAdd(){
      this.form={}
      this.formVisible=true
    },
    save(){

    },
    load(pageNum){
      this.pageNum=pageNum
      this.$request.get('/user/selectByPage',{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
        }
      }).then(res => {
        this.users = res.data.records
        this.total = res.data.total
      })
    },
    handleCurrentChange(pageNum){
      this.load(pageNum)
    }

  }


}
</script>