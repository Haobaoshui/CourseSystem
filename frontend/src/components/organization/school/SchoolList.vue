<template>
  <div class="user-container">
    <el-table
      :data="tableData"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>

      <el-table-column label="学院名称" width="150">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.name}}</span>
        </template>
      </el-table-column>

      <el-table-column label="备注" width="150">
        <template slot-scope="scope">
          <span style="margin-left: 10px">{{ scope.row.note}}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleUpdate(scope.$index, scope.row)">编辑</el-button>

          <el-button size="mini" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <UpdateDialog
      :dialogVisible="updateDialogVisible"
      :school="school"
      @onDilalogOk="onUpdateDilalogOk"
      @onDilalogCancel="onUpdateDilalogCancel"
    ></UpdateDialog>
  </div>
</template>

<script>
import UpdateDialog from "@/components/organization/school/UpdateDialog.vue";
export default {
  name: "SchoolList",
  components: { UpdateDialog },
  props: { dataViewDataPage: Object },
  computed: {
    tableData: function() {
      if (this.isNull(this.dataViewDataPage)) return null;
      return this.dataViewDataPage.result;
    }
  },
  data() {
    return {
      updateDialogVisible: false,
      school: null,
      multipleSelection: []
    };
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.$emit("onSelectionChange", val);
    },
    handleUpdate(index, val) {
      this.school = val;
      this.updateDialogVisible = true;
    },
    handleDelete(index, school) {
      this.school = school;
      this.$emit("onDelete", school);
    },

    /**
    修改对话框“确定”按钮
     */
    onUpdateDilalogOk() {
      this.updateDialogVisible = false;
      this.$emit("onUpdate");
    },
    /**
    修改对话框“取消”按钮
     */
    onUpdateDilalogCancel() {
      this.updateDialogVisible = false;
      this.$emit("onDilalogCancel");
    }
  }
};
</script>

<style scoped>
.user-container {
  padding: 10px;
}
</style>
