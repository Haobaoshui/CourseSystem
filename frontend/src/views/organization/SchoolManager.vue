<template>
  <div>
    <div class="user-manager-toolbar">
      <el-form :inline="true" :model="formSearch" class="demo-form-inline">
        <el-form-item label="查询">
          <el-input placeholder="请输入查询内容" v-model="formSearch.searchText" class="input-with-select">
            <el-button slot="append" icon="el-icon-search" @click="onSearch"></el-button>
          </el-input>
        </el-form-item>

        <el-form-item style="float: right;">
          <el-button type="primary" plain @click="onOpenDialogAddUser()">增加</el-button>
          <el-button type="danger" plain @click="onDeleteInBatches()">批量删除</el-button>
          <el-button icon="el-icon-refresh" @click="onRefresh()">刷新</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div v-if="totalCount==0">
      <h1>没有数据</h1>
    </div>
    <div v-else>
      <SchoolList
        :dataViewDataPage="schoolViewDataPage"
        @onUpdate="onUpdate"
        @onDelete="onDelete"
        @onDilalogOk="onDilalogOk"
        @onSelectionChange="onSelectionChange"
      ></SchoolList>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNo"
        :page-sizes="[10, 20, 30, 40,50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount"
        :hide-on-single-page="true"
      ></el-pagination>
    </div>

    <AddDialog
      :dialogVisible="addDialogVisible"
      @onDilalogOk="onAddDilalogOk"
      @onDilalogCancel="onAddDilalogCancel"
    ></AddDialog>
  </div>
</template>

<script>
import SchoolList from "@/components/organization/school/SchoolList";
import AddDialog from "@/components/organization/school/AddDialog.vue";
import BASEURL from "@/httpconfig/api.js";
export default {
  name: "SchoolManager",
  components: { SchoolList, AddDialog },
  computed: {
    //用户总数
    totalCount: function() {
      if (this.isNull(this.schoolViewDataPage)) return 0;
      return this.schoolViewDataPage.totalCount;
    }
  },
  data() {
    return {
      formSearch: {
        searchText: ""
      },
      addDialogVisible: false, //增加对话框是否可见
      schoolViewDataPage: null,
      dataSelections: [],
      pageSize: 10,
      pageNo: 1,
      isSearchStatus: false
    };
  },
  mounted() {
    this.onRefreshDataFromServer();
  },
  methods: {
    /**
     * 刷新数据，重新从服务器获得数据
     */
    onRefreshDataFromServer() {
      if (this.isSearchStatus)
        this.getSearchedSchoolPageFromServer(
          this.formSearch.searchText,
          this.pageNo,
          this.pageSize
        );
      else this.getSchoolPageFromServer(this.pageNo, this.pageSize);
    },
    /**
     * 每页显示数目发生改变
     */
    handleSizeChange(size) {
      this.pageSize = size;
      this.onRefreshDataFromServer();
    },
    /**
     * 当前页码发生改变
     */
    handleCurrentChange(page) {
      this.pageNo = page;
      this.onRefreshDataFromServer();
    },
    /**
     * 更新数据
     */
    onUpdate() {
      this.onRefreshDataFromServer();
    },

    /**
     * 用户选择事件
     */
    onSelectionChange(val) {
      this.dataSelections = val;
    },

    /**
    显示增加对话框
    */
    onOpenDialogAddUser() {
      this.addDialogVisible = true;
    },
    /**
    增加对话框“确定”按钮
     */
    onAddDilalogOk() {
      this.onRefreshDataFromServer();
      this.addDialogVisible = false;
    },
    /**
    增加对话框“取消”按钮
     */
    onAddDilalogCancel() {
      this.addDialogVisible = false;
    },

    /**
     * 查询按钮
     */
    onSearch() {
      var self = this;
      if (this.isNull(this.formSearch.searchText)) {
        self.$alert("请输入要查询的名称", "信息提示", {
          confirmButtonText: "确定"
        });
        return;
      }
      this.pageNo = 1;
      this.isSearchStatus = true;
      this.onRefreshDataFromServer();
    },

    /**
     * 批量删除数据
     */
    onDeleteInBatches() {
      this.isSearchStatus = false;
      var self = this;
      if (this.isNull(this.dataSelections)) {
        self.$alert("请先选择要删除的对象，然后再进行删除", "信息提示", {
          confirmButtonText: "确定"
        });
        return;
      }

      this.$confirm("是否要删除选中的对象？", "确认信息", {
        distinguishCancelAndClose: true,
        confirmButtonText: "确认删除",
        cancelButtonText: "取消"
      })
        .then(() => {
          self.dataSelections.forEach(data => {
            self.deleteSchoolFromServer(data.id);
          });
        })
        .catch(action => {});
    },
    /**
    删除单个数据
    */
    onDelete(school) {
      this.isSearchStatus = false;
      if (this.isNull(school) || this.isNull(school.id)) return;
      var self = this;

      this.$confirm("是否要删除该学院？", "确认信息", {
        distinguishCancelAndClose: true,
        confirmButtonText: "确认删除",
        cancelButtonText: "取消"
      })
        .then(() => {
          self.deleteSchoolFromServer(school.id);
        })
        .catch(action => {});
    }
  }
};
</script>

<style scoped>
.user-manager-toolbar {
  padding: 10px;
}
</style>
