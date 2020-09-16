<template>
  <div>
    <div class="user-manager-toolbar">
      <el-form :inline="true" :model="formSearch" class="demo-form-inline">
        <el-select v-model="value" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>

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
        :dataViewDataPage="dataViewDataPage"
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
  name: "DepartmentManager",
  components: { SchoolList, AddDialog },
  computed: {
    //用户总数
    totalCount: function() {
      if (this.isNull(this.dataViewDataPage)) return 0;
      return this.dataViewDataPage.totalCount;
    }
  },
  data() {
    return {
      formSearch: {
        searchText: ""
      },
      addDialogVisible: false, //增加对话框是否可见
      dataViewDataPage: null,
      dataSelections: [],
      pageSize: 10,
      pageNo: 1,
      isSearchStatus: false
    };
  },
  mounted() {
    this.getDataFromServer();
  },
  methods: {
    onRefresh() {
      if (this.isSearchStatus) this.getSearchedDataFromServer();
      else this.getDataFromServer();
    },
    /**
     * 每页显示数目发生改变
     */
    handleSizeChange(size) {
      this.pageSize = size;
      this.onRefresh();
    },
    /**
     * 当前页码发生改变
     */
    handleCurrentChange(page) {
      this.pageNo = page;
      this.onRefresh();
    },
    onUpdate() {
      this.onRefresh();
    },

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
      this.onRefresh();
      this.addDialogVisible = false;
    },
    /**
    增加对话框“取消”按钮
     */
    onAddDilalogCancel() {
      this.addDialogVisible = false;
    },

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
      this.onRefresh();
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
            self.deleteFromServer(data.id);
          });
        })
        .catch(action => {});
    },
    /**
    删除数据
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
          self.deleteFromServer(school.id);
        })
        .catch(action => {});
    },
    /**
     * 从服务器上删除用户
     */
    deleteFromServer(t_school_id) {
      var self = this;
      this.$http
        .delete(BASEURL.school + "delete", {
          data: {
            id: t_school_id
          }
        })
        .then(response => {
          if (self.isNull(response.data) || response.data == 0) {
            self.$alert("抱歉，删除学院失败", "删除失败", {
              confirmButtonText: "确定"
            });
            //
          } else {
            self.$message({
              message: "恭喜，删除学院成功",
              type: "success"
            });
            self.onRefresh();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    /**
     * 从服务器获得数据
     */
    getDataFromServer() {
      var self = this;

      this.$http
        .get(BASEURL.school + "page", {
          params: {
            pageNo: self.pageNo,
            pageSize: self.pageSize
          }
        })
        .then(response => {
          if (self.isNull(response.data)) {
            //
          } else {
            self.dataViewDataPage = response.data;
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    /**
     * 从服务器获得查询数据
     */
    getSearchedDataFromServer() {
      var self = this;

      this.$http
        .get(BASEURL.school + "searchpage", {
          params: {
            searchText: self.formSearch.searchText,
            pageNo: self.pageNo,
            pageSize: self.pageSize
          }
        })
        .then(response => {
          if (self.isNull(response.data)) {
            //
          } else {
            self.dataViewDataPage = response.data;
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    }
    /**
     * 从服务器获得数据
     */,
    getSchoolDataFromServer() {
      var self = this;

      this.$http
        .get(BASEURL.school + "page", {
          params: {
            pageNo: self.pageNo,
            pageSize: self.pageSize
          }
        })
        .then(response => {
          if (self.isNull(response.data)) {
            //
          } else {
            self.dataViewDataPage = response.data;
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
