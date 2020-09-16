<template>
  <div id="app">
    <el-container>
      <el-header>
        <courseHeader />
      </el-header>
      <el-main style="padding:0px;">
        <router-view></router-view>
      </el-main>
      <el-footer height="100%">
        <courseFooter />
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import courseHeader from './views/Header.vue'
import courseFooter from './views/Footer.vue'

export default {
  name: 'app',
  components: {
    courseHeader,
    courseFooter
  }
}
</script>

<style>
body {
  margin: 0;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: left;
  color: #2c3e50;
  margin-top: 0px;
}
.el-header {
  background-color: #545c64;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-footer {
  background-color: #545c64;
  color: white;
  text-align: center;
}
</style>
