<template>
    <div style="height:100vh;display:flex;align-items:center;justify-content:center;background-image: url('https://www.animegeek.com/wp-content/uploads/2022/02/chiikawa-anime-image.jpg');background-attachment: fixed;background-position: center center;background-repeat: no-repeat;background-size: cover;">
        <div style="display:flex; background-color:white;width:50%; border-radius: 5px; overflow:hidden;border:1px solid black">
            <div style="flex:1">
                <img src="@/assets/register.jpg" alt="" style="width:100%">
            </div>
            <div style="flex:1 ;display: flex;align-items: center;justify-content: center;">
                <el-form :model="user" style="width:80%" :rules="rules" ref="registerRef">
                    <div style="font-size:20px;font-weight:bold;text-align:center;margin-bottom:20px"> 欢迎注册</div>
                    <el-form-item prop="username">
                        <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="user.username"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input prefix-icon="el-icon-lock" show-password placeholder="请输入密码" v-model="user.password"></el-input>
                    </el-form-item>
                    <el-form-item prop="confirmPass">
                        <el-input prefix-icon="el-icon-lock" show-password placeholder="请确认密码" v-model="user.confirmPass"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" style="width:100%; background-color: #7B68EE;" @click="register">注册</el-button>
                    </el-form-item>

                    <div style="display:flex">
                        <div style="flex:1">以及有账号了?请<span style="color:#7B68EE;cursor: pointer;" @click="$router.push('/login')">登录</span></div>
                        <div style="flex:1;text-align:right;" ><span style="color:#7B68EE;cursor: pointer;;">忘记密码</span></div>
                    </div>
                </el-form>
            </div>

        </div>



</div>
</template>

<script>
export default{
    name:"Register",
    data(){
        const validatePassword=(rule,confirmPass,callback)=>{
            if(confirmPass===''){
                callback(new Error('请确认密码'))
            }else if(confirmPass!==this.user.password){
                callback(new Error('两次输入的密码不一致'))
            }else{
                callback()
            }
        }

        return{
            user:{
                username:'',
                password:'',
                confirmPass:''
            },
            rules:{
                username:[
                    {required:true,message:'请输入账号',trigger:'blur'}
                ],
                password:[
                    {required:true,message:'请输入密码',trigger:'blur'}
                ],
                confirmPass:[
                    {validator:validatePassword,trigger:'blur'}
                ],
            }
        }
    },
    created(){

    },
    methods:{
        register(){
            this.$refs['registerRef'].validate((valid)=>{
                if(valid){
                    this.$request.post('/register',this.user).then(res =>{
                    if(res.code==='200'){
                        this.$router.push('/login')
                        this.$message.success('注册成功')
                    }else{
                        this.$message.error(res.msg)
                    }
                    })
               }
            })
        }
    }
}


</script>