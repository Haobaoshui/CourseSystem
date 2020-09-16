<template>
  <div>
    <el-row>
      <el-col :span="4" style="text-align:right;padding-right:10px;">用户名称</el-col>
      <el-col :span="20">{{getUserEmplName()}}</el-col>
    </el-row>
    <el-row>
      <el-col :span="4" style="text-align:right;padding-right:10px;">工号</el-col>
      <el-col :span="20">{{getUserEmplNum()}}</el-col>
    </el-row>

    <el-row>
      <el-col :span="4" style="text-align:right;padding-right:10px;">密码</el-col>
      <el-col :span="20">
        <el-button plain size="small" @click="onResetPwd">修改密码</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="4" style="text-align:right;padding-right:10px;">角色</el-col>
      <el-col :span="20">
        <el-tag
          v-for="item in roleList"
          :key="item.id"
          type="info"
          effect="dark"
          style="margin-left: 10px"
        >{{ item.name }}</el-tag>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import BASEURL from "@/httpconfig/api.js";
export default {
  name: "Profile",
  components: {},
  computed: {},
  data() {
    return {
      roleList: []
    };
  },
  mounted() {
    this.getUserRoleDataFromServer();
  },
  methods: {
    getUserRoleDataFromServer() {
      var self = this;

      var t_user_id = this.getUserId();
      if (this.isNull(t_user_id)) return;

      this.$http
        .get(BASEURL.userrole + "roles", {
          params: {
            userId: t_user_id
          }
        })
        .then(response => {
          if (self.isNull(response.data)) {
            //
          } else {
            self.roleList = response.data;
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
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
</style>