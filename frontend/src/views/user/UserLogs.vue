<template>
  <div>
    <div class="user-manager-toolbar">
      <el-form
       
        :inline="true"
        :model="formSearchUser"
        class="demo-form-inline"
      >
        <el-form-item>
          <el-date-picker
            v-model="dateRange"
            type="datetimerange"
            align="right"
            :picker-options="pickerOptions"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="['0:00:00', '23:59:59']"
          ></el-date-picker>
        </el-form-item>
      </el-form>
    </div>
    <div v-if="totalCount == 0">
      <h1>没有数据</h1>
    </div>
    <div v-else >
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNo"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount"
        :hide-on-single-page="true"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import BASEURL from "@/httpconfig/api.js";
export default {
  name: "UserRoleUsersManager",
  components: {},
  computed: {
    //用户总数
    totalCount: function() {
      if (this.isNull(this.logViewDataPage)) return 0;
      return this.logViewDataPage.totalCount;
    }
  },
  data() {
    return {
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              end.setHours(23);
              end.setMinutes(59);
              end.setSeconds(59);
              const start = new Date();
              start.setHours(24);
              start.setMinutes(0);
              start.setSeconds(0);
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              end.setHours(23);
              end.setMinutes(59);
              end.setSeconds(59);
              const start = new Date();
              start.setHours(24);
              start.setMinutes(0);
              start.setSeconds(0);
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              end.setHours(23);
              end.setMinutes(59);
              end.setSeconds(59);
              const start = new Date();
              start.setHours(24);
              start.setMinutes(0);
              start.setSeconds(0);
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      },
      formSearchUser: {
        emplNum: ""
      },
      dateRange: "",
      logViewDataPage: null,
      pageSize: 10,
      pageNo: 1
    };
  },
  mounted() {
    this.getLogViewDataPageDataFromServer();
  },
  methods: {
    /**
     * 每页显示数目发生改变
     */
    handleSizeChange(size) {
      this.pageSize = size;
      this.getLogViewDataPageDataFromServer();
    },
    /**
     * 当前页码发生改变
     */
    handleCurrentChange(page) {
      this.pageNo = page;
      this.getLogViewDataPageDataFromServer();
    },

    /**
     * 从服务器获得用户数据
     */
    getLogViewDataPageDataFromServer() {
      var self = this;

      this.$http
        .get(BASEURL.log + "userpage", {
          params: {
            userId: self.getUserId(),
            pageNo: self.pageNo,
            pageSize: self.pageSize
          }
        })
        .then(response => {
          if (self.isNull(response.data)) {
            //
          } else {
            self.logViewDataPage = response.data;
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
.user-manager-toolbar {
  padding: 10px;
}
</style>
