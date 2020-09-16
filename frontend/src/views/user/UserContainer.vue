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
          <el-menu-item index="/home/user/profile">
            <i class="el-icon-setting"></i>
            <span slot="title">用户信息</span>
          </el-menu-item>

          <el-menu-item index="/home/user/changepwd">
            <i class="el-icon-setting"></i>
            <span slot="title">修改密码</span>
          </el-menu-item>

          <el-menu-item index="/home/user/logs">
            <i class="el-icon-setting"></i>
            <span slot="title">用户日志</span>
          </el-menu-item>
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
  name: "AuthorityContainer",
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