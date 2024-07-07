<template>
  <div>
    <el-card >
      <el-table :data="informs">
        <el-table-column label="设备名" prop="device"></el-table-column>
        <el-table-column label="测点名" prop="devicepoint"></el-table-column>
        <el-table-column label="值" prop="value"></el-table-column>
        <el-table-column label="时间" prop="time" :formatter="timeFormatter"></el-table-column>
      </el-table>


      <div>
      <el-button @click="load1">1</el-button>
        <el-button @click="load2">2</el-button>
        <el-button @click="load3">3</el-button>
        <el-button @click="load4">4</el-button>
        <el-button @click="load5">5</el-button>
        <el-button @click="load6">6</el-button>
        <el-button @click="load">6</el-button>
      </div>
    </el-card>
    <el-card >{{inform1}} </el-card >
    <el-card >{{inform2}} </el-card >
    <el-card >{{inform3}} </el-card >
    <el-card >{{inform4}} </el-card >
    <el-card >{{inform5}} </el-card >
    <el-card >{{inform6}} </el-card >


  </div>
</template>

<script>

import axios from "axios";

export default {
  data() {
    return {
      inform1: '',
      inform2: '',
      inform3: '',
      inform4: '',
      inform5: '',
      inform6: '',
      informs: [
        {"device":"人体红外传感器","devicepoint":"红外","value":"0","time":"1715221740188"},
        {"device":"烟雾传感器","devicepoint":"烟雾","value":"0","time":"1713846173460"},
        {"device":"门磁传感器","devicepoint":"门磁","value":"256","time":"1713846174061"},
        {"device":"温湿度传感器","devicepoint":"温度","value":"23.7","time":"1715301919052"},
        {"device":"温湿度传感器","devicepoint":"湿度","value":"61.3","time":"1715301901425"},
        {"device":"水浸传感器","devicepoint":"水浸","value":"256","time":"1713846173660"},
        {"device":"智能水表","devicepoint":"电流","value":"0","time":"1713846174061"},
        {"device":"智能水表","devicepoint":"有功总电能","value":"3.63","time":"1713846174061"},
        {"device":"智能水表","devicepoint":"电压","value":"225.9","time":"1715301943289"},
        {"device":"智能水表","devicepoint":"电网频率","value":"50.02","time":"1715301943289"},
        {"device":"智能水表","devicepoint":"瞬时功率","value":"0","time":"1713846174061"}

      ],
      token1: '',

    }
  },
  async created() {
    await this.login();
    console.log('Token:', this.token1)
  },

  methods: {
    transformData(inform, deviceName) {
      const result = [];
      for (const key in inform) {
        if (inform.hasOwnProperty(key)) {
          const data = inform[key];
          for (const item of data) {
            result.push({
              device: deviceName,
              devicepoint: key,
              value: item.value,
              time: new Date(item.ts).toLocaleString()
            });
          }
        }
      }
      return result;
    },

    load1(){
      axios.get(`http://101.35.53.92:8080/api/plugins/telemetry/DEVICE/89faf2f0-58f5-11ed-b235-87ea6d533e56/values/timeseries`,{
        headers: {
          'Authorization': 'Bearer ' + this.token1
        },
        params: {
          interval:0,
          limit:1000,
          agg:'COUNT',
          useStrictDataTypes:false,
          keys:"红外",
        }}).then(res=>{
        this.inform1 = res.data;
      }).catch(error => {
        console.error(error.response);
      });
    },
    load2(){
      axios.get(`http://101.35.53.92:8080/api/plugins/telemetry/DEVICE/e59d79f0-5997-11ed-b235-87ea6d533e56/values/timeseries`,{
        headers: {
          'Authorization': 'Bearer ' + this.token1
        },
        params: {
          interval:0,
          limit:1000,
          agg:'COUNT',
          useStrictDataTypes:false,
          keys:"烟雾",
        }}).then(res=>{
        this.inform2 = res.data;
      }).catch(error => {
        console.error(error.response);
      });
    },
    load3(){
      axios.get(`http://101.35.53.92:8080/api/plugins/telemetry/DEVICE/b04581c0-5a42-11ed-b235-87ea6d533e56/values/timeseries`,{
        headers: {
          'Authorization': 'Bearer ' + this.token1
        },
        params: {
          interval:0,
          limit:1000,
          agg:'COUNT',
          useStrictDataTypes:false,
          keys:"门磁",
        }}).then(res=>{
        this.inform3 = res.data;
      }).catch(error => {
        console.error(error.response);
      });
    },

    load4(){
      axios.get(`http://101.35.53.92:8080/api/plugins/telemetry/DEVICE/8dfe19c0-58f7-11ed-b235-87ea6d533e56/values/timeseries`,{
        headers: {
          'Authorization': 'Bearer ' + this.token1
        },
        params: {
          interval:0,
          limit:1000,
          agg:'COUNT',
          useStrictDataTypes:false,
          keys:"温度,湿度",
        }}).then(res=>{
        this.inform4 = res.data;
      }).catch(error => {
        console.error(error.response);
      });
    },
    load5(){
      axios.get(`http://101.35.53.92:8080/api/plugins/telemetry/DEVICE/d0716170-bbf0-11ed-b217-b599993d3108/values/timeseries`,{
        headers: {
          'Authorization': 'Bearer ' + this.token1
        },
        params: {
          interval:0,
          limit:1000,
          agg:'COUNT',
          useStrictDataTypes:false,
          keys:"水浸",
        }}).then(res=>{
        this.inform5 = res.data;
      }).catch(error => {
        console.error(error.response);
      });
    },
    load6(){
      axios.get(`http://101.35.53.92:8080/api/plugins/telemetry/DEVICE/12ae2af0-bbf1-11ed-b217-b599993d3108/values/timeseries`,{
        headers: {
          'Authorization': 'Bearer ' + this.token1
        },
        params: {
          interval:0,
          limit:1000,
          agg:'COUNT',
          useStrictDataTypes:false,
          keys:"电流,有功总电能,电压,电网频率,瞬时功率",
        }}).then(res=>{
        this.inform6 = res.data;
      }).catch(error => {
        console.error(error.response);
      });
    },

    load() {
    this.load1()
      this.load2();
      this.load3();
      this.load4();
      this.load5();
      this.load6();
      this.transformData();



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

    timeFormatter(row, column, cellValue) {
      // cellValue 是当前单元格的值
      // 使用 new Date() 将时间戳转换为 Date 对象，然后使用 toLocaleString() 将其转换为字符串
      return new Date(Number(cellValue)).toLocaleString();
    },





  }


}
</script>