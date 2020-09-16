<template>
  <div>
    <div class="user-manager-toolbar">
      <el-form :inline="true" :model="formSearchUser" class="demo-form-inline">
        <el-form-item label="查询">
          <el-input
            placeholder="请输入工号或姓名"
            v-model="formSearchUser.searchText"
            class="input-with-select"
          >
            <el-button slot="append" icon="el-icon-search" @click="onSearchUser"></el-button>
          </el-input>
        </el-form-item>

        <el-form-item style="float: right;">
          <el-button type="primary" plain @click="onOpenDialogAddUser">增加用户</el-button>
          <el-button type="danger" plain @click="onDeleteInBatches">批量删除</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div v-if="totalCount==0">
      <h1>没有数据</h1>
    </div>
    <div v-else>
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

    <el-dialog title="添加用户" :visible.sync="dialogAddUserVisible" width="400px">
      <el-form
        label-position="right"
        label-width="80px"
        :rules="rules"
        :model="formAddUser"
        ref="formAddUser"
      >
        <el-form-item label="用户名" prop="emplName" required>
          <el-input v-model="formAddUser.emplName"></el-input>
        </el-form-item>

        <el-form-item label="工号" prop="emplNum" required>
          <el-input v-model="formAddUser.emplNum"></el-input>
        </el-form-item>
        <el-form-item label="默认密码" prop="emplPwd" required>
          <el-input v-model="formAddUser.emplPwd"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onAddUser('formAddUser')">确 定</el-button>
        <el-button @click="dialogAddUserVisible = false">取 消</el-button>

        <el-button @click="resetForm('formAddUser')">重置</el-button>
      </div>
    </el-dialog>

    <el-dialog title="编辑用户" :visible.sync="dialogEditUserVisible" width="400px">
      <el-form
        label-position="right"
        label-width="80px"
        :rules="rules"
        :model="formEditUser"
        ref="formEditUser"
      >
        <el-form-item label="用户名" prop="emplName" required>
          <el-input v-model="formEditUser.emplName"></el-input>
        </el-form-item>

        <el-form-item label="工号" prop="emplNum" required>
          <el-input v-model="formEditUser.emplNum"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onEditUser('formEditUser')">确 定</el-button>
        <el-button @click="dialogAddUserVisible = false">取 消</el-button>

        <el-button @click="resetForm('formEditUser')">重置</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import BASEURL from "@/httpconfig/api.js";
