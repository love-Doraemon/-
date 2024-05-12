<template>
    <div>
      <el-table :data="alarm_inform" stripe>
      <el-table-column prop="id.entityType" label="id类型"></el-table-column>
      <el-table-column prop="id.id" label="id"></el-table-column>
      <el-table-column prop="createdTime" label="创造时间"></el-table-column>
      <el-table-column prop="tenantId.entityType" label="客户类型"></el-table-column>
      <el-table-column prop="tenantId.id" label="客户id"></el-table-column>
      <el-table-column prop="customerId.entityType" label="顾客类型"></el-table-column>
      <el-table-column prop="customerId.id" label="顾客id"></el-table-column>
      <el-table-column prop="type" label="类型"></el-table-column> 
      <el-table-column prop="originator.entityType" label="发起人"></el-table-column>
      <el-table-column prop="originator.id" label="发起人id"></el-table-column>
      <el-table-column prop="severity" label="severity"></el-table-column>
      <el-table-column prop="status" label="status"></el-table-column>
      <el-table-column prop="startTs" label="开始时间"></el-table-column>
      <el-table-column prop="endTs" label="结束时间"></el-table-column>
      <el-table-column prop="ackTs" label="攻击时间"></el-table-column>
      <el-table-column prop="clearTs" label="清除时间"></el-table-column>
      <el-table-column prop="details" label="详情"></el-table-column>
      <el-table-column prop="propagate" label="propagate"></el-table-column>
      <el-table-column prop="propagateToOwner" label="propagateToOwner"></el-table-column>
      <el-table-column prop="propagateToTenant" label="propagateToTenant"></el-table-column>
      <el-table-column prop="propagateRelationTypes" label="ropagateRelationTypes"></el-table-column>
      <el-table-column prop="originatorName" label="originatorName"></el-table-column>
      <el-table-column prop="name" label="name"></el-table-column>

      
      </el-table>



      <div >

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 10, 20, 30]"
      :page-size="currentPage"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
        </div>
    </div>
</template>


<script>
import axios from 'axios';

export default {
  name:"AlarmView",
  data(){
    return{
      alarm_inform:[],
      currentPage:0,
      pageSize:10,
      total:0,
      token1:'',
    }
  },
  async created(){
    await this.login();
    console.log('Token:', this.token1)
    this.load();
  },
  methods: {
    load(){
      axios.get('http://101.35.53.92:8080/api/alarm/DEVICE/8dfe19c0-58f7-11ed-b235-87ea6d533e56',{
        headers: {
         'Authorization': 'Bearer ' + this.token1
       },  
      params: {
        page:this.currentPage,
        pageSize:this.pageSize,
        // sortProperty:
        // sortOrder:
        // startTime:
        // endTime:
        // searchStatus:
        // fetchOriginator:
      }}).then(res=>{
        this.alarm_inform = res.data.data;
        this.total=res.data.totalPages;
        console.log(res)
      }).catch(error => {
      console.error(error.response);
      // 在这里处理错误，例如显示一个错误消息
    });
    },
    handleSizeChange(val) {
      this.pageSize = val  ;
      this.load();
      },
    handleCurrentChange(val) {
      this.currentPage = val  ;
      this.load();
    },


    async login() {
      try {
        const response = await axios.post('http://101.35.53.92:8080/api/auth/login', {
          username: 'tenant@thingsboard.org',
          password: 'tenant'
        });
        // this.token1 = response.data.refreshToken?response.data.refreshToken:response.data.token;
        this.token1 = response.data.token
        // 保存 token1 到 localStorage
        localStorage.setItem('token1', this.token1);
      } catch (error) {
        console.error('登录失败:', error);
      }
    },
  }
}


</script>