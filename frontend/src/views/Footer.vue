<template>
  <div style="padding-top:20px;">
    <el-row justify="center">
      <el-col
        :xs="24"
        :sm="12"
        :md="6"
        :lg="6"
        :xl="6"
      >
        <div class="grid-content">
          <el-row>
            <el-col :span="24">
              <router-link to="faq">AAA</router-link>
            </el-col>
          </el-row>

        </div>
      </el-col>

      <el-col
        :xs="24"
        :sm="12"
        :md="6"
        :lg="6"
        :xl="6"
      >
        <div class="grid-content">
          <el-row>
            <el-col :span="24">
              AAA
            </el-col>
          </el-row>

        </div>
      </el-col>

      <el-col
        :xs="24"
        :sm="12"
        :md="6"
        :lg="6"
        :xl="6"
      >
        <div class="grid-content">
          <el-row>
            <el-col :span="24">
              AAA
            </el-col>
          </el-row>

        </div>
      </el-col>

      <el-col
        :xs="24"
        :sm="12"
        :md="6"
        :lg="6"
        :xl="6"
      >
        <div class="grid-content">
          <el-row>
            <el-col :span="24">
              AAA
            </el-col>
          </el-row>

        </div>
      </el-col>
    </el-row>
    <hr />
    <div style="padding:20px">
      <span style="”font-family:Arial;”">&copy;okdice.io </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "Footer"
};
</script>
<style scoped>
.el-row {
  margin: 20px;
}

hr {
  display: block;
  height: 1px;
  border: 0;
  border-top: 1px solid rgb(103, 103, 105);
  margin: 0px;
  padding: 0;
}

a {
  text-decoration: none;
}
a:link {
  color: white;
}
a:visited {
  color: white;
}
a:hover {
  color: silver;
}
a:active {
  color: yellow;
}
</style>
