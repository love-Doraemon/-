<template>
<div>
    <el-form ref="form" >


    <el-form-item label="选择传感器">
    <el-select v-model="item_id" placeholder="请选择">
    <el-option
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.id"
      >
    </el-option>
   </el-select>
   </el-form-item>




  




   <el-form-item label="interval"><el-input v-model="interval" placeholder="时间间隔（如0）"></el-input></el-form-item>
  <el-form-item label="limit"><el-input v-model="limit" placeholder="返回记录个数（如5）"></el-input></el-form-item>
  <el-form-item label="agg"><el-input v-model="agg" placeholder="聚合方式（如COUNT）"></el-input></el-form-item>
  <el-form-item label="useStrictDataTypes"><el-input v-model="useStrictDataTypes" placeholder="如false"></el-input></el-form-item>
  <el-form-item label="keys"><el-input v-model="keys" placeholder="如（温度，湿度）（电流,有功总电能,电压,电网频率,瞬时功率）"></el-input></el-form-item>

  <el-form-item><el-button type="primary" @click="load('DEVICE',item_id)">发送请求</el-button></el-form-item>

</el-form>
 <el-card>
        {{ data_inform }}

 </el-card>


</div>

</template>

<script>
import axios from 'axios';

export default {
  name:"GetData",
  data(){
    return{options: [{
          value: '选项1',
          label: '人体红外传感器',
          id:'89faf2f0-58f5-11ed-b235-87ea6d533e56',
          keys:'红外',
        }, {
          value: '选项2',
          label: '烟雾传感器',
          id:'e59d79f0-5997-11ed-b235-87ea6d533e56',
          keys:'烟雾',
        }, {
          value: '选项3',
          label: '门磁传感器',
          id:'b04581c0-5a42-11ed-b235-87ea6d533e56',
          keys:'门磁',
        }, {
          value: '选项4',
          label: '温湿度传感器',
          id:'8dfe19c0-58f7-11ed-b235-87ea6d533e56',
          keys:'温度,湿度',
        }, {
          value: '选项5',
          label: '水浸传感器',
          id:'d0716170-bbf0-11ed-b217-b599993d3108',
          keys:'水浸',
        }, {
          value: '选项6',
          label: '智能水表',
          id:'12ae2af0-bbf1-11ed-b217-b599993d3108',
          keys:'电流,有功总电能,电压,电网频率,瞬时功率'
        }],
        id:'',
        data_inform:'',
        item_id:'',
        interval:'',
        limit:'',
        agg:'',
        useStrictDataTypes:'',
        keys:'',
      

      token1:'',
    }
  },
  async created(){
    await this.login();
    console.log('Token:', this.token1)
  },
  methods: {
    load(entityType,entityId){

      axios.get(`http://101.35.53.92:8080/api/plugins/telemetry/${entityType}/${entityId}/values/timeseries`,{
        headers: {
         'Authorization': 'Bearer ' + this.token1
       },  
      params: {
        interval:this.interval,
        limit:this.limit,
        agg:this.agg,
        useStrictDataTypes:this.useStrictDataTypes,
        keys:this.keys,
      }}).then(res=>{
        this.data_inform = res.data;
        console.log(res)
      }).catch(error => {
      console.error(error.response);
      // 在这里处理错误，例如显示一个错误消息
    });
    },
    


    async login() {
      try {
        const response = await axios.post('http://101.35.53.92:8080/api/auth/login', {
          username: 'tenant@thingsboard.org',
          password: 'tenant'
        });
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