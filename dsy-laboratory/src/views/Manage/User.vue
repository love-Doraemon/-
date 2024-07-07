<template>
  <div>

    <div >
      <el-input style="width:200px" placeholder="查询用户名" v-model="username"></el-input>
      <el-button @click="load(1)">查询</el-button>
    </div>
    <div style ="margin-bottom: 10px">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <!--        <el-button type="danger" >批量删除</el-button>-->
    </div>
    <el-card >
      {{this.users}}
      </el-card >

    <el-card >
      <el-table :data="users">
        <el-table-column label="id" prop="id"></el-table-column>
        <el-table-column label="用户名" prop="username"></el-table-column>
        <el-table-column label="密码" prop="password"></el-table-column>
        <el-table-column label="是否管理员" prop="isadministrator" :formatter="(row, column, cellValue) => cellValue == 1 ? '是' : '否'"></el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
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
        <el-form :model="form" label-width="80px" style="padding-right:20px" :rules="rules" ref="formRef">
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
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" placeholder="用户名" ></el-input>
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



export default {
  data() {
    return {
      users: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      username: '',
      formVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.load(1)
  },
  mounted() {
    this.load(1)
  },
  methods: {
    del(id){
      this.$confirm('您确认删除吗','确认删除',{type:"warning"}).then(response=>{
        this.$request.delete('/user/delete/'+id).then(res=>{
          if(res.code==='200'){
            this.$message.success('删除成功')
            this.load(1)
          }else{
            this.$message.error(res.msg)
          }
        })
      }).catch(()=>{})
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.formVisible = true
    },
    handleAdd() {
      this.form = {}
      this.formVisible = true
    },
    save() {
      this.$refs.formRef.validate(valid => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/user/update' : '/user/add',
            method: this.form.id ? 'put' : 'post',
            data: this.form
          }).then(res => {
            if (res.code === '200') {
              this.$message.success('保存成功')
              this.formVisible = false
              this.load(1)
            } else {
              this.$message.error('res.msg')
            }
          })
        }
      })
    },
    load(pageNum) {
      this.pageNum = pageNum
      this.$request.get('/user/selectByPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
        }
      }).then(res => {
        this.users = res.data.records
        this.total = res.data.total
      })
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum)
    },

  }


}
</script>