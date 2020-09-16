<template>
  <div>
    <h1>课程管理</h1>
    <el-button type="primary">创建课程</el-button>
    <el-button type="primary">加入课程</el-button>
    <el-divider></el-divider>
    <userCoursesList></userCoursesList>
  </div>
</template>
<script>
import UserCoursesList from "@/components/UserCoursesList";
export default {
  name: "CoursesList",
  components: { userCoursesList: UserCoursesList }
};
</script>
<style scoped>
</style>