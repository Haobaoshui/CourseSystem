<template>
  <div>
    <el-container>
      <el-aside class="aside" width="200px">
        <el-menu
          :default-active="activeIndex"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          @select="handleSelect"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
        >
          <el-submenu index="/home/logs/">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>学院组织管理</span>
            </template>

            <el-menu-item index="/home/organization/school">学院</el-menu-item>
            <el-menu-item index="/home/organization/department">系部</el-menu-item>
            <el-menu-item index="/home/organization/naturalclass">班级</el-menu-item>
          </el-submenu>

          <el-submenu index="/home/logs/statistics">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>课程管理</span>
            </template>

            <el-menu-item index="/home/logs/statistics">课程</el-menu-item>
            <el-menu-item index="/home/logs/statistics">课程类别</el-menu-item>
            <el-menu-item index="/home/logs/statistics">课程性质</el-menu-item>
            <el-menu-item index="/home/logs/statistics">授课类型</el-menu-item>
            <el-menu-item index="/home/logs/statistics">教学学期</el-menu-item>
          </el-submenu>

          <el-submenu index="/home/logs/statistics">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>考勤管理</span>
            </template>

            <el-menu-item index="/home/logs/statistics">考勤状况</el-menu-item>
            <el-menu-item index="/home/logs/statistics">签到方式</el-menu-item>
          </el-submenu>

          <el-submenu index="/home/logs/statistics">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>权限管理</span>
            </template>

            <el-menu-item index="/home/logs/statistics">组</el-menu-item>
            <el-menu-item index="/home/logs/statistics">用户-组</el-menu-item>
            <el-menu-item index="/home/logs/statistics">角色</el-menu-item>
            <el-menu-item index="/home/logs/statistics">角色-组</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>
<script>
export default {
  name: "OrganizationContainer",
  data() {
    return {
      activeIndex: "/home/permission/role"
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    handleSelect(key, keyPath) {
      //  console.log(key, keyPath);
      this.$router.push({ path: key });
      this.activeIndex = key;
    }
  }
};
</script>
<style scoped>
.aside {
  background-color: #545c64;
}
</style>