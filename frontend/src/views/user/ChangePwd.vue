<template>
  <div>
    <h1>修改密码</h1>
    <el-form
      label-position="right"
      label-width="80px"
      :model="form"
      :rules="rules"
      ref="form"
      style="width:300px;"
    >
      <el-form-item label="原始密码" required prop="oldpassword">
        <el-input v-model="form.oldpassword" show-password></el-input>
      </el-form-item>
      <el-form-item label="新密码" required prop="newpassword">
        <el-input v-model="form.newpassword" show-password></el-input>
      </el-form-item>
      <el-form-item label="确认密码" required prop="confirmpassword">
        <el-input v-model="form.confirmpassword" show-password></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('form')">修改</el-button>
        <el-button @click="resetForm('form')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import BASEURL from "@/httpconfig/api.js";
export default {
  name: "ChangePwd",
  components: {},
  data() {
    return {
      form: {
        oldpassword: "",
        newpassword: "",
        confirmpassword: ""
      },
      rules: {
        oldpassword: [
          { required: true, message: "请输入旧密码", trigger: "change" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur"
          }
        ],
        newpassword: [
          { required: true, message: "请输入新密码", trigger: "change" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur"
          }
        ],
        confirmpassword: [
          { required: true, message: "请输入确认密码", trigger: "change" },
          {
            min: 3,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur"
          }
        ]
      }
    };
  },
  mounted() {},
  methods: {
    submitForm(formName) {
      var self = this;
      this.$refs[formName].validate(valid => {
        if (valid) {
          self.changePwd();
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    changePwd() {
      if (this.form.newpassword !== this.form.confirmpassword) {
        this.$alert("确认", "错误", {
          confirmButtonText: "确定"
        });
        return;
      }
      this.verifyOldpwd();
    },
    /**
     * 验证用户旧密码是否正确
     */
    verifyOldpwd() {
      var self = this;

      this.$http
        .get(BASEURL.user + "isusercorrect", {
          params: {
            userId: self.getUserId(),
            password: self.form.oldpassword
          }
        })
        .then(response => {
          if (self.isNull(response.data)) {
            self.$alert("抱歉，用户旧密码输入错误", "请重新输入", {
              confirmButtonText: "确定"
            });
            self.resetForm("form");
          } else {
            self.changePwdAtServer();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    /**
     * 修改服务器中用户密码
     */
    changePwdAtServer() {
      var self = this;
      var user = {
        id: self.getUserId(),
        emplPwd: this.form.newpassword
      };

      this.$http
        .put(BASEURL.user + "changepwd", user)
        .then(response => {
          if (self.isNull(response.data)) {
          } else {
            self.resetForm("form");
            self.$message({
              message: "恭喜，修改密码成功",
              type: "success"
            });
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
</style>