export default {
  name: "UserMananger",
  components: {},
  computed: {
    //用户总数
    totalCount: function() {
      if (this.isNull(this.userViewDataPage)) return 0;
      return this.userViewDataPage.totalCount;
    }
  },
  data() {
    return {
      formSearchUser: {
        searchText: ""
      },
      dialogAddUserVisible: false,

      formAddUser: {
        emplName: "",
        emplNum: "",
        emplPwd: "123456"
      },
      dialogEditUserVisible: false,

      formEditUser: {
        id: "",
        emplName: "",
        emplNum: ""
      },
      rules: {
        emplNum: [
          { required: true, message: "请输入工号", trigger: "blur" },
          { min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "blur" }
        ],
        emplName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "blur" }
        ],
        emplPwd: [{ required: true, message: "请输入密码", trigger: "change" }]
      },

      userViewDataPage: null,
      userSelections: [],
      pageSize: 10,
      pageNo: 1,
      isSearchStatus: false
    };
  },
  mounted() {
    this.getDataFromServer();
  },
  methods: {
    /**
     * 每页显示数目发生改变
     */
    handleSizeChange(size) {
      this.pageSize = size;
      if (isSearchStatus) this.getUserSearchedDataFromServer();
      else this.getDataFromServer();
    },
    /**
     * 当前页码发生改变
     */
    handleCurrentChange(page) {
      this.pageNo = page;
      if (isSearchStatus) this.getUserSearchedDataFromServer();
      else this.getDataFromServer();
    },

    onUserSelectionChange(val) {
      this.userSelections = val;
    },

    onSearchUser() {
      var self = this;
      if (this.isNull(this.formSearchUser.searchText)) {
        self.$alert("请输入要查询的用户工号或名称", "信息提示", {
          confirmButtonText: "确定"
        });
        return;
      }
      this.pageNo = 1;
      this.isSearchStatus = true;
      this.getUserSearchedDataFromServer();
    },
    /**
     * 打开增加用户对话框
     */
    onOpenDialogAddUser() {
      this.formAddUser.emplName = "";
      this.formAddUser.emplNum = "";
      this.formAddUser.emplPwd = "123456";
      this.dialogAddUserVisible = true;
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    onAddUser(formName) {
      this.isSearchStatus = false;
      var self = this;
      this.$refs[formName].validate(valid => {
        if (valid) {
          self.dialogAddUserVisible = false;
          self.addUserToServer(
            self.formAddUser.emplName,
            self.formAddUser.emplNum,
            self.formAddUser.emplPwd
          );
        } else {
          return false;
        }
      });
    },

    /**
     * 将用户添加到服务端
     */
    addUserToServer(empl_Name, empl_Num, empl_Pwd) {
      var self = this;
      var user = {
        emplName: empl_Name,
        emplNum: empl_Num,
        emplPwd: empl_Pwd
      };

      this.$http
        .post(BASEURL.user + "add", user)
        .then(response => {
          if (self.isNull(response.data)) {
            self.$alert("抱歉，添加用户失败", "添加失败", {
              confirmButtonText: "确定"
            });
            //
          } else {
            self.$message({
              message: "恭喜，添加用户成功",
              type: "success"
            });
            self.getDataFromServer();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    onEditUser(formName) {
      this.isSearchStatus = false;
      var self = this;
      this.$refs[formName].validate(valid => {
        if (valid) {
          self.dialogEditUserVisible = false;
          self.editUserFromServer({
            id: self.formEditUser.id,
            emplName: self.formEditUser.emplName,
            emplNum: self.formEditUser.emplNum
          });
        } else {
          return false;
        }
      });
    },
    /**
     * 编辑用户
     */
    onUserEdit(user) {
      this.isSearchStatus = false;
      if (this.isNull(user) || this.isNull(user.id)) return;
      this.formEditUser.id = user.id;
      this.formEditUser.emplName = user.emplName;
      this.formEditUser.emplNum = user.emplNum;
      this.dialogEditUserVisible = true;
    },
    editUserFromServer(user) {
      var self = this;
      this.$http
        .put(BASEURL.user + "update", user)
        .then(response => {
          if (self.isNull(response.data)) {
            self.$alert("抱歉，编辑用户失败", "编辑失败", {
              confirmButtonText: "确定"
            });
            //
          } else {
            self.$message({
              message: "恭喜，编辑用户成功",
              type: "success"
            });
            self.getDataFromServer();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    /**
     * 批量删除用户
     */
    onDeleteInBatches() {
      this.isSearchStatus = false;
      var self = this;
      if (this.isNull(this.userSelections)) {
        self.$alert("请先选择用户，然后再进行删除", "信息提示", {
          confirmButtonText: "确定"
        });
        return;
      }

      this.$confirm("是否要删除选中的用户？", "确认信息", {
        distinguishCancelAndClose: true,
        confirmButtonText: "确认删除",
        cancelButtonText: "取消"
      })
        .then(() => {
          self.userSelections.forEach(user => {
            self.deleteFromServer(user.id);
          });
        })
        .catch(action => {});
    },
    /**
     * 删除用户
     */
    onUserDelete(user) {
      this.isSearchStatus = false;
      if (this.isNull(user) || this.isNull(user.id)) return;
      var self = this;

      this.$confirm("是否要删除该用户？", "确认信息", {
        distinguishCancelAndClose: true,
        confirmButtonText: "确认删除",
        cancelButtonText: "取消"
      })
        .then(() => {
          self.deleteFromServer(user.id);
        })
        .catch(action => {});
    },
    /**
     * 从服务器上删除用户
     */
    deleteFromServer(t_user_id) {
      var self = this;
      this.$http
        .delete(BASEURL.user + "delete", {
          data: {
            id: t_user_id
          }
        })
        .then(response => {
          if (self.isNull(response.data) || response.data == 0) {
            self.$alert("抱歉，删除用户失败", "删除失败", {
              confirmButtonText: "确定"
            });
            //
          } else {
            self.$message({
              message: "恭喜，删除用户成功",
              type: "success"
            });
            self.getDataFromServer();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    /**
     * 重置密码
     */
    onUserResetPwd(user) {
      this.isSearchStatus = false;
      if (this.isNull(user) || this.isNull(user.id)) return;
      var self = this;

      this.$confirm("是否要重置该用户密码为123456？", "确认信息", {
        distinguishCancelAndClose: true,
        confirmButtonText: "确认重置",
        cancelButtonText: "取消"
      })
        .then(() => {
          self.resetPwdFromServer(user);
        })
        .catch(action => {});
    },

    /**
     * 在服务器上重置用户密码
     */
    resetPwdFromServer(user) {
      var self = this;

      this.$http
        .put(BASEURL.user + "resetpwd", user)
        .then(response => {
          if (self.isNull(response.data)) {
            self.$alert("抱歉，重置该用户密码失败", "重置用户密码失败", {
              confirmButtonText: "确定"
            });
            //
          } else {
            self.$message({
              message: "恭喜，重置该用户密码成功",
              type: "success"
            });
            self.getDataFromServer();
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    /**
     * 从服务器获得用户数据
     */
    getDataFromServer() {
      var self = this;

      var userId = this.getUserId();

      this.$http
        .get(BASEURL.user + "page", {
          params: {
            pageNo: self.pageNo,
            pageSize: self.pageSize
          }
        })
        .then(response => {
          if (self.isNull(response.data)) {
            //
          } else {
            self.userViewDataPage = response.data;
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    /**
     * 从服务器获得用户数据
     */ getUserSearchedDataFromServer() {
      var self = this;

      var userId = this.getUserId();

      this.$http
        .get(BASEURL.user + "searchpage", {
          params: {
            searchText: self.formSearchUser.searchText,
            pageNo: self.pageNo,
            pageSize: self.pageSize
          }
        })
        .then(response => {
          if (self.isNull(response.data)) {
            //
          } else {
            self.userViewDataPage = response.data;
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
