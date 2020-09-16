import store from '../store/index'
import BASEURL from "@/httpconfig/api.js";
export default {
  install(Vue, options) {
    /**
     * 从服务器获得数据
     */
    Vue.prototype.getSchoolPageFromServer = function (pageNo, pageSize) {
      var self = this;

      this.$http
        .get(BASEURL.school + "page", {
          params: {
            pageNo: pageNo,
            pageSize: pageSize
          }
        })
        .then(response => {
          if (self.isNull(response.data)) {
            //
          } else {
            self.schoolViewDataPage = response.data;
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }

    /**
     * 从服务器获得查询数据
     */
    Vue.prototype.getSearchedSchoolPageFromServer = function (searchText, pageNo, pageSize) {
      var self = this;

      this.$http
        .get(BASEURL.school + "searchpage", {
          params: {
            searchText: searchText,
            pageNo: pageNo,
            pageSize: pageSize
          }
        })
        .then(response => {
          if (self.isNull(response.data)) {
            //
          } else {
            self.schoolViewDataPage = response.data;
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }


    /**
     * 在服务器上增加对象
     */
    Vue.prototype.addSchoolDataToServer = function (name, note) {
      var self = this;
      var school = {
        name: name,
        note: note
      };

      this.$http
        .post(BASEURL.school + "add", school)
        .then(response => {
          if (self.isNull(response.data)) {
            self.$alert("抱歉，添加学院失败", "添加失败", {
              confirmButtonText: "确定"
            });
            //
          } else {
            self.$message({
              message: "恭喜，添加学院成功",
              type: "success"
            });
            self.onOk();
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }

    /**
     * 从服务器上删除对象
     */
    Vue.prototype.deleteSchoolFromServer = function (t_school_id) {
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
            self.onRefreshDataFromServer();
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }




    /**
     * 编辑对象
     */
    Vue.prototype.updateSchoolDataToServer = function (school) {
      var self = this;
      this.$http
        .put(BASEURL.school + "update", school)
        .then(response => {
          if (self.isNull(response.data)) {
            self.$alert("抱歉，编辑名称失败", "编辑失败", {
              confirmButtonText: "确定"
            });
            self.onCancel();
          } else {
            self.$message({
              message: "恭喜，编辑名称成功",
              type: "success"
            });
            self.onOk();
          }
        })
        .catch(function (error) {
          self.onCancel();
        });
    }

  }
}