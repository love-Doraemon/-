<template>
  <div>
<el-form ref="form">
<!-- <el-form-item label="attrSubCmds"><el-input v-model="JSON.stringify(attrSubCmds)" placeholder="如[]"></el-input></el-form-item>
  <el-form-item label="tsSubCmds"><el-input v-model="JSON.stringify(tsSubCmds)" placeholder='如[{ "entityType": "DEVICE", "entityId": "b04581c0-5a42-11ed-b235-87ea6d533e56", "scope": "LATEST_TELEMETRY", "cmdId": 3 }]'></el-input></el-form-item>
  <el-form-item label="historyCmds"><el-input v-model="JSON.stringify(historyCmds)" placeholder="如[]"></el-input></el-form-item>
  <el-form-item label="entityDataCmds"><el-input v-model="JSON.stringify(entityDataCmds)" placeholder='如[{"cmdId":3,"historyCmd":{"keys":["温度"],"startTs":1684806180825,"endTs":1685410980825,"interval":1800000,"limit":336,"agg":"AVG"}}]'></el-input></el-form-item> -->

  <el-form-item><el-button type="primary" @click="load()">发送请求</el-button></el-form-item>

</el-form>



 <el-card>
        {{Wsdata}}
 </el-card>









</div>
</template>



<script>
import axios from 'axios';


export default {
  name:"WsData",
  data(){
    return{
        attrSubCmds: [],
        tsSubCmds:[{ "entityType": "DEVICE", "entityId": "b04581c0-5a42-11ed-b235-87ea6d533e56", "scope": "LATEST_TELEMETRY", "cmdId": 3 }],
        historyCmds:[],
        entityDataCmds:[{"cmdId":3,"historyCmd":{"keys":["温度"],"startTs":1684806180825,"endTs":1685410980825,"interval":1800000,"limit":336,"agg":"AVG"}}],
        token1:'',
        Wsdata:'',
        socket:null,
    }
  },



  async created(){
    await this.login();
    this.init()
    
    
  },



  methods: {
    init(){
      let socketUrl = 'ws://101.35.53.92:8080/api/ws/plugins/telemetry?token=' + this.token1;
      if(this.socket != null){
        this.socket.close();
        this.socket= null;
      }
      this.socket = new WebSocket(socketUrl);
      this.socket.open =function(){
        console.log('websocket已打开');
      };
      this.socket.onmessage = function(msg){
        console.log('websocket收到消息:',msg.data);
        this.Wsdata=JSON.parse(msg.data);
      };
      this.socket.onclose = function(){
        console.log('websocket已关闭');
      };
      this.socket.onerror = function(){
        console.log('websocket发生错误');
      };

    },
    

    load(){
      this.socket.onopen =function(){
        this.socket.send(JSON.stringify({
          tsSubCmds: this.tsSubCmds,
          attrSubCmds: this.attrSubCmds,
          historyCmds: this.historyCmds,
          entityDataCmds: this.entityDataCmds,
        }))
        
    }
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