<template>
  <div>
    <el-row>
      <el-col :xs="4" :sm="6" :md="4" :lg="4" :xl="4">
        <div class="hidden-sm-and-down">
          <!--当视口在 md 及以上尺寸时显示-->

          <router-link to="/">
            <img class="logo" :src="logoImage" />
          </router-link>
        </div>
        <div class="hidden-md-and-up">
          <!--当视口在 sm 及以下尺寸时显示-->

          <div class="hidden-sm-only">
            <!--当视口在 xs尺寸时显示-->
            <router-link to="/" class="hidden-md-and-up">
              <img class="logo" :src="logoImageSmall" />
            </router-link>
          </div>

          <div class="hidden-xs-only">
            <!--当视口在 sm尺寸时显示-->
            <router-link to="/">
              <img class="logo" :src="logoImage" />
            </router-link>
          </div>
        </div>
      </el-col>
      <el-col :xs="20" :sm="18" :md="20" :lg="20" :xl="20">
        <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          @select="handleSelect"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
        >
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/home/organization">系统设置</el-menu-item>
          <el-menu-item index="/help">帮助</el-menu-item>

          <el-menu-item style="float: right" v-if="!isSignin()" index="/login">
            <span>登录</span>
          </el-menu-item>

          <el-submenu v-else style="float: right;" index="/owner">
            <template slot="title">
              <img :src="smallAvatarImg" class="smallavatar" />
              <span class="kingname">{{username}}</span>
            </template>
            <el-menu-item index="/usertransactions">用户</el-menu-item>
            <el-menu-item index="/profile">用户信息</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import LogoImage from "@/assets/images/logo.svg";
import LogoImageSmall from "@/assets/images/logo.svg";
import "element-ui/lib/theme-chalk/display.css";

export default {
  name: "Header",

  computed: {
    username: function() {
      return this.getUsername();
    },

    smallAvatarImg: function() {
      return this.getUserAvatarImg();
    }
  },
  data() {
    return {
      activeIndex: "/",
      logoImage: LogoImage,
      logoImageSmall: LogoImageSmall
    };
  },
  mounted() {},
  methods: {
    handleSelect(key, keyPath) {
      //  console.log(key, keyPath);
      this.$router.push({ path: key });
      this.activeIndex = key;
    }
  }
};
</script>
<style scoped>
.kingname {
  padding-right: 10px;
  font-size: 16px;
  color: white;
}
.logo {
  margin: auto;
  padding-top: 5px;

  height: 50px;

  display: inline-block;

  text-align: left;
}

.smallavatar {
  width: 30px;
  height: 30px;
  display: inline-block;
  border: 1px #1d1a1a;
  border-radius: 5px;
  text-align: center;
}
.flagimg {
  margin: auto;

  text-align: center;
  vertical-align: middle;
}

.flag-icon {
  position: relative;
  display: inline-block;
  width: 2em;
  height: 1.5em;
  line-height: 1em;
}
</style>
