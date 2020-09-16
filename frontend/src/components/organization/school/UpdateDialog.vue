<template>
  <div>
    <el-dialog
      title="修改学院"
      :visible.sync="isDialogVisible"
      :before-close="handleClose"
      width="600px"
      @open="onOpenDialog"
    >
      <el-form label-position="right" label-width="120px" :model="form" ref="form">
        <el-form-item label="学院名称" prop="name" required>
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="note">
          <el-input v-model="form.note"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="onOkBtnClick('form')">确 定</el-button>
        <el-button @click="onCancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import BASEURL from "@/httpconfig/api.js";
export default {
  name: "UpdateDialog",
  components: {},
  props: { dialogVisible: Boolean, school: Object },
  computed: {
    isDialogVisible: function() {
      return this.dialogVisible;
    },
    schoolName: function() {
      if (this.isNull(this.school)) return "";
      return this.school.name;
    }
  },
  data() {
    return {
      form: {
        name: "",
        note: ""
      }
    };
  },
  mounted() {
    this.reset(this.school);
  },
  watch: {
    project(newProject, oldValue) {
      this.reset(newProject);
    }
  },
  methods: {
    reset(data) {
      if (this.isNull(data)) return;
      this.form.name = data.name;
      this.form.note = data.note;
    },
    onOpenDialog() {
      this.reset(this.school);
    },
    handleClose() {
      this.$emit("onDilalogCancel");
    },
    onOkBtnClick(formName) {
      var self = this;
      this.$refs[formName].validate(valid => {
        if (valid) {
          self.dialogEditUserVisible = false;
          self.updateSchoolDataToServer({
            id: self.school.id,
            name: self.form.name,
            note: self.form.note
          });
        } else {
          return false;
        }
      });
    },
    onCancel() {
      this.$emit("onDilalogCancel");
    },
    onOk() {
      this.$emit("onDilalogOk");
    }
  }
};
</script>
