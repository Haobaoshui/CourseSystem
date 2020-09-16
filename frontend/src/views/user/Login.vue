<template>
  <div class="loginbg">

    <div class="deck">
      <h1>登录</h1>
      <el-form
        label-position="right"
        label-width="80px"
        :model="fromLogin"
        :rules="rules"
        ref="fromLogin"
      >
        <el-form-item
          label="账号"
          prop="userid"
          required
        >
          <el-input v-model="fromLogin.userid"></el-input>
        </el-form-item>
        <el-form-item
          label="密码"
          required
          prop="password"
        >
          <el-input
            v-model="fromLogin.password"
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="submitForm('fromLogin')"
          >登录</el-button>
          <el-button @click="resetForm('fromLogin')">重置</el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>
<script>
import BASEURL from "@/httpconfig/api.js";
export default {
  name: "Login",
  data() {
    return {
      fromLogin: {
        userid: "19991363",
        password: "hbs03182034883"
      },
      rules: {
        userid: [
          { required: true, message: "请输入账号", trigger: "blur" },
          { min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "change" }]
      }
    };
  },
  methods: {
    submitForm(formName) {
      var self = this;
      this.$refs[formName].validate(valid => {
        if (valid) {
          self.doLogin();
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    doLogin() {
      var self = this;
      var user = {
        userName: this.fromLogin.userid,
        userPassword: this.fromLogin.password
      };
      this.$http
        .post(BASEURL.account + "login", user)
        .then(response => {
          if (self.isNull(response.data)) {
            //
          } else {
            this.$router.push({ path: "/courses/" });
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  }
};
</script>
<style scoped>
.loginbg {
  width: 100%;
  height: calc(100vh - 250px);
  background: linear-gradient(160deg, #a6a8a8 0%, #4c494e 100%);
  display: flex;
  justify-content: center;
  align-items: center;
}
.deck {
  margin: auto;
  padding: 10px;
  width: 400px;
  min-height: 200px;
  background: white;
  border-radius: 10px;
  box-shadow: 12px 15px 20px 0 rgba(46, 61, 73, 0.5);
}
</style>