<template>
<div>












</div>
</template>














<script >
import axios from 'axios';

export default {
  name:"Postcom",
  data(){
    return{

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
      axios.post('http:///101.35.53.92:8080/api/plugins/rpc/oneway/2829b950-5b14-11ed-b235-87ea6d533e56',{
        headers: {
          'Authorization': 'Bearer ' + this.token1
        },
        params: {

        }}).then(res=>{

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


<style >

</style>