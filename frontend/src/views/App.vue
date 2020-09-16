<template>
  <div id="app">
    <el-container>
      <el-header>
        <okDiceHeader />
      </el-header>
      <el-main style="padding:0px;">
        <router-view></router-view>
      </el-main>
      <el-footer height="100%">
        <okDiceFooter />
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import OkDiceHeader from '@/views/Header.vue'
import OkDiceFooter from '@/views/Footer.vue'

var unlockIntervalId
var jackpotIntervalId
export default {
  name: 'app',
  components: {
    okDiceHeader: OkDiceHeader,
    okDiceFooter: OkDiceFooter
  },
  created() {},

  mounted() {
    document.body.removeChild(document.getElementById('app-loading'))

    this.setDefaultDocumentTitleAndMeta()

    this.setCheckMetaMask()
    this.updateshowflags()

    this.setGameJackpotSize()
  },
  beforeDestroy() {
    clearInterval(unlockIntervalId)
    clearInterval(jackpotIntervalId)
  },
  methods: {
    setCheckMetaMask() {
      this.SecurityMetaMask()

      //每隔一秒检查MetaMask是否解锁
      clearInterval(unlockIntervalId)
      // this.getAccount();
      unlockIntervalId = setInterval(() => {
        this.getAccount()
      }, 500)
      //  unlockIntervalId = setInterval(this.updateshowflags, 2000);
    },
    setGameJackpotSize() {
      //每隔2秒检查大奖
      clearInterval(jackpotIntervalId)
      // this.getAccount();
      jackpotIntervalId = setInterval(() => {
        this.setJackpotSize()
      }, 1000)
    }
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
