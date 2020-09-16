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
          <el-submenu index="/home/logs/" >
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>审计</span>
            </template>

            <el-menu-item index="/home/logs/">日志</el-menu-item>
          </el-submenu>

          <el-submenu index="/home/logs/statistics">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>统计</span>
            </template>

            <el-menu-item index="/home/logs/statistics">饼形图</el-menu-item>
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
  name: "LogsContainer",
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