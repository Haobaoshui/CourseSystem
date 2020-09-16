<template>
  <div >
    <div class="user-manager-toolbar">
      <el-form :inline="true" :model="formSearch" class="demo-form-inline">
        <el-form-item label="查询">
          <el-input
            placeholder="请输入工号或姓名"
            v-model="formSearch.searchText"
            class="input-with-select"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-date-picker
            v-model="formSearch.dateRange"
            type="datetimerange"
            align="right"
            :picker-options="pickerOptions"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="['0:00:00', '23:59:59']"
          ></el-date-picker>
        </el-form-item>

        <el-button icon="el-icon-search" @click="onSearch">查询</el-button>
      </el-form>
    </div>

    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div v-show="chartVisible" id="chart" style="width: 800px;height:600px;"></div>
    <div v-show="!chartVisible">没有数据,请重新输入查询条件</div>
  </div>
</template>

<script>
import BASEURL from "@/httpconfig/api.js";

// 引入 ECharts 主模块
var echarts = require("echarts/lib/echarts");
// 引入饼状图
require("echarts/lib/chart/pie");
// 引入提示框和标题组件
require("echarts/lib/component/tooltip");
require("echarts/lib/component/title");
require("echarts/lib/component/legend");

export default {
  name: "LogsStatistics",
  components: {},
  computed: {
    //用户总数
    chartVisible: function() {
      if (this.isNull(this.statisticsDataList)) return false;
      return true;
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
      formSearch: {
        searchText: "",
        dateRange: ""
      },

      selectedUser: null,

      statisticsDataList: []
    };
  },
  mounted() {
    this.getStatisticsDataFromServer();
  },
  methods: {
    onSearch() {
      var self = this;
      if (
        this.isNull(this.formSearch.searchText) &&
        this.isNull(this.formSearch.dateRange)
      ) {
        self.$alert("请输入要查询的条件", "信息提示", {
          confirmButtonText: "确定"
        });
        return;
      }

      this.getStatisticsDataFromServer();
    },
    onDrawCharts() {
      if (this.isNull(this.statisticsDataList)) return;
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById("chart"));

      // 指定图表的配置项和数据
      var data = this.genData();

      var option = {
        title: {
          text: "日志统计",
          subtext: "用户操作统计",
          left: "center"
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          orient: "vertical",
          left: 10,
          data: data.legendData
        },
        emphasis: {
          label: {
            show: true,
            fontSize: "30",
            fontWeight: "bold"
          }
        },
        series: [
          {
            name: "日志统计",
            type: "pie",
            radius: "55%",
            center: ["50%", "50%"],
            data: data.seriesData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)"
              }
            }
          }
        ]
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },

    genData() {
      var legendData = [];
      var seriesData = [];

      this.statisticsDataList.forEach(item => {
        legendData.push(item.typeName);
        seriesData.push({
          value: item.value,
          name: item.typeName
        });
      });

      return {
        legendData: legendData,
        seriesData: seriesData
      };
    },

    /**
     * 从服务器获得统计数据
     */
    getStatisticsDataFromServer() {
      var self = this;
      var search_text = null;
      var date_begin = null;
      var date_end = null;

      if (!this.isNull(this.formSearch.searchText)) {
        search_text = this.formSearch.searchText;
      }
      if (!this.isNull(this.formSearch.dateRange)) {
        date_begin = this.formSearch.dateRange[0];
        date_end = this.formSearch.dateRange[1];
      }
      self.statisticsDataList = [];

      this.$http
        .get(BASEURL.log + "statistics", {
          params: {
            searchText: search_text,
            dateBegin: date_begin,
            dateEnd: date_end
          }
        })
        .then(response => {
          if (self.isNull(response.data)) {
            //
          } else {
            self.statisticsDataList = response.data;
            self.onDrawCharts();
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
